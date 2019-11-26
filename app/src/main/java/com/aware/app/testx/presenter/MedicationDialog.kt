package com.aware.app.testx.presenter

import android.app.TimePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.aware.app.testx.R
import com.aware.app.testx.model.User.Medication
import com.aware.app.testx.model.User.Medication.IntakeTime
import kotlinx.android.synthetic.main.dialog_add_medication.*
import java.util.*
import kotlin.collections.ArrayList

class MedicationDialog : DialogFragment() {

    private val intakes: ArrayList<IntakeTime> = ArrayList() // List of intake times
    private val adapter = MedicationAdapter(intakes)         // List adapter
    var listener: MedicationDialogListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.AppTheme_FullScreenDialog)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return activity!!.layoutInflater.inflate(R.layout.dialog_add_medication, null)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dialog_toolbar.setNavigationOnClickListener { dismiss() }
        dialog_toolbar.title = "Add a medication"
        dialog_toolbar.inflateMenu(R.menu.medication_dialog)
        dialog_toolbar.setOnMenuItemClickListener {

            if (dialog_name.text.toString() != "" &&
                dialog_dosage.text.toString() != "" &&
                (dialog_booster.isChecked || intakes.isNotEmpty())) {

                val medication = Medication()
                medication.name = dialog_name.text.toString()
                medication.dosage = dialog_dosage.text.toString()
                medication.intakeTime = intakes
                medication.booster = dialog_booster.isChecked
                medication.notes = dialog_notes.text.toString()
                listener!!.onDismiss(medication)

                dismiss()
            } else {
                Toast.makeText(context, "Please fill the required fields", Toast.LENGTH_SHORT).show()
            }
            true
        }

        dialog_intake_btn.setOnClickListener(add_time)
        dialog_intake_tv.setOnClickListener(add_time)

        dialog_intake_list.layoutManager = LinearLayoutManager(context)
        dialog_intake_list.adapter = adapter
    }

    override fun onStart() {
        super.onStart()
        // sets up dialog in full screen mode
        dialog?.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        dialog?.window?.setWindowAnimations(R.style.AppTheme_Slide)
    }

    private val add_time = View.OnClickListener {
        val picker = TimePickerDialog(context, TimePickerDialog.OnTimeSetListener { _, hourOfDay, minute ->
            // update intake list, refresh ui
            intakes.add(IntakeTime(hourOfDay, minute))
            adapter.notifyDataSetChanged()
        }, Calendar.getInstance()[Calendar.HOUR_OF_DAY], Calendar.getInstance()[Calendar.MINUTE], true)
        picker.setTitle("Intake time")
        picker.show()
    }

    // Listener that sends data to fragment when dialog is dismissed
    interface MedicationDialogListener {
        fun onDismiss(medication: Medication)
    }
}
