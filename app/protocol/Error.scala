package protocol

import play.api.libs.json.Json

object Error {
  val cmd = "error"
}

class Error(message: String, code: String, override val id: Option[String] = None)
  extends BaseMessage(Error.cmd, Some(Json.obj("message" -> message, "code" -> code)), id = id)