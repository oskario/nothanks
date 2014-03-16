package errors

/**
 * Exception thrown when current action requires a running game but the game has not yet been started.
 */
case class GameNotStarted() extends GameError(s"Game not started")