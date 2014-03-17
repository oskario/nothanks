package protocols.user

import play.api.libs.json.JsValue
import play.api.libs.json.Json
import play.api.libs.json.Reads
import protocols.BaseCommand
import play.api.libs.json.JsSuccess

object HostGameCommand {
  val name: String = "host"

  implicit val reads = new Reads[HostGameCommand] {
    def reads(js: JsValue) = {
      val name = (js \ "data" \ "name").as[String]
      JsSuccess(HostGameCommand(name))
    }
  }
}

case class HostGameCommand(name: String) extends BaseCommand {
  val cmdName = HostGameCommand.name
  val cmdData = Json.obj("name" -> name)
}