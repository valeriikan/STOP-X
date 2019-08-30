package com.aware.app.testx.presenter;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
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
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

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

    private Toolbar toolbar;

    private TextInputEditText dialog_name, dialog_dosage, dialog_notes;
    private ImageButton dialog_intake_btn;
    private ListView dialog_intake_list;
    private TextView dialog_intake_tv;

    public static MedicationDialog display(FragmentManager manager) {
        MedicationDialog dialog = new MedicationDialog();
        dialog.show(manager, "TAG");
        return dialog;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.AppTheme_FullScreenDialog);

        intakeTimes = new ArrayList<>();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = getActivity().getLayoutInflater().inflate(R.layout.dialog_add_medication, null);

        toolbar = view.findViewById(R.id.dialog_toolbar);

        dialog_name = view.findViewById(R.id.dialog_name);
        dialog_dosage = view.findViewById(R.id.dialog_dosage);
        dialog_notes = view.findViewById(R.id.dialog_notes);
        dialog_intake_btn = view.findViewById(R.id.dialog_intake_btn);
        dialog_intake_list = view.findViewById(R.id.dialog_intake_list);
        dialog_intake_tv = view.findViewById(R.id.dialog_intake_tv);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        dialog_intake_btn.setOnClickListener(add_time);
        dialog_intake_tv.setOnClickListener(add_time);

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

                RegisterFragment_01.medications.add(dialog_name.getText().toString());
                RegisterFragment_01.adapter.notifyDataSetChanged();

//                        User.Medication medication = user.new Medication(
//                                dialog_name.getText().toString(),
//                                dialog_dosage.getText().toString(),
//                                dialog_notes.getText().toString(),
//                                dialog_notes.getText().toString()
//                        );

                dismiss();
                return true;
            }
        });

        adapter = new IntakeTimeAdapter(getContext(), intakeTimes);
        dialog_intake_list.setAdapter(adapter);
    }

    @Override
    public void onStart() {
        super.onStart();

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
