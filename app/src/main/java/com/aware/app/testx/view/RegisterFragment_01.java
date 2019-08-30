package com.aware.app.testx.view;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.aware.app.testx.R;
import com.aware.app.testx.presenter.MedicationAdapter;
import com.aware.app.testx.presenter.MedicationDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class RegisterFragment_01 extends Fragment {

    FloatingActionButton fab;
    ListView listView;

    public static ArrayList<String> medications;
    public static MedicationAdapter adapter;

    public RegisterFragment_01() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_register_01, container, false);

        medications = new ArrayList<>();
        adapter = new MedicationAdapter(getContext(), medications);

        fab = view.findViewById(R.id.medication_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new MedicationDialog().show(getFragmentManager(), null);
            }
        });

        listView = view.findViewById(R.id.medication_list_view);
        listView.setAdapter(adapter);


        return view;
    }

}
