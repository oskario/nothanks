package models

import akka.actor.Actor
import akka.actor.ActorRef
import akka.actor.Props
import play.api.Play.current
import play.api.libs.concurrent.Akka

/**
 * Companion object for the main [[models.Game]] actor.
 */
object Game {
  /**
   * Creates new game actor.
   *
   * @param name name of the game
   * @param host player that is hosting the game
   * @param password (optional) game's password
   */
  def newGameActor(name: String, host: Player, password: Option[String] = None): ActorRef =
    Akka.system.actorOf(Props(new GameActor(name, host, password)))

}

/**
 * Protocol used by the main [[models.Game]] actor.
 */
object GameProtocol {
  case object Start
  case class Join(player: Player, password: Option[String] = None)
  case class Leave(player: Player)
}

/**
 * Actor that manages a single game.
 *
 * @param name game's name
 * @param host player that is hosting the game
 * @param password game's password
 */
class GameActor(val name: String, val host: Player, val password: Option[String]) extends Actor {

  /**
   *  Players that are involved in the game.
   *
   *  Note: In the beginning there is only one player - the host.
   */
  val players = scala.collection.mutable.Seq[Player](host)

  /** Start time of the game (or None, if not yet started). */
  var startTime: Option[Long] = None

  /** Returns true if the game is already running. */
  def isRunning(): Boolean = {
    startTime match {
      case Some(_) => true
      case None => false
    }
  }
  
  /**
   * Is true if game is password protected, otherwise false.
   */
  val isPasswordProtected: Boolean = {
    password match {
      case Some(_) => true
      case None => false
    }
  }

  /**
   * Receives actor's messages.
   */
  def receive = {
    case GameProtocol.Start =>
      println("Game started")
    case GameProtocol.Join(newPlayer, password) =>
      println(s"Player $newPlayer joined $name with password: $password")
    case GameProtocol.Leave(playerLeaving) =>
      println(s"Player $playerLeaving left $name")
  }
}