package services

import akka.actor.Actor
import akka.actor.Props
import akka.actor.actorRef2Scala
import play.api.Logger
import play.api.Play.current
import play.api.libs.concurrent.Akka

/**
 * Logging service.
 *
 * Offers dynamic, extensive and configurable logging system.
 */
object LoggingService {
  import services.LoggingServiceProtocol._

  private val actor = Akka.system.actorOf(Props[LoggingService])

  def error(message: String) = {
    actor ! LogError(message)
  }

  def error(sender: String, message: String) = {
    actor ! LogError(s"[$sender] $message")
  }

  def info(message: String) = {
    actor ! LogInfo(message)
  }

  def info(sender: String, message: String) = {
  }

  def debug(message: String) = {
    actor ! LogDebug(message)
  }
}

/**
 * Protocol used by the logging service.
 */
object LoggingServiceProtocol {
  case class LogError(message: String)
  case class LogInfo(message: String)
  case class LogDebug(message: String)
}

/**
 * Logging service actor.
 *
 * Defines custom logging procedures (e.g. broadcasting messages to other log provider, saving to file, etc.).
 */
class LoggingService extends Actor {
  import services.LoggingServiceProtocol._

  val logger = Logger

  def receive = {
    case LogError(msg) => {
      logger.error(msg)
    }

    case LogInfo(msg) => {
      logger.info(msg)
    }

    case LogDebug(msg) => {
      logger.debug(msg)
    }
  }
}