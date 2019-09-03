package com.aware.app.testx.presenter;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.aware.app.testx.R;

import java.util.Arrays;

public class SymptomAdapter extends BaseAdapter {

    private Context context;
    private TypedArray symptoms;
    private int[] result;

    public SymptomAdapter(Context context) {
        this.context = context;
        symptoms = context.getResources().obtainTypedArray(R.array.symptomsList);

        result = new int[symptoms.length()];
        for (int i=0; i<result.length; i++) result[i] = -1;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        View listItem = convertView;
        if (listItem == null) {
            listItem = LayoutInflater.from(context).inflate(R.layout.view_list_item_symptom, parent, false);
        }

        TextView symptomText = listItem.findViewById(R.id.symptomName);
        RadioGroup group = listItem.findViewById(R.id.symptomRate);
        RadioButton rate0 = listItem.findViewById(R.id.rate0);
        RadioButton rate1 = listItem.findViewById(R.id.rate1);
        RadioButton rate2 = listItem.findViewById(R.id.rate2);
        RadioButton rate3 = listItem.findViewById(R.id.rate3);
        RadioButton rate4 = listItem.findViewById(R.id.rate4);

        String[] symptom = context.getResources().getStringArray(symptoms.getResourceId(position, -1));
        symptomText.setText(symptom[0]);
        rate0.setText(symptom[1]);
        rate1.setText(symptom[2]);
        rate2.setText(symptom[3]);
        rate3.setText(symptom[4]);
        rate4.setText(symptom[5]);

        group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                result[position] = group.indexOfChild(group.findViewById(checkedId));
            }
        });

        return listItem;
    }

    @Override
    public int getCount() {
        return symptoms.length();
    }

    @Override
    public String[] getItem(int position) {
        return context.getResources().getStringArray(symptoms.getResourceId(position, -1));
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public int getViewTypeCount() {
        return symptoms.length();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public int getChecked(int position) {
        return result[position];
    }
}
