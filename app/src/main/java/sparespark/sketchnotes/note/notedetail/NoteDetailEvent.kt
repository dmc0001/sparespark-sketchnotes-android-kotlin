package sparespark.sketchnotes.note.notedetail

sealed class NoteDetailEvent {

    /*
    * Note
    * */
    data class GetNote(val noteId: String) : NoteDetailEvent()
    object OnDeleteClick : NoteDetailEvent()
    data class OnUpdateClick(
        val contents: String, val hexCardColor: String
    ) : NoteDetailEvent()

    /*
    * Other
    * */
    object GetCardColorsCollection : NoteDetailEvent()

}
