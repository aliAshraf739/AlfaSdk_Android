package com.example.alfasdk;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.alfasdk.Models.AccountOpening.AccountOpeningObject;
import com.shuhart.stepview.StepView;

public class AccountOpeningActivity extends AppCompatActivity {

    public StepView stepView;
    public AccountOpeningObject accountOpeningObject = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_opening);
        initViews();

        //Get 'accountOpeningObject' object here
        accountOpeningObject = (AccountOpeningObject) getIntent().getSerializableExtra("accountOpeningObject");
    }

    private void initViews() {
        stepView = findViewById(R.id.stepView);
    }

}