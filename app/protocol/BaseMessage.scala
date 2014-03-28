package protocol

import play.api.libs.json.JsString
import play.api.libs.json.JsSuccess
import play.api.libs.json.JsValue
import play.api.libs.json.Json
import play.api.libs.json.Json.toJsFieldJsValueWrapper
import play.api.libs.json.Reads
import play.api.libs.json.Writes
import play.api.libs.json.JsObject

/**
 * Companion object for [[protocol.BaseMessage]].
 */
object BaseMessage {
  def apply(body: JsValue): BaseMessage = {
    val cmd = (body \ "cmd").as[String]
    val data = (body \ "data").asOpt[JsObject]
    val id = (body \ "id").asOpt[String]

    new BaseMessage(cmd, data, id)
  }
}

/**
 * Base class for all messages.
 */
class BaseMessage(val cmd: String, val data: Option[JsObject] = None, val id: Option[String] = None) {
  implicit val writes = new Writes[BaseMessage] {
    def writes(response: BaseMessage): JsValue = {
      Json.obj(
        "cmd" -> response.cmd,
        "data" -> response.data,
        "id" -> response.id)
    }
  }

  implicit val reads = new Reads[BaseMessage] {
    def reads(js: JsValue) = {
      JsSuccess(BaseMessage(js))
    }
  }

  implicit def toJson(): JsValue = {
    var result = Json.obj(
      "cmd" -> cmd)

    if (data.isDefined)
      result = result + ("data" -> data.get)
      
    if (id.isDefined)
      result = result + ("id" -> JsString(id.get))

    result
  }
}