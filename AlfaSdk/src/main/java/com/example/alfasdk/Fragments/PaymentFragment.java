package com.example.alfasdk.Fragments;


import android.os.Bundle;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.alfasdk.Adapters.SearchClientListAdapter;
import com.example.alfasdk.MyMainActivity;
import com.example.alfasdk.Models.PaymentModel.PaymentResponse;
import com.example.alfasdk.R;
import com.example.alfasdk.Util.Alert;
import com.google.gson.Gson;
import com.google.gson.JsonObject;


import java.text.DecimalFormat;
import java.util.ArrayList;



public class PaymentFragment extends Fragment {

    //    @BindView(R.id.edittext_username)
    // EditText edit_username;
//    @BindView(R.id.edittext_clientcode)
    EditText clientcode;
//    @BindView(R.id.edittext_exchange)
    EditText edit_exchange;
//    @BindView(R.id.edittext_amount)
    EditText edit_amount;
//    @BindView(R.id.radioGroup)
    RadioGroup radioGroup;
//    @BindView(R.id.cashBalance)
    TextView cashBalance;
//    @BindView(R.id.withdrawalLimit)
    TextView withdrawalLimit;
//    @BindView(R.id.pendingWithdrawal)
    TextView pendingWithdrawal;
//    @BindView(R.id.remainingAmount)
    TextView remainingAmount;
//    @BindView(R.id.search_list_view1)
    LinearLayout listSearch_view1;
//    @BindView(R.id.search_list1)
    ListView listSearch1;
    ArrayList<String> clientlist;
    SearchClientListAdapter searchClientListAdapter;
    String client;
    String selectedVal = null; //B bank, C cheque
    Button button_payment_request;
    boolean isSetInitialText = false;

    public PaymentFragment() {
        // Required empty public constructor
    }

    public static PaymentFragment newInstance() {
        return new PaymentFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_payment, container, false);
//        ButterKnife.bind(this, view);
        button_payment_request=view.findViewById(R.id.button_payment_request);
        clientcode=view.findViewById(R.id.edittext_clientcode);
        edit_exchange=view.findViewById(R.id.edittext_exchange);
        edit_amount=view.findViewById(R.id.edittext_amount);
        radioGroup=view.findViewById(R.id.radioGroup);
        cashBalance=view.findViewById(R.id.cashBalance);
        withdrawalLimit=view.findViewById(R.id.withdrawalLimit);
        remainingAmount=view.findViewById(R.id.remainingAmount);
        listSearch_view1=view.findViewById(R.id.search_list_view1);
        listSearch1=view.findViewById(R.id.search_list1);



        button_payment_request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submit(v);
            }
        });




        if (MyMainActivity.loginResponse.getResponse().getUsertype() == 1 ||
                MyMainActivity.loginResponse.getResponse().getUsertype() == 2) {

            clientcode.setText(MyMainActivity.loginResponse.getResponse().getClient());
            client = clientcode.getText().toString();
            clientcode.setEnabled(false);
            ((MyMainActivity) getActivity()).getPaymentRequest(clientcode.getText().toString());
        } else if (MyMainActivity.loginResponse.getResponse().getUsertype() == 0 ||
                MyMainActivity.loginResponse.getResponse().getUsertype() == 3) {
            clientlist = new ArrayList<String>(MyMainActivity.loginResponse.getResponse().getClientlist());
            searchClientListAdapter = new SearchClientListAdapter(getActivity(), clientlist);
            listSearch1.setAdapter(searchClientListAdapter);

        }
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //      edit_username.setText(MainActivity.loginResponse.getResponse().getUserId());
        edit_exchange.setText(MyMainActivity.loginResponse.getResponse().getServerCode());

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                Log.d("checkedId", "checkedId: " + checkedId);

                if (checkedId == R.id.radio1) {

                    selectedVal = "B";

                } else if (checkedId == R.id.radio2) {

                    selectedVal = "C";

                } else {

                    selectedVal = null;
                }
            }
        });
        clientcode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (isSetInitialText) {
                    isSetInitialText = false;
                    listSearch_view1.setVisibility(View.GONE);
                } else {
                    if (s.length() > 0) {

                        listSearch_view1.setVisibility(View.VISIBLE);

                        String text = clientcode.getText().toString();
                        searchClientListAdapter.filter(text);
                    } else {
                        listSearch_view1.setVisibility(View.GONE);
                    }
                }

            }
        });
        listSearch1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                listSearch_view1.setVisibility(View.GONE);
                isSetInitialText = true;
                clientcode.setText(clientlist.get(position));
                ((MyMainActivity) getActivity()).getPaymentRequest(clientcode.getText().toString());

            }
        });


    }

