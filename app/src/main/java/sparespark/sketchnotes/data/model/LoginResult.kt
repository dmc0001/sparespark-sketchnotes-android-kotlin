package sparespark.sketchnotes.data.model

/**
 * Wrapper class for data received in LoginActivity's onActivityResult()
 * function
 */
data class LoginResult(val requestCode: Int, val userToken: String?)