package sparespark.sketchnotes.data.implementation

import sparespark.sketchnotes.common.*
import sparespark.sketchnotes.data.db.dao.NoteDao
import sparespark.sketchnotes.data.model.Color
import sparespark.sketchnotes.data.model.Note
import sparespark.sketchnotes.data.provider.UserPreferenceProvider
import sparespark.sketchnotes.data.repository.NoteRepository

class NoteRepositoryImpl(
    val local: NoteDao, private val userPreferenceProvider: UserPreferenceProvider

) : NoteRepository {

    /*
    * Note
    *
    * */
    override suspend fun getNotes(): DataResult<Exception, List<Note>> {
        return getLocalNotes()
    }

    override suspend fun getNoteById(noteId: String): DataResult<Exception, Note> {
        return getLocalNote(noteId)
    }

    override suspend fun updateNote(note: Note): DataResult<Exception, Unit> {
        return updateLocalNote(note)
    }

    override suspend fun deleteNote(note: Note): DataResult<Exception, Unit> {
        return deleteLocalNote(note)
    }

    /*
    *
    * */
    override suspend fun getCardColorsCollection(): DataResult<Exception, List<Color>> =
        DataResult.build {
            cardColorsCollection()
        }


    /*
    * Local Implementation...
    *
    * */

    private suspend fun getLocalNotes(): DataResult<Exception, List<Note>> = DataResult.build {
        /*
        * Add local info data items
        * 1- Add a new note
        * 2- About app
        * 3- SignIn status
        * */
        if (!userPreferenceProvider.isLocalDataItemsAdded()) {
            addLocalStaticDataItems(local)
            userPreferenceProvider.updateLocalDataItems()
        }
        local.getNotes().toNoteListFromRoomNote()
    }

    private suspend fun getLocalNote(id: String): DataResult<Exception, Note> = DataResult.build {
        local.getNoteById(id).toNote
    }

    private suspend fun updateLocalNote(note: Note): DataResult<Exception, Unit> =
        DataResult.build {
            local.insertOrUpdateNote(note.toRoomNote)
            Unit
        }

    private suspend fun deleteLocalNote(note: Note): DataResult<Exception, Unit> =
        DataResult.build {
            local.deleteNote(note.toRoomNote)
            Unit
        }
}