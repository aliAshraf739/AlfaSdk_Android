package com.example.alfasdk.Fragments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.alfasdk.Adapters.GridViewAdapter;
import com.example.alfasdk.Models.LoginModel.LoginResponse;
import com.example.alfasdk.Models.MarketModel.MarketResponse;
import com.example.alfasdk.Models.Menu;
import com.example.alfasdk.Models.SymbolsModel.SymbolsResponse;
import com.example.alfasdk.MyMainActivity;
import com.example.alfasdk.R;
import com.google.gson.Gson;

import java.util.ArrayList;


public class MenuFragment extends Fragment implements GridViewAdapter.ItemClickListener{
    GridViewAdapter gridViewAdapter;
    ArrayList<Menu> navMenuList=new ArrayList<>();
    FragmentManager fragmentManager;
    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_menu, container, false);
        fragmentManager = getFragmentManager();
        setRecyclerView();
        return view;
    }

    @Override
    public void onResume() {

        ActionBar toolbar = ((AppCompatActivity) getActivity()).getSupportActionBar();

        if (toolbar != null) {
            toolbar.setTitle("Home");
        }
        super.onResume();
    }

    private void setRecyclerView(){

        String TrnCodes = MyMainActivity.loginResponse.getResponse().getTrnCodes();
        Log.d("TrnCodes", "TrnCodes: " + TrnCodes);



        if (TrnCodes.length() > 0) {

            if (TrnCodes.contains("OM01")) {
                navMenuList.add(new Menu("Market", R.drawable.market_red, false));
            }
            if (TrnCodes.contains("OM19")) {
                //   navMenuList.add(new Menu("Exchange", R.drawable.marketicon2x, false));
                navMenuList.add(new Menu("Index Watch", R.drawable.index_watch_red, false));
            }
            if (TrnCodes.contains("OM24")) {
                navMenuList.add(new Menu("Explore Us", R.drawable.research_red, false));
            }
            //            if (TrnCodes.contains("OM13")) {
//                navMenuList.add(new Menu("Message Board", R.drawable.message_board_red, false));
//            }
            if (TrnCodes.contains("OM03")) {
                navMenuList.add(new Menu("Order Status", R.drawable.order_status, false));
            }
            if (TrnCodes.contains("OM06") || TrnCodes.contains("OM012")) {
                navMenuList.add(new Menu("Order", R.drawable.order_red, false));
            }
            if (TrnCodes.contains("OM07")) {
                navMenuList.add(new Menu("Symbols Summary", R.drawable.qoutes_red, false));
            }
            if (TrnCodes.contains("OM15")) {
                navMenuList.add(new Menu("Symbols", R.drawable.symbols_red, false));
            }
            if (TrnCodes.contains("OM09")) {
                navMenuList.add(new Menu("Top Symbols", R.drawable.top_symbols_red, false));
            }
            if (TrnCodes.contains("OM04")) {
                navMenuList.add(new Menu("My Portfolio", R.drawable.potfolio_summary_red, false));
            }
            if (TrnCodes.contains("OM21")) {
                navMenuList.add(new Menu("Transaction History", R.drawable.cashbook_red, false));
            }
            if (TrnCodes.contains("OM14")) {
                navMenuList.add(new Menu("Cash Withdrawal", R.drawable.payment_request_red, false));
            }

            //            if (TrnCodes.contains("OM18")) {
//                navMenuList.add(new Menu("Profile", R.drawable.profile_red, false));
//            }

            if (TrnCodes.contains("OM02")) {
                navMenuList.add(new Menu("Account Status", R.drawable.account_status_red, false));
            }

            //            if (TrnCodes.contains("OM10")) {
//                navMenuList.add(new Menu("Settings", R.drawable.settings_red, false));
//            }

//            if (TrnCodes.contains("OM17")) {
//                navMenuList.add(new Menu("Links", R.drawable.links_red, false));
//            }

            if (TrnCodes.contains("OM16")) {
                navMenuList.add(new Menu("Margin Detail", R.drawable.account_status_red, false));
            }
            if (TrnCodes.contains("OM22")) {
                navMenuList.add(new Menu("Net Shares", R.drawable.netcustody_red, false));
            }


        } else {
            navMenuList.add(new Menu("Market", R.drawable.market_red, false));

            //     navMenuList.add(new Menu("Exchanges", R.drawable.marketicon2x, false));
            navMenuList.add(new Menu("Market Indices", R.drawable.index_watch_red, false));
        }

        RecyclerView recyclerView = view.findViewById(R.id.rvNumbers);
        int numberOfColumns = 4;
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
        gridViewAdapter = new GridViewAdapter(getContext(), navMenuList);
        gridViewAdapter.setClickListener(this);
        recyclerView.setAdapter(gridViewAdapter);
    }

    @Override
    public void onItemClick(View view, int position) {

        if (navMenuList.get(position).getIc_resource()== R.drawable.market_red) {
            replaceFragment(new MarketFragment(), true, false);
        }
        else if (navMenuList.get(position).getIc_resource() == R.drawable.order_status) {
            replaceFragment(new OrderStatsFragment(), true, false);
        }

        else if (navMenuList.get(position).getIc_resource() == R.drawable.index_watch_red) {
            replaceFragment(new ExchangeFragment(), true, false);
        }
//        else if (item.getIc_resource() == R.drawable.logout2x) {
//            logoutAlert();
//        }
        else if (navMenuList.get(position).getIc_resource() == R.drawable.message_board_red) {
            replaceFragment(EventsFragment.newInstance(), true, false);
        } else if (navMenuList.get(position).getIc_resource() == R.drawable.links_red) {
            replaceFragment(LinksFragment.newInstance(), true, false);
        }

//        else if (navMenuList.get(position).getIc_resource() == R.drawable.account_status_red) {
//            replaceFragment(MarginDetailFragment.newInstance(MyMainActivity.loginResponse.getResponse().getClient()), true, false);
//        }
        else if (navMenuList.get(position).getIc_resource() == R.drawable.market_red) {
            replaceFragment(ExchangeFragment.newInstance(), true, false);
        } else if (navMenuList.get(position).getIc_resource() == R.drawable.qoutes_red) {
            replaceFragment(QuotesFragment.newInstance(null), true, false);
        } else if (navMenuList.get(position).getIc_resource()== R.drawable.top_symbols_red) {
            replaceFragment(TopSymbolsFragment.newInstance(), true, false);
        } else if (navMenuList.get(position).getIc_resource()== R.drawable.potfolio_summary_red) {
            replaceFragment(PortfolioFragment.newInstance(), true, false);
        } else if (navMenuList.get(position).getIc_resource() == R.drawable.symbols_red) {
            replaceFragment(SymbolsFragment.newInstance(), true, false);
        } else if (navMenuList.get(position).getIc_resource()== R.drawable.profile_red) {
            replaceFragment(UserProfileFragment.newInstance(MyMainActivity.loginResponse.getResponse().getClient()
                    , MyMainActivity.loginResponse.getResponse().getExchange()), true, false);
        } else if (navMenuList.get(position).getIc_resource()== R.drawable.account_status_red) {
            replaceFragment(AccountFragment.newInstance(), true, false);
        } else if (navMenuList.get(position).getIc_resource() == R.drawable.settings_red) {
            replaceFragment(SettingFragment.newInstance(), true, false);
        } else if (navMenuList.get(position).getIc_resource()== R.drawable.payment_request_red) {
            replaceFragment(PaymentFragment.newInstance(), true, false);
        } else if (navMenuList.get(position).getIc_resource()== R.drawable.order_red) {
//            if (isTrnCodeAvailable("OM06") || isTrnCodeAvailable("OM012")) {
                replaceFragment(TradeFragment.newInstance(null), true, false);
//            }
        } else if (navMenuList.get(position).getIc_resource() == R.drawable.cashbook_red) {
            replaceFragment(CashBookFragment.newInstance(), true, false);
        } else if (navMenuList.get(position).getIc_resource()== R.drawable.netcustody_red) {
            replaceFragment(NetSharesFragment.newInstance(), true, false);
        } else if (navMenuList.get(position).getIc_resource() == R.drawable.research_red) {
            replaceFragment(ResearchPortalFragment.newInstance(null), true, false);
        }
    }

    public void replaceFragment(Fragment fragment, boolean popStack, boolean isChild) {

        String backStateName = fragment.getClass().getName();

        FragmentTransaction ft = fragmentManager.beginTransaction();

        if (isChild) {
            ft.add(R.id.fragment_container, fragment, backStateName);
        } else {
//            fragmentManager.popBackStackImmediate();
            ft.add(R.id.fragment_container, fragment, backStateName);
        }


        ft.addToBackStack(backStateName);
        ft.commit();

    }

}