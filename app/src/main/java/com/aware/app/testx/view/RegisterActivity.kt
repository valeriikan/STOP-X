package com.aware.app.testx.view

import android.content.ContentValues
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.aware.Aware
import com.aware.Aware_Preferences
import com.aware.app.testx.R
import com.aware.app.testx.model.Provider
import com.aware.app.testx.model.User
import com.aware.app.testx.presenter.SectionsAdapter
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_register.*
import java.util.ArrayList

class RegisterActivity : AppCompatActivity(R.layout.activity_register) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()

        // retrieve user from consent activity
        val user = intent.getSerializableExtra("user") as User

        // initialize fragments
        val fragments: ArrayList<Fragment> = ArrayList()
        val profileFragment = RegisterFragment_01()
        val medicationsFragment = RegisterFragment_02()
        val symptomsFragment = RegisterFragment_03()
        fragments.add(profileFragment)
        fragments.add(medicationsFragment)
        fragments.add(symptomsFragment)

        // set up pager adapter
        val adapter = SectionsAdapter(supportFragmentManager, this,
                fragments, register_layout_dots, register_btn_previous, register_btn_next)
        register_view_pager.adapter = adapter
        register_view_pager.addOnPageChangeListener(adapter.listener)
        adapter.updateDots(0)

        register_btn_previous.setOnClickListener { register_view_pager.currentItem -= 1 }

        register_btn_next.setOnClickListener {
            if (register_view_pager.currentItem < fragments.size - 1) {
                register_view_pager.currentItem += 1

            } else {
                // run the app as pd user
                // TODO: open app as pd
                if (profileFragment.getYear() != -1 &&
                        symptomsFragment.getSymptoms() != null){
                    user.diagnosis_year = profileFragment.getYear()
                    user.dbs = profileFragment.getDbs()
                    user.medications = medicationsFragment.medications
                    user.symptoms = symptomsFragment.getSymptoms()

                    val values = ContentValues()
                    values.put(Provider.AWAREColumns.TIMESTAMP, System.currentTimeMillis())
                    values.put(Provider.AWAREColumns.DEVICE_ID, Aware.getSetting(applicationContext, Aware_Preferences.DEVICE_ID))
                    values.put(Provider.Users_Table.DATA, Gson().toJson(user))
                    contentResolver.insert(Provider.Users_Table.CONTENT_URI, values)

                    Log.d("STOP_TAG", "RESULT: " + Gson().toJson(user))
                    Toast.makeText(applicationContext, "Inserted as PD", Toast.LENGTH_SHORT).show()
                    
                } else {
                    Toast.makeText(applicationContext, "Please fill at least the diagnosis year and all symptoms",
                            Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}
