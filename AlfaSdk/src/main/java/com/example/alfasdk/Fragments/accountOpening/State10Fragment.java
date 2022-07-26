package com.example.alfasdk.Fragments.accountOpening;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import com.example.alfasdk.AccountOpeningActivity;
import com.example.alfasdk.Const.ConnectionDetector;
import com.example.alfasdk.Const.Constants;
import com.example.alfasdk.Models.AccountOpening.AccountOpeningObject;
import com.example.alfasdk.Models.AccountOpening.AccountOpeningRequestResponse;
import com.example.alfasdk.Network.RetrofitApi;
import com.example.alfasdk.R;
import com.example.alfasdk.Util.Alert;
import com.example.alfasdk.Util.FileMetaData;
import com.example.alfasdk.Util.Loading;
import com.example.alfasdk.Util.MyClickableSpan;
import com.example.alfasdk.Util.Util;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.material.textfield.TextInputEditText;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import org.jetbrains.annotations.NotNull;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class State10Fragment extends Fragment implements View.OnClickListener {

    private static final String TAG = "State10Fragment";

    private ImageView ivBack;
    private TextView tvTitle;

    private ImageView ivCnicFront;
    private TextView tvFileName1;
    private ImageView ivAdd1;
    private ImageView ivRemove1;
    private RelativeLayout rlCnicFront;

    private ImageView ivCnicBack;
    private TextView tvFileName2;
    private ImageView ivAdd2;
    private ImageView ivRemove2;
    private RelativeLayout rlCnicBack;

    private ImageView ivApplicantSignature;
    private TextView tvFileName3;
    private ImageView ivAdd3;
    private ImageView ivRemove3;
    private RelativeLayout rlSignature;


    private ImageView ivProofResidentialAddress;
    private TextView tvFileName4;
    private ImageView ivAdd4;
    private ImageView ivRemove4;
    
    //    private ImageView ivProofIncomeSource;
//    private TextView tvFileName5;
//    private ImageView ivAdd5;
//    private ImageView ivRemove5;
//
//    private ImageView ivProofEmployerAddress;
//    private TextView tvFileName6;
//    private ImageView ivAdd6;
//    private ImageView ivRemove6;
    
    private ImageView ivZakatForm;
    private TextView tvFileName7;
    private ImageView ivAdd7;
    private ImageView ivRemove7;
    private RelativeLayout rlZakatForm;

    private ImageView ivNomineeCnicFront;
    private TextView tvFileName8;
    private ImageView ivAdd8;
    private ImageView ivRemove8;
    private RelativeLayout rlNomineeFront;

    private ImageView ivNomineeCnicBack;
    private TextView tvFileName9;
    private ImageView ivAdd9;
    private ImageView ivRemove9;
    private RelativeLayout rlNomineeBack;

    private TextInputEditText etReferenceCode;

    //
//    private ImageView ivForm;
//    private TextView tvFileName10;
//    private ImageView ivAdd10;
//    private ImageView ivRemove10;

    private CheckBox checkbox;
    private TextView tvTermsAndPrivacyPolicy;
    private Button btnSubmit;



    private int imagePosition = -1;
    private Loading loading;
    private AccountOpeningObject accountOpeningObject;

    private Boolean isCnicFrontEnabled = false;
    private Boolean isCnicBackEnabled = false;
    private Boolean isSignatureEnabled = false;
    private Boolean isZakatFormEnabled = false;
    private Boolean isNmnCnicFrontEnabled = false;
    private Boolean isNmnCnicBackEnabled = false;
    private Uri proofCnicFront, proofCnicBack, proofSignature, proofResidentialAddress, proofNmnCnicFront, proofNmnCnicBack, proofZakatDecalration;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_state10, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
        checkData();
        setData();
    }


    private void initViews(View view) {
        loading = new Loading(requireActivity(), "");
        accountOpeningObject = ((AccountOpeningActivity) requireActivity()).accountOpeningObject;

        ivBack = view.findViewById(R.id.ivBack);
        tvTitle = view.findViewById(R.id.tvTitle);

        ivCnicFront = view.findViewById(R.id.ivCnicFront);
        tvFileName1 = view.findViewById(R.id.tvFileName1);
        ivAdd1 = view.findViewById(R.id.ivAdd1);
        ivRemove1 = view.findViewById(R.id.ivRemove1);
        rlCnicFront = view.findViewById(R.id.rlCnicFront);

        ivCnicBack = view.findViewById(R.id.ivCnicBack);
        tvFileName2 = view.findViewById(R.id.tvFileName2);
        ivAdd2 = view.findViewById(R.id.ivAdd2);
        ivRemove2 = view.findViewById(R.id.ivRemove2);
        rlCnicBack = view.findViewById(R.id.rlCnicBack);

        ivApplicantSignature = view.findViewById(R.id.ivApplicantSignature);
        tvFileName3 = view.findViewById(R.id.tvFileName3);
        ivAdd3 = view.findViewById(R.id.ivAdd3);
        ivRemove3 = view.findViewById(R.id.ivRemove3);
        rlSignature = view.findViewById(R.id.rlSignature);

//        ivProofResidentialAddress = view.findViewById(R.id.ivProofResidentialAddress);
//        tvFileName4 = view.findViewById(R.id.tvFileName4);
//        ivAdd4 = view.findViewById(R.id.ivAdd4);
//        ivRemove4 = view.findViewById(R.id.ivRemove4);

        //        ivProofIncomeSource = view.findViewById(R.id.ivProofIncomeSource);
//        tvFileName5 = view.findViewById(R.id.tvFileName5);
//        ivAdd5 = view.findViewById(R.id.ivAdd5);
//        ivRemove5 = view.findViewById(R.id.ivRemove5);
//
//        ivProofEmployerAddress = view.findViewById(R.id.ivProofEmployerAddress);
//        tvFileName6 = view.findViewById(R.id.tvFileName6);
//        ivAdd6 = view.findViewById(R.id.ivAdd6);
//        ivRemove6 = view.findViewById(R.id.ivRemove6);
//

        ivZakatForm = view.findViewById(R.id.ivZakatForm);
        tvFileName7 = view.findViewById(R.id.tvFileName7);
        ivAdd7 = view.findViewById(R.id.ivAdd7);
        ivRemove7 = view.findViewById(R.id.ivRemove7);
        rlZakatForm = view.findViewById(R.id.rlZakatForm);

        ivNomineeCnicFront = view.findViewById(R.id.ivNomineeCnicFront);
        tvFileName8 = view.findViewById(R.id.tvFileName8);
        ivAdd8 = view.findViewById(R.id.ivAdd8);
        ivRemove8 = view.findViewById(R.id.ivRemove8);
        rlNomineeFront = view.findViewById(R.id.rlNomineeFront);

        ivNomineeCnicBack = view.findViewById(R.id.ivNomineeCnicBack);
        tvFileName9 = view.findViewById(R.id.tvFileName9);
        ivAdd9 = view.findViewById(R.id.ivAdd9);
        ivRemove9 = view.findViewById(R.id.ivRemove9);
        rlNomineeBack = view.findViewById(R.id.rlNomineeBack);

        etReferenceCode = view.findViewById(R.id.etReferenceCode);

        checkbox = view.findViewById(R.id.checkbox);
        tvTermsAndPrivacyPolicy = view.findViewById(R.id.tvTermsAndPrivacyPolicy);
        btnSubmit = view.findViewById(R.id.btnSubmit);

        ivBack.setOnClickListener(this);

        ivAdd1.setOnClickListener(this);
        ivRemove1.setOnClickListener(this);

        ivAdd2.setOnClickListener(this);
        ivRemove2.setOnClickListener(this);

        ivAdd3.setOnClickListener(this);
        ivRemove3.setOnClickListener(this);

        //ivAdd4.setOnClickListener(this);
        //ivRemove4.setOnClickListener(this);

        ivAdd7.setOnClickListener(this);
        ivRemove7.setOnClickListener(this);

        ivAdd8.setOnClickListener(this);
        ivRemove8.setOnClickListener(this);

        ivAdd9.setOnClickListener(this);
        ivRemove9.setOnClickListener(this);


        //        ivAdd5.setOnClickListener(this);
//        ivRemove5.setOnClickListener(this);
//        ivAdd6.setOnClickListener(this);
//        ivRemove6.setOnClickListener(this);
        btnSubmit.setOnClickListener(this);


        setTextSpans();

        checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    accountOpeningObject.setTERMSCONDITIONS("Y");
                }else{
                    accountOpeningObject.setTERMSCONDITIONS("N");
                }
            }
        });
    }

    private void checkData() {
        if(accountOpeningObject.getCNICFRONT().isEmpty() || accountOpeningObject.getCNICFRONT()==null){
            Constants.isCnicFrontDocProvided = false;
        }
        if(accountOpeningObject.getCNICBACK().isEmpty() || accountOpeningObject.getCNICBACK()==null){
            Constants.isCnicBackDocProvided = false;
        }
        if(accountOpeningObject.getSIGNATURE().isEmpty() || accountOpeningObject.getSIGNATURE()==null){
            Constants.isSignatureDocProvided = false;
        }
    }

    private void setData() {

        if(Constants.isCnicFrontDocProvided){
            rlCnicFront.setVisibility(View.VISIBLE);
            Bitmap cnicFrontBitmap = Util.decodeImage(accountOpeningObject.getCNICFRONT());
            if(cnicFrontBitmap!=null){
                ivCnicFront.setImageBitmap(cnicFrontBitmap);
            }
        }else{
            isCnicFrontEnabled = true;
            ivAdd1.setVisibility(View.VISIBLE);
            tvFileName1.setVisibility(View.VISIBLE);
        }

        //        if(accountOpeningObject.getCNICFRONT().isEmpty() || accountOpeningObject.getCNICFRONT()==null){
//
//
//        }else{
//        }


        if(Constants.isCnicBackDocProvided){
            rlCnicBack.setVisibility(View.VISIBLE);
            Bitmap cnicBackBitmap = Util.decodeImage(accountOpeningObject.getCNICBACK());
            if(cnicBackBitmap!=null){
                ivCnicBack.setImageBitmap(cnicBackBitmap);
            }
        }else{
            isCnicBackEnabled = true;
            ivAdd2.setVisibility(View.VISIBLE);
            tvFileName2.setVisibility(View.VISIBLE);
        }

        //        if(accountOpeningObject.getCNICBACK().isEmpty() || accountOpeningObject.getCNICBACK()==null){
//        }else{
//        }


        if(Constants.isCnicBackDocProvided){
            rlSignature.setVisibility(View.VISIBLE);
            Bitmap signatureBitmap = Util.decodeImage(accountOpeningObject.getSIGNATURE());
            if(signatureBitmap!=null){
                ivApplicantSignature.setImageBitmap(signatureBitmap);
            }
        }else{
            isSignatureEnabled = true;
            ivAdd3.setVisibility(View.VISIBLE);
            tvFileName3.setVisibility(View.VISIBLE);
        }

        //        if(accountOpeningObject.getSIGNATURE().isEmpty() || accountOpeningObject.getSIGNATURE()==null){
//        }else{
//        }

        if(accountOpeningObject.getZAKATSTATUS().equals("N")){
            isZakatFormEnabled=true;
            rlZakatForm.setVisibility(View.VISIBLE);
        }else{
            rlZakatForm.setVisibility(View.GONE);
        }

        if(accountOpeningObject.getNOMINEE().equals("Y")){
            isNmnCnicFrontEnabled = true;
            isNmnCnicBackEnabled = true;
            rlNomineeFront.setVisibility(View.VISIBLE);
            rlNomineeBack.setVisibility(View.VISIBLE);
        }else{
            isNmnCnicFrontEnabled = false;
            isNmnCnicBackEnabled = false;
            rlNomineeFront.setVisibility(View.GONE);
            rlNomineeBack.setVisibility(View.GONE);
        }

    }

    @Override
    public void onStart() {
        super.onStart();
        if(accountOpeningObject.getNOMINEE().equals("Y")){
            ((AccountOpeningActivity) requireActivity()).stepView.go(6, true);
        }else{
            ((AccountOpeningActivity) requireActivity()).stepView.go(5, true);
        }
    }

    private void submitData() {

        Log.e(TAG, "Trying on: "+ Constants.ACCOUNT_OPENING_BASE_URL);

        if (!ConnectionDetector.getInstance(requireActivity()).isConnectingToInternet()) {
            Alert.show(requireActivity(), "Error", "No Internet Connection.");
            return;
        }

        Log.e(TAG, "Nationality: "+accountOpeningObject.getNATIONALITY());
        Log.e(TAG, "Zakat Status: "+accountOpeningObject.getZAKATSTATUS());
        Log.e(TAG, "dateOfExpiry: "+accountOpeningObject.getDATEOFEXPIRY());
        Log.e(TAG, "RESIDENTIAL_STATUS: "+accountOpeningObject.getRESIDENTIALSTATUS());
        Log.e(TAG, "Date of Birth: "+accountOpeningObject.getDATEOFBIRTH() );

        Log.e(TAG, "accountOpeningObject: "+accountOpeningObject.toString() );

        loading.show();
        Call<AccountOpeningRequestResponse> call = RetrofitApi.getService().createAccount ( accountOpeningObject );
        call.enqueue(new Callback<AccountOpeningRequestResponse>() {
            @Override
            public void onResponse(@NotNull Call<AccountOpeningRequestResponse> call, @NotNull Response<AccountOpeningRequestResponse> response) {
                loading.dismiss();
                Log.e(TAG, "onResponse: "+ response.toString());
                if (response.code()==500){
                    Alert.show(requireActivity(), "Error", "Something went wrong.");
                    return;
                }
                if(response.code()==200){
                    if(response.isSuccessful() && response.body()!=null){
                        if(response.body().getCode().equals("00")){
                            Log.e(TAG, "Success: ");
                            showAlert();
                        }
                        else if(response.body().getCode().equals("16")){
                            Log.e(TAG, "invalid: ");
                            Alert.show(requireActivity(), "Error", "Invalid Data.");
                        }
                        else{
                            Alert.show(requireActivity(), "Error", "Something went wrong.");
                        }
                    }else{
                        Alert.show(requireActivity(), "Error", "Something went wrong.");
                    }
                }else{
                    Alert.show(requireActivity(), "Error", "Something went wrong.");
                }
            }
            @Override
            public void onFailure(@NotNull Call<AccountOpeningRequestResponse> call, @NotNull Throwable t) {
                loading.dismiss ();
                Log.e(TAG, "onFailure: "+t.getMessage());
                Alert.show(requireActivity(), "Error", t.getMessage());
            }
        });

    }

    private void showAlert() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(requireActivity());
        dialog.setCancelable(false);
        dialog.setTitle("Success");
        dialog.setMessage("Account Created Successfully." );
        dialog.setPositiveButton("Ok", (dialog12, id) -> {
            requireActivity().finish();
        });

        final AlertDialog alert = dialog.create();
        alert.show();
    }

    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.ivBack){
            requireActivity().onBackPressed();
        }
        else if(view.getId()==R.id.ivAdd1){
            if(isCnicFrontEnabled){
                checkPermission(1);
            }
        }
        else if(view.getId()==R.id.ivRemove1){
            if(isCnicFrontEnabled){
                proofCnicFront = null;
                setImageToImageView(null, ivCnicFront, tvFileName1);
                ivRemove1.setVisibility(View.GONE);
                ivAdd1.setVisibility(View.VISIBLE);
            }
        }

        else if(view.getId()==R.id.ivAdd2){
            if(isCnicBackEnabled){
                checkPermission(2);
            }
        }
        else if(view.getId()==R.id.ivRemove2){
            if(isCnicBackEnabled){
                proofCnicBack = null;
                setImageToImageView(null, ivCnicBack, tvFileName2);
                ivRemove2.setVisibility(View.GONE);
                ivAdd2.setVisibility(View.VISIBLE);
            }
        }

        else if(view.getId()==R.id.ivAdd3){
            if(isSignatureEnabled){
                checkPermission(3);
            }
        }
        else if(view.getId()==R.id.ivRemove3){
            if(isSignatureEnabled){
                proofSignature = null;
                setImageToImageView(null, ivApplicantSignature, tvFileName3);
                ivRemove3.setVisibility(View.GONE);
                ivAdd3.setVisibility(View.VISIBLE);
            }
        }

        else if(view.getId()==R.id.ivAdd4){
            checkPermission(4);
        }
        else if(view.getId()==R.id.ivRemove4){
            proofResidentialAddress = null;
            setImageToImageView(null, ivProofResidentialAddress, tvFileName4);
            ivRemove4.setVisibility(View.GONE);
            ivAdd4.setVisibility(View.VISIBLE);
        }
        else if(view.getId()==R.id.ivAdd7){
            checkPermission(7);
        }
        else if(view.getId()==R.id.ivRemove7){
            proofZakatDecalration = null;
            setImageToImageView(null, ivZakatForm, tvFileName7);
            ivRemove7.setVisibility(View.GONE);
            ivAdd7.setVisibility(View.VISIBLE);
        }
        else if(view.getId()==R.id.ivAdd8){
            checkPermission(8);
        }
        else if(view.getId()==R.id.ivRemove8){
            proofNmnCnicFront = null;
            setImageToImageView(null, ivNomineeCnicFront, tvFileName8);
            ivRemove8.setVisibility(View.GONE);
            ivAdd8.setVisibility(View.VISIBLE);
        }
        else if(view.getId()==R.id.ivAdd9){
            checkPermission(9);
        }
        else if(view.getId()==R.id.ivRemove9){
            proofNmnCnicBack = null;
            setImageToImageView(null, ivNomineeCnicBack, tvFileName9);
            ivRemove9.setVisibility(View.GONE);
            ivAdd9.setVisibility(View.VISIBLE);
        }

        else if(view.getId()==R.id.btnSubmit){
            if(validateData()){
                submitData();
            }
        }
    }

    private boolean validateData() {

        if(accountOpeningObject.getCNICFRONT().isEmpty() || accountOpeningObject.getCNICFRONT()==null){
            if(proofCnicFront==null){
                Alert.show(requireActivity(), "", "Please attach proof of your CNIC Front.");
                return false;
            }else{
                String encodedImage = Util.convertUriToBase64(proofCnicFront, requireActivity());
                accountOpeningObject.setCNICFRONT(encodedImage);
            }
        }

        if(proofCnicBack==null){
            Alert.show(requireActivity(), "", "Please attach proof of your CNIC Back.");
            return false;
        }else{
            String encodedImage = Util.convertUriToBase64(proofCnicBack, requireActivity());
            accountOpeningObject.setCNICBACK(encodedImage);
        }

        if(proofSignature==null){
            Alert.show(requireActivity(), "", "Please attach proof of your Signature.");
            return false;
        }else{
            String encodedImage = Util.convertUriToBase64(proofSignature, requireActivity());
            accountOpeningObject.setSIGNATURE(encodedImage);
        }

        //        if(proofResidentialAddress==null){
//            Alert.show(requireActivity(), "", "Please attach proof of your Residential address.");
//            return false;
//        }else{
//            String encodedImage = Util.convertUriToBase64(proofResidentialAddress, requireActivity());
//            accountOpeningObject.setRESDOC1(encodedImage);
//        }

        if(isZakatFormEnabled){
            if(proofZakatDecalration==null){
                Alert.show(requireActivity(), "", "Please attach proof of Zakat Declaration.");
                return false;
            }else{
                String encodedImage = Util.convertUriToBase64(proofZakatDecalration, requireActivity());
                accountOpeningObject.setZAKAATDECLARATION(encodedImage);
            }
        }else{
            accountOpeningObject.setZAKAATDECLARATION("");
        }

        if(isNmnCnicFrontEnabled){
            if(proofNmnCnicFront==null){
                Alert.show(requireActivity(), "", "Please attach proof of Nominee CNIC Front.");
                return false;
            }else{
                String encodedImage = Util.convertUriToBase64(proofNmnCnicFront, requireActivity());
                accountOpeningObject.setCNICNMNFRONT(encodedImage);
                //Log.e(TAG, "Nominee CNIC Front: "+ encodedImage);
            }
        }else{
            accountOpeningObject.setCNICNMNFRONT("");
        }

        if(isNmnCnicBackEnabled){
            if(proofNmnCnicBack==null){
                Alert.show(requireActivity(), "", "Please attach proof of Nominee CNIC Back.");
                return false;
            }else{
                String encodedImage = Util.convertUriToBase64(proofNmnCnicBack, requireActivity());
                accountOpeningObject.setCNICNMNBACK(encodedImage);
                //Log.e(TAG, "Nominee CNIC Back: "+ encodedImage);
            }
        }else{
            accountOpeningObject.setCNICNMNBACK("");
        }

        //set reference code to object

        if(!checkbox.isChecked()){
            Alert.show(requireActivity(), "", "Please check Terms of Service and Privacy Policy.");
            return false;
        }

        return true;
    }

    private void checkPermission(int i) {
        Dexter.withContext(requireActivity())
                .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                .withListener(new PermissionListener() {
                    @Override public void onPermissionGranted(PermissionGrantedResponse response) {
                        selectImageFromGallery(i);
                    }
                    @Override public void onPermissionDenied(PermissionDeniedResponse response) {
                        Toast.makeText(requireActivity(), "Permission Denied.", Toast.LENGTH_SHORT).show();
                    }
                    @Override public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {

                    }
                }).check();
    }

    private void selectImageFromGallery(int i) {
        ImagePicker.with(this)
                .galleryOnly()
                .crop()	    			//Crop image(Optional), Check Customization for more option
                .compress(1024)			//Final image size will be less than 1 MB(Optional)
                .maxResultSize(1080, 1080)	//Final image resolution will be less than 1080 x 1080(Optional)
                .start();
        imagePosition = i;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            Log.e(TAG, "onActivityResult: ");
            Uri uri = data.getData();
            switch (imagePosition){
                case 1: {
                    proofCnicFront = uri;
                    setImageToImageView(uri, ivCnicFront, tvFileName1);
                    ivAdd1.setVisibility(View.GONE);
                    ivRemove1.setVisibility(View.VISIBLE);
                    break;
                }
                case 2: {
                    proofCnicBack = uri;
                    setImageToImageView(uri, ivCnicBack, tvFileName2);
                    ivAdd2.setVisibility(View.GONE);
                    ivRemove2.setVisibility(View.VISIBLE);
                    break;
                }
                case 3: {
                    proofSignature = uri;
                    setImageToImageView(uri, ivApplicantSignature, tvFileName3);
                    ivAdd3.setVisibility(View.GONE);
                    ivRemove3.setVisibility(View.VISIBLE);
                    break;
                }
                case 4: {
                    proofResidentialAddress = uri;
                    setImageToImageView(uri, ivProofResidentialAddress, tvFileName4);
                    ivAdd4.setVisibility(View.GONE);
                    ivRemove4.setVisibility(View.VISIBLE);
                    break;
                }
                case 7: {
                    proofZakatDecalration = uri;
                    setImageToImageView(uri, ivZakatForm, tvFileName7);
                    ivAdd7.setVisibility(View.GONE);
                    ivRemove7.setVisibility(View.VISIBLE);
                    break;
                }
                case 8: {
                    proofNmnCnicFront = uri;
                    setImageToImageView(uri, ivNomineeCnicFront, tvFileName8);
                    ivAdd8.setVisibility(View.GONE);
                    ivRemove8.setVisibility(View.VISIBLE);
                    break;
                }
                case 9: {
                    proofNmnCnicBack = uri;
                    setImageToImageView(uri, ivNomineeCnicBack, tvFileName9);
                    ivAdd9.setVisibility(View.GONE);
                    ivRemove9.setVisibility(View.VISIBLE);
                    break;
                }
            }

        } else if (resultCode == ImagePicker.RESULT_ERROR) {
            Toast.makeText(requireActivity(), ImagePicker.getError(data), Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(requireActivity(), "Cancelled", Toast.LENGTH_SHORT).show();
        }
    }

    private void setImageToImageView(Uri uri, ImageView imageView, TextView tvFileName) {
        Glide.with(requireActivity())
                .load(uri)
                .centerCrop()
                .placeholder(R.drawable.image)
                .into(imageView);

        if(uri==null){
            tvFileName.setText("No File Chosen");
        }else{
            FileMetaData fileMetaData = Util.getFileMetaData(requireActivity(), uri);
            if (fileMetaData != null) {
                tvFileName.setText(fileMetaData.displayName);
            }
        }
    }

    private void setTextSpans() {

        String textTermOfService = getString(R.string.termsOfService);
        String textPrivacyPolicy = getString(R.string.privacyPolicy);

        SpannableStringBuilder ssb = new SpannableStringBuilder("");
        ssb.append("I agree to the ");

        addClickableText(ssb, ssb.length(), textTermOfService, "", textTermOfService);
        ssb.append(" and ");

        addClickableText(ssb, ssb.length(), textPrivacyPolicy, "", textPrivacyPolicy);
        ssb.append(".");

        tvTermsAndPrivacyPolicy.setMovementMethod(LinkMovementMethod.getInstance());
        tvTermsAndPrivacyPolicy.setText(ssb);

    }

    private void addClickableText(SpannableStringBuilder ssb, int startPos, String clickableText, String link, String title) {
        ssb.append(clickableText);
        ssb.setSpan(new MyClickableSpan(requireActivity(), title, link, ContextCompat.getColor(requireActivity(), R.color.colorSpanningLink)), startPos, ssb.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
    }


}