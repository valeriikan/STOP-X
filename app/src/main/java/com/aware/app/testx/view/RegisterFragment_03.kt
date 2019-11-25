package com.aware.app.testx.view


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import com.aware.app.testx.R
import com.aware.app.testx.model.User
import com.aware.app.testx.presenter.SymptomAdapter
import java.util.*
import kotlin.collections.ArrayList

class RegisterFragment_03 : Fragment() {

    lateinit var adapter: SymptomAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_register_03, container, false)

        adapter = SymptomAdapter(context)

        val listView = view.findViewById<ListView>(R.id.symptoms_list_view)
        listView.adapter = adapter

        return view
    }

    fun getSymptoms(): ArrayList<User.Symptom>? {
        var symptoms: ArrayList<User.Symptom>? = ArrayList()

        for (i in 0 until adapter.count) {
            val checked = adapter.getChecked(i)
            if (checked != -1) {
                val name = adapter.getItem(i)[0].replace(" ".toRegex(), "_").toLowerCase(Locale.getDefault())
                symptoms?.add(User.Symptom(name, checked))
            } else {
                symptoms = null
                break
            }
        }

        return symptoms
    }
}
