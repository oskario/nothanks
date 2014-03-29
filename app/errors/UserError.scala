package errors

/**
 * Trait that encapsulates all error that can be send to the user.
 */
trait UserError {
  val code: String
  val message: String
}
