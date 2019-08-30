package com.aware.app.testx.view;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.aware.app.testx.R;
import com.aware.app.testx.model.User;
import com.aware.app.testx.presenter.RegisterArrayAdapter;
import com.aware.app.testx.presenter.MedicationDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;

import java.util.ArrayList;

public class RegisterFragment_01 extends Fragment {

    private FloatingActionButton fab;
    private ListView listView;

    public ArrayList<String> medicationsList;
    public RegisterArrayAdapter adapter;

    private ArrayList<User.Medication> medications;

    private User user;
    private MedicationDialog dialog;


    public RegisterFragment_01(User user) {
        this.user = user;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_register_01, container, false);

        medicationsList = new ArrayList<>();
        adapter = new RegisterArrayAdapter(getContext(), medicationsList, medications);

//        Log.d("STOP_TAG", "2: " + new Gson().toJson(user));



        fab = view.findViewById(R.id.medication_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog = new MedicationDialog(user);
                dialog.setListener(new MedicationDialog.MedicationDialogListener() {
                    @Override
                    public void onDismiss(User.Medication medication) {

                        medicationsList.add(medication.getName());
                        adapter.notifyDataSetChanged();

                        String str = new Gson().toJson(medication);
                        Log.d("STOP_TAG", "1: " + str);

//                        Log.d("STOP_TAG", "3: " + new Gson().toJson(user));

                        dialog = null;
                    }
                });
                dialog.show(getFragmentManager(), null);

            }
        });

        listView = view.findViewById(R.id.medication_list_view);
        listView.setAdapter(adapter);


        return view;
    }

}
