package actors

import scala.concurrent.duration.DurationInt

import akka.actor.Actor
import akka.actor.ActorRef
import akka.actor.PoisonPill
import akka.actor.Props
import akka.actor.actorRef2Scala
import akka.pattern.ask
import akka.util.Timeout
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import play.api.libs.iteratee.Concurrent
import play.api.libs.iteratee.Concurrent.Channel
import play.api.libs.iteratee.Done
import play.api.libs.iteratee.Enumerator
import play.api.libs.iteratee.Input
import play.api.libs.iteratee.Iteratee
import play.api.libs.json.JsValue
import play.libs.Akka
import protocols.user.HostGameCommand
import protocols.user.UserProtocol
import services.LoggingService

/**
 * Companion object for the [[actors.PlayerActor]] class.
 */
object PlayerActor {

  /**
   * Creates new [[actors.PlayerActor]] with a given name.
   *
   * @param name player name
   */
  def apply(name: String): ActorRef =
    Akka.system.actorOf(Props(new PlayerActor(name)))

  def getConnection(user: String): scala.concurrent.Future[(Iteratee[JsValue, _], Enumerator[JsValue])] = {
    implicit val timeout = Timeout(10.seconds)
    val playerActor = PlayerActor(user)

    LoggingService.info(user, "Connected")

    (playerActor ? PlayerActorProtocol.Initialize())(timeout).map {
      case PlayerActorProtocol.Initialized(enumerator, channel) =>

        // Create an Iteratee to consume the input
        val iteratee = Iteratee.foreach[JsValue]({ received =>
          LoggingService.debug(user, s"Received: $received")
          playerActor ! PlayerActorProtocol.Received(received)

        }).map(_ => {
          LoggingService.info(user, "Disconnected")
          playerActor ! PoisonPill
        })

        (iteratee, enumerator)

      case _ =>
        // Something went wrong
        val iteratee = Done[JsValue, Unit]((), Input.EOF)
        val enumerator = Enumerator[JsValue]().andThen(Enumerator.enumInput(Input.EOF))
        playerActor ! PoisonPill
        (iteratee, enumerator)
    }

  }
}

/**
 * Protocol used by the [[actors.PlayerActor]] actor.
 */
object PlayerActorProtocol {

  case class Initialize()

  case class Received(message: JsValue)

  case class Initialized(enumerator: Enumerator[JsValue], channel: Channel[JsValue])
}

/**
 * Actor that represents single game user.
 *
 * It handles all communication between user and internal system.
 */
class PlayerActor(name: String) extends Actor {
  import protocols.user.UserProtocol
  import protocols.user.HostGameCommand

  val (enumerator, channel) = Concurrent.broadcast[JsValue]

  def receive = {
    case PlayerActorProtocol.Initialize() =>
      sender ! PlayerActorProtocol.Initialized(enumerator, channel)

    case PlayerActorProtocol.Received(msg) =>
      onReceive(msg)
  }

  /**
   * Called every time a massage has been received.
   *
   * @param msg received message
   */
  def onReceive(msg: JsValue) = {
    UserProtocol.getCmdName(msg) match {
      case protocols.user.HostGameCommand.name =>
        onHostCommandReceived(msg.as[HostGameCommand])
    }
  }

  /**
   * Called every time a host game command has been received.
   */
  def onHostCommandReceived(command: HostGameCommand) = {
    LoggingService.debug(name, s"Requested host: ${command.name}")
  }
}