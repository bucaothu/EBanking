package com.bank.ebanking.fragment;

import static android.content.Intent.FLAG_ACTIVITY_CLEAR_TASK;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.bank.EBanking.R;
import com.bank.ebanking.intent.IntentChangePassword;
import com.bank.ebanking.intent.IntentChangeProfile;
import com.bank.ebanking.intent.IntentLogin;
import com.bank.ebanking.services.Services.UserService;
import com.bank.ebanking.services.Services.UserSessionManager;

public class FragmentProfile extends Fragment {
    RelativeLayout rlEditProfile, rlChangePassword;
    TextView btnLogout, tvName;
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
        tvName.setText(UserSessionManager.getUsername());
        rlEditProfile.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {
                UserService.getUserProfile(UserSessionManager.getUsername(), view.getContext(), new IntentChangeProfile());
            }
        });
        rlChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = getActivity().getIntent();
                intent.setClass(getContext(), IntentChangePassword.class);
                getContext().startActivity(intent);
            }
        });
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
                Intent intent = new Intent(getActivity(), IntentLogin.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                getContext().startActivity(intent);
            }
        });
    }

    private void setControl(View view) {
        rlEditProfile = view.findViewById(R.id.rlEditProfile);
        rlChangePassword = view.findViewById(R.id.rlChangePassword);
        btnLogout = view.findViewById(R.id.btnLogout);
        tvName = view.findViewById(R.id.name);
    }

}
