package protocol

import play.api.libs.json.Json

object UserCreated {
  val cmd = "userCreated"
}

case class UserCreated(email: String, password: String, override val id: Option[String] = None)
  extends BaseMessage(UserCreated.cmd, Some(Json.obj("email" -> email, "password" -> password)), id = id)