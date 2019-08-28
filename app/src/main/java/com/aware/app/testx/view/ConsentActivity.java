package com.aware.app.testx.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.aware.app.testx.R;
import com.aware.app.testx.model.User;
import com.aware.app.testx.presenter.VPAdapter;

public class ConsentActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private LinearLayout dotsLayout;
    private Button btnPrevious, btnNext;
    private EditText etUsername, etAge;
    private CheckBox cbDiagnosis, cbConsent;

    private int[] layouts;

    private VPAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consent);
        getSupportActionBar().hide();

        // initialize viewpager elements
        layouts = new int[]{
                R.layout.consent_01,
                R.layout.consent_02,
                R.layout.consent_03
        };

        // initialize ui
        viewPager = findViewById(R.id.consent_view_pager);
        dotsLayout = findViewById(R.id.consent_layout_dots);
        btnPrevious = findViewById(R.id.consent_btn_previous);
        btnNext = findViewById(R.id.consent_btn_next);

        // set up pager adapter
        adapter = new VPAdapter(this, layouts, dotsLayout, btnPrevious, btnNext);
        viewPager.setAdapter(adapter);;
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
                if (current < layouts.length-1){
                    viewPager.setCurrentItem(current + 1);
                } else {
                    // initialize views
                    etUsername = (EditText) adapter.initView(viewPager.getCurrentItem(), R.id.et_username);
                    etAge = (EditText) adapter.initView(viewPager.getCurrentItem(), R.id.et_age);
                    cbDiagnosis = (CheckBox)  adapter.initView(viewPager.getCurrentItem(), R.id.cb_diagnosis);
                    cbConsent = (CheckBox) adapter.initView(viewPager.getCurrentItem(), R.id.cb_consent);

                    if (!cbConsent.isChecked()) {
                        // run demo mode if consent is declined
                        // TODO: open demo mode without db call


                    } else if (!cbDiagnosis.isChecked()) {
                        // run app as non-pd user
                        // TODO: call db


                    } else {
                        // open pd registration profile
                        // TODO: run med log
                        Intent registration = new Intent(ConsentActivity.this, RegisterActivity.class);
                        User user = new User(etUsername.getText().toString(),
                                Integer.valueOf(etAge.getText().toString()),
                                cbDiagnosis.isChecked());
                        registration.putExtra("user", user);
                        startActivity(registration);
                        finish();
                    }
                }
            }
        });
    }
}