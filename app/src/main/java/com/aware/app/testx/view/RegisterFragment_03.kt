package com.aware.app.testx.view


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.aware.app.testx.R
import com.aware.app.testx.model.User
import com.aware.app.testx.presenter.SymptomAdapter
import java.util.*
import kotlin.collections.ArrayList

class RegisterFragment_03 : Fragment() {

    private lateinit var adapter : SymptomAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_register_03, container, false)

        // Set up symptoms list
        adapter = SymptomAdapter(context!!)
        val list = view.findViewById<RecyclerView>(R.id.symptoms_recycler_view)
        list.layoutManager = LinearLayoutManager(context)
        list.adapter = adapter

        return view
    }

    fun getSymptoms(): ArrayList<User.Symptom>? {
        var symptoms: ArrayList<User.Symptom>? = ArrayList()
        for (i in 0 until adapter.itemCount) {
            val checked = adapter.result[i]
            if (checked != -1) {
                val name = adapter.getName(i)
                symptoms?.add(User.Symptom(name, checked))
            } else {
                symptoms = null
                break
            }
        }

        return symptoms
    }
}
