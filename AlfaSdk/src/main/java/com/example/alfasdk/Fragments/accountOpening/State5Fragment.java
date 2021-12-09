package com.example.alfasdk.Fragments.accountOpening;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.InputFilter;
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

import com.example.alfasdk.AccountOpeningActivity;
import com.example.alfasdk.Models.AccountOpening.AccountOpeningObject;
import com.example.alfasdk.R;
import com.example.alfasdk.Util.Alert;
import com.example.alfasdk.Util.MyDatePickerDialog;
import com.example.alfasdk.Util.Util;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Calendar;


public class State5Fragment extends Fragment implements View.OnClickListener {

    private static final String TAG = "State5Fragment";

    private ImageView ivBack;
    private TextView tvTitle;
    private TextInputEditText etName;
    private TextInputEditText etRelationShip;
    private AutoCompleteTextView atvIdentificationType;
    private TextInputEditText etCnicPassportNumber;
    private TextInputEditText etExpiryDate;
    private CheckBox checkBox;
    private TextInputEditText etIssueDate;
    private TextInputEditText etIssuePlace;
    private TextInputLayout textInputLayoutPlace;
    private Button btnNext;


    private AccountOpeningObject obj;
    boolean isPlaceOfIssueEnabled = false;
    String[] mListIdentificationTypes = { "CNIC", "SNIC", "Passport" };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_state5, container, false);
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
        etName = view.findViewById(R.id.etName);
        etRelationShip = view.findViewById(R.id.etRelationShip);
        atvIdentificationType = view.findViewById(R.id.atvIdentificationType);
        etCnicPassportNumber = view.findViewById(R.id.etCnicPassportNumber);
        etIssueDate = view.findViewById(R.id.etIssueDate);
        etExpiryDate = view.findViewById(R.id.etExpiryDate);
        checkBox = view.findViewById(R.id.checkBox);
        etIssuePlace = view.findViewById(R.id.etIssuePlace);
        textInputLayoutPlace = view.findViewById(R.id.textInputLayoutPlace);
        btnNext = view.findViewById(R.id.btnNext);

        ivBack.setOnClickListener(this);
        etExpiryDate.setOnClickListener(this);
        etIssueDate.setOnClickListener(this);
        btnNext.setOnClickListener(this);

        atvIdentificationType.setOnClickListener(this);
        atvIdentificationType.setOnItemClickListener((adapterView, view1, i, l) -> {
            Log.e(TAG, "initViews: ");
            if(mListIdentificationTypes[i].equals("CNIC") || mListIdentificationTypes[i].equals("SNIC")){
                setMaxLength(etCnicPassportNumber, 13);
                isPlaceOfIssueEnabled = false;
                textInputLayoutPlace.setVisibility(View.GONE);
                obj.setUINTYPENMN(mListIdentificationTypes[i]);
            }else{
                setMaxLength(etCnicPassportNumber, 25);
                isPlaceOfIssueEnabled = true;
                textInputLayoutPlace.setVisibility(View.VISIBLE);
                obj.setUINTYPENMN("NICOP");
            }
        });

        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    obj.setCNICLIFETIMENMN("Y");
                    etExpiryDate.setText("");
                    etExpiryDate.setEnabled(false);
                }else{
                    obj.setCNICLIFETIMENMN("Null");
                    etExpiryDate.setEnabled(true);
                }
            }
        });

    }

    private void setMaxLength(TextInputEditText textInputEditText, int length) {
        InputFilter[] FilterArray = new InputFilter[1];
        FilterArray[0] = new InputFilter.LengthFilter(length);
        textInputEditText.setFilters(FilterArray);
    }

    private void setData() {
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(requireActivity(), android.R.layout.simple_list_item_1, mListIdentificationTypes);
        atvIdentificationType.setAdapter(arrayAdapter);
    }

    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.ivBack){
            requireActivity().onBackPressed();
        }
        else if(view.getId()==R.id.atvIdentificationType){
            atvIdentificationType.showDropDown();
        }
        else if(view.getId()==R.id.etIssueDate){
            pickDate(etIssueDate);
        }
        else if(view.getId()==R.id.etExpiryDate){
            if(!checkBox.isChecked()){
                pickDate(etExpiryDate);
            }
        }
        else if(view.getId()==R.id.btnNext){
            if(isValidInputs()){
                Log.e(TAG, "placeOfIssue: "+ obj.getISSUEPLACE());
                Util.performNavigation(requireActivity(), R.id.action_state5Fragment_to_state6Fragment);
            }
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

        if(etName.getText().toString().isEmpty()){
            //Util.setInputEditable(etCnicPassportNumber,true);
            Util.setInputError(etName);
            return false;
        }else{
            obj.setNAMENMN(etName.getText().toString());
        }

        if(etRelationShip.getText().toString().isEmpty()){
            //Util.setInputEditable(etCnicPassportNumber,true);
            Util.setInputError(etRelationShip);
            return false;
        }else{
            obj.setRELATIONSHIPNMN(etRelationShip.getText().toString());
        }

        if(atvIdentificationType.getText().toString().isEmpty()){
            //Show Alert
            Alert.show(requireActivity(), "", "Please select an Identification type.");
            return false;
        }else{
            obj.setUINTYPENMN(atvIdentificationType.getText().toString());
        }

        if(etCnicPassportNumber.getText().toString().isEmpty()){
            //Util.setInputEditable(etCnicPassportNumber,true);
            Util.setInputError(etCnicPassportNumber);
            return false;
        }else{
            obj.setUINNMN(etCnicPassportNumber.getText().toString());
        }

        if(etIssueDate.getText().toString().isEmpty()){
            //Show Alert
            Alert.show(requireActivity(), "", "Please select an Issue Date.");
            return false;
        }else{
            obj.setCNICISSUEDATENMN(etIssueDate.getText().toString());
        }

        if(!checkBox.isChecked()){
            if(etExpiryDate.getText().toString().isEmpty()){
                //Show Alert
                Alert.show(requireActivity(), "", "Please select an Expiry Date.");
                return false;
            }else{
                obj.setCNICEXPIRYDATENMN(etExpiryDate.getText().toString());
            }
        }else{
            obj.setCNICEXPIRYDATENMN("");
        }

        if(isPlaceOfIssueEnabled){
            if(etIssuePlace.getText().toString().isEmpty()){
                //Util.setInputEditable(etIssuePlace,true);
                Util.setInputError(etIssuePlace);
                return false;
            }else{
                obj.setCNICISSUEPLACENMN(etIssuePlace.getText().toString());
            }
        }else{
            obj.setCNICISSUEPLACENMN("");
        }

        return true;
    }

    @Override
    public void onStart() {
        super.onStart();
        ((AccountOpeningActivity) requireActivity()).stepView.go(4, true);
    }

}