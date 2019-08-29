package com.aware.app.testx.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.aware.app.testx.R;
import com.aware.app.testx.model.User;
import com.aware.app.testx.presenter.SectionsAdapter;

import java.util.ArrayList;
import java.util.List;

public class ConsentActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private LinearLayout dotsLayout;
    private Button btnPrevious, btnNext;

    private ConsentFragment_03 details_fragment;
    private List<Fragment> fragments;
    private SectionsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consent);
        getSupportActionBar().hide();

        // initialize ui
        viewPager = findViewById(R.id.consent_view_pager);
        dotsLayout = findViewById(R.id.consent_layout_dots);
        btnPrevious = findViewById(R.id.consent_btn_previous);
        btnNext = findViewById(R.id.consent_btn_next);

        // initialize fragments
        fragments = new ArrayList<>();
        fragments.add(new ConsentFragment_01());
        fragments.add(new ConsentFragment_02());
        details_fragment = new ConsentFragment_03();
        fragments.add(details_fragment);

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
                    if (!details_fragment.cbConsent.isChecked()) {
                        // run demo mode if consent is declined
                        // TODO: open demo mode without db call


                    } else if (!details_fragment.cbDiagnosis.isChecked()) {
                        // run app as non-pd user
                        // TODO: call db


                    } else {
                        // open pd registration profile
                        // TODO: run med log
                        Intent registration = new Intent(ConsentActivity.this, RegisterActivity.class);
                        User user = new User(details_fragment.etUsername.getText().toString(),
                                Integer.valueOf(details_fragment.etAge.getText().toString()),
                                details_fragment.cbDiagnosis.isChecked());
                        registration.putExtra("user", user);
                        startActivity(registration);
                        finish();
                    }
                }
            }
        });
    }
}