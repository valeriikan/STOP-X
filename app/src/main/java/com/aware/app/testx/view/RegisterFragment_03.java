package com.aware.app.testx.view;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.aware.app.testx.R;
import com.aware.app.testx.model.User;
import com.aware.app.testx.presenter.SymptomAdapter;

import java.util.ArrayList;

public class RegisterFragment_03 extends Fragment {

    // views
    private ListView listView;

    // UI list view elements
    public SymptomAdapter adapter;

    // User data class elements
    private User user;

    public RegisterFragment_03(User user) {
        this.user = user;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_register_03, container, false);
        adapter = new SymptomAdapter(getContext());
        listView = view.findViewById(R.id.symptoms_list_view);
        listView.setAdapter(adapter);

        return view;
    }

    public ArrayList<User.Symptom> getSymptoms() {
        ArrayList<User.Symptom> symptoms = new ArrayList<>();

        for (int i=0; i<adapter.getCount(); i++) {
            int checked = adapter.getChecked(i);
            if (checked != -1) {
                String name = adapter.getItem(i)[0].replaceAll(" ", "_").toLowerCase();
                symptoms.add(user.new Symptom(name, checked));
            } else {
                symptoms = null;
                break;
            }
        }

        return symptoms;
    }

}
