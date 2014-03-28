package protocol

import play.api.libs.json.Json

object UnknownCommand {
  val message = "Unknown command"
  val code = "BE001"
}

case class UnknownCommand(override val id: Option[String] = None)
  extends Error(UnknownCommand.message, UnknownCommand.code, id)