package protocol

/**
 * All error codes that exist in the protocol.
 */
object Codes {
  val unknownError: String = "E000"
  val userAlreadyExists: String = "E001"
  val emailDoesNotExist: String = "E002"
  val invalidPassword: String = "E003"
}
