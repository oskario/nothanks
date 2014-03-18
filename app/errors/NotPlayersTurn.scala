package errors

import models.Player

/**
 * Exception thrown when player tries to make move but it is not his turn.
 * 
 * @param player player that the message corresponds to
 */
case class NotPlayersTurn(player: Player) extends GameError(s"It is not $player turn")