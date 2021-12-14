package com.example.alfasdk.Fragments.accountOpening;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.alfasdk.AccountOpeningActivity;
import com.example.alfasdk.Models.AccountOpening.AccountOpeningObject;
import com.example.alfasdk.R;
import com.example.alfasdk.Util.Alert;
import com.example.alfasdk.Util.MyDatePickerDialog;
import com.example.alfasdk.Util.Util;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Calendar;

public class State1Fragment extends Fragment implements View.OnClickListener {

    private static final String TAG = "State1Fragment";

    private ImageView ivBack;
    private TextView tvTitle;
    private AutoCompleteTextView atvTitle;
    private TextInputEditText etName;
    private TextInputEditText etFatherName;
    private TextInputEditText etBirthDate;
    private TextInputEditText etNationality;
    private AutoCompleteTextView atvMartialStatus;
    private AutoCompleteTextView atvResidentialStatus;
    private Button btnNext;

    private AccountOpeningObject obj;
    //String[] mListResidentialStatus = { "Resident", "Non-Resident" };

    private boolean isBirthDateEnabled = false;
    private boolean isMartialStatusEnabled = false;
    private String[] mListTitles = { "Mr.", "Mrs.", "Ms." };
    private String[] mListMartialStatus = { "Single", "Married" };


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_state1, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initViews(view);

        setData();

        ivBack.setOnClickListener(view1 -> {
            requireActivity().onBackPressed();
        });


    }

    private void initViews(View view) {
        ivBack = view.findViewById(R.id.ivBack);
        tvTitle = view.findViewById(R.id.tvTitle);
        atvTitle = view.findViewById(R.id.atvTitle);
        etName = view.findViewById(R.id.etName);
        etFatherName = view.findViewById(R.id.etFatherName);
        etBirthDate = view.findViewById(R.id.etBirthDate);
        etNationality = view.findViewById(R.id.etNationality);
        atvMartialStatus = view.findViewById(R.id.atvMartialStatus);
        atvResidentialStatus = view.findViewById(R.id.atvResidentialStatus);
        btnNext = view.findViewById(R.id.btnNext);

        etBirthDate.setOnClickListener(this);
        ivBack.setOnClickListener(this);
        btnNext.setOnClickListener(this);

        atvTitle.setOnClickListener(this);
        atvMartialStatus.setOnClickListener(this);

        atvTitle.setOnItemClickListener((adapterView, view1, i, l) -> {
            obj.setSALUTATION(mListTitles[i]);
        });
        atvMartialStatus.setOnItemClickListener((adapterView, view1, i, l) -> {
            if(mListMartialStatus[i].equals("Single")){
                obj.setMARITALSTATUS("S");
                obj.setRELATIONSHIP("F");
            }else{
                obj.setMARITALSTATUS("M");
                obj.setRELATIONSHIP("H");
            }
        });
    }

    private void setData() {
        obj = ((AccountOpeningActivity) requireActivity()).accountOpeningObject;
        etName.setText(obj.getNAME());
        etFatherName.setText(obj.getFATHERHUSBANDNAME());
        etBirthDate.setText(obj.getDATEOFBIRTH());
        etNationality.setText("Pakistani");
        atvResidentialStatus.setText("Pakistani");

        if(obj.getMARITALSTATUS().equals("S")){
            atvMartialStatus.setText("Single");
            obj.setRELATIONSHIP("F");
        }
        else if(obj.getMARITALSTATUS().equals("M")){
            atvMartialStatus.setText("Married");
            obj.setRELATIONSHIP("H");
        }

        ArrayAdapter<String> arrayAdapter1 = new ArrayAdapter<>(requireActivity(), android.R.layout.simple_list_item_1, mListTitles);
        atvTitle.setAdapter(arrayAdapter1);

        ArrayAdapter<String> arrayAdapter2 = new ArrayAdapter<>(requireActivity(), android.R.layout.simple_list_item_1, mListMartialStatus);
        atvMartialStatus.setAdapter(arrayAdapter2);


        setInputsEditAble();

    }

    private void setInputsEditAble() {

        if(etName.getText().toString().isEmpty()){
            Util.setInputEditable(etName, true);
        }

        if(etFatherName.getText().toString().isEmpty()){
            Util.setInputEditable(etFatherName, true);
        }

        if(obj.getDATEOFBIRTH().isEmpty() || obj.getDATEOFBIRTH()==null){
            isBirthDateEnabled = true;
        }

        if(obj.getMARITALSTATUS().isEmpty() || obj.getMARITALSTATUS()==null){
            isMartialStatusEnabled=true;
        }

    }

    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.atvTitle){
            atvTitle.showDropDown();
        }
        else if(view.getId()==R.id.etBirthDate){
            if(isBirthDateEnabled){
                pickDate(etBirthDate);
            }
        }
        else if(view.getId()==R.id.atvMartialStatus){
            if(isMartialStatusEnabled){
                atvMartialStatus.showDropDown();
            }
        }
        else if(view.getId()==R.id.btnNext){
            if(isValidInputs()){
                Log.e(TAG, "Residential Status: ");
                Util.performNavigation(requireActivity(), R.id.action_state1Fragment_to_state2Fragment);
            }
        }
        else if(view.getId()==R.id.ivBack){
            requireActivity().onBackPressed();
        }
    }

    private void pickDate(TextInputEditText textInputEditText) {
        MyDatePickerDialog dialog = new MyDatePickerDialog(requireActivity());
        dialog.setTitle("Select Date");
        dialog.showDatePicker((view, year, month, dayOfMonth) -> {
            //Date select callback
            textInputEditText.setText(dayOfMonth+"/"+month+"/"+year);
        }, Calendar.getInstance());
    }


    private Boolean isValidInputs() {

        if(atvTitle.getText().toString().isEmpty()){
            Alert.show(requireActivity(), "", "Please select your Title.");
            return false;
        }

        if(etName.getText().toString().isEmpty()){
            Util.setInputError(etName);
            return false;
        }else{
            obj.setNAME(etName.getText().toString());
        }

        if(etFatherName.getText().toString().isEmpty()){
            Util.setInputError(etFatherName);
            return false;
        }else{
            obj.setFATHERHUSBANDNAME(etFatherName.getText().toString());
        }

        if(etBirthDate.getText().toString().isEmpty()){
            //Show Alert
            Alert.show(requireActivity(), "", "Please select your Date of Birth.");
            return false;
        }else{
            obj.setDATEOFBIRTH(etBirthDate.getText().toString());
        }

        if(etNationality.getText().toString().isEmpty()){
            //Show Alert
            Alert.show(requireActivity(), "", "Please select your Nationality.");
            return false;
        }else{
            etNationality.setText("Pakistani");
            obj.setNATIONALITY("Pakistani");
        }

        if(atvMartialStatus.getText().toString().isEmpty()){
            //Show Alert
            Alert.show(requireActivity(), "", "Please select your Martial Status.");
            return false;
        }

        if(atvResidentialStatus.getText().toString().isEmpty()){
            //Show Alert
            Alert.show(requireActivity(), "", "Please select your Residential Status.");
            return false;
        }else{
            obj.setRESIDENTIALSTATUS("Pakistani");
        }

        if(obj.getZAKATSTATUS().isEmpty()){
            obj.setZAKATSTATUS("A");
        }

        return true;
    }

    @Override
    public void onStart() {
        super.onStart();
        ((AccountOpeningActivity) requireActivity()).stepView.go(0, true);
    }
}
