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

public class MedicationAdapter extends ArrayAdapter<String> {

    private Context context;
    private ArrayList<String> list;

    public MedicationAdapter(@NonNull Context context, ArrayList<String> list) {
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
//                list.remove(position);
//                medicationJSONArray.remove(position);
                notifyDataSetChanged();
            }
        });

        return listItem;
    }
}
