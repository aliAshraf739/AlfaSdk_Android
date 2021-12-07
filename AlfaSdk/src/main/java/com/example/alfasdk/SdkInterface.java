package com.example.alfasdk;

import android.view.View;

import com.example.alfasdk.Models.AccountOpening.AccountOpeningObject;

public interface SdkInterface {
    void login(View view,String token,String bankAcctNo,String cnic);
    void createAccount(AccountOpeningObject accountOpeningObject);
}
