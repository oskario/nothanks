package errors

/**
 * Base class for all game errors.
 * 
 * @param message error message
 */
abstract class GameError(message: String) extends Throwable(message)