package protocol

object LogIn {
  val cmd = "logIn"

  implicit def baseMessage2LogIn(base: BaseMessage): LogIn = {
    val email: String = (base.data.get \ "email").as[String]
    val password: String = (base.data.get \ "password").as[String]
    
    LogIn(email, password, base.id)
  }
}

case class LogIn(email: String, password: String, override val id: Option[String] = None) extends BaseMessage(LogIn.cmd, id = id)