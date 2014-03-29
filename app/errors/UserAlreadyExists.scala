package errors


/**
 * Exception thrown when given user is already registered.
 */
case class UserAlreadyExists(user: db.User) extends GameError(s"User ${user.email} already exists") with UserError {
  val code = protocol.Codes.userAlreadyExists
  val message = "User already exists"
}