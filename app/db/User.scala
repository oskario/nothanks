package db

import play.api.Play.current
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import play.api.libs.json._
import play.modules.reactivemongo.ReactiveMongoPlugin
import play.modules.reactivemongo.json.collection.JSONCollection
import scala.concurrent.Future
import scala.util.{Success, Failure, Try}

object User {
  implicit val userFormat = Json.format[User]

  def db: reactivemongo.api.DB = ReactiveMongoPlugin.db

  def collection: JSONCollection = db.collection[JSONCollection]("users")

  /**
   * Find users by email.
   * @param email email to search for
   * @return Some(user) if user found, otherwise None
   */
  def findByEmail(email: String): Future[Option[User]] = {
    collection
      .find(Json.obj("email" -> email))
      .one[User]
  }

  /**
   * Adds new user to the database.
   * @param user user to add
   * @return Future containing last error
   */
  def add(user: User): Future[Try[User]] = {
    collection.insert(user).flatMap {
      lastError =>
        if (!lastError.ok)
          Future(Failure(new Throwable(lastError.message)))
        else
          Future(Success(user))
    }
  }
}

/**
 * Database representation model for the user.
 */
case class User(email: String, password: String)

