package sparespark.sketchnotes.note.notedetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.launch
import sparespark.sketchnotes.common.BaseViewModel
import sparespark.sketchnotes.common.DataResult
import sparespark.sketchnotes.common.SingleLiveData
import sparespark.sketchnotes.common.getCalendarTime
import sparespark.sketchnotes.data.model.Color
import sparespark.sketchnotes.data.model.Note
import sparespark.sketchnotes.data.repository.NoteRepository
import sparespark.sketchnotes.note.notelist.adapter.ABOUT_APP_POS_ID
import kotlin.coroutines.CoroutineContext

class NoteViewModel(
    val noteRepo: NoteRepository, uiContext: CoroutineContext
) : BaseViewModel<NoteDetailEvent>(uiContext) {

    private val noteState = MutableLiveData<Note?>()
    val note: MutableLiveData<Note?> get() = noteState

    private val updatedState = SingleLiveData<Boolean>()
    val updated: LiveData<Boolean> get() = updatedState

    private val deletedState = SingleLiveData<Boolean>()
    val deleted: LiveData<Boolean> get() = deletedState

    private val colorListState = MutableLiveData<List<Color>?>()
    val colorList: MutableLiveData<List<Color>?> get() = colorListState

    override fun handleEvent(event: NoteDetailEvent) {
        when (event) {
            /*
            * Note
            * */
            is NoteDetailEvent.GetNote -> getNote(event.noteId)
            is NoteDetailEvent.OnUpdateClick -> updateNote(event.contents, event.hexCardColor)
            is NoteDetailEvent.OnDeleteClick -> deleteNote()
            /*
            * Other
            * */
            is NoteDetailEvent.GetCardColorsCollection -> getCardColorsCollection()

        }
    }

    /*
    * Note
    *
    * */
    private fun getNote(noteId: String) = launch {
        if (noteId == "") newNote()
        else {
            loadingState.value = true
            when (val noteResult = noteRepo.getNoteById(noteId)) {
                is DataResult.Error -> errorState.value = "GET_NOTE_ERROR"
                is DataResult.Value -> {
                    noteState.value = noteResult.value
                    loadingState.value = false
                }
            }
        }

    }

    private fun updateNote(contents: String, hexCardColor: String) = launch {
        loadingState.value = true
        val updateResult = noteRepo.updateNote(
            note.value!!.copy(
                contents = contents, hexCardColor = hexCardColor
            )
        )
        when (updateResult) {
            is DataResult.Value -> {
                updatedState.value = true
                loadingState.value = false
            }

            is DataResult.Error -> {
                updatedState.value = false
                errorState.value = "Couldn't update note!"
            }
        }
    }

    private fun deleteNote() = launch {
        if (note.value?.position != ABOUT_APP_POS_ID // 1-info note item
            && note.value?.contents != ""  // 2-new note
        ) {
            loadingState.value = true
            when (noteRepo.deleteNote(note.value!!)) {
                is DataResult.Value -> {
                    deletedState.value = true
                    loadingState.value = false
                }
                is DataResult.Error -> {
                    deletedState.value = false
                    errorState.value = "Couldn't delete note!!"
                }
            }
        }
    }

    private fun newNote() {
        noteState.value = Note(getCalendarTime(), "", "", null)
    }

    /*
    * Other
    * */
    private fun getCardColorsCollection() = launch {
        when (val colorsResult = noteRepo.getCardColorsCollection()) {
            is DataResult.Value -> colorListState.value = colorsResult.value
            is DataResult.Error -> errorState.value = "error!"
        }
    }

}