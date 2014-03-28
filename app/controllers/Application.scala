package controllers

import play.api._
import play.api.mvc._
import play.api.libs.json.JsValue
import actors.PlayerActor
import actors.ConnectionManager

object Application extends Controller {

  def index = Action {
    Ok(views.html.index("Your new application is ready."))
  }
  
  def start(user: String) = WebSocket.async[JsValue] { request =>
  	PlayerActor.getConnection(user)
  }
  
  def ws = WebSocket.async[JsValue] { request => 
    ConnectionManager.getConnection  
  }
}