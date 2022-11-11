package sparespark.sketchnotes.data.implementation

import sparespark.sketchnotes.common.DataResult
import sparespark.sketchnotes.data.model.User
import sparespark.sketchnotes.data.repository.UserRepository

class UserRepositoryImpl(

) : UserRepository {

    override suspend fun getCurrentUser(): DataResult<Exception, User?> {
        TODO("Not yet implemented")
    }

    override suspend fun signOutCurrentUser(): DataResult<Exception, Unit> {
        TODO("Not yet implemented")
    }
}