package com.aware.app.testx.presenter;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;

import com.aware.app.testx.R;
import com.aware.app.testx.model.User;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.Calendar;

public class MedicationDialog extends DialogFragment {

    // views
    private Toolbar toolbar;
    private TextInputEditText dialog_name, dialog_dosage, dialog_notes;
    private CheckBox dialog_booster;
    private TextView dialog_intake_tv;
    private ImageButton dialog_intake_btn;
    private ListView dialog_intake_list;

    // UI list view elements
    private ArrayList<String> intakeTimes;
    private MedicationAdapter adapter;
    private MedicationDialogListener listener;

    // User data class elements
    private ArrayList<User.Medication.IntakeTime> intakeList;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.AppTheme_FullScreenDialog);

        intakeList = new ArrayList<User.Medication.IntakeTime>(); // for User class
        intakeTimes = new ArrayList<String>(); // entries of list view in dialog ui
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = getActivity().getLayoutInflater().inflate(R.layout.dialog_add_medication, null);

        toolbar = view.findViewById(R.id.dialog_toolbar);
        dialog_name = view.findViewById(R.id.dialog_name);
        dialog_dosage = view.findViewById(R.id.dialog_dosage);
        dialog_notes = view.findViewById(R.id.dialog_notes);
        dialog_booster = view.findViewById(R.id.dialog_booster);
        dialog_intake_tv = view.findViewById(R.id.dialog_intake_tv);
        dialog_intake_btn = view.findViewById(R.id.dialog_intake_btn);
        dialog_intake_list = view.findViewById(R.id.dialog_intake_list);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        toolbar.setTitle("Add a medication");
        toolbar.inflateMenu(R.menu.medication_dialog);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                if (!dialog_name.getText().toString().equals("") &&
                    !dialog_dosage.getText().toString().equals("") &&
                    (dialog_booster.isChecked() || !intakeList.isEmpty())) {

                    User.Medication medication = new User.Medication();

                    medication.setName(dialog_name.getText().toString());
                    medication.setDosage(dialog_dosage.getText().toString());
                    medication.setIntakeTime(intakeList);
                    medication.setBooster(dialog_booster.isChecked());
                    medication.setNotes(dialog_notes.getText().toString());
                    listener.onDismiss(medication);

                    dismiss();
                } else{
                    Toast.makeText(getContext(), "Please fill the required fields", Toast.LENGTH_SHORT).show();
                }
                return  true;
            }
        });

        dialog_intake_btn.setOnClickListener(add_time);
        dialog_intake_tv.setOnClickListener(add_time);

        adapter = new MedicationAdapter(getContext(), intakeTimes, intakeList);
        dialog_intake_list.setAdapter(adapter);
    }

    @Override
    public void onStart() {
        super.onStart();

        // sets up dialog in full screen mode
        Dialog dialog = getDialog();
        if (dialog != null) {
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.MATCH_PARENT;
            dialog.getWindow().setLayout(width, height);
            dialog.getWindow().setWindowAnimations(R.style.AppTheme_Slide);
        }
    }

    private View.OnClickListener add_time = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            TimePickerDialog picker = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                    intakeTimes.add(String.format("%02d:%02d", hourOfDay, minute)); // add element to UI array
                    adapter.notifyDataSetChanged();                                // update dialog list view
                    intakeList.add(new User.Medication.IntakeTime(hourOfDay, minute));   // add element to User array

                }
            }, Calendar.getInstance().get(Calendar.HOUR_OF_DAY), Calendar.getInstance().get(Calendar.MINUTE), true);
            picker.setTitle("Intake time");
            picker.show();
        }
    };

    // Listener that sends data to fragment when dialog is dismissed
    public interface MedicationDialogListener {
        void onDismiss(User.Medication medication);
    }

    public MedicationDialogListener getListener() {
        return listener;
    }

    public void setListener(MedicationDialogListener listener) {
        this.listener = listener;
    }
}
