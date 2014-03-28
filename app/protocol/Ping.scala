package protocol

object Ping {
  val cmd = "ping"
}

case class Ping(override val id: Option[String] = None) extends BaseMessage(Ping.cmd, id = id)