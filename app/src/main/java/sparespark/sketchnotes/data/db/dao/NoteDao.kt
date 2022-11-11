package sparespark.sketchnotes.data.db.dao

import androidx.room.*
import sparespark.sketchnotes.data.db.RoomNote


@Dao
interface NoteDao {
    @Query("SELECT * FROM notes ORDER BY creation_date")
    suspend fun getNotes(): List<RoomNote>

    @Query("SELECT * FROM notes WHERE creation_date = :creationDate")
    suspend fun getNoteById(creationDate: String): RoomNote

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdateNote(note: RoomNote): Long

    @Delete
    suspend fun deleteNote(note: RoomNote)

}
