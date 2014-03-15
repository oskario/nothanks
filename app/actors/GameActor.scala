package actors

import models.Player
import errors.GameError
import scala.util.Failure
import play.libs.Akka
import scala.util.Success
import akka.actor.ActorRef
import akka.actor.Props
import models.Game
import akka.actor.Actor

/**
 * Companion object for the main [[models.Game]] actor.
 */
object GameActor {
  /**
   * Creates new game actor.
   *
   * @param game underlying game
   */
  def apply(game: Game): ActorRef =
    Akka.system.actorOf(Props(new GameActor(game)))

}

/**
 * Protocol used by the main [[actors.GameActor]] actor.
 */
object GameActorProtocol {
  case object Start
  case class Join(player: Player, password: Option[String] = None)
  case class Leave(player: Player)

  case object Started
  case class Error(error: GameError)
}

class GameActor(val game: Game) extends Actor {

  /**
   * Receives actor's messages.
   */
  def receive = {
    case GameActorProtocol.Start =>
      val started = game.start()

      started match {
        case Success(started) =>
          if (started) {
            sender ! GameActorProtocol.Started
          } else {
            // Game already running, indeed started
            sender ! GameActorProtocol.Started
          }
        case Failure(error: GameError) =>
          sender ! GameActorProtocol.Error(error)
        case Failure(error) =>
          throw new Exception(s"Critical error in game actor: $error")
      }
    case GameActorProtocol.Join(newPlayer, password) =>
      println(s"Player $newPlayer joined ${game.name}")
    case GameActorProtocol.Leave(playerLeaving) =>
      println(s"Player $playerLeaving left ${game.name}")
  }

}