package com.aware.app.testx.presenter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.aware.app.testx.R
import kotlinx.android.synthetic.main.view_list_item_symptom.view.*
import java.util.*

class SymptomAdapter(private val context: Context) : RecyclerView.Adapter<SymptomAdapter.SymptomHolder>() {

    // get array of symptoms description from resource file
    private val symptoms = context.resources.obtainTypedArray(R.array.symptomsList)

    // array for storing self-evaluation values
    var result = IntArray(symptoms.length())

    inner class SymptomHolder(view: View) : RecyclerView.ViewHolder(view) {
        val symptomText: TextView = view.symptomName
        val group: RadioGroup = view.symptomRate
        var rate0: RadioButton = view.findViewById(R.id.rate0)
        var rate1: RadioButton = view.findViewById(R.id.rate1)
        var rate2: RadioButton = view.findViewById(R.id.rate2)
        var rate3: RadioButton = view.findViewById(R.id.rate3)
        var rate4: RadioButton = view.findViewById(R.id.rate4)
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)

        // set default assessment values as -1
        Arrays.fill(result, -1)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SymptomHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.view_list_item_symptom, parent, false)

        return SymptomHolder(view)
    }

    override fun onBindViewHolder(holder: SymptomHolder, position: Int) {
        // verify that radiogroup has correct checked parameter
        holder.group.setOnCheckedChangeListener(null)
        holder.group.clearCheck()
        if (result[position] != -1) holder.group.check(holder.group.getChildAt(result[position]).id)

        // label views from resource file
        val symptom = context.resources.getStringArray(symptoms.getResourceId(position, -1))
        holder.symptomText.text = symptom[0]
        holder.rate0.text = symptom[1]
        holder.rate1.text = symptom[2]
        holder.rate2.text = symptom[3]
        holder.rate3.text = symptom[4]
        holder.rate4.text = symptom[5]

        // record assessment selection
        holder.group.setOnCheckedChangeListener { group, checkedId ->
                result[position] = group.indexOfChild(group.findViewById(checkedId))
        }
    }

    override fun getItemCount(): Int {
        return symptoms.length()
    }

    fun getName(position: Int): String {
        return context.resources.getStringArray(symptoms.getResourceId(position, -1))[0]
                .replace(" ".toRegex(), "_").toLowerCase(Locale.getDefault())
    }
}
