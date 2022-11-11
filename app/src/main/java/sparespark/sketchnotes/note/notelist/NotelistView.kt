package sparespark.sketchnotes.note.notelist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import kotlinx.android.synthetic.main.notelist_view.*
import sparespark.sketchnotes.R
import sparespark.sketchnotes.common.makeToast
import sparespark.sketchnotes.data.model.Note
import sparespark.sketchnotes.note.NoteActivity
import sparespark.sketchnotes.note.NoteCommunicator
import sparespark.sketchnotes.note.notelist.adapter.ADD_NEW_NOTE_POS_ID
import sparespark.sketchnotes.note.notelist.adapter.NoteListAdapter
import sparespark.sketchnotes.note.notelist.adapter.SIGN_POS_ID
import sparespark.sketchnotes.note.notelist.buildlogic.NoteListInjector

class NotelistView : Fragment() {

    private lateinit var viewModel: NoteListViewModel
    private lateinit var communicator: NoteCommunicator
    private lateinit var listAdapter: NoteListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.notelist_view, container, false)
    }

    override fun onStart() {
        super.onStart()
        communicator = activity as NoteCommunicator
        communicator.showProgress()
        /*
        * viewModel injection...
        *
        * */
        viewModel = ViewModelProvider(
            this, NoteListInjector(requireActivity().application).provideNoteListViewModelFactory()
        )[NoteListViewModel::class.java]
        viewModel.handleEvent(NoteListEvent.GetNotes)
        /*
        * UI
        * */
        setUpNoteListAdapter()
        setUpClickListeners()
        viewModelObserver()

    }

    override fun onDestroyView() {
        super.onDestroyView()
        rec_note_list.adapter = null
    }

    private fun viewModelObserver() {
        with(viewModel) {
            noteList.observe(viewLifecycleOwner) { noteList ->
                listAdapter.submitList(noteList)
            }
            /*
            * states
            * */
            error.observe(viewLifecycleOwner) {
                if (it.isNotBlank()) makeToast(it)
            }
            loading.observe(viewLifecycleOwner) {
                if (it) communicator.showProgress()
                else communicator.hideProgress()
            }
        }
    }

    private fun setUpNoteListAdapter() {
        listAdapter = NoteListAdapter { note ->
            setUpNoteItemOnClicked(note = note)
        }
        rec_note_list.apply {
            setHasFixedSize(true)
            val sGridLayoutManager = StaggeredGridLayoutManager(
                2, StaggeredGridLayoutManager.VERTICAL
            )
            layoutManager = sGridLayoutManager
            adapter = listAdapter
        }
    }

    private fun setUpClickListeners() {
        /*
        * Whatever navigation view we are in, back-pressed in notelist view finish activity.
        * */
        val callback = object : OnBackPressedCallback(
            true
        ) {
            override fun handleOnBackPressed() {
                (activity as NoteActivity).finish()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)
    }

    private fun setUpNoteItemOnClicked(note: Note) {
        when (note.position) {
            ADD_NEW_NOTE_POS_ID -> startNoteDetailWithArgs(noteId = "")
            SIGN_POS_ID -> navigateToLoginView()
            else -> note.creationDate?.let { startNoteDetailWithArgs(noteId = it) }
        }
    }

    private fun startNoteDetailWithArgs(noteId: String) = findNavController().navigate(
        NotelistViewDirections.toNoteDetailView(
            noteId = noteId
        )
    )

    private fun navigateToLoginView() = findNavController().navigate(
        NotelistViewDirections.toLoginActivity()
    )
}