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

import com.aware.app.testx.R;
import com.aware.app.testx.model.Test;
import com.aware.app.testx.model.User;
import com.aware.app.testx.presenter.SectionsAdapter;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class RegisterActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private LinearLayout dotsLayout;
    private Button btnPrevious, btnNext;

    private RegisterFragment_01 fragment_medications;
    private RegisterFragment_02 fragment_updrs;
    private List<Fragment> fragments;
    private SectionsAdapter adapter;

    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getSupportActionBar().hide();

        Intent intent = getIntent();
        user = (User) intent.getSerializableExtra("user");
        Log.d("STOP_TAG", "1: " + new Gson().toJson(user));

//        ArrayList<User.Medication> medications = new ArrayList<>();


//        ArrayList<User.Medication> medications1 = new ArrayList<>();
//        medications1.add(user.new Medication("11","22", "33","44"));
//        medications1.add(user.new Medication("55","66", "77","88"));
//        user.setMedications(medications1);
//        Log.d("STOP_TAG", "3: " + new Gson().toJson(user));


//        Test test = new Test();
//        Test.Medication meds = test.new Medication();
//        Test.Medication medication = new Test.new Test.Medication("","","","");

        // initialize ui
        viewPager = findViewById(R.id.register_view_pager);
        dotsLayout = findViewById(R.id.register_layout_dots);
        btnPrevious = findViewById(R.id.register_btn_previous);
        btnNext = findViewById(R.id.register_btn_next);

        // initialize fragments
        fragments = new ArrayList<>();
        fragment_medications = new RegisterFragment_01(user);
        fragment_updrs = new RegisterFragment_02();
        fragments.add(fragment_medications);
        fragments.add(fragment_updrs);

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

                }
            }
        });
    }
}
