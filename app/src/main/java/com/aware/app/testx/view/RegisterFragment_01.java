package com.aware.app.testx.view;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;

import com.aware.app.testx.R;

public class RegisterFragment_01 extends Fragment {

    public EditText etYear;
    public CheckBox cbDbs;

    public RegisterFragment_01() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_register_01, container, false);

        etYear = view.findViewById(R.id.et_year);
        etYear.clearFocus();
        cbDbs = view.findViewById(R.id.cb_dbs);

        return view;
    }

}