//    @OnClick(R.id.button_payment_request)
    public void submit(View view) {

        String amount = edit_amount.getText().toString();
        String client = clientcode.getText().toString();
        if (TextUtils.equals(client, "")) {
            Alert.show(getActivity(), getString(R.string.app_name), "Please Select Client.");
        }
        else if (TextUtils.equals(amount, "")) {

            Alert.show(getActivity(), getString(R.string.app_name), "Please enter amount.");

        } else if (selectedVal == null) {

            Alert.show(getActivity(), getString(R.string.app_name), "Please select Bank Transfer or Cheque Delivery.");
        } else {

            try {
                int amountVal = Integer.parseInt(amount);

                ((MyMainActivity) getActivity()).paymentRequest(amountVal, selectedVal,clientcode.getText().toString());
            } catch (Exception e) {
                e.printStackTrace();
                Alert.show(getActivity(), getString(R.string.app_name), "Invalid amount entered.");
            }
        }


//        String amount= edit_amount.gettex.toString();
//        String newPin= edit_newPin.getText().toString();
//        String confirmPin= edit_confirmPin.getText().toString();
//
//
//        if (!TextUtils.equals(oldPin, "")){
//
//            if(!TextUtils.equals(newPin,"")&&newPin.length()>=8){
//
//                if(TextUtils.equals(newPin,confirmPin)){
//
//                    ((MainActivity)getActivity()).changePinRequest(oldPin, newPin);
//
//                }else{
//                    HToast.showMsg(getActivity(), "Pins do not match.");
//                }
//            }else{
//                HToast.showMsg(getActivity(),"New Pin must be equal to 4 characters.");
//            }
//
//        }else {
//            HToast.showMsg(getActivity(),"Please type your old pin.");
//        }


    }

    @Override
    public void onResume() {

        ActionBar toolbar = ((AppCompatActivity) getActivity()).getSupportActionBar();

        if (toolbar != null) {
            toolbar.setTitle("Cash Withdrawal");
        }
        super.onResume();
    }

    public void setResult(JsonObject json) {

        final PaymentResponse paymentResponse = new Gson().fromJson(json, PaymentResponse.class);

        if (paymentResponse != null) {

            final String cash = paymentResponse.getResponse().getCashBalance();
            final String withDrawal = paymentResponse.getResponse().getWithdrawalLimit();
            String pending = paymentResponse.getResponse().getPendingWithdrawal();

            cashBalance.setText(cash);
            withdrawalLimit.setText(withDrawal);
            pendingWithdrawal.setText(pending);

            edit_amount.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {

                    try {
                        double amountEntered = Double.parseDouble(s.toString());
                        double withDrawalLimit = Double.parseDouble(paymentResponse.getResponse().getWithdrawalLimit().replace(",", ""));

                        String remaining = String.valueOf(withDrawalLimit - amountEntered);

                        double amount = Double.parseDouble(remaining);
                        DecimalFormat formatter = new DecimalFormat("#,###.00");


                        remainingAmount.setText(formatter.format(amount));

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    if (s.length() == 0) {
                        remainingAmount.setText("");
                    }

                }
            });


        }


    }
}
