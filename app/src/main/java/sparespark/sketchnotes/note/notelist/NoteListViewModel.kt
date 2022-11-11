package sparespark.sketchnotes.note.notelist

import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.launch
import sparespark.sketchnotes.common.BaseViewModel
import sparespark.sketchnotes.common.DataResult
import sparespark.sketchnotes.data.model.Note
import sparespark.sketchnotes.data.repository.NoteRepository
import kotlin.coroutines.CoroutineContext


/*
* Viewmodel in the middle handle both behavior and state, state being data and behavior represent
* action we do when some event happens.
*
* Uses observer pattern to communicate with the view, and ask model for data.
*
*
*
* */

class NoteListViewModel(
    private val noteRepo: NoteRepository, uiContext: CoroutineContext
) : BaseViewModel<NoteListEvent>(uiContext) {

    private val noteListState = MutableLiveData<List<Note>?>()
    val noteList: MutableLiveData<List<Note>?> get() = noteListState

    override fun handleEvent(event: NoteListEvent) {
        when (event) {
            is NoteListEvent.GetNotes -> getNotes()
        }
    }

    private fun getNotes() = launch {
        when (val notesResult = noteRepo.getNotes()) {
            is DataResult.Error ->
                errorState.value = "Error while loading notes!"
            is DataResult.Value -> {
                noteListState.value = notesResult.value
                loadingState.value = false
            }
        }
    }
}