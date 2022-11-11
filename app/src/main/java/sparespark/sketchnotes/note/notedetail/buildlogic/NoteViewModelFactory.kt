package sparespark.sketchnotes.note.notedetail.buildlogic

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kotlinx.coroutines.Dispatchers
import sparespark.sketchnotes.data.repository.NoteRepository
import sparespark.sketchnotes.note.notedetail.NoteViewModel

class NoteViewModelFactory(
    private val noteRepo: NoteRepository
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {

        return NoteViewModel(noteRepo, Dispatchers.Main) as T
    }

}