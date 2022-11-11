package sparespark.sketchnotes.login

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import sparespark.sketchnotes.R
import sparespark.sketchnotes.login.buildlogic.LoginInjector
import sparespark.sketchnotes.note.NoteActivity

class LoginView : Fragment() {
    private lateinit var viewModel: UserViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.login_view, container, false)
    }

    override fun onStart() {
        super.onStart()
        /*
        * init login injector class
        * viewmodel injection..
        *
        * */
        viewModel = ViewModelProvider(
            this, LoginInjector(requireActivity().application).provideUserViewModelFactory()
        )[UserViewModel::class.java]

        /*
        * UI
        * */
        setUpClickListeners()
        viewModelObserver()

        viewModel.handleEvent(LoginEvent.OnStart)

    }

    private fun viewModelObserver() {


    }

    private fun setUpClickListeners() {

    }

    private fun startListActivity() = requireActivity().startActivity(
        Intent(
            activity, NoteActivity::class.java
        )
    )
}