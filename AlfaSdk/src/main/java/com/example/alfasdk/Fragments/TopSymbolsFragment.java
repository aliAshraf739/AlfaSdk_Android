package com.example.alfasdk.Fragments;


import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;


import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.alfasdk.Adapters.TopSymbolAdapter;
import com.example.alfasdk.MyMainActivity;
import com.example.alfasdk.Models.TopSymModel.TopSymbol;
import com.example.alfasdk.R;
import com.example.alfasdk.Util.Alert;

import java.util.ArrayList;
import java.util.List;



public class TopSymbolsFragment extends Fragment {

//    @BindView(R.id.top_symbol_list)
    ListView top_symbol_list;

//    @BindViews({R.id.gainers, R.id.losers, R.id.volleader})
//    List<Button> buttonViews;
    Button gainers,losers,volleader;

    private List<TopSymbol> values;

    public TopSymbolsFragment() {
    }

    public static TopSymbolsFragment newInstance() {
        return new TopSymbolsFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_top_symbols, container, false);
//        ButterKnife.bind(this, view);
        top_symbol_list=view.findViewById(R.id.top_symbol_list);
        gainers=view.findViewById(R.id.gainers);
        losers=view.findViewById(R.id.losers);
        volleader=view.findViewById(R.id.volleader);


        gainers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gainers(v);
            }
        });

        losers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                losers(v);
            }
        });

        volleader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                leaders(v);
            }
        });
        return view;
    }

//    @OnClick(R.id.gainers)
    public void gainers(View view) {

        setValues(values, "1");

        resetButtonView(view);
    }

//    @OnClick(R.id.losers)
    public void losers(View view) {

        setValues(values, "2");

        resetButtonView(view);
    }

//    @OnClick(R.id.volleader)
    public void leaders(View view) {

        setValues(values, "3");

        resetButtonView(view);
    }


    private void resetButtonView(View view) {

//        for (Button button : buttonViews) {

        gainers.setBackgroundResource(R.drawable.unselected);
        losers.setBackgroundResource(R.drawable.unselected);
        volleader.setBackgroundResource(R.drawable.unselected);
//        }

        view.setBackgroundResource(R.drawable.selected);
    }

    @Override
    public void onResume() {

        ActionBar toolbar = ((AppCompatActivity) getActivity()).getSupportActionBar();

        if (toolbar != null) {
            // change1
            toolbar.setTitle("Top Symbols");
        }
        super.onResume();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ((MyMainActivity) getActivity()).topSymbolRequest();
    }

    public void setValues(List<TopSymbol> values, String dataType) {
        this.values = values;

        if (values != null && values.size() > 0) {

            List<TopSymbol> arrayToShow = new ArrayList<>();

            for (TopSymbol obj : values) {

                if (obj.getDataType().equals(dataType)) {

                    arrayToShow.add(obj);
                }
            }

            TopSymbolAdapter adapter = new TopSymbolAdapter(getActivity(), arrayToShow);
            top_symbol_list.setAdapter(adapter);

        } else {

            Alert.show(getActivity(), getString(R.string.app_name), "No Symbols to display, Try again later.");
        }
    }
}
