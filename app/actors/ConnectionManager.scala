package actors

import scala.concurrent.Await
import scala.concurrent.duration.DurationInt

import akka.actor.Actor
import akka.actor.ActorRef
import akka.actor.PoisonPill
import akka.actor.Props
import akka.actor.actorRef2Scala
import akka.pattern.ask
import akka.util.Timeout.durationToTimeout
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import play.api.libs.iteratee.Enumerator
import play.api.libs.iteratee.Iteratee
import play.api.libs.json.JsValue
import play.libs.Akka
import services.LoggingService

/**
 * Companion object for [[actors.ConnectionManager]] actor.
 */
object ConnectionManager {
  implicit val timeout = 1.second
  private val manager = Akka.system.actorOf(Props[ConnectionManager], "ConnectionManager")

  /**
   * Returns brand new WebSocket connection.
   */
  def getConnection(): scala.concurrent.Future[(Iteratee[JsValue, _], Enumerator[JsValue])] = {
    LoggingService.info(s"New user connected")
    import ConnectionManagerProtocol._

    val connectionActor = getConnectionActor()

    (connectionActor ? ConnectionActorProtocol.Initialize)(timeout).map {
      case ConnectionActorProtocol.Initialized(enumerator, channel) =>
        val iteratee = Iteratee.foreach[JsValue]({ received =>
          LoggingService.debug(s"Received from user: $received")
          connectionActor ! ConnectionActorProtocol.FromUser(received)
        }).map(_ => {
          LoggingService.info(s"Disconnected")
          connectionActor ! PoisonPill
        })

        (iteratee, enumerator)
    }
  }

  /**
   * Returns new connection actor.
   */
  private def getConnectionActor(): ActorRef = {
    import ConnectionManagerProtocol._

    val actor = (manager ? Create())(timeout).map {
      case Created(connectionActor) => connectionActor
    }

    Await.result(actor, timeout)
  }
}

/**
 * Protocol used by [[actors.ConnectionActor]] actor.
 */
object ConnectionManagerProtocol {
  case class Create()
  case class Created(actor: ActorRef)
}

/**
 * Actor that manages [[actors.ConnectionActor]] actor.
 */
class ConnectionManager extends Actor {
  import ConnectionManagerProtocol._

  def receive = {
    case Create() => {
      LoggingService.debug(s"New ConnectionActor created")
      sender ! Created(this.context.actorOf(Props[ConnectionActor]))
    }
  }
}