package com.example.alfasdk.Fragments.accountOpening;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.alfasdk.AccountOpeningActivity;
import com.example.alfasdk.Models.AccountOpening.AccountOpeningObject;
import com.example.alfasdk.R;
import com.example.alfasdk.Util.Alert;
import com.example.alfasdk.Util.Util;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;


public class State6Fragment extends Fragment implements View.OnClickListener {

    private static final String TAG = "State5Fragment";

    private ImageView ivBack;
    private TextView tvTitle;
    private AutoCompleteTextView atvOccupation;
    private TextInputLayout textInputLayoutOther;
    private TextInputEditText etOtherOccupation;

    private AutoCompleteTextView atvGrossAnnualIncome;
    private TextInputEditText etIncomeSource;
    private TextInputEditText etEmployerName;
    private TextInputEditText etJobTitle;
    private TextInputEditText etDepartment;
    private TextInputEditText etEmployerBusiness;
    private Button btnNext;

    private AccountOpeningObject obj;

    //Validators
    boolean isOccupationEnabled = false;
    boolean isOtherOccupationEnabled = false;

    boolean isEmployerNameEnabled = false;
    boolean isJobTitleEnabled = false;
    boolean isDepartmentEnabledEnabled = false;
    boolean isEmployerAddressEnabled = false;

