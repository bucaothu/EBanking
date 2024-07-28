package com.bank.ebanking.fragment;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bank.EBanking.R;
import com.bank.ebanking.adapter.AdapterBill;
import com.bank.ebanking.model.Transaction;
import com.bank.ebanking.model.User;
import com.bank.ebanking.services.Services.UserSessionManager;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class FragmentBills extends Fragment {
    private Button btnStartDate;
    private Button btnEndDate;
    private Button btnQuery;
    private AdapterBill adapterBill;
    private List<Transaction> transactions;
    private Calendar startDateFilter = Calendar.getInstance();
    private Calendar endDateFilter = Calendar.getInstance();
    private RecyclerView recyclerView;
    private final String defaultStartDateText = getContext().getResources().getString(R.string.btn_start_date_query);
    private final String defaultEndDateText = getContext().getResources().getString(R.string.btn_end_date_query);


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bills, container, false);
        recyclerView = view.findViewById(R.id.recyclerview_bill);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        Intent intent = getActivity().getIntent();
        transactions = (List<Transaction>) intent.getSerializableExtra("transactions");
        adapterBill = new AdapterBill(transactions);
        recyclerView.setAdapter(adapterBill);
        return view;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setControl(view);
        setEvent();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void setEvent() {
        btnStartDate.setText(defaultStartDateText);
        btnEndDate.setText(defaultEndDateText);
        btnStartDate.setOnClickListener(v -> showDatePickerDialog(true));
        btnEndDate.setOnClickListener(v -> showDatePickerDialog(false));

        Intent intent = getActivity().getIntent();
        transactions = (List<Transaction>) intent.getSerializableExtra("transactions");
        try{
            if(intent.getLongExtra("startDateFilter", 1)!=1){
                Date startDateFilter = new Date(intent.getLongExtra("startDateFilter", 1));
                Date endDateFilter = new Date(intent.getLongExtra("endDateFilter", 1));
                btnStartDate.setText(new SimpleDateFormat("dd/MM/yyyy").format(startDateFilter));
                btnEndDate.setText(new SimpleDateFormat("dd/MM/yyyy").format(endDateFilter));
                query(startDateFilter, endDateFilter, transactions);
            }
        }catch (Exception e){

        }
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        btnQuery.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                boolean isStartDateSelected = !btnStartDate.getText().toString().equals(defaultStartDateText);
                boolean isEndDateSelected = !btnEndDate.getText().toString().equals(defaultEndDateText);
                if (isStartDateSelected && isEndDateSelected) {
                    Date startDateFilter = new Date();
                    Date endDateFilter = new Date();
                    try {
                        startDateFilter = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).parse(btnStartDate.getText().toString());
                        endDateFilter = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).parse(btnEndDate.getText().toString());
                    } catch (ParseException e) {
                        throw new RuntimeException(e);
                    }
                    if (((endDateFilter.getTime() - startDateFilter.getTime()) / (1000 * 60 * 60 * 24)) > 182.5) {
                        Toast.makeText(view.getContext(), getContext().getResources().getString(R.string.warning_start_end_date_6_months), Toast.LENGTH_SHORT).show();
                    } else if(startDateFilter.getTime()>endDateFilter.getTime()){
                        Toast.makeText(view.getContext(), getContext().getResources().getString(R.string.warning_start_more_than_end), Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Intent intent = getActivity().getIntent();
                        intent.putExtra("startDateFilter", startDateFilter.getTime());
                        intent.putExtra("endDateFilter", endDateFilter.getTime());
                        query(startDateFilter, endDateFilter, transactions);
                    }
                } else {
                    Toast.makeText(view.getContext(), getContext().getResources().getString(R.string.warning_no_start_end_date), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void setControl(View view) {
        btnStartDate = view.findViewById(R.id.btn_start_date);
        btnEndDate = view.findViewById(R.id.btn_end_date);
        btnQuery = view.findViewById(R.id.btn_query);
        recyclerView = view.findViewById(R.id.recyclerview_bill);
    }

    private void showDatePickerDialog(boolean isStartDate) {
        Calendar calendar = isStartDate ? startDateFilter : endDateFilter;
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this.getContext(),
                (view, selectedYear, selectedMonth, selectedDay) -> {
                    calendar.set(selectedYear, selectedMonth, selectedDay);
                    String selectedDate = selectedDay + "/" + (selectedMonth + 1) + "/" + selectedYear;
                    if (isStartDate) {
                        btnStartDate.setText(selectedDate);
                    } else {
                        btnEndDate.setText(selectedDate);
                    }
                },
                year, month, day);
        datePickerDialog.show();
    }
    @RequiresApi(api = Build.VERSION_CODES.N)
    private void query(Date startDateFilter, Date endDateFilter, List<Transaction> transactions){
        String username = UserSessionManager.getUsername();
        Date finalStartDate = startDateFilter;
        Date finalEndDate = endDateFilter;

        List<Transaction> transfer = transactions.stream()
                .filter(t-> t.getAccountNumber().getIdUser().getUsername().equals(username))
                .filter(t -> !t.getDate().before(finalStartDate) && !t.getDate().after(finalEndDate))
                .collect(Collectors.toList());
        adapterBill = new AdapterBill(transfer);
        recyclerView.setAdapter(adapterBill);
    }
}
