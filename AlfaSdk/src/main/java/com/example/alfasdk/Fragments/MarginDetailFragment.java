package com.example.alfasdk.Fragments;


import android.os.Bundle;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;


import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.alfasdk.Adapters.MarginDetailAdapter;
import com.example.alfasdk.Adapters.SearchClientListAdapter;
import com.example.alfasdk.MyMainActivity;
import com.example.alfasdk.Models.MarginModel.CustodyHeader;
import com.example.alfasdk.Models.MarginModel.CustodyList;
import com.example.alfasdk.Models.MarginModel.MarginDetail;
import com.example.alfasdk.Models.MarginModel.MarginResponse;
import com.example.alfasdk.Models.MarginModel.Response;
import com.example.alfasdk.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;


public class MarginDetailFragment extends Fragment {

    private static final String PARAM = "clientCode";


//    @BindView(R.id.custody_listView)
    RecyclerView custody_listView;

//    @BindView(R.id.etclientcode)
    EditText clientcode;
//    @BindView(R.id.search_list1)
    ListView listSearch1;
//    @BindView(R.id.search_list_view1)
    LinearLayout listSearch_view1;
    ImageView cancel_search1;
    private SearchClientListAdapter searchClientListAdapter;

    private String clientCode = "";
    ArrayList<String> clientlist;
    boolean isSetInitialText = false;

    public MarginDetailFragment() {
        // Required empty public constructor
    }


    public static MarginDetailFragment newInstance(String code) {
        MarginDetailFragment fragment = new MarginDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putString(PARAM, code);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        Bundle bundle = getArguments();
        clientCode = bundle.getString(PARAM);
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_margin_detail, container, false);
//        ButterKnife.bind(this, view);
        custody_listView=view.findViewById(R.id.custody_listView);
        clientcode=view.findViewById(R.id.etclientcode);
        listSearch1=view.findViewById(R.id.search_list1);
        listSearch_view1=view.findViewById(R.id.search_list_view1);
        cancel_search1=view.findViewById(R.id.cancel_search1);

        cancel_search1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listSearch_view1.setVisibility(View.GONE);

            }
        });

        custody_listView.setLayoutManager(new LinearLayoutManager(getActivity()));

        if (MyMainActivity.loginResponse.getResponse().getUsertype() == 1 ||
                MyMainActivity.loginResponse.getResponse().getUsertype() == 2) {

            clientcode.setText(MyMainActivity.loginResponse.getResponse().getClient());
            clientcode.setEnabled(false);
            ((MyMainActivity) getActivity()).marginRequest(clientcode.getText().toString());
        } else if (MyMainActivity.loginResponse.getResponse().getUsertype() == 0 ||
                MyMainActivity.loginResponse.getResponse().getUsertype() == 3) {

            clientlist = new ArrayList<String>(MyMainActivity.loginResponse.getResponse().getClientlist());
            searchClientListAdapter = new SearchClientListAdapter(getActivity(), clientlist);
        }
        return view;
    }

