package com.bank.ebanking.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bank.EBanking.R;
import com.bank.ebanking.model.SavingAccountType;

import java.util.List;

public class SpinnerSavingAccountTypeAdapter extends ArrayAdapter<SavingAccountType> {
    private Context context;
    private List<SavingAccountType> savingAccountTypes;

    public SpinnerSavingAccountTypeAdapter(Context context, List<SavingAccountType> savingAccountTypes) {
        super(context, 0, savingAccountTypes);
        this.context = context;
        this.savingAccountTypes = savingAccountTypes;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return createViewFromResource(position, convertView, parent, R.layout.spinner_item_saving_types);
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return createViewFromResource(position, convertView, parent, R.layout.spinner_item_saving_types);
    }

    private View createViewFromResource(int position, View convertView, ViewGroup parent, int resource) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(resource, parent, false);
        }

        SavingAccountType savingAccountType = savingAccountTypes.get(position);

        TextView name = convertView.findViewById(R.id.type_name);
        TextView description = convertView.findViewById(R.id.type_description);

        name.setText(savingAccountType.getName()); // Replace with actual account holder name if available
        description.setText(savingAccountType.getDescription());

        return convertView;
    }
}
