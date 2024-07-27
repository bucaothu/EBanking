package com.bank.ebanking.intent;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.bank.EBanking.R;
import com.bank.ebanking.fragment.FragmentBills;
import com.bank.ebanking.fragment.FragmentGraph;
import com.bank.ebanking.fragment.FragmentMain;
import com.bank.ebanking.fragment.FragmentProfile;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class IntentMainScreen extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragmentSelected = null;
                switch (item.getItemId()){
                    case R.id.main:
                        fragmentSelected = new FragmentMain();
                        break;
                    case R.id.bills:
                        fragmentSelected = new FragmentBills();
                        break;
                    case R.id.graph:
                        fragmentSelected = new FragmentGraph();
                        break;
                    case R.id.setting:
                        fragmentSelected = new FragmentProfile();
                        break;
                }
                if (fragmentSelected != null) {
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.fragment_container, fragmentSelected);
                    fragmentTransaction.commit();
                }
                return true;
            }
        });

        if (savedInstanceState == null) {
            bottomNavigationView.setSelectedItemId(R.id.main);
        }
    }
    @Override
    public void onBackPressed() {
    }
}
