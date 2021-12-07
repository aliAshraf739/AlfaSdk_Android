package com.example.alfasdk.Fragments;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.alfasdk.Adapters.ExchangeAdapter;
import com.example.alfasdk.Const.Constants;
import com.example.alfasdk.MyMainActivity;
import com.example.alfasdk.Models.ExchangeModel.Exchange;
import com.example.alfasdk.Models.ExchangeModel.ExchangeResponse;
import com.example.alfasdk.R;
import com.example.alfasdk.Util.DividerItemDecoration;
import com.example.alfasdk.Util.MyItemAnimator;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;


import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ExchangeFragment extends Fragment {

    private RecyclerView recyclerView;
    private ExchangeAdapter exchangesAdapter;
    private LinearLayoutManager linearLayoutManager;
    private BroadcastReceiver mFeedReceiver;
    private List<Exchange> exchangeList = new ArrayList<>();

    public ExchangeFragment() {
        // Required empty public constructor
    }


    public static ExchangeFragment newInstance() {
        return new ExchangeFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_exchange, container, false);

        recyclerView=view.findViewById(R.id.exchange_list);
        linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        exchangesAdapter = new ExchangeAdapter(getActivity(), exchangeList, linearLayoutManager, this);
        recyclerView.setItemAnimator(new MyItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));
        recyclerView.setAdapter(exchangesAdapter);
        return view;
    }

    @Override
    public void onResume() {

        ActionBar toolbar = ((AppCompatActivity) getActivity()).getSupportActionBar();

        if (toolbar != null) {
            toolbar.setTitle("Index Watch");
        }
        super.onResume();
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mFeedReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String message = intent.getStringExtra("response");

                if (message != null) {
                    onFeedReceived(message);
                } else {
                    try {
                        ((MyMainActivity) getActivity()).showFeedDisconnectAlert();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        };

        LocalBroadcastManager.getInstance(getContext()).registerReceiver(mFeedReceiver,
                new IntentFilter(Constants.FEED_SERVER_BROADCAST));
        ((MyMainActivity) getActivity()).exchangesRequest();

    }

    @Override
    public void onDetach() {
        // Unregister since the activity is about to be closed.
        LocalBroadcastManager.getInstance(getContext()).unregisterReceiver(mFeedReceiver);
        super.onDetach();
    }

    public void setResult(ExchangeResponse result) {

        if (result != null) {

            exchangeList.clear();
            exchangeList.addAll(result.getResponse().getExchanges());

            exchangesAdapter.notifyDataSetChanged();


        }
    }


    private void onFeedReceived(String resp) {

        Log.d("ExchangesFrag", "resp: " + resp);
        try {
            JsonParser parser = new JsonParser();
            JsonObject json = parser.parse(resp).getAsJsonObject();

            if (json.get("code").getAsString().equals("200")) {

                JsonObject response = json.getAsJsonObject("response");

                JsonArray exchangesArr = response.getAsJsonArray("exchanges");

                if (exchangesArr.size() > 0) {

                    Type listType = new TypeToken<List<Exchange>>() {
                    }.getType();

                    List<Exchange> exchangeList = new Gson().fromJson(exchangesArr, listType);

//                    Log.d("feed exchanges", "" + new Gson().toJson(exchangeList, listType));

                    if (exchangeList != null && exchangeList.size() > 0) {

                        try {
                            exchangesAdapter.updateFeed(exchangeList);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                }


            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
