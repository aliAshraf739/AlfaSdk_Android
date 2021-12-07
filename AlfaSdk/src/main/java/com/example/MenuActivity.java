package com.example;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.alfasdk.Adapters.GridViewAdapter;
import com.example.alfasdk.Adapters.NavAdapter;
import com.example.alfasdk.Fragments.AccountFragment;
import com.example.alfasdk.Fragments.CashBookFragment;
import com.example.alfasdk.Fragments.EventsFragment;
import com.example.alfasdk.Fragments.ExchangeFragment;
import com.example.alfasdk.Fragments.LinksFragment;
import com.example.alfasdk.Fragments.MarginDetailFragment;
import com.example.alfasdk.Fragments.MarketFragment;
import com.example.alfasdk.Fragments.NetSharesFragment;
import com.example.alfasdk.Fragments.OrderStatsFragment;
import com.example.alfasdk.Fragments.PaymentFragment;
import com.example.alfasdk.Fragments.PortfolioFragment;
import com.example.alfasdk.Fragments.QuotesFragment;
import com.example.alfasdk.Fragments.ResearchPortalFragment;
import com.example.alfasdk.Fragments.SettingFragment;
import com.example.alfasdk.Fragments.SymbolsFragment;
import com.example.alfasdk.Fragments.TopSymbolsFragment;
import com.example.alfasdk.Fragments.TradeFragment;
import com.example.alfasdk.Fragments.UserProfileFragment;
import com.example.alfasdk.Models.LoginModel.LoginResponse;
import com.example.alfasdk.Models.MarketModel.MarketResponse;
import com.example.alfasdk.Models.MarketModel.MarketSymbol;
import com.example.alfasdk.Models.Menu;
import com.example.alfasdk.Models.OrderStatsModel.OrdersList;
import com.example.alfasdk.Models.SymbolsModel.Symbol;
import com.example.alfasdk.Models.SymbolsModel.SymbolsResponse;
import com.example.alfasdk.R;
import com.google.gson.Gson;

import java.util.ArrayList;

public class MenuActivity extends AppCompatActivity implements GridViewAdapter.ItemClickListener {

