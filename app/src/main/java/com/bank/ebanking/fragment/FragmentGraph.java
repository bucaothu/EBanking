package com.bank.ebanking.fragment;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.bank.EBanking.R;
import com.bank.ebanking.model.Transaction;
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

public class FragmentGraph extends Fragment {
    private Button btnStartDate;
    private Button btnEndDate;
    private Button btnQuery;
    private final Calendar startDate = Calendar.getInstance();
    private final Calendar endDate = Calendar.getInstance();
    private String defaultStartDateText;
    private String defaultEndDateText;
    private BarChart barChart;
    private List<Transaction> transactions;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_graph, container, false);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        defaultStartDateText  = requireContext().getResources().getString(R.string.btn_start_date_query);
        defaultEndDateText = requireContext().getResources().getString(R.string.btn_end_date_query);
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
            if(intent.getLongExtra("startDate", 1)!=1){
                Date startDate = new Date(intent.getLongExtra("startDate", 1));
                Date endDate = new Date(intent.getLongExtra("endDate", 1));
                btnStartDate.setText(new SimpleDateFormat("dd/MM/yyyy").format(startDate));
                btnEndDate.setText(new SimpleDateFormat("dd/MM/yyyy").format(endDate));
                barChart.setVisibility(View.VISIBLE);
                query(startDate, endDate, transactions);
            }
        }catch (Exception e){

        }
        btnQuery.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                boolean isStartDateSelected = !btnStartDate.getText().toString().equals(defaultStartDateText);
                boolean isEndDateSelected = !btnEndDate.getText().toString().equals(defaultEndDateText);
                if (isStartDateSelected && isEndDateSelected) {
                    Date startDate = new Date();
                    Date endDate = new Date();
                    try {
                        startDate = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).parse(btnStartDate.getText().toString());
                        endDate = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).parse(btnEndDate.getText().toString());
                    } catch (ParseException e) {
                        throw new RuntimeException(e);
                    }
                    if (((endDate.getTime() - startDate.getTime()) / (1000 * 60 * 60 * 24)) > 182.5) {
                        Toast.makeText(view.getContext(), requireContext().getResources().getString(R.string.warning_start_end_date_6_months), Toast.LENGTH_SHORT).show();
                    } else if(startDate.getTime()>endDate.getTime()){
                        Toast.makeText(view.getContext(), requireContext().getResources().getString(R.string.warning_start_more_than_end), Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Intent intent = getActivity().getIntent();
                        intent.putExtra("startDate", startDate.getTime());
                        intent.putExtra("endDate", endDate.getTime());
                        barChart.setVisibility(View.VISIBLE);
                        query(startDate, endDate, transactions);
                    }
                } else {
                    Toast.makeText(view.getContext(), requireContext().getResources().getString(R.string.warning_no_start_end_date), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void setControl(View view) {
        btnStartDate = view.findViewById(R.id.btn_start_date);
        btnEndDate = view.findViewById(R.id.btn_end_date);
        btnQuery = view.findViewById(R.id.btn_query);
        barChart = view.findViewById(R.id.chart);
    }

    private void showDatePickerDialog(boolean isStartDate) {
        Calendar calendar = isStartDate ? startDate : endDate;
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
    private void query(Date startDate, Date endDate, List<Transaction> transactions){
        String username = UserSessionManager.getUsername();
        Date finalStartDate = startDate;
        Date finalEndDate = endDate;

        // Get transactions
        List<Transaction> transfersTo = transactions.stream()
                .filter(t -> t.getTransactionType().getIdTransactionType() == 1)
                .filter(t-> t.getAccountNumber().getIdUser().getUsername().equals(username))
                .filter(t -> !t.getDate().before(finalStartDate) && !t.getDate().after(finalEndDate))
                .collect(Collectors.toList());

        List<Transaction> transfersFrom = transactions.stream()
                .filter(t -> t.getTransactionType().getIdTransactionType() == 1)
                .filter(t-> t.getToAccountNumber().getIdUser().getUsername().equals(username))
                .filter(t -> !t.getDate().before(finalStartDate) && !t.getDate().after(finalEndDate))
                .collect(Collectors.toList());

        List<Transaction> paymentsTo = transactions.stream()
                .filter(t -> t.getTransactionType().getIdTransactionType() == 2)
                .filter(t-> t.getAccountNumber().getIdUser().getUsername().equals(username))
                .filter(t -> !t.getDate().before(finalStartDate) && !t.getDate().after(finalEndDate))
                .collect(Collectors.toList());

        List<Transaction> paymentsFrom = transactions.stream()
                .filter(t -> t.getTransactionType().getIdTransactionType() == 2)
                .filter(t-> t.getToAccountNumber().getIdUser().getUsername().equals(username))
                .filter(t -> !t.getDate().before(finalStartDate) && !t.getDate().after(finalEndDate))
                .collect(Collectors.toList());
        // Group by month
        Map<String, Double> transfersToMonthlySums = transfersTo.stream()
                .collect(Collectors.groupingBy(
                        t -> {
                            Calendar cal = Calendar.getInstance();
                            cal.setTime(t.getDate());
                            return Integer.toString(cal.get(Calendar.MONTH) + 1);
                        },
                        Collectors.summingDouble(Transaction::getAmount)
                ));

        Map<String, Double> transfersFromMonthlySums = transfersFrom.stream()
                .collect(Collectors.groupingBy(
                        t -> {
                            Calendar cal = Calendar.getInstance();
                            cal.setTime(t.getDate());
                            return Integer.toString(cal.get(Calendar.MONTH) + 1);
                        },
                        Collectors.summingDouble(Transaction::getAmount)
                ));

        Map<String, Double> paymentsToMonthlySums = paymentsTo.stream()
                .collect(Collectors.groupingBy(
                        t -> {
                            Calendar cal = Calendar.getInstance();
                            cal.setTime(t.getDate());
                            return Integer.toString(cal.get(Calendar.MONTH) + 1);
                        },
                        Collectors.summingDouble(Transaction::getAmount)
                ));

        Map<String, Double> paymentsFromMonthlySums = paymentsFrom.stream()
                .collect(Collectors.groupingBy(
                        t -> {
                            Calendar cal = Calendar.getInstance();
                            cal.setTime(t.getDate());
                            return Integer.toString(cal.get(Calendar.MONTH) + 1);
                        },
                        Collectors.summingDouble(Transaction::getAmount)
                ));

        // Collect all unique months
        Set<String> allMonths = new HashSet<>(transfersToMonthlySums.keySet());
        allMonths.addAll(transfersToMonthlySums.keySet());
        allMonths.addAll(paymentsToMonthlySums.keySet());
        allMonths.addAll(paymentsFromMonthlySums.keySet());
        ArrayList<BarEntry> entriesTransfer = new ArrayList<>();

        // Prepare entries
        for (String month : allMonths) {
            int xIndex = Integer.parseInt(month) - 1; // convert month to zero-based index
            float transferToAmount = transfersToMonthlySums.getOrDefault(month, 0.0).floatValue();
            float transferFromAmount = transfersFromMonthlySums.getOrDefault(month, 0.0).floatValue();
            float paymentToAmount = paymentsToMonthlySums.getOrDefault(month, 0.0).floatValue();
            float paymentFromAmount = paymentsFromMonthlySums.getOrDefault(month, 0.0).floatValue();
            entriesTransfer.add(new BarEntry(xIndex, new float[]{0-transferToAmount, 0-paymentToAmount, transferFromAmount, paymentFromAmount}));
        }

        // Create datasets
        BarDataSet set1 = new BarDataSet(entriesTransfer, "Transfers");
        set1.setColors(ColorTemplate.COLORFUL_COLORS[0], ColorTemplate.COLORFUL_COLORS[2], ColorTemplate.COLORFUL_COLORS[1], ColorTemplate.COLORFUL_COLORS[3]);
        set1.setValueTextSize(10f);
        set1.setStackLabels(new String[]{"Chuyển tiền đi", "Thanh toán", "Nhận tiền", "Nhận thanh toán"});

        BarData data = new BarData(set1);
        data.setBarWidth(0.3f); // set the width of each bar
        data.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                if (value!= 0) return String.valueOf((int)value);
                else return "";
            }
        });

        // X-axis settings
        XAxis xAxis = barChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                String[] monthLabels = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
                int index = (int) value;
                return (index >= 0 && index < monthLabels.length) ? monthLabels[index] : "";
            }
        });
        barChart.setData(data);
        xAxis.setGranularity(1f);
        xAxis.setAxisMinimum(allMonths.stream()
                .map(Float::parseFloat)
                .reduce(Float.MAX_VALUE, Float::min)-1);
        xAxis.setAxisMaximum(allMonths.stream()
                .map(Float::parseFloat)
                .reduce(Float.MIN_VALUE, Float::max));
        barChart.setScaleEnabled(true);
        barChart.setPinchZoom(true);
        barChart.invalidate(); // refresh the chart
    }
}
