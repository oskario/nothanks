package errors

/**
 * Exception thrown when given password does not match provided email.
 */
case class InvalidPassword(email: String) extends GameError(s"Password for email ${email} does not match")