package com.aware.app.testx.presenter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.aware.app.testx.R
import com.aware.app.testx.model.User
import kotlinx.android.synthetic.main.view_list_item_medication.view.*
import java.util.*

class MedicationAdapter(private val list: ArrayList<*>) : RecyclerView.Adapter<MedicationAdapter.ItemHolder>() {

    private lateinit var recyclerViewId: String

    inner class ItemHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name: TextView = view.consentMedicationName
        val remove: ImageView = view.consentMedicationRemove
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        recyclerViewId = recyclerView.resources.getResourceEntryName(recyclerView.id)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.view_list_item_medication, parent, false)

        return ItemHolder(view)
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {

        // Given adapter is used for two recycler list views, fill it correspondingly based on id
        var text = ""
        if (recyclerViewId == "dialog_intake_list") {
            val item = list[position] as User.Medication.IntakeTime
            text =  String.format("%02d:%02d", item.hour, item.minute)
        } else if (recyclerViewId == "medication_recycler_view") {
            val item = list[position] as User.Medication
            text = item.name!!
        }

        holder.name.text = text
        holder.remove.setOnClickListener {
            list.removeAt(position)
            notifyDataSetChanged()
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }
}
