package protocol

import errors.UserError
import play.api.libs.json.Json

object Error {
  val cmd = "error"

  def apply(exception: Throwable, id: Option[String] = None): Error = {
    exception match {
      case ue: UserError =>
        new Error(ue.message, ue.code, id)
      case ue @ _ =>
        new UnknownError(id)
    }
  }
}

class Error(message: String, code: String, override val id: Option[String] = None)
  extends BaseMessage(Error.cmd, Some(Json.obj("message" -> message, "code" -> code)), id = id)