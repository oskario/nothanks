package models

import scala.util.Try
import errors.UserAlreadyExists
import scala.util.Failure
import scala.util.Success

object User {

  val users = scala.collection.mutable.Buffer[User]()

  /**
   * Registers given user.
   */
  def add(email: String, password: String): Try[User] = {
    val alreadyRegistered = users.find(_.email == email)

    alreadyRegistered match {
      case None => {
        val user = User(email, password)
        users += user
        Success(user)
      }
      case Some(u) => Failure(UserAlreadyExists(u))
    }
  }
}

case class User(email: String, password: String)