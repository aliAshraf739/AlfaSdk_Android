package com.example.alfasdk;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

import com.example.alfasdk.Adapters.NavAdapter;
import com.example.alfasdk.Fragments.MarketFragment;
import com.example.alfasdk.Fragments.OrderStatsFragment;
import com.example.alfasdk.Fragments.QuotesFragment;
import com.example.alfasdk.Models.MarketModel.MarketSymbol;
import com.example.alfasdk.Models.Menu;
import com.example.alfasdk.Models.OrderStatsModel.OrdersList;
import com.example.alfasdk.Models.SymbolsModel.Symbol;

public class EmptyActivity extends BaseActivity implements NavAdapter.OnMenuInteractionListener,
        MarketFragment.OnMarketFragmentListener, MarketFragment.OnSymbolRequest,
        OrderStatsFragment.OnOrderDeleteRequest, QuotesFragment.OnQoutesFragmentListener {

    TextView tvText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_empty);
        SharedPreferences mPrefs = getPreferences(MODE_PRIVATE);
        String loginJson = mPrefs.getString("loginResult", "");

        tvText=findViewById(R.id.tvText);
        tvText.setText("asdasd");
    }

    @Override
    public void onMenuInteraction(Menu item) {

    }

    @Override
    public void onMarketFragmentListener(int which, MarketSymbol sym) {

    }

    @Override
    public void onSymbolAddRequest(Symbol symbol) {

    }

    @Override
    public void onSymbolDeleteRequest(MarketSymbol symbol) {

    }

    @Override
    public void onOrderDeleteRequest(OrdersList order) {

    }

    @Override
    public void onOnQoutesFragmentListener(Symbol symbol) {

    }
}