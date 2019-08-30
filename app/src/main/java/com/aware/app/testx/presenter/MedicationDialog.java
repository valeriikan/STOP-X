package com.aware.app.testx.presenter;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.aware.app.testx.R;
import com.aware.app.testx.view.RegisterFragment_01;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.Calendar;

public class MedicationDialog extends DialogFragment {

//    private User user;
//
//    public MedicationDialog(User user) {
//        this.user = user;
//    }

    private ArrayList<String> intakeTimes;
    private IntakeTimeAdapter adapter;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        intakeTimes = new ArrayList<>();


        View view = getActivity().getLayoutInflater().inflate(R.layout.dialog_add_medication, null);
        final TextInputEditText dialog_name = view.findViewById(R.id.dialog_name);
        final TextInputEditText dialog_dosage = view.findViewById(R.id.dialog_dosage);
        final TextInputEditText dialog_notes = view.findViewById(R.id.dialog_notes);
        final TextInputEditText dialog_intake_et = view.findViewById(R.id.dialog_intake_et);
        final ImageButton dialog_intake_btn = view.findViewById(R.id.dialog_intake_btn);
        dialog_intake_btn.setOnClickListener(add_time);
        dialog_intake_et.setOnClickListener(add_time);

        ListView dialog_intake_list = view.findViewById(R.id.dialog_intake_list);
        adapter = new IntakeTimeAdapter(getContext(), intakeTimes);
        dialog_intake_list.setAdapter(adapter);

        final AlertDialog dialog = new AlertDialog.Builder(getActivity())
                .setTitle("Add a medication")
                .setView(view)
                .setPositiveButton("Add", null)
                .setNegativeButton("Cancel", null)
                .create();

        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dial) {
                dialog.getButton(DialogInterface.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        RegisterFragment_01.medications.add(dialog_name.getText().toString());
                        RegisterFragment_01.adapter.notifyDataSetChanged();

                        dialog.dismiss();


//                        User.Medication medication = user.new Medication(
//                                dialog_name.getText().toString(),
//                                dialog_dosage.getText().toString(),
//                                dialog_notes.getText().toString(),
//                                dialog_notes.getText().toString()
//                        );
                    }
                });

                dialog.getButton(DialogInterface.BUTTON_NEGATIVE).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
            }
        });


        return dialog;
    }

    private View.OnClickListener add_time = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            TimePickerDialog picker = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    Log.d("STOP_TAG", hourOfDay+""+minute);
                    intakeTimes.add(String.format("%02d:%02d", hourOfDay, minute));
                    adapter.notifyDataSetChanged();
                }
            }, Calendar.getInstance().get(Calendar.HOUR_OF_DAY), Calendar.getInstance().get(Calendar.MINUTE), true);
            picker.setTitle("Intake time");
            picker.show();
        }
    };

    private class IntakeTimeAdapter extends ArrayAdapter<String> {

        private Context context;
        private ArrayList<String> list;

        public IntakeTimeAdapter(@NonNull Context context, ArrayList<String> list) {
            super(context, 0, list);
            this.context = context;
            this.list = list;
        }

        @NonNull
        @Override
        public View getView(final int position, @Nullable View listItem, @NonNull ViewGroup parent) {
            if (listItem == null) {
                listItem = LayoutInflater.from(context)
                        .inflate(R.layout.view_list_item_consent_medication, parent, false);
            }

            TextView name = listItem.findViewById(R.id.consentMedicationName);
            name.setText(list.get(position));

            ImageView remove = listItem.findViewById(R.id.consentMedicationRemove);
            remove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                intakeTimes.remove(position);
                notifyDataSetChanged();
                }
            });

            return listItem;
        }
    }


}
