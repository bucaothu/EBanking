package com.bank.ebanking.intent;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.bank.EBanking.R;
import com.bank.ebanking.services.Services.TransactionService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class IntentPayBill extends AppCompatActivity {
    Spinner spinnerBillType, spinnerWaterSuppiler;
    TextView tvWaterSuppliers;
    Button btnFindBill;
    EditText edtBillId;
    String selectedType, selectedCompany;
    View viewHidable;
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill_payment);
        setControl();
        setEvent();
    }

    private void setEvent() {
        List<String> billTypes = new ArrayList<>();
        billTypes.add("Hóa đơn tiền điện");
        billTypes.add("Hóa đơn tiền nước");
        selectedType = billTypes.get(0);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.spinner_item_text, billTypes);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerBillType.setAdapter(adapter);
        spinnerBillType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                selectedType = billTypes.get(position);
                if(position == 0){
                    spinnerWaterSuppiler.setVisibility(View.GONE);
                    tvWaterSuppliers.setVisibility(View.GONE);
                    viewHidable.setVisibility(View.GONE);
                }else{
                    spinnerWaterSuppiler.setVisibility(View.VISIBLE);
                    tvWaterSuppliers.setVisibility(View.VISIBLE);
                    viewHidable.setVisibility(View.VISIBLE);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parentView) {}
        });
        Map<String, String> waterCompanies = new HashMap<>();
        waterCompanies.put("Cty nước số 3 Hà Nội", "WB_HN3"); waterCompanies.put("Cty DVXD cấp nước Đồng Nai", "WB_DNIDVXD"); waterCompanies.put("Cty CP Viwaco", "WB_VIWACO"); waterCompanies.put("Cty nước Thủ Đức – TP.HCM", "WB_HCMTD"); waterCompanies.put("Cty nước Tân Hòa – TP.HCM", "WB_HCMTH"); waterCompanies.put("Cty nước Sơn La", "WB_SL"); waterCompanies.put("Cty nước số 2 Hải Phòng", "WB_HP2"); waterCompanies.put("Cty nước Phú Mỹ - BRVT", "WB_BRVTPM"); waterCompanies.put("Cty nước Long Khánh – Đồng Nai", "WB_DNILK"); waterCompanies.put("Cty nước Huế", "WB_HUE"); waterCompanies.put("Cty nước Hải Phòng", "WB_HP"); waterCompanies.put("Cty nước Hà Nam", "WB_HNA"); waterCompanies.put("Cty nước Gia Định", "WB_HCMGD"); waterCompanies.put("Cty nước Đồng Tháp", "WB_DTD"); waterCompanies.put("Cty nước Cao Bằng", "WB_CB"); waterCompanies.put("Cty nước Cần Thơ 2", "WB_CTO2"); waterCompanies.put("Cty nước Bến Thành – TP.HCM", "WB_HCMBT"); waterCompanies.put("Cty nước Cần Thơ", "WB_CTO"); waterCompanies.put("Cty nước Bình Dương", "WB_BDU"); waterCompanies.put("Cty nước Nhơn Trạch – Đồng Nai", "WB_DNINT"); waterCompanies.put("Cty nước Đồng Nai", "WB_DNI"); waterCompanies.put("Cty nước Bà Rịa - Vũng Tàu", "WB_BRVT"); waterCompanies.put("Cty nước Nhà Bè", "WB_NBE"); waterCompanies.put("Cty nước Nông thôn – TP.HCM", "WB_HCMNT"); waterCompanies.put("Cty nước Chợ Lớn – TP.HCM", "WB_HCMCLO"); waterCompanies.put("Cty nước Trung An – TP.HCM", "WB_HCMTA"); waterCompanies.put("Nước Đà Nẵng", "WB_DNA"); waterCompanies.put("Nước Vĩnh Phúc", "WB_NVP"); waterCompanies.put("Cty Nước TP.Hồ Chí Minh", "WB_NHCM"); waterCompanies.put("Nước Quảng Nam", "WB_NQN"); waterCompanies.put("Nước Tiền Giang", "WB_TGGWACO"); waterCompanies.put("Nước Cà Mau", "WB_NCM"); waterCompanies.put("Nước Phú Hòa Tân (HCM)", "WB_NPHT"); waterCompanies.put("Nước Bắc Giang", "WB_NBGG"); waterCompanies.put("Nước Bắc Ninh", "WB_NBNH"); waterCompanies.put("Nước Kon Tum", "WB_NKTM"); waterCompanies.put("Nước Bình Thuận", "WB_NBTN"); waterCompanies.put("Nước Quảng Trị", "WB_NQTI"); waterCompanies.put("Nước Hà Nội", "WB_NHNI"); waterCompanies.put("Nước Vĩnh Long", "WB_NVLG"); waterCompanies.put("Nước Lai Châu", "WB_NLCU"); waterCompanies.put("Nước Hà Bắc", "WB_NHB"); waterCompanies.put("Nước Thái Hòa", "WB_NTH"); waterCompanies.put("Nước Bình Phước", "WB_NBPC"); waterCompanies.put("Nước Nghệ An", "WB_NNAN"); waterCompanies.put("Nước Đắk Lắk", "WB_NDLK"); waterCompanies.put("Nước Long An", "WB_NLAN"); waterCompanies.put("Nước Ninh Thuận", "WB_NNTN"); waterCompanies.put("Nước Bạc Liêu", "WB_NBLU"); waterCompanies.put("Nước Hưng Yên", "WB_NHYN"); waterCompanies.put("Nước Sóc Trăng", "WB_NSTG"); waterCompanies.put("Nước Tây Ninh", "WB_NTNH"); waterCompanies.put("Nước Bến Tre", "WB_NBTE"); waterCompanies.put("Nước Minh Phong", "WB_NMP"); waterCompanies.put("Nước Khánh Hòa", "WB_NKHA"); waterCompanies.put("Nước Gia Lai", "WB_NGLI"); waterCompanies.put("Nước Hòa Bình", "WB_NHBH"); waterCompanies.put("Nước Trà Vinh", "WB_NTVH"); waterCompanies.put("Nước Quảng Ninh", "WB_NQNH"); waterCompanies.put("Nước Lạng Sơn", "WB_NLSN"); waterCompanies.put("Nước Hà Giang", "WB_NHGG"); waterCompanies.put("Nước Điện Biên", "WB_NDBN"); waterCompanies.put("Nước Hà Đông", "WB_NHD"); waterCompanies.put("Nước Nam Định", "WB_NNDH"); waterCompanies.put("Nước Phú Yên", "WB_NPYN"); waterCompanies.put("Nước Kiên Giang", "WB_NKGG");
        List<String> companyNames = new ArrayList<>();
        Set<String> keys = waterCompanies.keySet();
        for (String key : keys) {
            companyNames.add(key);
        }
        selectedCompany = "WB_HN3";
        ArrayAdapter<String> adapterCompany = new ArrayAdapter<>(this, R.layout.spinner_item_text, companyNames);
        adapterCompany.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerWaterSuppiler.setAdapter(adapterCompany);
        spinnerWaterSuppiler.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                selectedCompany = waterCompanies.get(companyNames.get(position));
            }
            @Override
            public void onNothingSelected(AdapterView<?> parentView) {}
        });
        btnFindBill.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {
                if(edtBillId.getText().toString().isEmpty()){
                    Toast.makeText(IntentPayBill.this, "Hãy nhập mã số hóa đơn", Toast.LENGTH_SHORT).show();
                }else {
                    if(selectedType.equals("Hóa đơn tiền nước")){
                        Map<String, String> data = new HashMap<>();
                        data.put("billCode", edtBillId.getText().toString());
                        data.put("serviceCode", selectedCompany);
                        Intent intent = getIntent();
                        intent.putExtra("billCode", edtBillId.getText().toString());
                        intent.putExtra("serviceCode", selectedCompany);
                        intent.setClass(IntentPayBill.this, IntentBillDetails.class);
                        TransactionService.getBill(data, IntentPayBill.this, intent);
                    }else {
                        Map<String, String> data = new HashMap<>();
                        data.put("billCode", edtBillId.getText().toString());
                        data.put("serviceCode", "EVN");
                        Intent intent = getIntent();
                        intent.putExtra("billCode", edtBillId.getText().toString());
                        intent.putExtra("serviceCode", "EVN");
                        intent.setClass(IntentPayBill.this, IntentBillDetails.class);
                        TransactionService.getBill(data, IntentPayBill.this, intent);
                    }
                }
            }
        });
    }

    private void setControl() {
        spinnerBillType = findViewById(R.id.spinnerBillTypes);
        spinnerWaterSuppiler = findViewById(R.id.spinnerWaterSuppliers);
        tvWaterSuppliers = findViewById(R.id.tvWaterSuppliers);
        edtBillId = findViewById(R.id.edtBillId);
        btnFindBill = findViewById(R.id.btn_find_bill);
        viewHidable = findViewById(R.id.viewHidable);
    }
}
