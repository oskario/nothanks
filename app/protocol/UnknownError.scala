package protocol


object UnknownError {
  val message = "Unknown error occured, please contact support"
  val code = Codes.unknownError
}

case class UnknownError(override val id: Option[String] = None)
  extends Error(UnknownError.message, UnknownError.code, id)