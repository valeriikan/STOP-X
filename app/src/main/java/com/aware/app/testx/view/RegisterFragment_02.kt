package com.aware.app.testx.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.aware.app.testx.R
import com.aware.app.testx.model.User.Medication
import com.aware.app.testx.presenter.MedicationAdapter
import com.aware.app.testx.presenter.MedicationDialog
import com.aware.app.testx.presenter.MedicationDialog.MedicationDialogListener
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.fragment_register_02.*
import kotlin.collections.ArrayList

class RegisterFragment_02 : Fragment() {

    val medications: ArrayList<Medication> = ArrayList()     // List of Medications

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_register_02, container, false)

        // Set up medications list
        val adapter = MedicationAdapter(medications)
        val list = view.findViewById<RecyclerView>(R.id.medication_recycler_view)
        list.layoutManager = LinearLayoutManager(context)
        list.adapter = adapter

        val fab = view.findViewById<FloatingActionButton>(R.id.medication_fab)
        fab.setOnClickListener{
            var dialog: MedicationDialog? = MedicationDialog()
            dialog?.listener = object : MedicationDialogListener {
                override fun onDismiss(medication: Medication) {
                    // update medications list, refresh ui
                    medications.add(medication)
                    adapter.notifyDataSetChanged()

                    // destroy dialog for further reuse
                    dialog = null
                }
            }
            dialog?.show(fragmentManager!!, null)
        }

        return  view
    }
}
