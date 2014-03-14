package errors

import models.Player

/**
 * Exception thrown when player cannot bid (e.g. does not have any chips left).
 * 
 * @param player player that the message corresponds to
 */
case class PlayerCantBid(player: Player) extends GameError(s"Player $player can't bid")