package protocol

import play.api.libs.json.Json

object Confirmation {
  val cmd = "confirmation"
}

case class Confirmation(override val id: Option[String] = None)
  extends BaseMessage(Confirmation.cmd, id = id)