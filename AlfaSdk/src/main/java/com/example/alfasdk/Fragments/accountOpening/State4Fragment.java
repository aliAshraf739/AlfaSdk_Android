package com.example.alfasdk.Fragments.accountOpening;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.alfasdk.AccountOpeningActivity;
import com.example.alfasdk.Models.AccountOpening.AccountOpeningObject;
import com.example.alfasdk.R;
import com.example.alfasdk.Util.Util;
import com.google.android.material.textfield.TextInputEditText;


public class State4Fragment extends Fragment implements View.OnClickListener {

    private static final String TAG = "State4Fragment";

    private ImageView ivBack;
    private TextView tvTitle;
    private TextInputEditText etAccountName;
    private TextInputEditText etBankName;
    private TextInputEditText etIbanNumber;
//    private TextInputEditText etBranchName;
//    private TextInputEditText etBranchCode;
//    private TextInputEditText etBranchAddress;

    private AccountOpeningObject obj;
    private RadioGroup radioGroup;
    private Button btnNext;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_state4, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
        setData();
    }

    private void initViews(View view) {
        obj = ((AccountOpeningActivity) requireActivity()).accountOpeningObject;

        ivBack = view.findViewById(R.id.ivBack);
        tvTitle = view.findViewById(R.id.tvTitle);
        etAccountName = view.findViewById(R.id.etAccountName);
        etBankName = view.findViewById(R.id.etBankName);
        etIbanNumber = view.findViewById(R.id.etIbanNumber);
//        etBranchName = view.findViewById(R.id.etBranchName);
//        etBranchCode = view.findViewById(R.id.etBranchCode);
//        etBranchAddress = view.findViewById(R.id.etBranchAddress);
        radioGroup = view.findViewById(R.id.radioGroup);
        btnNext = view.findViewById(R.id.btnNext);


        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkId) {
                if(checkId==R.id.radio1){
                    obj.setNOMINEE("Y");
                }else{
                    obj.setNOMINEE("N");
                }
            }
        });

        ivBack.setOnClickListener(this);
        btnNext.setOnClickListener(this);

    }

    private void setData() {
        etBankName.setText(obj.getBANKNAME());
        etIbanNumber.setText(obj.getIBAN());
        obj.setNOMINEE("Y");
//        etBranchName.setText(obj.getBRANCHNAME());
//        etBranchCode.setText(obj.getBRANCHCODE());
//        etBranchAddress.setText(obj.getBRANCHADDRESS());

        setInputsEditAble();

    }

    private void setInputsEditAble() {
        if(obj.getBANKNAME().isEmpty() || obj.getBANKNAME()==null){
            Util.setInputEditable(etBankName, true);
        }

        if(obj.getIBAN().isEmpty() || obj.getIBAN()==null){
            Util.setInputEditable(etIbanNumber, true);
        }
    }


    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.btnNext){
            if(isValidInputs()){
                if(obj.getNOMINEE().equals("Y")){
                    Log.e(TAG, "onClick: 5");
                    setNextStepColor(7);
                    Util.performNavigation(requireActivity(), R.id.action_state4Fragment_to_state5Fragment);
                }else{
                    Log.e(TAG, "onClick: 6");
                    setNextStepColor(6);
                    resetNomineeData();
                    Util.performNavigation(requireActivity(), R.id.action_state4Fragment_to_state6Fragment);
                }
            }
        }
        else if(view.getId()==R.id.ivBack){
            requireActivity().onBackPressed();
        }
    }

    private void setNextStepColor(int steps) {
        ((AccountOpeningActivity) requireActivity()).stepView.setStepsNumber(steps);
    }

    private void resetNomineeData() {
        obj.setNAMENMN("");
        obj.setRELATIONSHIPNMN("");
        obj.setUINTYPENMN("");
        obj.setUINNMN("");
        obj.setCNICEXPIRYDATENMN("");
        obj.setCNICISSUEDATENMN("");
        obj.setCNICISSUEPLACENMN("");
    }

    private Boolean isValidInputs() {

        if(etAccountName.getText().toString().isEmpty()){
            //Util.setInputEditable(etBankName,true);
            Util.setInputError(etAccountName);
            return false;
        }else{
            obj.setBANKACCOUNTTITLE(etAccountName.getText().toString());
        }

        if(etBankName.getText().toString().isEmpty()){
            Util.setInputError(etBankName);
            return false;
        }else{
            obj.setBANKNAME(etBankName.getText().toString());
        }

        if(etIbanNumber.getText().toString().isEmpty()){
            Util.setInputError(etIbanNumber);
            return false;
        }else {
            obj.setIBAN(etIbanNumber.getText().toString());
        }

        return true;
    }

    @Override
    public void onStart() {
        super.onStart();
        ((AccountOpeningActivity) requireActivity()).stepView.go(3, true);
    }

}