package errors

import models.User

/**
 * Exception thrown when given user is already registered.
 */
case class UserAlreadyExists(user: User) extends GameError(s"User ${user.email} already exists")