package sparespark.sketchnotes.note.notelist.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_info.view.*
import kotlinx.android.synthetic.main.item_note.view.*
import sparespark.sketchnotes.R
import sparespark.sketchnotes.common.setCardColor
import sparespark.sketchnotes.data.model.Note

const val ADD_NEW_NOTE_POS_ID = 0
const val ABOUT_APP_POS_ID = 1
const val SIGN_POS_ID = 2

const val ITEM_INFO_STATE = 0
const val ITEM_NOTE_STATE = 1


class NoteListAdapter(
    private val listener: (Note) -> Unit
) : ListAdapter<Note, RecyclerView.ViewHolder>(NoteDiffUtilCallback()) {

    override fun getItemViewType(position: Int): Int {
        if (position == ADD_NEW_NOTE_POS_ID || position == SIGN_POS_ID) {
            return ITEM_INFO_STATE
        }
        return ITEM_NOTE_STATE
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view: View


        if (viewType == ITEM_INFO_STATE) {
            view = inflater.inflate(R.layout.item_info, parent, false);
            return InfoViewHolder(view)
        }

        view = inflater.inflate(R.layout.item_note, parent, false);
        return NoteViewHolder(view)

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val note = getItem(position)

        if (position == ADD_NEW_NOTE_POS_ID || position == SIGN_POS_ID) {
            val infoViewHolder: InfoViewHolder = holder as InfoViewHolder
            if (position == ADD_NEW_NOTE_POS_ID) infoViewHolder.icon.visibility = View.VISIBLE
            if (position == SIGN_POS_ID) {
                infoViewHolder.info.visibility = View.VISIBLE
                infoViewHolder.info.text = note.contents
            }

            if (note.hexCardColor?.isNotEmpty() == true)
                infoViewHolder.cardView.setCardColor(note.hexCardColor)
            infoViewHolder.itemView.setOnClickListener {
                listener(note)
            }

        } else {
            val noteViewHolder: NoteViewHolder = holder as NoteViewHolder

            noteViewHolder.content.text = note.contents
            noteViewHolder.date.text = note.creationDate

            if (note.hexCardColor?.isNotEmpty() == true)
                noteViewHolder.cardview.setCardColor(note.hexCardColor)

            noteViewHolder.itemView.setOnClickListener {
                listener(note)

            }

        }
    }

    /*
    * ViewHolders...
    *
    * */
    class NoteViewHolder(root: View) : RecyclerView.ViewHolder(root) {
        var content: TextView = root.lbl_content
        var date: TextView = root.lbl_date
        var cardview: CardView = root.content_card_view
    }

    class InfoViewHolder(root: View) : RecyclerView.ViewHolder(root) {
        var info: TextView = root.lbl_info
        var icon: ImageView = root.icon
        var cardView: CardView = root.info_card_view
    }
}