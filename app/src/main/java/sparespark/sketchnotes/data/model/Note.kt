package sparespark.sketchnotes.data.model


data class Note(
    val position: Int?,
    val creationDate: String?,
    val contents: String?,
    val hexCardColor: String?,
    val creator: User?
) {
    constructor(
        creationDate: String,
        contents: String,
        hexCardColor: String?,
        creator: User?
    ) : this(
        null, creationDate, contents, hexCardColor, creator
    )
}

