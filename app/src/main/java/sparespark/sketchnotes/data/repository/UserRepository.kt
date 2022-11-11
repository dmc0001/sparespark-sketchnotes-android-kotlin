package sparespark.sketchnotes.data.repository

import sparespark.sketchnotes.common.DataResult
import sparespark.sketchnotes.data.model.User

interface UserRepository {

    suspend fun getCurrentUser(): DataResult<Exception, User?>

    suspend fun signOutCurrentUser(): DataResult<Exception, Unit>

}