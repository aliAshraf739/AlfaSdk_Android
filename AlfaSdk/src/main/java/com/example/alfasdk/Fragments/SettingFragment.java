package com.example.alfasdk.Fragments;


import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.alfasdk.MyMainActivity;
import com.example.alfasdk.R;


public class SettingFragment extends Fragment {

//    @BindView(R.id.text_change_pass)
    TextView tvChangePass;
//    @BindView(R.id.text_change_pin)
    TextView tvChangePin;

    Button text_change_pass;

    public SettingFragment() {
        // Required empty public constructor
    }

    public static SettingFragment newInstance() {
        return new SettingFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_setting, container, false);
//        tvChangePass=view.findViewById(R.id.text_change_pass);
        tvChangePin=view.findViewById(R.id.text_change_pin);


//        tvChangePass.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                ((MyMainActivity) getActivity()).replaceFragment(ChangePassFragment.newInstance(), false, true);
//
//            }
//        });

        tvChangePin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MyMainActivity) getActivity()).replaceFragment(ChangePinFragment.newInstance(), false, true);

            }
        });

//        ButterKnife.bind(this, view);
        return view;
    }

//    @OnClick({R.id.text_change_pass, R.id.text_change_pin})
//    public void pickSetting(View view) {
//        if (view.getId() == R.id.text_change_pass) {
//
//            ((MainActivity) getActivity()).replaceFragment(ChangePassFragment.newInstance(), false, true);
//        } else {
//            ((MainActivity) getActivity()).replaceFragment(ChangePinFragment.newInstance(), false, true);
//        }
//    }

    @Override
    public void onResume() {

        ActionBar toolbar = ((AppCompatActivity) getActivity()).getSupportActionBar();

        if (toolbar != null) {
            toolbar.setTitle("Settings");
        }
        super.onResume();
    }

}
