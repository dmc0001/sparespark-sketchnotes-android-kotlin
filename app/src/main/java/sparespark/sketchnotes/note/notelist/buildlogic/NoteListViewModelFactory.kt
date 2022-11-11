package sparespark.sketchnotes.note.notelist.buildlogic

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kotlinx.coroutines.Dispatchers
import sparespark.sketchnotes.data.repository.NoteRepository
import sparespark.sketchnotes.note.notelist.NoteListViewModel

class NoteListViewModelFactory(
    private val noteRepo: NoteRepository
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return NoteListViewModel(noteRepo, Dispatchers.Main) as T
    }

}