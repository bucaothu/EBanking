package com.bank.ebanking.fragment;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.bank.EBanking.R;
import com.bank.ebanking.intent.IntentChangeProfile;
import com.bank.ebanking.services.Services.UserService;
import com.bank.ebanking.services.Services.UserSessionManager;

public class FragmentProfile extends Fragment {
    RelativeLayout rlEditProfile;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_profile, container, false);
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setControl(view);
        setEvent();

    }

    private void setEvent() {
        rlEditProfile.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {
                UserService.getUserProfile(UserSessionManager.getUsername(), view.getContext(), new IntentChangeProfile());
            }
        });
    }

    private void setControl(View view) {
        rlEditProfile = view.findViewById(R.id.rlEditProfile);
    }
}
