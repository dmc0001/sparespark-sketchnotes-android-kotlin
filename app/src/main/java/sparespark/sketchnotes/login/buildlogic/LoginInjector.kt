package sparespark.sketchnotes.login.buildlogic

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import sparespark.sketchnotes.data.implementation.UserRepositoryImpl
import sparespark.sketchnotes.data.repository.UserRepository

class LoginInjector(application: Application) : AndroidViewModel(application) {

    init {

    }

    private fun getUserRepository(): UserRepository {
        return UserRepositoryImpl()
    }

    fun provideUserViewModelFactory(): UserViewModelFactory =
        UserViewModelFactory(
            getUserRepository()
        )
}