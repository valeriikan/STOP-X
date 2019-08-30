package com.aware.app.testx.presenter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.aware.app.testx.R;

import java.util.ArrayList;

public class RegisterArrayAdapter extends ArrayAdapter<String> {

    private Context context;
    private ArrayList<String> uiList;
    private ArrayList userList;

    public RegisterArrayAdapter(@NonNull Context context, ArrayList<String> uiList, ArrayList userList) {
        super(context, 0, uiList);
        this.context = context;
        this.uiList = uiList;
        this.userList = userList;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View listItem, @NonNull ViewGroup parent) {

        if (listItem == null) {
            listItem = LayoutInflater.from(context)
                    .inflate(R.layout.fragment_register_list_item, parent, false);
        }

        TextView name = listItem.findViewById(R.id.consentMedicationName);
        name.setText(uiList.get(position));

        ImageView remove = listItem.findViewById(R.id.consentMedicationRemove);
        remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uiList.remove(position);
                userList.remove(position);
                notifyDataSetChanged();
            }
        });

        return listItem;
    }
}
