package sparespark.sketchnotes.note.notelist

sealed class NoteListEvent {
    object GetNotes : NoteListEvent()
}