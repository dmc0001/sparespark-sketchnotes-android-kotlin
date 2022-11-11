package sparespark.sketchnotes.note

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import kotlinx.android.synthetic.main.activity_note.*
import sparespark.sketchnotes.R
import sparespark.sketchnotes.common.visible

const val TAG = "NoteActivityTAG++"

class NoteActivity : AppCompatActivity(), NoteCommunicator {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragment_container) as NavHostFragment
        val navController =
            navHostFragment.navController

        /*
        *  getting started!
        *
        * */

    }

    override fun showProgress() {
        progress_circular.visible(true)
    }

    override fun hideProgress() {
        progress_circular.visible(false)
    }
}