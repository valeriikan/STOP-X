package com.aware.app.testx.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.aware.app.testx.R;
import com.aware.app.testx.model.User;
import com.aware.app.testx.presenter.VPAdapter;
import com.google.gson.Gson;

import java.util.ArrayList;

public class RegisterActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private LinearLayout dotsLayout;
    private Button btnPrevious, btnNext;

    private int[] layouts;

    private VPAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getSupportActionBar().hide();

        Intent intent = getIntent();
        User user = (User) intent.getSerializableExtra("user");

        ArrayList<User.Medication> medications = new ArrayList<>();
        medications.add(user.new Medication("11","22", "33","44"));
        medications.add(user.new Medication("55","66", "77","88"));
        user.setMedications(medications);
        Log.d("STOP_TAG", "3: " + new Gson().toJson(user));

        layouts = new int[]{
                R.layout.register_01,
                R.layout.register_02
        };

        viewPager = findViewById(R.id.register_view_pager);
        dotsLayout = findViewById(R.id.register_layout_dots);
        btnPrevious = findViewById(R.id.register_btn_previous);
        btnNext = findViewById(R.id.register_btn_next);

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
//                    Log.d("STOP_TAG")
                }
            }
        });
    }

}
