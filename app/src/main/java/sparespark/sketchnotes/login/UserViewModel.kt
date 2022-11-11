package sparespark.sketchnotes.login

import kotlinx.coroutines.launch
import sparespark.sketchnotes.common.BaseViewModel
import sparespark.sketchnotes.data.model.LoginResult
import sparespark.sketchnotes.data.repository.UserRepository
import kotlin.coroutines.CoroutineContext

class UserViewModel(
    val userRepository: UserRepository,
    uiContext: CoroutineContext

) : BaseViewModel<LoginEvent<LoginResult>>(uiContext) {

    override fun handleEvent(event: LoginEvent<LoginResult>) {
        when (event) {
            is LoginEvent.OnStart -> getUser()
            is LoginEvent.OnAuthButtonClick -> onAuthButtonClicked()
            is LoginEvent.OnGoogleSignInResult -> onSignInResult()
        }
    }

    private fun getUser() = launch {

        println("Hello")
    }

    private fun onAuthButtonClicked() = launch {

    }

    private fun onSignInResult() = launch {

    }
}