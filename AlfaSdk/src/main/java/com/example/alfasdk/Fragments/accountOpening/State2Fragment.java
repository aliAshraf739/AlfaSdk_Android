package com.example.alfasdk.Fragments.accountOpening;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
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
import com.google.android.material.textfield.TextInputLayout;

import java.util.Calendar;

public class State2Fragment extends Fragment implements View.OnClickListener {

    private static final String TAG = "State2Fragment";

    private ImageView ivBack;
    private TextView tvTitle;
    private AutoCompleteTextView atvIdentificationType;
    private TextInputEditText etCnicPassportNumber;
    private TextInputEditText etExpiryDate;
    private TextInputEditText etIssueDate;
    private TextInputEditText etIssuePlace;
    private TextInputLayout textInputLayoutPlace;
    private CheckBox checkBox;
    private Button btnNext;

    private AccountOpeningObject obj;
    boolean isIssueDateEnabled = false;
    boolean isExpiryDateEnabled = false;
    boolean isPlaceOfIssueEnabled = false;

    String[] mListIdentificationTypes = { "CNIC", "SNIC", "Passport" };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_state2, container, false);
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
        atvIdentificationType = view.findViewById(R.id.atvIdentificationType);
        etCnicPassportNumber = view.findViewById(R.id.etCnicPassportNumber);
        etCnicPassportNumber = view.findViewById(R.id.etCnicPassportNumber);
        etExpiryDate = view.findViewById(R.id.etExpiryDate);
        etIssueDate = view.findViewById(R.id.etIssueDate);
        textInputLayoutPlace = view.findViewById(R.id.textInputLayoutExpiryDate);
        etIssuePlace = view.findViewById(R.id.etIssuePlace);
        textInputLayoutPlace = view.findViewById(R.id.textInputLayoutPlace);
        checkBox = view.findViewById(R.id.checkBox);
        btnNext = view.findViewById(R.id.btnNext);

        ivBack.setOnClickListener(this);
        etIssueDate.setOnClickListener(this);
        etExpiryDate.setOnClickListener(this);
        btnNext.setOnClickListener(this);

        atvIdentificationType.setOnClickListener(this);
        atvIdentificationType.setOnItemClickListener((adapterView, view1, i, l) -> {
            Log.e(TAG, "initViews: ");
            if(mListIdentificationTypes[i].equals("CNIC") || mListIdentificationTypes[i].equals("SNIC")){
                isPlaceOfIssueEnabled = false;
                textInputLayoutPlace.setVisibility(View.GONE);
                obj.setUINTYPE(mListIdentificationTypes[i]);
            }else{
                isPlaceOfIssueEnabled = true;
                textInputLayoutPlace.setVisibility(View.VISIBLE);
                obj.setUINTYPE("NICOP");
            }
        });

        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    obj.setLIFETIME("Y");
                    etExpiryDate.setText("");
                    etExpiryDate.setEnabled(false);
                }else{
                    obj.setLIFETIME("Null");
                    etExpiryDate.setEnabled(true);
                }
            }
        });

    }

    private void setData() {
        obj = ((AccountOpeningActivity) requireActivity()).accountOpeningObject;
        etCnicPassportNumber.setText(obj.getUIN());
        etIssueDate.setText(obj.getISSUEDATE());
        etExpiryDate.setText(obj.getDATEOFEXPIRY());

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(requireActivity(), android.R.layout.simple_list_item_1, mListIdentificationTypes);
        atvIdentificationType.setAdapter(arrayAdapter);


        setInputsEditAble();

    }

    private void setInputsEditAble() {

        if(etCnicPassportNumber.getText().toString().isEmpty()){
            Util.setInputEditable(etCnicPassportNumber, true);
        }

        if(obj.getISSUEDATE().isEmpty() || obj.getISSUEDATE()==null){
            isIssueDateEnabled = true;
        }

        if(obj.getDATEOFEXPIRY().isEmpty() || obj.getDATEOFEXPIRY()==null){
            checkBox.setEnabled(true);
            isExpiryDateEnabled = true;
        }

    }

    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.atvIdentificationType){
            atvIdentificationType.showDropDown();
        }
        else if(view.getId()==R.id.etIssueDate){
            if(isIssueDateEnabled){
                pickDate(etIssueDate);
            }
        }
        else if(view.getId()==R.id.etExpiryDate){
            if(isExpiryDateEnabled){
                if(!checkBox.isChecked()){
                    pickDate(etExpiryDate);
                }
            }
        }
        else if(view.getId()==R.id.btnNext){
            if(isValidInputs()){
                Log.e(TAG, "placeOfIssue: "+ obj.getISSUEPLACE());
                Util.performNavigation(requireActivity(), R.id.action_state2Fragment_to_state3Fragment);
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

        if(atvIdentificationType.getText().toString().isEmpty()){
            //Show Alert
            Alert.show(requireActivity(), "", "Please select an Identification type.");
            return false;
        }

        if(etCnicPassportNumber.getText().toString().isEmpty()){
            Util.setInputError(etCnicPassportNumber);
            return false;
        }else{
            obj.setUIN(etCnicPassportNumber.getText().toString());
        }

        if(etIssueDate.getText().toString().isEmpty()){
            //Show Alert
            Alert.show(requireActivity(), "", "Please select an Issue Date.");
            return false;
        }else{
            obj.setISSUEDATE(etIssueDate.getText().toString());
        }

        if(!checkBox.isChecked()){
            if(etExpiryDate.getText().toString().isEmpty()){
                //Show Alert
                Alert.show(requireActivity(), "", "Please select an Expiry Date.");
                return false;
            }else{
                obj.setDATEOFEXPIRY(etExpiryDate.getText().toString());
            }
        }else{
            obj.setDATEOFEXPIRY("");
        }

        if(isPlaceOfIssueEnabled){
            if(etIssuePlace.getText().toString().isEmpty()){
                //Util.setInputEditable(etIssuePlace,true);
                Util.setInputError(etIssuePlace);
                return false;
            }else{
                obj.setISSUEPLACE(atvIdentificationType.getText().toString());
            }
        }else{
            obj.setISSUEPLACE("");
        }

        return true;
    }

    @Override
    public void onStart() {
        super.onStart();
        ((AccountOpeningActivity) requireActivity()).stepView.go(1, true);
    }

}
