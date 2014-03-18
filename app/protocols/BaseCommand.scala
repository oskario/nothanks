package protocols

import play.api.libs.json.JsValue
import play.api.libs.json.Json
import play.api.libs.json.Json.toJsFieldJsValueWrapper
import play.api.libs.json.Writes
import play.api.libs.json.Reads

trait BaseCommand {
  val cmdName: String
  val cmdData: JsValue

  implicit val writes = new Writes[BaseCommand] {
    def writes(cmd: BaseCommand): JsValue = {
      Json.obj(
        "cmd" -> cmd.cmdName,
        "data" -> cmd.cmdData)
    }
  }
}