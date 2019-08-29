package com.aware.app.testx.view;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;

import com.aware.app.testx.R;

public class ConsentFragment_03 extends Fragment {

    public EditText etUsername, etAge;
    public CheckBox cbDiagnosis, cbConsent;

    public ConsentFragment_03() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_consent_03, container, false);

        etUsername = view.findViewById(R.id.et_username);
        etAge = view.findViewById(R.id.et_age);
        cbDiagnosis = view.findViewById(R.id.cb_diagnosis);
        cbConsent = view.findViewById(R.id.cb_consent);

        return view;

    }

}
