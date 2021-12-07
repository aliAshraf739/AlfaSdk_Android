package com.example.alfasdk.Fragments.accountOpening;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.alfasdk.R;
import com.example.alfasdk.Util.Alert;
import com.example.alfasdk.Util.Util;
import com.google.android.material.textfield.TextInputEditText;


public class State7Fragment extends Fragment {

    private ImageView ivBack;
    private TextView tvTitle;
    private TextInputEditText etFatcaClassification;
    private TextInputEditText etQ2;
    private TextInputEditText etQ3;
    private TextInputEditText etQ4;
    private TextInputEditText etQ5;
    private Button btnNext;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_state7, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);

        ivBack.setOnClickListener(view1 -> {
            requireActivity().onBackPressed();
        });

        btnNext.setOnClickListener(view1 -> {
            Util.performNavigation(requireActivity(), R.id.action_state1Fragment_to_state2Fragment);
        });

    }

    private void initViews(View view) {
        
        ivBack = view.findViewById(R.id.ivBack);
        tvTitle = view.findViewById(R.id.tvTitle);
        etFatcaClassification = view.findViewById(R.id.etFatcaClassification);
        etQ2 = view.findViewById(R.id.etQ2);
        etQ3 = view.findViewById(R.id.etQ3);
        etQ4 = view.findViewById(R.id.etQ4);
        etQ5 = view.findViewById(R.id.etQ5);
        btnNext = view.findViewById(R.id.btnNext);

    }

}