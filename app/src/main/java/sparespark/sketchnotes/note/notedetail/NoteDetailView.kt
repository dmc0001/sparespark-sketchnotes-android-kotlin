package sparespark.sketchnotes.note.notedetail

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.item_action_card_btn.*
import kotlinx.android.synthetic.main.item_action_card_btn.view.*
import kotlinx.android.synthetic.main.item_input_text.view.*
import kotlinx.android.synthetic.main.item_pick_colors.view.*
import kotlinx.android.synthetic.main.notedetail_view.*
import sparespark.sketchnotes.R
import sparespark.sketchnotes.common.makeToast
import sparespark.sketchnotes.common.setCardColor
import sparespark.sketchnotes.common.toEditable
import sparespark.sketchnotes.data.model.Color
import sparespark.sketchnotes.note.NoteCommunicator
import sparespark.sketchnotes.note.notedetail.adapter.ColorListAdapter
import sparespark.sketchnotes.note.notedetail.buildlogic.NoteDetailInjector

class NoteDetailView : Fragment() {

    private lateinit var noteViewModel: NoteViewModel
    private lateinit var communicator: NoteCommunicator
    private lateinit var tempHexCardColor: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.notedetail_view, container, false)
    }

    override fun onStart() {
        super.onStart()
        communicator = activity as NoteCommunicator
        /*
        * viewModel injector
        * */
        noteViewModel = ViewModelProvider(
            this, NoteDetailInjector(requireActivity().application).provideNoteViewModelFactory()
        )[NoteViewModel::class.java]
        noteViewModel.handleEvent(
            NoteDetailEvent.GetNote(
                NoteDetailViewArgs.fromBundle(requireArguments()).noteId
            )
        )
        /*
        * UI
        * */
        setUpCardColorsList()
        viewModelObserver()
        setUpClickListener()
    }

    private fun viewModelObserver() {
        with(noteViewModel) {
            note.observe(
                viewLifecycleOwner
            ) { note ->
                /*
                * */
                if (note != null) if (!note.hexCardColor.isNullOrEmpty()) {
                    card_note_content.setCardColor(note.hexCardColor)
                    tempHexCardColor = note.hexCardColor
                }
                /*
                *
                * */
                item_input_text.edt_note_detail_text.text = note?.contents?.toEditable()
            }
            /*
            * */
            handleEvent(NoteDetailEvent.GetCardColorsCollection)
            colorList.observe(
                viewLifecycleOwner
            ) { color ->
                item_pick_colors.colors_rec_list.adapter =
                    ColorListAdapter(color as ArrayList<Color>) {
                        tempHexCardColor = it.hexColor
                        card_note_content.setCardBackgroundColor(
                            android.graphics.Color.parseColor(
                                tempHexCardColor
                            )
                        )
                    }
            }
            /*
            * States*/
            error.observe(viewLifecycleOwner) {
                if (it.isNotBlank()) makeToast(it)
            }
            loading.observe(viewLifecycleOwner) {
                if (it) communicator.showProgress()
                else communicator.hideProgress()
            }
            deleted.observe(
                viewLifecycleOwner
            ) {
                if (it) findNavController().navigate(R.id.notelistView)
            }
            updated.observe(
                viewLifecycleOwner
            ) {
                if (it) findNavController().navigate(R.id.notelistView)
            }
        }
    }

    private fun setUpCardColorsList() {
        item_pick_colors.colors_rec_list.apply {
            val layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            setLayoutManager(layoutManager)
        }
    }

    private fun setUpClickListener() {
        /*
        * Update
        * */

        item_update_card.setOnClickListener {
            if (!TextUtils.isEmpty(item_input_text.edt_note_detail_text.text.toString()) &&
                item_input_text.edt_note_detail_text.text.toString().length <= 200
            ) noteViewModel.handleEvent(
                NoteDetailEvent.OnUpdateClick(
                    contents = item_input_text.edt_note_detail_text.text.toString(),
                    hexCardColor = if (this::tempHexCardColor.isInitialized) tempHexCardColor else ""
                )
            )
        }

        /*
        * Delete
        * */
        item_delete_card.setOnClickListener {
            noteViewModel.handleEvent(NoteDetailEvent.OnDeleteClick)
        }
    }

}