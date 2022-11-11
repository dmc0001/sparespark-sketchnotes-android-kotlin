package sparespark.sketchnotes.note.notelist.buildlogic

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import sparespark.sketchnotes.data.db.RoomNoteDatabase
import sparespark.sketchnotes.data.implementation.NoteRepositoryImpl
import sparespark.sketchnotes.data.provider.UserPreferenceProvider
import sparespark.sketchnotes.data.provider.UserPreferenceProviderImpl
import sparespark.sketchnotes.data.repository.NoteRepository

class NoteListInjector(application: Application) : AndroidViewModel(application) {

    private fun getUserPreferenceProvider(): UserPreferenceProvider {
        return UserPreferenceProviderImpl(getApplication())
    }

    private fun getNoteRepository(): NoteRepository {
        return NoteRepositoryImpl(
            local = RoomNoteDatabase.getInstance(getApplication()).roomNoteDao(),
            getUserPreferenceProvider()
        )
    }

    fun provideNoteListViewModelFactory(): NoteListViewModelFactory =
        NoteListViewModelFactory(
            getNoteRepository()
        )
}