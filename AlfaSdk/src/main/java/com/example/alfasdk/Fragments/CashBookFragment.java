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

import com.example.alfasdk.Adapters.CashBookAdapter;
import com.example.alfasdk.Adapters.SearchClientListAdapter;
import com.example.alfasdk.MyMainActivity;
import com.example.alfasdk.Models.CashBookModel.CashBookResponse;
import com.example.alfasdk.Models.CashBookModel.CashDatum;
import com.example.alfasdk.R;
import com.example.alfasdk.Util.DividerItemDecoration;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;


public class CashBookFragment extends Fragment {

//    @BindView(R.id.recyclerView_cashBook)
    RecyclerView recyclerView_cashBook;
//    @BindView(R.id.etclientcode)
    EditText clientcode;
//    @BindView(R.id.search_list1)
    ListView listSearch1;
//    @BindView(R.id.search_list_view1)
    LinearLayout listSearch_view1;
    ImageView cancel_search1;
    private SearchClientListAdapter searchClientListAdapter;
    private CashBookResponse values;
    ArrayList<String> clientlist;
    boolean isSetInitialText = false;

    public CashBookFragment() {
        // Required empty public constructor
    }

    public static CashBookFragment newInstance() {
        return new CashBookFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cash_book, container, false);
//        ButterKnife.bind(this, view);
        recyclerView_cashBook=view.findViewById(R.id.recyclerView_cashBook);
        clientcode=view.findViewById(R.id.etclientcode);
        listSearch1=view.findViewById(R.id.search_list1);
        listSearch_view1=view.findViewById(R.id.search_list_view1);
        cancel_search1=view.findViewById(R.id.cancel_search1);
        if (MyMainActivity.loginResponse.getResponse().getUsertype() == 1 ||
                MyMainActivity.loginResponse.getResponse().getUsertype() == 2) {

            clientcode.setText(MyMainActivity.loginResponse.getResponse().getClient());
            clientcode.setEnabled(false);
            ((MyMainActivity) getActivity()).cashBookRequest(clientcode.getText().toString());
        } else if (MyMainActivity.loginResponse.getResponse().getUsertype() == 0 ||
                MyMainActivity.loginResponse.getResponse().getUsertype() == 3) {

            clientlist = new ArrayList<String>(MyMainActivity.loginResponse.getResponse().getClientlist());
            searchClientListAdapter = new SearchClientListAdapter(getActivity(), clientlist);
        }


        cancel_search1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listSearch_view1.setVisibility(View.GONE);

            }
        });
        return view;
    }

    @Override
    public void onResume() {

        ActionBar toolbar = ((AppCompatActivity) getActivity()).getSupportActionBar();

        if (toolbar != null) {
            toolbar.setTitle("Transaction History");
        }
        super.onResume();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView_cashBook.setLayoutManager(new LinearLayoutManager(getActivity()));

        recyclerView_cashBook.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));
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
                ((MyMainActivity) getActivity()).cashBookRequest(clientlist.get(position));
            }
        });
    }

//    @OnClick(R.id.cancel_search1)
//    public void cancelSearch(View view) {
//        listSearch_view1.setVisibility(View.GONE);
//    }

    public void setValues(CashBookResponse values) {

        this.values = values;

        if (values != null) {

            ArrayList<CashDatum> listMain = (ArrayList<CashDatum>) values.getResponse().getCashData();

            Collections.sort(listMain, new Comparator<CashDatum>() {
                public int compare(CashDatum o1, CashDatum o2) {
                    return o1.getIndex().compareTo(o2.getIndex());
                }
            });

            listMain.add(null);// last balance row in adapter

            CashBookAdapter adapter = new CashBookAdapter(getActivity(), listMain);
            recyclerView_cashBook.setAdapter(adapter);

        }
    }
}
