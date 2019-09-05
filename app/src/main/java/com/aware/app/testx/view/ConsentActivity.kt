package com.aware.app.testx.view

import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.aware.Aware
import com.aware.Aware_Preferences
import com.aware.app.testx.R
import com.aware.app.testx.model.Provider
import com.aware.app.testx.model.User
import com.aware.app.testx.presenter.SectionsAdapter
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_consent.*
import kotlinx.android.synthetic.main.fragment_consent_03.*
import java.util.ArrayList

class ConsentActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_consent)
        supportActionBar?.hide()

        // initialize fragments
        val fragments: ArrayList<Fragment> = ArrayList()
        val detailsFragment = ConsentFragment_03()
        fragments.add(ConsentFragment_01())
        fragments.add(ConsentFragment_02())
        fragments.add(detailsFragment)

        // set up pager adapter
        val adapter = SectionsAdapter(supportFragmentManager, this,
                fragments, consent_layout_dots, consent_btn_previous, consent_btn_next)
        consent_view_pager.adapter = adapter
        consent_view_pager.addOnPageChangeListener(adapter.listener)
        adapter.updateDots(0)

        consent_btn_previous.setOnClickListener { consent_view_pager.currentItem -= 1 }

        consent_btn_next.setOnClickListener {
            if (consent_view_pager.currentItem < fragments.size - 1) {
                consent_view_pager.currentItem += 1

            } else {
                if (!detailsFragment.cb_consent.isChecked) {
                    // run demo mode if consent is declined
                    // TODO: show warning, then open demo mode without db call


                } else if (!detailsFragment.cb_diagnosis.isChecked) {
                    // run app as non-pd user
                    // TODO: call db
                    val data = Gson().toJson(User(detailsFragment.et_username.text.toString(),
                            Integer.valueOf(detailsFragment.et_age.text.toString()),
                            detailsFragment.cb_diagnosis.isChecked))

                    val values = ContentValues()
                    values.put(Provider.AWAREColumns.TIMESTAMP, System.currentTimeMillis())
                    values.put(Provider.AWAREColumns.DEVICE_ID, Aware.getSetting(applicationContext, Aware_Preferences.DEVICE_ID))
                    values.put(Provider.Users_Table.DATA, data)
                    contentResolver.insert(Provider.Users_Table.CONTENT_URI, values)
                    Toast.makeText(applicationContext, "Inserted", Toast.LENGTH_SHORT).show()

                } else {
                    // open pd registration profile
                    // TODO: run med reg
                    val user = User(detailsFragment.et_username.text.toString(),
                            Integer.valueOf(detailsFragment.et_age.text.toString()),
                            detailsFragment.cb_diagnosis.isChecked)

                    val registration = Intent(applicationContext, RegisterActivity::class.java)
                    registration.putExtra("user", user)
                    startActivity(registration)
                    finish()
                }
            }
        }
    }
}
