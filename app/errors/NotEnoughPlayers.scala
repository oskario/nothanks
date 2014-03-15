package errors

/**
 * Exception thrown when there are not enough players to start the game.
 */
case class NotEnoughPlayers() extends GameError(s"Not enough players")