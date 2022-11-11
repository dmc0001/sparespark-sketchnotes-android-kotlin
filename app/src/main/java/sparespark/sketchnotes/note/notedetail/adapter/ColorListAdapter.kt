package sparespark.sketchnotes.note.notedetail.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import sparespark.sketchnotes.R
import sparespark.sketchnotes.common.setCardColor
import sparespark.sketchnotes.data.model.Color


class ColorListAdapter(
    private val itemList: ArrayList<Color>,
    private val listener: (Color) -> Unit,
) : RecyclerView.Adapter<ColorListAdapter.ColorViewHolder>() {

    override fun onBindViewHolder(holder: ColorViewHolder, position: Int) {
        val colorItem = itemList[position]
        holder.card.setCardColor(colorItem.hexColor)
        holder.itemView.setOnClickListener {
            listener(colorItem)
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ColorViewHolder {
        return ColorViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_color, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    class ColorViewHolder(root: View) : RecyclerView.ViewHolder(root) {
        var card: CardView = root.findViewById(R.id.card_view)
    }

}