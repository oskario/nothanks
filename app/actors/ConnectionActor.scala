package actors

import akka.actor.Actor
import akka.actor.actorRef2Scala
import play.api.libs.iteratee.Concurrent
import play.api.libs.iteratee.Concurrent.Channel
import play.api.libs.iteratee.Enumerator
import play.api.libs.json.JsValue
import services.LoggingService
import models.User
import scala.util.{Success, Failure}
import play.api.libs.concurrent.Execution.Implicits.defaultContext

/**
 * Companion object for the [[actors.ConnectionActor]] actor.
 */
object ConnectionActor {
}

/**
 * Protocol used by the [[actors.ConnectionActor]] actor.
 */
object ConnectionActorProtocol {

  case object Initialize

  case class Initialized(enumerator: Enumerator[JsValue], channel: Channel[JsValue])

  case class FromUser(message: JsValue)

}

/**
 * Actor that manages single WebSocket connection with the user.
 */
class ConnectionActor extends Actor {

  import ConnectionActorProtocol._

  val (enumerator, channel) = Concurrent.broadcast[JsValue]

  def receive = {
    case Initialize =>
      sender ! ConnectionActorProtocol.Initialized(enumerator, channel)

    case FromUser(message) =>
      process(message)
  }

  /**
   * Process message from the user.
   */
  def process(message: JsValue) = {
    LoggingService.debug(s"Processing: $message")
    val baseMessage = protocol.BaseMessage(message)

    import protocol._
    baseMessage.cmd match {
      case Ping.cmd =>
        processPingMessage(baseMessage.asInstanceOf[Ping])
      case CreateUser.cmd =>
        processCreateUserMessage(baseMessage)
      case LogIn.cmd =>
        processLogInMessage(baseMessage)
      case x@_ =>
        processUnknownMessage(baseMessage)
    }
  }

  /**
   * Process ping message.
   */
  def processPingMessage(message: protocol.Ping) = {
    LoggingService.debug(s"Received ping command")
    sendToUser(protocol.Pong(message.id))
  }

  /**
   * Processes create user message.
   * @param message create user message
   */
  def processCreateUserMessage(message: protocol.CreateUser) = {
    LoggingService.debug(s"Received createUser command (${message.email} / ${message.password})")
    User.add(message.email, message.password).map {
      case Success(user) =>
        sendToUser(protocol.UserCreated(message.email, message.password, message.id))
      case Failure(exception) =>
        LoggingService.error(s"Exception occured while creating new acount: ${exception}")
        sendToUser(protocol.Error(exception, message.id))
    }
  }

  /**
   * Processes log in message.
   * @param message log in message
   */
  def processLogInMessage(message: protocol.LogIn) = {
    LoggingService.debug(s"Processing login command (${message.email} / ${message.password})")
    User.login(message.email, message.password).map {
      case Success(user) =>
        sendToUser(protocol.Confirmation(message.id))
      case Failure(exception) =>
        LoggingService.error(s"Exception occured while logging in: ${exception}")
        sendToUser(protocol.Error(exception, message.id))
    }
  }

  /**
   * Process message with unknown (unsupported) `cmd` field.
   * @param message unknown message
   */
  def processUnknownMessage(message: protocol.BaseMessage) = {
    LoggingService.info(s"Received unknown message: ${message.toJson()}")
    sendToUser(protocol.UnknownCommand(message.id))
  }

  /**
   * Sends given message directly to user.
   */
  def sendToUser(message: protocol.BaseMessage) = {
    LoggingService.debug(s"Sending to user: ${message.toJson()}")
    channel.push(message.toJson())
  }
}