package sparespark.sketchnotes.common

import sparespark.sketchnotes.data.db.RoomNote
import sparespark.sketchnotes.data.model.Note
import sparespark.sketchnotes.data.model.User


internal val Note.safeGetUid: String
    get() = this.creator?.uid ?: ""

internal val Note.toRoomNote: RoomNote
    get() = RoomNote(
        this.position,
        this.creationDate.toString(),
        this.contents.toString(),
        this.hexCardColor.toString(),
        this.safeGetUid
    )

internal val RoomNote.toNote: Note
    get() = Note(
        this.position,
        this.creationDate,
        this.contents,
        this.hexCardColor,
        User(this.creatorId)
    )

internal fun List<RoomNote>.toNoteListFromRoomNote(): List<Note> = this.flatMap {
    listOf(it.toNote)
}