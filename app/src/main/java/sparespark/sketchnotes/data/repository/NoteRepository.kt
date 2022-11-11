package sparespark.sketchnotes.data.repository

import sparespark.sketchnotes.common.DataResult
import sparespark.sketchnotes.data.model.Color
import sparespark.sketchnotes.data.model.Note

interface NoteRepository {
    /*
    * Note...
    * */
    suspend fun getNotes(): DataResult<Exception, List<Note>>
    suspend fun getNoteById(noteId: String): DataResult<Exception, Note>
    suspend fun updateNote(note: Note): DataResult<Exception, Unit>
    suspend fun deleteNote(note: Note): DataResult<Exception, Unit>
    /*
    * Other...
    * */
    suspend fun getCardColorsCollection(): DataResult<Exception, List<Color>>

}