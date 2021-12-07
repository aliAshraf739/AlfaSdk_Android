package com.example.alfasdk.Fragments;


import android.os.Bundle;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;


import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.alfasdk.Adapters.SymbolsAdapter;
import com.example.alfasdk.MyMainActivity;
import com.example.alfasdk.Models.SymbolsModel.Symbol;
import com.example.alfasdk.R;

import java.util.ArrayList;
import java.util.List;


public class SymbolsFragment extends Fragment {

    ListView symbol_list;
    EditText searchField;
    List<Symbol> arrayList,filteredArraylist;
    boolean isSearched = false;
    SymbolsAdapter symbolsAdapter;

    public SymbolsFragment() {
    }

    public static SymbolsFragment newInstance() {
        return new SymbolsFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_symbols, container, false);
//        ButterKnife.bind(this, view);
        symbol_list=view.findViewById(R.id.symbol_list);
        searchField=view.findViewById(R.id.searchField);
        return view;
    }

    @Override
    public void onResume() {

        ActionBar toolbar = ((AppCompatActivity) getActivity()).getSupportActionBar();

        if (toolbar != null) {
            toolbar.setTitle("Symbols");
        }
        super.onResume();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        try {
            arrayList = new ArrayList<>(MyMainActivity.symbolsResponse.getResponse().getSymbols());
            symbolsAdapter = new SymbolsAdapter(getActivity(), arrayList);
            symbol_list.setAdapter(symbolsAdapter);
            searchField.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    filter(s.toString());
                }
            });
            symbol_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Symbol symbol = null;
                    if (!isSearched) {
                        symbol = arrayList.get(position);
                    }
                    else
                        {
                            symbol=filteredArraylist.get(position);
                        }
// filtered arraylist
                    ((MyMainActivity) getActivity()).addSymbolRequest(symbol);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void filter(String text) {

        filteredArraylist = new ArrayList();

        for (Symbol symbol : arrayList) {

            if (symbol.getSymbol().toLowerCase().startsWith(text.toLowerCase())) {
                filteredArraylist.add(symbol);
                isSearched=true;
            }
            symbolsAdapter.updateList((List<Symbol>) filteredArraylist);
        }
    }
}
