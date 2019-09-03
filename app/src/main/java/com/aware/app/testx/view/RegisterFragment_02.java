package com.aware.app.testx.view;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.aware.app.testx.R;
import com.aware.app.testx.model.User;
import com.aware.app.testx.presenter.MedicationAdapter;
import com.aware.app.testx.presenter.MedicationDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class RegisterFragment_02 extends Fragment {

    // views
    private FloatingActionButton fab;
    private ListView listView;
    private MedicationDialog dialog;

    // UI list view elements
    private ArrayList<String> medicationsList;
    private MedicationAdapter adapter;

    // User data class elements
    private User user;
    private ArrayList<User.Medication> medications;

    public RegisterFragment_02(User user) {
        this.user = user;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_register_02, container, false);

        medications = new ArrayList<User.Medication>(); // for User class
        medicationsList = new ArrayList<String>(); // entries of list view in dialog ui
        adapter = new MedicationAdapter(getContext(), medicationsList, medications);

        fab = view.findViewById(R.id.medication_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog = new MedicationDialog(user);
                dialog.setListener(new MedicationDialog.MedicationDialogListener() {
                    @Override
                    public void onDismiss(User.Medication medication) {
                        // on dialog dismiss:
                        // update ui
                        medicationsList.add(medication.getName());
                        adapter.notifyDataSetChanged();

                        // update user class medications list
                        medications.add(medication);

                        // destroy dialog for further reuse
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

    public ArrayList<User.Medication> getMedications() {
        return medications;
    }

}
