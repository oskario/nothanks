package models

import errors.EmailDoesNotExist
import errors.InvalidPassword
import errors.UserAlreadyExists
import play.api.libs.concurrent.Execution.Implicits._
import scala.concurrent.Future
import scala.util.Failure
import scala.util.Success
import scala.util.Try

object User {

  val users = scala.collection.mutable.Buffer[User]()

  /**
   * Registers given user.
   */
  def add(email: String, password: String): Future[Try[db.User]] = {
    db.User.findByEmail(email).flatMap {
      case Some(user) => Future(Failure(UserAlreadyExists(user)))
      case None => db.User.add(db.User(email, password))
    }
  }

  /**
   * Logs user in.
   */
  def login(email: String, password: String): Future[Try[db.User]] = {
    db.User.findByEmail(email).flatMap {
      case Some(user) => {
        if (user.password == password)
          Future(Success(user))
        else
          Future(Failure(InvalidPassword(email)))
      }
      case None => Future(Failure(EmailDoesNotExist(email)))
    }
  }
}

case class User(email: String, password: String)