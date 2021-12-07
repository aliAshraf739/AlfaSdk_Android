package com.example.alfasdk;

import android.view.View;

public interface SdkInterface {
    void login(View view,String token,String bankAcctNo,String cnic);
    void createAccount();
}