//    @OnClick(R.id.cancel_search1)
//    public void cancelSearch(View view) {
//        listSearch_view1.setVisibility(View.GONE);
//    }

    @Override
    public void onResume() {

        ActionBar toolbar = ((AppCompatActivity) getActivity()).getSupportActionBar();

        if (toolbar != null) {
            toolbar.setTitle("Margin Detail");
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
                ((MyMainActivity) getActivity()).marginRequest(clientlist.get(position));

            }
        });

    }

    public void setResult(MarginResponse result) {

        if (result != null) {

//            View header = LayoutInflater.from(getActivity()).inflate(R.layout.item_list_margin_header, null);
//
//            HeaderView headerView = new HeaderView(header);
//            if (custody_listView.getHeaderViewsCount() > 0) {
//
//            } else {
//                custody_listView.addHeaderView(header);
//
//
//            }
            Response response = result.getResponse();

            CustodyHeader custodyHeader = new CustodyHeader();
            custodyHeader.setClientcode(clientcode.getText().toString());
            custodyHeader.setDate(getDateTimeString());
            custodyHeader.setEquityCashBalance(response.getEquityCashBalance());
            custodyHeader.setTotalWorth(response.getTotalWorth());
            custodyHeader.setEquityReducedValue(response.getEquityReducedValue());
            custodyHeader.setEquityProfitLos(response.getEquityProfitLos());
            custodyHeader.setEquityBlockedMTMProfit(response.getEquityBlockedMTMProfit());
            custodyHeader.setNetLiquidityEquity(response.getNetLiquidityEquity());
            custodyHeader.setOpenPositionEquity(response.getOpenPositionEquity());
            custodyHeader.setEquityMarginRequired(response.getEquityMarginRequired());
            custodyHeader.setEquityMarginRequiredAsCash(response.getEquityMarginRequiredAsCash());
            custodyHeader.setEquityFreeMargin(response.getEquityFreeMargin());
            custodyHeader.setEquityMarginPerc(response.getEquityMarginPerc());
            custodyHeader.setEquityCashWithdrawalinProcess(response.getEquityCashWithdrawalinProcess());
            custodyHeader.setEquityCashWithdrawal(response.getEquityCashWithdrawal());
            custodyHeader.setEquityNetMarginCall(response.getEquityNetMarginCall());
            custodyHeader.setEquityFreeCash(response.getEquityFreeCash());


            ArrayList<MarginDetail> arrayMain = new ArrayList<>();

            arrayMain.add(custodyHeader);
            arrayMain.addAll(response.getCustodyList());
//            headerView.textViewDateTime.setText(getDateTimeString());
//            headerView.usertext.setText(clientCode);
//            headerView.cashtext.setText(response.getEquityCashBalance());
//            headerView.worthText.setText(response.getTotalWorth());
//            headerView.custodyText.setText(response.getEquityReducedValue());
//            headerView.mtmText.setText(response.getEquityProfitLos());
//            headerView.blockedMtmText.setText(response.getEquityBlockedMTMProfit());
//            headerView.marginText.setText(response.getNetLiquidityEquity());
//            headerView.exposureText.setText(response.getOpenPositionEquity());
//            headerView.marginRequiredText.setText(response.getEquityMarginRequired());
//            headerView.cashRequiredText.setText(response.getEquityMarginRequiredAsCash());
//            headerView.availableMarginText.setText(response.getEquityFreeMargin());
//            headerView.currentMarginText.setText(response.getEquityMarginPerc());
//            headerView.cashWithdrawalText.setText(response.getEquityCashWithdrawalinProcess());
//            headerView.cashWithdrawalLimitText.setText(response.getEquityCashWithdrawal());
//            headerView.marginCallText.setText(response.getEquityCashMarginCall());
//            headerView.cashCallText.setText(response.getEquityNetMarginCall());
//            headerView.availableCashText.setText(response.getEquityFreeCash());
//            header.invalidate();
//            Log.d("Cashbalance", headerView.cashtext.getText().toString());
            final ArrayList<CustodyList> custodyList = (ArrayList<CustodyList>) response.getCustodyList();
            custody_listView.setAdapter(new MarginDetailAdapter(getActivity(), arrayMain));
            // custody_listView.setAdapter(new CustodyAdapter(getActivity(), custodyList));


        }
    }

    public String getDateTimeString() {
        return new SimpleDateFormat("MMMM dd, yyyy, hh:mm a", Locale.UK).format(new Date());
    }

//    static class HeaderView {
//        @BindView(R.id.textViewDateTime)
//        TextView textViewDateTime;
//        @BindView(R.id.usertext)
//        TextView usertext;
//        @BindView(R.id.cashtext)
//        TextView cashtext;
//        @BindView(R.id.worthText)
//        TextView worthText;
//        @BindView(R.id.custodyText)
//        TextView custodyText;
//        @BindView(R.id.mtmText)
//        TextView mtmText;
//        @BindView(R.id.blockedMtmText)
//        TextView blockedMtmText;
//        @BindView(R.id.marginText)
//        TextView marginText;
//        @BindView(R.id.exposureText)
//        TextView exposureText;
//        @BindView(R.id.marginRequiredText)
//        TextView marginRequiredText;
//        @BindView(R.id.cashRequiredText)
//        TextView cashRequiredText;
//        @BindView(R.id.availableMarginText)
//        TextView availableMarginText;
//        @BindView(R.id.currentMarginText)
//        TextView currentMarginText;
//        @BindView(R.id.cashWithdrawalText)
//        TextView cashWithdrawalText;
//        @BindView(R.id.cashWithdrawalLimitText)
//        TextView cashWithdrawalLimitText;
//        @BindView(R.id.marginCallText)
//        TextView marginCallText;
//        @BindView(R.id.cashCallText)
//        TextView cashCallText;
//        @BindView(R.id.availableCashText)
//        TextView availableCashText;
//
//        public HeaderView(View view) {
//            ButterKnife.bind(this, view);
//        }
//    }


}

