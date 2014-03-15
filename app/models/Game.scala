package models

import scala.util.Failure
import scala.util.Success
import scala.util.Try

import errors.NotEnoughPlayers
import errors.TooManyPlayers
import services.TimestampService

/**
 * Single game.
 *
 * @param name game's name
 * @param host player that is hosting the game
 * @param minPlayers minimum number of players to start the game
 * @param maxPlayers maximum number of players in the game
 * @param password game's password
 */
class Game(val name: String, val host: Player, val minPlayers: Integer, val maxPlayers: Integer) {

  /**
   *  Players that are involved in the game.
   *
   *  Note: In the beginning there is only one player - the host.
   */
  val players = scala.collection.mutable.Buffer[Player](host)

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
    //TODO: Add password protection
    false
    //    password match {
    //      case Some(_) => true
    //      case None => false
    //    }
  }

  /**
   * Starts the game.
   *
   * Return a Try with a boolean value that indicates weather game has started correctly (true)
   * or game is already running (false). If anything goes wrong try results in a Failure with
   * corresponding error message.
   */
  def start(): Try[Boolean] = {
    try {
      if (players.size < minPlayers)
        Failure(NotEnoughPlayers())
      else {
        startTime match {
          case Some(_) => Success(false)
          case None =>
            startTime = Some(TimestampService.now())
            Success(true)
        }
      }
    }
  }

  /**
   * Joins player to the game.
   * 
   * Returns a Try with a boolean value that indicates weather player has successfully joined (true)
   * or has previously joined the game (false). If anything goes wrong Try results in a Failure with
   * corresponding error message.
   */
  def join(newPlayer: Player): Try[Boolean] = {
    try {
      if (players.size + 1 <= maxPlayers) {
        if (players.find(_.name == newPlayer.name).isDefined) {
          // Player already joined the game
          Success(false)
        } else {
          players += newPlayer
          Success(true)
        }
      } else {
        Failure(TooManyPlayers())
      }
    }
  }
}