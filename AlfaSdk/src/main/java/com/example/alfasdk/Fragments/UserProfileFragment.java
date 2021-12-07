package com.example.alfasdk.Fragments;


import android.os.Bundle;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;


import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.alfasdk.Adapters.SearchClientListAdapter;
import com.example.alfasdk.MyMainActivity;
import com.example.alfasdk.Models.ProfileModel.ProfileResponse;
import com.example.alfasdk.Models.ProfileModel.Response;
import com.example.alfasdk.R;

import java.util.ArrayList;



public class UserProfileFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
//    @BindView(R.id.clientName)
    TextView clientName;
//    @BindView(R.id.clientCode)
    TextView clientCode;
//    @BindView(R.id.tradingAccount)
    TextView tradingAccount;
//    @BindView(R.id.email)
    TextView email;
//    @BindView(R.id.address)
    TextView address;
//    @BindView(R.id.smsService)
    TextView smsService;
//    @BindView(R.id.accountOpenDate)
    TextView accountOpenDate;
//    @BindView(R.id.nicExpiryDate)
    TextView nicExpiryDate;
//    @BindView(R.id.cdcAccount)
    TextView cdcAccount;
//    @BindView(R.id.branchName)
    TextView branchName;
//    @BindView(R.id.traderName)
    TextView traderName;
//    @BindView(R.id.traderCode)
    TextView traderCode;
//    @BindView(R.id.traderMobile)
    TextView traderMobile;
//    @BindView(R.id.traderEmail)
    TextView traderEmail;
//    @BindView(R.id.zakatStatus)
    TextView zakatStatus;
//    @BindView(R.id.cityName)
    TextView cityName;
//    @BindView(R.id.nic)
    TextView nic;
//    @BindView(R.id.nominee)
    TextView nominee;
//    @BindView(R.id.jointHolders)
    TextView jointHolders;
//    @BindView(R.id.bankName)
    TextView bankName;
//    @BindView(R.id.accountTitle)
    TextView accountTitle;
//    @BindView(R.id.accountNo)
    TextView accountNo;
//    @BindView(R.id.accountBranch)
    TextView accountBranch;
//    @BindView(R.id.accountCity)
    TextView accountCity;
//    @BindView(R.id.dividend)
    TextView dividend;
    private String mParam1;
    private String mParam2;
//    @BindView(R.id.etclientcode)
    EditText clientcode;
//    @BindView(R.id.search_list1)
    ListView listSearch1;
//    @BindView(R.id.search_list_view1)
    LinearLayout listSearch_view1;

    ImageView cancel_search1;
    boolean isSetInitialText = false;
    private SearchClientListAdapter searchClientListAdapter;
    ArrayList<String> clientlist;

    public UserProfileFragment() {
        // Required empty public constructor
    }

    public static UserProfileFragment newInstance(String param1, String param2) {
        UserProfileFragment fragment = new UserProfileFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);

            Log.d("mParam1", mParam1);
            Log.d("mParam2", mParam2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_profile, container, false);
//        ButterKnife.bind(this, view);
        clientName=view.findViewById(R.id.clientName);
        clientCode=view.findViewById(R.id.clientCode);
        tradingAccount=view.findViewById(R.id.tradingAccount);
        email=view.findViewById(R.id.email);
        address=view.findViewById(R.id.address);
        smsService=view.findViewById(R.id.smsService);
        accountOpenDate=view.findViewById(R.id.accountOpenDate);
        nicExpiryDate=view.findViewById(R.id.nicExpiryDate);
        cdcAccount=view.findViewById(R.id.cdcAccount);
        branchName=view.findViewById(R.id.branchName);
        traderName=view.findViewById(R.id.traderName);
        traderCode=view.findViewById(R.id.traderCode);
        traderMobile=view.findViewById(R.id.traderMobile);
        traderEmail=view.findViewById(R.id.traderEmail);
        zakatStatus=view.findViewById(R.id.zakatStatus);
        cityName=view.findViewById(R.id.cityName);
        nic=view.findViewById(R.id.nic);
        nominee=view.findViewById(R.id.nominee);
        jointHolders=view.findViewById(R.id.jointHolders);
        bankName=view.findViewById(R.id.bankName);
        accountTitle=view.findViewById(R.id.accountTitle);
        accountNo=view.findViewById(R.id.accountNo);
        accountBranch=view.findViewById(R.id.accountBranch);
        accountCity=view.findViewById(R.id.accountCity);
        dividend=view.findViewById(R.id.dividend);
        clientcode=view.findViewById(R.id.etclientcode);
        listSearch1=view.findViewById(R.id.search_list1);
        listSearch_view1=view.findViewById(R.id.search_list_view1);
        cancel_search1=view.findViewById(R.id.cancel_search1);

        cancel_search1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelSearch(v);
            }
        });




        if (MyMainActivity.loginResponse.getResponse().getUsertype() == 1 ||
                MyMainActivity.loginResponse.getResponse().getUsertype() == 2) {

            clientcode.setText(MyMainActivity.loginResponse.getResponse().getClient());
            clientcode.setEnabled(false);
            ((MyMainActivity) getActivity()).profileRequest(clientcode.getText().toString());
        } else if (MyMainActivity.loginResponse.getResponse().getUsertype() == 0 ||
                MyMainActivity.loginResponse.getResponse().getUsertype() == 3) {

            clientlist = new ArrayList<String>(MyMainActivity.loginResponse.getResponse().getClientlist());
            searchClientListAdapter = new SearchClientListAdapter(getActivity(), clientlist);
        }
        return view;
    }

    @Override
    public void onResume() {

        ActionBar toolbar = ((AppCompatActivity) getActivity()).getSupportActionBar();

        if (toolbar != null) {
            toolbar.setTitle("Profile");
        }
        super.onResume();
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        listSearch1.setAdapter(searchClientListAdapter);
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
                ((MyMainActivity) getActivity()).profileRequest(clientlist.get(position));
            }
        });

    }

//    @OnClick(R.id.cancel_search1)
    public void cancelSearch(View view) {
        listSearch_view1.setVisibility(View.GONE);
    }

    public void setResult(ProfileResponse result) {

        if (result != null) {

            Response response = result.getResponse();

            clientName.setText(response.getClientName());
            clientCode.setText(response.getClientCode());
            tradingAccount.setText(response.getTradingAccountNo());
            email.setText(response.getEmail());
            address.setText(response.getAddress());
            smsService.setText(response.getSmsService());
            accountOpenDate.setText(response.getAccountOpenDate());
            nicExpiryDate.setText(response.getNICExpiryDate());
            cdcAccount.setText(response.getCdcAccountNo());
            branchName.setText(response.getBranchName());
            traderName.setText(response.getTraderName());
            traderCode.setText(response.getTraderCode());
            traderMobile.setText(response.getTraderMobile());
            traderEmail.setText(response.getTraderEmail());
            zakatStatus.setText(response.getZakatStatus());
            cityName.setText(response.getCityName());
            nic.setText(response.getNIC());
            nominee.setText(response.getNominee());
            jointHolders.setText(response.getJointHolders());
            bankName.setText(response.getBankName());
            accountTitle.setText(response.getAccountTitle());
            accountNo.setText(response.getAccountNo());
            accountBranch.setText(response.getBranchName());
            accountCity.setText(response.getAccountCity());
            dividend.setText(response.getDividend());
        }


    }
}
