package com.aware.app.testx.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.aware.app.testx.R;
import com.aware.app.testx.model.User;
import com.aware.app.testx.presenter.SectionsAdapter;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class RegisterActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private LinearLayout dotsLayout;
    private Button btnPrevious, btnNext;

    private RegisterFragment_01 fragment_profile;
    private RegisterFragment_02 fragment_medications;
    private RegisterFragment_03 fragment_symptoms;
    private List<Fragment> fragments;
    private SectionsAdapter adapter;

    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getSupportActionBar().hide();

        // retrieve user from consent activity
        user = (User) getIntent().getSerializableExtra("user");

        // initialize ui
        viewPager = findViewById(R.id.register_view_pager);
        dotsLayout = findViewById(R.id.register_layout_dots);
        btnPrevious = findViewById(R.id.register_btn_previous);
        btnNext = findViewById(R.id.register_btn_next);

        // initialize fragments
        fragments = new ArrayList<>();
        fragment_profile = new RegisterFragment_01();
        fragment_medications = new RegisterFragment_02(user);
        fragment_symptoms = new RegisterFragment_03(user);
        fragments.add(fragment_profile);
        fragments.add(fragment_medications);
        fragments.add(fragment_symptoms);

        // set up pager adapter
        adapter = new SectionsAdapter(getSupportFragmentManager(), this,
                                        fragments, dotsLayout, btnPrevious, btnNext);
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(adapter.listener);
        adapter.updateDots(0);

        btnPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewPager.setCurrentItem(viewPager.getCurrentItem() - 1);
            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int current = viewPager.getCurrentItem();
                if (current < fragments.size()-1){
                    viewPager.setCurrentItem(current + 1);
                } else {
                    // TODO:
                    if (!fragment_profile.etYear.getText().toString().equals("") &&
                        fragment_symptoms.getSymptoms() != null) {
                        user.setDiagnosed_time(Integer.valueOf(fragment_profile.etYear.getText().toString()));
                        user.setDbs(fragment_profile.cbDbs.isChecked());
                        user.setMedications(fragment_medications.getMedications());
                        user.setSymptoms(fragment_symptoms.getSymptoms());
                        Log.d("STOP_TAG", "RESULT: " + new Gson().toJson(user));
                    } else {
                        Toast.makeText(getApplicationContext(), "Please fill at least the diagnosis year and all symptoms",
                                Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}
