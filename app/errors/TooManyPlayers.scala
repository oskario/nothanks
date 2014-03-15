package errors

/**
 * Exception thrown when there are to many players in the game. 
 */
case class TooManyPlayers() extends GameError(s"Too manyp layers")