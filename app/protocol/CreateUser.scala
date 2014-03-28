package protocol

object CreateUser {
  val cmd = "createUser"

  implicit def baseMessage2CreateUser(base: BaseMessage): CreateUser = {
    val email: String = (base.data.get \ "email").as[String]
    val password: String = (base.data.get \ "password").as[String]
    
    CreateUser(email, password, base.id)
  }
}

case class CreateUser(email: String, password: String, override val id: Option[String] = None) extends BaseMessage(CreateUser.cmd, id = id)