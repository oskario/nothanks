package protocols.user

import play.api.libs.json.JsValue

object UserProtocol {
  
  /**
   * Gets command name from input JSON value.
   * 
   * @param command received JSON value
   */
  def getCmdName(command: JsValue): String = (command \ "cmd").as[String]
}