package errors

/**
 * Exception thrown when given email does not exist in the database.
 */
case class EmailDoesNotExist(email: String) extends GameError(s"Email ${email} does not exist in the database")