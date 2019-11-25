package com.aware.app.testx.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.EditText
import com.aware.app.testx.R

class RegisterFragment_01 : Fragment() {

    lateinit var etYear: EditText
    lateinit var cbDbs: CheckBox

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_register_01, container, false)

        etYear = view.findViewById(R.id.et_year)
        etYear.clearFocus()
        cbDbs = view.findViewById(R.id.cb_dbs)

        return view
    }

    fun getYear(): Int {
        return if (etYear.text.toString() == "") -1
            else Integer.valueOf(etYear.text.toString())
    }

    fun getDbs(): Boolean {
        return cbDbs.isChecked
    }
}
