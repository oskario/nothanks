package models

import scala.util.Try
import scala.util.Failure
import scala.util.Success
import errors.UserAlreadyExists
import errors.InvalidPassword
import errors.EmailDoesNotExist

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
  
  /**
   * Logs user in.
   */
  def login(email: String, password: String): Try[User] = {
    val userWithEmail = users.find(_.email == email)

    userWithEmail match {
      case Some(u) => {
        if(u.password == password)
          Success(u)
        else
          Failure(InvalidPassword(email))
      }
      case None => Failure(EmailDoesNotExist(email))
    }
  }
}

case class User(email: String, password: String)