    public static LoginResponse loginResponse;
    public static MarketResponse marketResponse;
    public static SymbolsResponse symbolsResponse;
    GridViewAdapter gridViewAdapter;
    ArrayList<Menu> navMenuList=new ArrayList<>();
    FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        fragmentManager = getSupportFragmentManager();
        initSessionData();
        setRecyclerView();
    }

    private void initSessionData() {

        Gson gson = new Gson();

//        loginResponse = gson.fromJson(preferences.getLoginResult(), LoginResponse.class);
//        marketResponse = gson.fromJson(preferences.getMarketResult(), MarketResponse.class);
//        symbolsResponse = gson.fromJson(preferences.getSymbolResult(), SymbolsResponse.class);
        SharedPreferences mPrefs = getPreferences(MODE_PRIVATE);

        Intent intent = getIntent();

        String marketJson = intent.getStringExtra("marketResponse");
        String loginResult = intent.getStringExtra("loginResult");
        String symbolJson = intent.getStringExtra("symbolResult");


//        String loginJson = mPrefs.getString("loginResult", "");
//        String marketJson = mPrefs.getString("marketResult", "");
//        String symbolJson = mPrefs.getString("symbolResult", "");
        loginResponse = gson.fromJson(loginResult, LoginResponse.class);
        marketResponse = gson.fromJson(marketJson, MarketResponse.class);
        symbolsResponse = gson.fromJson(symbolJson, SymbolsResponse.class);

    }

    private void setRecyclerView(){

        String TrnCodes = loginResponse.getResponse().getTrnCodes();
        Log.d("TrnCodes", "TrnCodes: " + TrnCodes);



        if (TrnCodes.length() > 0) {

            if (TrnCodes.contains("OM01")) {
                navMenuList.add(new Menu("Market", R.drawable.iconmarket2x, false));
            }
            if (TrnCodes.contains("OM19")) {
                //   navMenuList.add(new Menu("Exchange", R.drawable.marketicon2x, false));
                navMenuList.add(new Menu("Index Watch", R.drawable.marketicon2x, false));
            }
            if (TrnCodes.contains("OM24")) {
                navMenuList.add(new Menu("Research Portal", R.drawable.research2x, false));
            }
            if (TrnCodes.contains("OM13")) {
                navMenuList.add(new Menu("Message Board", R.drawable.events2x, false));
            }
            if (TrnCodes.contains("OM03")) {
                navMenuList.add(new Menu("Order Status", R.drawable.orderstatus2x, false));
            }
            if (TrnCodes.contains("OM06") || TrnCodes.contains("OM012")) {
                navMenuList.add(new Menu("Order", R.drawable.trade2x, false));
            }
            if (TrnCodes.contains("OM07")) {
                navMenuList.add(new Menu("Quotes", R.drawable.quotes2x, false));
            }
            if (TrnCodes.contains("OM15")) {
                navMenuList.add(new Menu("Symbols", R.drawable.symbols2x, false));
            }
            if (TrnCodes.contains("OM09")) {
                navMenuList.add(new Menu("Top Symbols", R.drawable.topsymbols2x, false));
            }
            if (TrnCodes.contains("OM04")) {
                navMenuList.add(new Menu("Portfolio Summary", R.drawable.portfoliosummary2x, false));
            }
            if (TrnCodes.contains("OM21")) {
                navMenuList.add(new Menu("Cash Book", R.drawable.cashbook2x, false));
            }
            if (TrnCodes.contains("OM14")) {
                navMenuList.add(new Menu("Payment Request", R.drawable.paymentrequest2x, false));
            }
            if (TrnCodes.contains("OM18")) {
                navMenuList.add(new Menu("Profile", R.drawable.profileicon2x, false));
            }

            if (TrnCodes.contains("OM02")) {
                navMenuList.add(new Menu("Account Status", R.drawable.account2x, false));
            }
            if (TrnCodes.contains("OM10")) {
                navMenuList.add(new Menu("Settings", R.drawable.setting2x, false));
            }
            if (TrnCodes.contains("OM17")) {
                navMenuList.add(new Menu("Links", R.drawable.linkicon2x, false));
            }


            if (TrnCodes.contains("OM16")) {
                navMenuList.add(new Menu("Margin Detail", R.drawable.margin2x, false));
            }
            if (TrnCodes.contains("OM22")) {
                navMenuList.add(new Menu("Net Shares", R.drawable.netcustody2x, false));
            }


        } else {
            navMenuList.add(new Menu("Market", R.drawable.iconmarket2x, false));

            //     navMenuList.add(new Menu("Exchanges", R.drawable.marketicon2x, false));
            navMenuList.add(new Menu("Market Indices", R.drawable.marketicon2x, false));
        }

        RecyclerView recyclerView = findViewById(R.id.rvNumbers);
        int numberOfColumns = 6;
        recyclerView.setLayoutManager(new GridLayoutManager(this, numberOfColumns));
        gridViewAdapter = new GridViewAdapter(this, navMenuList);
        gridViewAdapter.setClickListener(this);
        recyclerView.setAdapter(gridViewAdapter);
    }

    @Override
    public void onItemClick(View view, int position) {

        if (navMenuList.get(position).getIc_resource()== R.drawable.iconmarket2x) {
            replaceFragment(new MarketFragment(), true, false);
        } else if (navMenuList.get(position).getIc_resource() == R.drawable.orderstatus2x) {
            replaceFragment(new OrderStatsFragment(), true, false);
        }
//        else if (item.getIc_resource() == R.drawable.logout2x) {
//            logoutAlert();
//        }
        else if (navMenuList.get(position).getIc_resource() == R.drawable.events2x) {
            replaceFragment(EventsFragment.newInstance(), true, false);
        } else if (navMenuList.get(position).getIc_resource() == R.drawable.linkicon2x) {
            replaceFragment(LinksFragment.newInstance(), true, false);
        } else if (navMenuList.get(position).getIc_resource() == R.drawable.margin2x) {
            replaceFragment(MarginDetailFragment.newInstance(loginResponse.getResponse().getClient()), true, false);
        } else if (navMenuList.get(position).getIc_resource() == R.drawable.marketicon2x) {
            replaceFragment(ExchangeFragment.newInstance(), true, false);
        } else if (navMenuList.get(position).getIc_resource() == R.drawable.quotes2x) {
            replaceFragment(QuotesFragment.newInstance(null), true, false);
        } else if (navMenuList.get(position).getIc_resource()== R.drawable.topsymbols2x) {
            replaceFragment(TopSymbolsFragment.newInstance(), true, false);
        } else if (navMenuList.get(position).getIc_resource()== R.drawable.portfoliosummary2x) {
            replaceFragment(PortfolioFragment.newInstance(), true, false);
        } else if (navMenuList.get(position).getIc_resource() == R.drawable.symbols2x) {
            replaceFragment(SymbolsFragment.newInstance(), true, false);
        } else if (navMenuList.get(position).getIc_resource()== R.drawable.profileicon2x) {
            replaceFragment(UserProfileFragment.newInstance(loginResponse.getResponse().getClient()
                    , loginResponse.getResponse().getExchange()), true, false);
        } else if (navMenuList.get(position).getIc_resource()== R.drawable.account2x) {
            replaceFragment(AccountFragment.newInstance(), true, false);
        } else if (navMenuList.get(position).getIc_resource() == R.drawable.setting2x) {
            replaceFragment(SettingFragment.newInstance(), true, false);
        } else if (navMenuList.get(position).getIc_resource()== R.drawable.paymentrequest2x) {
            replaceFragment(PaymentFragment.newInstance(), true, false);
        } else if (navMenuList.get(position).getIc_resource()== R.drawable.trade2x) {
//            if (isTrnCodeAvailable("OM06") || isTrnCodeAvailable("OM012")) {
//                replaceFragment(TradeFragment.newInstance(null), true, false);
//            }
        } else if (navMenuList.get(position).getIc_resource() == R.drawable.cashbook2x) {
            replaceFragment(CashBookFragment.newInstance(), true, false);
        } else if (navMenuList.get(position).getIc_resource()== R.drawable.netcustody2x) {
            replaceFragment(NetSharesFragment.newInstance(), true, false);
        } else if (navMenuList.get(position).getIc_resource() == R.drawable.research2x) {
            replaceFragment(ResearchPortalFragment.newInstance(null), true, false);
        }
    }

    public void replaceFragment(Fragment fragment, boolean popStack, boolean isChild) {

        String backStateName = fragment.getClass().getName();

        FragmentTransaction ft = fragmentManager.beginTransaction();

        if (isChild) {
            ft.add(R.id.fragment_container, fragment, backStateName);
        } else {
            fragmentManager.popBackStackImmediate();
            ft.replace(R.id.fragment_container, fragment, backStateName);
        }


        ft.addToBackStack(backStateName);
        ft.commit();

    }


//
//    @Override
//    public void onMarketFragmentListener(int which, MarketSymbol sym) {
//        switch (optionItems.get(which)) {
//
//            case "Summary": { //summary
//                Log.d("Summary", new Gson().toJson(sym, MarketSymbol.class));
//                QuotesFragment fragment = QuotesFragment.newInstance(new Gson().toJson(sym, MarketSymbol.class));
//                replaceFragment(fragment, true, true);
//            }
//            break;
//
//            case "Trade": { //trade
////                goToTrade(sym);
//            }
//            break;
//
//            case "Charts": { //charts
////                goToCharts(sym);
//
//            }
//            break;
//            case "Research":
//                ResearchPortalFragment fragment = ResearchPortalFragment.newInstance(sym.getSymbol());
//                replaceFragment(fragment, true, true);
//                // callingResearchPortalService(Constants.RESEARCH_PORTAL_URL);
//                break;
//        }
//    }

}