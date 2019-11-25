package com.aware.app.testx.view


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import android.widget.TextView
import com.aware.app.testx.R
import com.aware.app.testx.model.User
import com.aware.app.testx.presenter.MedicationAdapter
import com.aware.app.testx.presenter.MedicationDialog
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.util.ArrayList

class RegisterFragment_02 : Fragment() {

    lateinit var medications: ArrayList<User.Medication>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_register_02, container, false)

        // User data related class elements
        medications = ArrayList<User.Medication>()      // for User class
        val medicationsList = ArrayList<String>()       // entries of list view in dialog ui
        val adapter = MedicationAdapter(context!!, medicationsList, medications)

        // UI elements
        val fab = view.findViewById<FloatingActionButton>(R.id.medication_fab)
        fab.setOnClickListener{
            var dialog: MedicationDialog? = MedicationDialog()
            dialog?.setListener {medication ->
                // on dialog dismiss:
                // update ui
                medicationsList.add(medication.name!!)
                adapter.notifyDataSetChanged()

                // update user class medications list
                medications.add(medication)

                // destroy dialog for further reuse
                dialog = null
            }
            dialog?.show(fragmentManager!!, null)
        }

        val listView = view.findViewById<ListView>(R.id.medication_list_view)
        listView!!.adapter = adapter

        return  view
    }
}
