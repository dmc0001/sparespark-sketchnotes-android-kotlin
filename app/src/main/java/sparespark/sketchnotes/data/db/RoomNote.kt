package sparespark.sketchnotes.data.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey


@Entity(
    tableName = "notes",
    indices = [Index("creation_date")]
)
data class RoomNote(
    @ColumnInfo(name = "position")
    val position: Int?,

    @PrimaryKey
    @ColumnInfo(name = "creation_date")
    val creationDate: String,

    @ColumnInfo(name = "contents")
    val contents: String,

    @ColumnInfo(name = "hex_card_color")
    var hexCardColor: String,

    @ColumnInfo(name = "creator_id")
    val creatorId: String
)
