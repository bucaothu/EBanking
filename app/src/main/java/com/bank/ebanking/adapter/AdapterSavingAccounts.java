package com.bank.ebanking.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bank.EBanking.R;
import com.bank.ebanking.model.InterestRate;
import com.bank.ebanking.model.SavingAccount;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AdapterSavingAccounts extends RecyclerView.Adapter<AdapterSavingAccounts.MyViewHolder> {
    private final List<SavingAccount> savingAccounts;
    private final List<InterestRate> interestRates;
    private final Context context;

    public AdapterSavingAccounts(List<SavingAccount> savingAccountsInp, List<InterestRate>interestRatesInp, Context context) {
        savingAccounts = new ArrayList<>();
        interestRates = new ArrayList<>();
        for(int i = 0; i<savingAccountsInp.size();i++){
            savingAccounts.add(savingAccountsInp.get(i));
        }
        for(int i = 0; i<interestRatesInp.size();i++){
            interestRates.add(interestRatesInp.get(i));
        }
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_saving_accounts, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        SavingAccount savingAccount = savingAccounts.get(position);
        holder.tvAccountNumber.setText(savingAccount.getAccountNumber());
        holder.tvBalance.setText(String.valueOf(savingAccount.getBalance()));
        holder.tvStartDate.setText(sdf.format(savingAccount.getStartDate()));
        if(savingAccount.getEndDate()!= null){
            holder.tvEndDate.setText(sdf.format(savingAccount.getEndDate()));
            holder.tvInterestRate.setText(getInterestRate(savingAccount, interestRates, false) +"%");
        }
        else {
            holder.tvEndDate.setText(context.getResources().getString(R.string.account_status_non_expire));
            holder.tvInterestRate.setText(getInterestRate(savingAccount, interestRates, true) +"%");
        }
        if(savingAccount.isStatus()==1) {
            holder.tvStatus.setText(context.getResources().getString(R.string.account_status_active));
            holder.tvStatus.setTextColor(Color.parseColor("#61964D"));
        }else{
            holder.tvStatus.setText(context.getResources().getString(R.string.account_status_expired));
            holder.tvStatus.setTextColor(Color.parseColor("#A84B40"));
        }

    }

    @Override
    public int getItemCount() {
        return savingAccounts.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvAccountNumber, tvBalance, tvStartDate, tvEndDate, tvInterestRate, tvStatus;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvAccountNumber = itemView.findViewById(R.id.tv_saving_account_number);
            tvBalance = itemView.findViewById(R.id.tv_saving_account_balance);
            tvStartDate = itemView.findViewById(R.id.tv_saving_account_start_date);
            tvEndDate = itemView.findViewById(R.id.tv_saving_account_end_date);
            tvInterestRate = itemView.findViewById(R.id.tv_saving_account_interest);
            tvStatus = itemView.findViewById(R.id.tv_saving_account_status);
        }
    }
    public float getInterestRate(SavingAccount savingAccount, List<InterestRate> interestRates, boolean isForever){
        if(isForever){
            for(int i = 0 ; i<interestRates.size();i++){
                if( 1 == interestRates.get(i).getIdSavingAccountType().getIdSavingAccountType())
                    return interestRates.get(i).getInterestRate();
            }
        }
        int idType = savingAccount.getIdSavingAccountType().getIdSavingAccountType();
        float balance = savingAccount.getBalance();
        int termMonths= getMonthDifference(savingAccount.getStartDate(), savingAccount.getEndDate());
        List<InterestRate> tempInterestRates= new ArrayList<>();
        for(int i = 0 ; i<interestRates.size();i++){
            if(     idType == interestRates.get(i).getIdSavingAccountType().getIdSavingAccountType()
                    && balance>= interestRates.get(i).getMinBalance()
                    && termMonths>interestRates.get(i).getTermMonths()){
                tempInterestRates.add(interestRates.get(i));
            }
        }
        float maxInterestRate = Float.NEGATIVE_INFINITY;
        for (InterestRate rate : tempInterestRates) {
            if (rate.getInterestRate() > maxInterestRate) {
                maxInterestRate = rate.getInterestRate();
            }
        }
        return maxInterestRate;
    }
    public static int getMonthDifference(Date startDate, Date endDate) {
        // Ensure startDate is before endDate

        if (startDate.after(endDate)) {
            Date temp = startDate;
            startDate = endDate;
            endDate = temp;
        }

        int startYear = startDate.getYear();
        int startMonth = startDate.getMonth();
        int endYear = endDate.getYear();
        int endMonth = endDate.getMonth();

        // Calculate the difference in years and months
        int yearDifference = endYear - startYear;
        int monthDifference = endMonth - startMonth;

        return yearDifference * 12 + monthDifference;
    }
}
