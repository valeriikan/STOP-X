package com.aware.app.testx.view;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.aware.app.testx.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class RegisterFragment_01 extends Fragment {

    FloatingActionButton fab_medication;

    public RegisterFragment_01() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_register_01, container, false);

        fab_medication = view.findViewById(R.id.fab_medication);
        fab_medication.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(),R.string.app_name, Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

}
