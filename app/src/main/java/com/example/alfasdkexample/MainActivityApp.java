package com.example.alfasdkexample;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.example.alfasdk.AccountOpeningActivity;
import com.example.alfasdk.Models.AccountOpening.AccountOpeningObject;
import com.example.alfasdk.MyLoginActivity;

public class MainActivityApp extends MyLoginActivity {

    Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_app);

//        startActivity(new Intent(this, AccountOpeningActivity.class));
//        finish();


        btnLogin=findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(v -> {
//            SdkInterface sdkInterface = new MyLoginActivity();
////            sdkInterface.login(v, token, "PK54ALFH0005001004799205","4210152572587");
//            sdkInterface.createAccount();

            AccountOpeningObject accountOpeningObject = getModelObject();
            Intent intent = new Intent(this, AccountOpeningActivity.class);
            intent.putExtra("accountOpeningObject", accountOpeningObject);
            startActivity(intent);

        });
    }

    private AccountOpeningObject getModelObject() {
        AccountOpeningObject accountOpeningObject = new AccountOpeningObject();

        //Page No.1
        accountOpeningObject.setNAME("Ali");
        accountOpeningObject.setFATHERHUSBANDNAME("Dev");
        accountOpeningObject.setMOTHERNAME("xyz");
        accountOpeningObject.setGENDER("Male");
        accountOpeningObject.setDATEOFBIRTH("05/01/1990");
        accountOpeningObject.setPLACEOFBIRTH("Lahore");
        accountOpeningObject.setNATIONALITY("Pakistani");
        accountOpeningObject.setMARITALSTATUS("M");

        //Page No.2
        accountOpeningObject.setUIN("3640214910877");
        accountOpeningObject.setISSUEDATE("05/01/1990");
        accountOpeningObject.setDATEOFEXPIRY("05/01/2021");

        //Page No.3
        accountOpeningObject.setMAILINGADDRESS1("ABC");
        accountOpeningObject.setCITYTOWNVILLAGE("Lahore");
        accountOpeningObject.setPROVINCESTATE("");
        accountOpeningObject.setCOUNTRY("");
        accountOpeningObject.setTELEPHONEOFFICE("03xxxxxxxxx");
        accountOpeningObject.setTELEPHONERESIDENCE("03xxxxxxxxx");
        accountOpeningObject.setFAX("N/A");
        accountOpeningObject.setMOBILENO("N/A");
        accountOpeningObject.setEMAIL("developer@gmail.com");
        accountOpeningObject.setPERMANENTADDRESS1("XYZ");
        accountOpeningObject.setPERMANENTCITYTOWN("");
        accountOpeningObject.setPERMANENTPROVINCE("Punjab");
        accountOpeningObject.setPERMANENTCOUNTRY("Pakistan");

        //Page No.4
        accountOpeningObject.setIBAN("xxxx");
        accountOpeningObject.setBANKNAME("Askari Commercial Bank");
        accountOpeningObject.setBRANCHNAME("Johar Town");
        accountOpeningObject.setBRANCHCODE("01000");
        accountOpeningObject.setBRANCHADDRESS("L block, phase#2, Johar Town");

        //Page No.5
        accountOpeningObject.setOCCUPATION("Housewife");
        accountOpeningObject.setZAKATSTATUS("A");

        //Page No.6

        //Page No.7
        accountOpeningObject.setFATCACLASSIFICATION("N/A");

        //Page No.8

        //Page No.9

        //Page No.10
        accountOpeningObject.setCNICFRONT("");
        accountOpeningObject.setCNICBACK("");
        accountOpeningObject.setSIGNATURE("N/A");

        //Page No.11
        accountOpeningObject.setDIVIDENDMENDATE("N/A");
        accountOpeningObject.setALERTSERVICES("N/A");


        return accountOpeningObject;
    }

}