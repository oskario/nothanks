package controllers

import play.api._
import play.api.libs.json.JsValue
import play.api.mvc._
import play.api.libs.json.Json
import scala.util.Try
import java.util.NoSuchElementException
import scala.util.Failure
import scala.util.Success
import services.LoggingService
import models.User

object Auth extends Controller {

  def create = Action { implicit request =>

    val email = fromPost("email")
    val password = fromPost("password")

    (email, password) match {
      case (Some(e), Some(p)) =>
        LoggingService.info(s"New user request: $e / $p")

        User.add(e.as[String], p.as[String]) match {
          case Success(user) => {
            Ok(Json.obj(
              "status" -> true,
              "email" -> e,
              "password" -> p))
          }
          case Failure(exception) => {
            Results.BadRequest(Json.obj(
              "status" -> false,
              "message" -> exception.getMessage()))
          }
        }

      case _ =>
        Results.BadRequest(Json.obj(
          "status" -> false))
    }
  }
  
  def login = Action { implicit request =>

    val email = fromPost("email")
    val password = fromPost("password")
    
  	(email, password) match {
      case (Some(e), Some(p)) =>
      	User.login(e.as[String], p.as[String]) match {
      	  case Success(user) => {
            Ok(Json.obj(
              "status" -> true,
              "email" -> e,
              "password" -> p))
          }
          case Failure(exception) => {
            Results.BadRequest(Json.obj(
              "status" -> false,
              "message" -> "Invalid email or password"))
          }
      	}
      case _ =>
        Results.BadRequest(Json.obj(
          "status" -> false,
          "message" -> "Invalid email or password"))
    }
  }

  def fromPost(key: String)(implicit request: Request[AnyContent]): Option[JsValue] = {
    request.body.asJson.map(_.\(key))
  }
}