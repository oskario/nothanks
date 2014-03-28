package protocol

object Pong {
  val cmd = "pong"
}

case class Pong(override val id: Option[String] = None) extends BaseMessage(Pong.cmd, id = id)