    String[] mListOccupations = { "Agriculture", "Business", "Housewife", "Household", "Retired Person", "Student", "Business Executive", "Industrialist", "Professional", "Service", "Govt./Public Sector", "Others" };
    String[] mListGrossAnnualIncomes = { "Below Rs. 100,000", "Rs. 100,001 - Rs. 250,000", "Rs. 250,001 - Rs. 500,000", "Rs. 500,001 - Rs. 1,000,000", "Rs. 1,000,001 - Rs. 2,500,000", "Above Rs. 2,500,001" };
    String[] mListIncomeItems = { "100,000", "100,001", "250,001", "500,001", "1,000,001", "2,500,001" };


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_state6, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
        setData();
    }

    private void initViews(View view) {


        ivBack = view.findViewById(R.id.ivBack);
        tvTitle = view.findViewById(R.id.tvTitle);
        atvOccupation = view.findViewById(R.id.atvOccupation);
        etOtherOccupation = view.findViewById(R.id.etOtherOccupation);
        textInputLayoutOther = view.findViewById(R.id.textInputLayoutOther);

        atvGrossAnnualIncome = view.findViewById(R.id.atvGrossAnnualIncome);
        etIncomeSource = view.findViewById(R.id.etIncomeSource);
        etEmployerName = view.findViewById(R.id.etEmployerName);
        etJobTitle = view.findViewById(R.id.etJobTitle);
        etDepartment = view.findViewById(R.id.etDepartment);
        etEmployerBusiness = view.findViewById(R.id.etEmployerBusiness);
        btnNext = view.findViewById(R.id.btnNext);

        ivBack.setOnClickListener(this);
        btnNext.setOnClickListener(this);
        atvOccupation.setOnClickListener(this);
        atvGrossAnnualIncome.setOnClickListener(this);

        atvOccupation.setOnItemClickListener((adapterView, view1, i, l) -> {
            if(mListOccupations[i].equals("Others")){
                isOtherOccupationEnabled = true;
                textInputLayoutOther.setVisibility(View.VISIBLE);
            }else{
                isOtherOccupationEnabled = false;
                textInputLayoutOther.setVisibility(View.GONE);
            }
        });

        atvGrossAnnualIncome.setOnItemClickListener((adapterView, view1, i, l) -> {
            obj.setGROSSANNUALINCOME(mListIncomeItems[i]);
        });

    }

    private void setData() {
        obj = ((AccountOpeningActivity) requireActivity()).accountOpeningObject;
        atvOccupation.setText(obj.getOCCUPATION());

        if(obj.getOCCUPATION().equals("Housewife") || obj.getOCCUPATION().equals("Household")){
            isEmployerNameEnabled=false;
            isJobTitleEnabled=false;
            isDepartmentEnabledEnabled=false;
            isEmployerAddressEnabled=false;

            etEmployerName.setVisibility(View.GONE);
            etJobTitle.setVisibility(View.GONE);
            etDepartment.setVisibility(View.GONE);
            etEmployerBusiness.setVisibility(View.GONE);
        }
        else if(obj.getOCCUPATION().equals("Others)") || obj.getOCCUPATION().equals("Others (Specify)")){
            isOtherOccupationEnabled = true;
        }
        else{
            isEmployerNameEnabled=true;
            isJobTitleEnabled=true;
            isDepartmentEnabledEnabled=true;
            isEmployerAddressEnabled=true;

            etEmployerName.setVisibility(View.VISIBLE);
            etJobTitle.setVisibility(View.VISIBLE);
            etDepartment.setVisibility(View.VISIBLE);
            etEmployerBusiness.setVisibility(View.VISIBLE);
        }

        ArrayAdapter<String> arrayAdapter1 = new ArrayAdapter<>(requireActivity(), android.R.layout.simple_list_item_1, mListOccupations);
        atvOccupation.setAdapter(arrayAdapter1);

        ArrayAdapter<String> arrayAdapter2 = new ArrayAdapter<>(requireActivity(), android.R.layout.simple_list_item_1, mListGrossAnnualIncomes);
        atvGrossAnnualIncome.setAdapter(arrayAdapter2);

        setInputsEditAble();

    }

    private void setInputsEditAble() {
        if(obj.getOCCUPATION().isEmpty() || obj.getOCCUPATION()==null){
            isOccupationEnabled=true;
        }

        if(isOtherOccupationEnabled){
            textInputLayoutOther.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.atvOccupation){
            if(isOccupationEnabled){
                atvOccupation.showDropDown();
            }
        }
        else if(view.getId()==R.id.atvGrossAnnualIncome){
            atvGrossAnnualIncome.showDropDown();
        }
        else if(view.getId()==R.id.btnNext){
            if(isValidInputs()){
                Util.performNavigation(requireActivity(), R.id.action_state6Fragment_to_state10Fragment);
            }
        }
        else if(view.getId()==R.id.ivBack){
            requireActivity().onBackPressed();
        }
    }

    private Boolean isValidInputs() {

        if(atvOccupation.getText().toString().isEmpty()){
            //Show Alert
            Alert.show(requireActivity(), "", "Please select your Occupation.");
            return false;
        }else{
            obj.setOCCUPATION(atvOccupation.getText().toString());
        }

        if(isOtherOccupationEnabled){
            if(etOtherOccupation.getText().toString().isEmpty()){
                Util.setInputError(etOtherOccupation);
                return false;
            }else{
                obj.setOCCUPATION(etOtherOccupation.getText().toString());
            }
        }

        if(atvGrossAnnualIncome.getText().toString().isEmpty()){
            //Show Alert
            Alert.show(requireActivity(), "", "Please select your Gross Annual Income.");
            return false;
        }

        if(etIncomeSource.getText().toString().isEmpty()){
            //Show Alert
            Util.setInputError(etIncomeSource);
            return false;
        }else{
            obj.setSOURCEOFINCOME(etIncomeSource.getText().toString());
        }

        if(isEmployerNameEnabled){
            if(etEmployerName.getText().toString().isEmpty()){
                Util.setInputError(etEmployerName);
                return false;
            }else{
                obj.setEMPLOYERNAME(etEmployerName.getText().toString());
            }
        }else{
            obj.setEMPLOYERNAME("");
        }

        if(isJobTitleEnabled){
            if(etJobTitle.getText().toString().isEmpty()){
                Util.setInputError(etJobTitle);
                return false;
            }else{
                obj.setJOBTITLE(etJobTitle.getText().toString());
            }
        }else{
            obj.setJOBTITLE("");
        }

        if(isDepartmentEnabledEnabled){
            if(etDepartment.getText().toString().isEmpty()){
                Util.setInputError(etDepartment);
                return false;
            }else{
                obj.setDEPARTMENT(etDepartment.getText().toString());
            }
        }else{
            obj.setDEPARTMENT("");
        }

        if(isEmployerAddressEnabled){
            if(etEmployerBusiness.getText().toString().isEmpty()){
                Util.setInputError(etEmployerBusiness);
                return false;
            }else{
                obj.setEMPLOYERADDRESS(etEmployerBusiness.getText().toString());
            }
        }else{
            obj.setEMPLOYERADDRESS("");
        }

        return true;
    }

    @Override
    public void onStart() {
        super.onStart();
        if(obj.getNOMINEE().equals("Y")){
            ((AccountOpeningActivity) requireActivity()).stepView.go(5, true);
        }else{
            ((AccountOpeningActivity) requireActivity()).stepView.go(4, true);
        }
    }

}