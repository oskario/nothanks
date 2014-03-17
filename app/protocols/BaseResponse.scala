package protocols

import play.api.libs.json.JsValue
import play.api.libs.json.Json
import play.api.libs.json.Json.toJsFieldJsValueWrapper
import play.api.libs.json.Writes

abstract class BaseResponse(val responseName: String, val data: JsValue) {
  implicit val writes = new Writes[BaseResponse] {
    def writes(response: BaseResponse): JsValue = {
      Json.obj(
        "cmd" -> response.responseName,
        "data" -> response.data)
    }
  }
}