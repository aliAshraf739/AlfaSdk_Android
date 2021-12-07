package com.example.alfasdk.Fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
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
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.example.alfasdk.Adapters.MarketAdapter;
import com.example.alfasdk.Adapters.SearchListAdapter;
import com.example.alfasdk.Adapters.TopPagerAdapter;
import com.example.alfasdk.Const.Constants;
import com.example.alfasdk.MyMainActivity;
import com.example.alfasdk.Models.MarketModel.Exchange;
import com.example.alfasdk.Models.MarketModel.MarketSymbol;
import com.example.alfasdk.Models.SymbolsModel.Symbol;
import com.example.alfasdk.R;
import com.example.alfasdk.Util.DividerItemDecoration;
import com.example.alfasdk.Util.HSnackBar;
import com.example.alfasdk.Util.HToast;
import com.example.alfasdk.Util.MyItemAnimator;
import com.example.alfasdk.Util.Util;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;


import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


public class MarketFragment extends Fragment implements MarketAdapter.OnMarketItemClickListener {

    private static int postionToRemove = -1;
    public Boolean shouldReload = false;
    public boolean isReloaded = false;

    ViewPager mPager;
    RecyclerView marketListView;
    EditText etSearch;
    ListView listSearch;
    LinearLayout listSearch_view;

    ImageView cancel_search,left_arrow,right_arrow;
    List<Symbol> searchKeywordsList = new ArrayList<>();
    private OnMarketFragmentListener mListener;
    private OnSymbolRequest mAddSymbol;
    private SearchListAdapter searchAdapter;
    private MarketAdapter marketAdapter;
    private RecyclerView.LayoutManager linearLayoutManager;
    private TopPagerAdapter pagerAdapter;
    private BroadcastReceiver mFeedReceiver;
    private MenuItem feedMenuItem;
    private boolean isConnected;
    private FloatingActionButton backbtn;
    public MarketFragment() {
        // Required empty public constructor
    }

    public static MarketFragment newInstance() {
        return new MarketFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_market, container, false);
//        ButterKnife.bind(this, view);
        mPager=view.findViewById(R.id.pager);
        marketListView=view.findViewById(R.id.market_list);
        etSearch=view.findViewById(R.id.editText);
        listSearch=view.findViewById(R.id.search_list);
        listSearch_view=view.findViewById(R.id.search_list_view);
        cancel_search=view.findViewById(R.id.cancel_search);
        left_arrow=view.findViewById(R.id.left_arrow);
        right_arrow=view.findViewById(R.id.right_arrow);
        backbtn=view.findViewById(R.id.backbtn);

        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });

        left_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mPager.getCurrentItem() == mPager.getAdapter().getCount() - 1) {
                    mPager.setCurrentItem(0, true);
                } else {
                    mPager.setCurrentItem(mPager.getCurrentItem() + 1, true);
                }
            }
        });


        right_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mPager.getCurrentItem() == 0) {
                    mPager.setCurrentItem(mPager.getAdapter().getCount() - 1, true);
                } else {
                    mPager.setCurrentItem(mPager.getCurrentItem() - 1, true);
                }

            }
        });


        cancel_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelSearch(v);
            }
        });

        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setAutoMeasureEnabled(false);

        try{
            Collections.sort(MyMainActivity.marketResponse.getResponse().getSymbols(), new Comparator<MarketSymbol>() {
                @Override
                public int compare(MarketSymbol e1, MarketSymbol e) {
                    return e1.getSymbol().compareTo(e.getSymbol());
                }
            });
            marketAdapter = new MarketAdapter(getActivity(), MyMainActivity.marketResponse.getResponse().getSymbols(), linearLayoutManager, MarketFragment.this);

        }
        catch (Exception e){
            e.printStackTrace();
        }

        setHasOptionsMenu(true);

        // For bipl flavor
//        searchKeywordsList = new ArrayList<>(MainActivity.symbolsResponse.getResponse().getSymbols());
        Log.d("search_debug", "onCreate: setting adapter with " + searchKeywordsList.size());
        searchAdapter = new SearchListAdapter(getContext(), searchKeywordsList);
        // For bipl flavor
    }

    public void setSearchSymbols() {
//        Log.d("MarketFragment","search");
        searchKeywordsList.clear();
        searchKeywordsList.addAll(MyMainActivity.symbolsResponse.getResponse().getSymbols());
        searchAdapter = new SearchListAdapter(getContext(), searchKeywordsList);
        Log.d("search_debug", "setSearchSymbols: setting adapter with " + searchKeywordsList.size());
        listSearch.setAdapter(searchAdapter);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        MenuItem item = menu.findItem(R.id.action_home);
        item.setVisible(false);

        MenuItem item2 = menu.findItem(R.id.action_net_share);
        item2.setVisible(false);

        feedMenuItem = menu.findItem(R.id.action_feed_status);
//        feedMenuItem.setVisible(true);
        feedMenuItem.setVisible(false);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_feed_status) {
            if (!isConnected) {
                ((MyMainActivity) getActivity()).connectFeed();
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onResume() {
        Log.d("search_debug", "onResume: ");
        // when fragment goes onPause state, it clears the last of search symbols
        // readding those symbols in onResume
        if(MyMainActivity.symbolsResponse != null) setSearchSymbols();

        ActionBar toolbar = ((AppCompatActivity) getActivity()).getSupportActionBar();

        if (toolbar != null) {
            toolbar.setTitle("Market");
        }
        listSearch_view.setVisibility(View.GONE);
        super.onResume();



        if (shouldReload) {

            ((MyMainActivity) getActivity()).getMarket();

            shouldReload = false;
        }

//        Log.d("MarketFragment", "isReloaded: " + isReloaded);

        if (isReloaded) {
//            Log.d("MarketFragment", "isReloaded: loaded ");
//            marketSymbolList = new ArrayList<>(MainActivity.marketResponse.getResponse().getSymbols());
            Collections.sort(MyMainActivity.marketResponse.getResponse().getSymbols(), new Comparator<MarketSymbol>() {
                @Override
                public int compare(MarketSymbol e1, MarketSymbol e) {
                    return e1.getSymbol().compareTo(e.getSymbol());
                }
            });
            marketAdapter = new MarketAdapter(getActivity(), MyMainActivity.marketResponse.getResponse().getSymbols(), linearLayoutManager, MarketFragment.this);
            marketListView.setAdapter(marketAdapter);

            isReloaded = false;
        }

//        Log.d("MarketFragment", "onResume");
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (marketListView.getLayoutManager() == null) {
            marketListView.setLayoutManager(linearLayoutManager);
        }

        marketListView.setItemAnimator(new MyItemAnimator());

        pagerAdapter = new TopPagerAdapter(getActivity(), MyMainActivity.marketResponse.getResponse().getExchanges(), mPager);

        mPager.setAdapter(pagerAdapter);

        marketListView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));
        marketListView.setHasFixedSize(true);
        marketListView.setAdapter(marketAdapter);

        listSearch.setAdapter(searchAdapter);

        listSearch.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                listSearch_view.setVisibility(View.GONE);

                Symbol symbol = searchKeywordsList.get(position);


                if (!symbolAlreadyAdded(symbol)) {

                    if (mAddSymbol != null) {
                        mAddSymbol.onSymbolAddRequest(searchKeywordsList.get(position));
                    } else {
//                        Log.d("onItemClick", "mAddSymbol==null");
                    }
                } else {

                    HToast.showMsg(getActivity(), "Symbol Already added.");

                }


            }
        });

        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {


            }

            @Override
            public void afterTextChanged(Editable s) {


                try {
                    if (s.length() >= 2) {
                        listSearch_view.setVisibility(View.VISIBLE);
                        String text = etSearch.getText().toString();
//                        Log.d("symbolsearch", text);
                        searchAdapter.filter(text);
                    } else {
                        listSearch_view.setVisibility(View.GONE);

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.i("testingsearch", "Exception: " + e.getMessage());
                }
            }
        });


        mPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {


//                onPageFinished(position);

            }

            @Override
            public void onPageScrollStateChanged(int state) {


            }
        });
//        Log.d("MarketFragment", "Created: ");


        mFeedReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
//                Log.d("MarketFragment", "mFeedReceiver: ");

                String message = intent.getStringExtra("response");


                if (message != null) {
//                    Log.d("MarketFragment", "message: "+message);
                    onFeedReceived(message);
                    updateFeedServerStatus(true);
                } else {
//                    Log.d("MarketFragment", "isConnected: "+isConnected);
                    isConnected = intent.getBooleanExtra("isConnected", false);
                    updateFeedServerStatus(isConnected);
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

    private void updateFeedServerStatus(boolean isConnected) {

//        Log.d("MarketFragment","isConnected: "+isConnected);

        try {
            if (isConnected) {
//                feedMenuItem.setIcon(R.drawable.light_green);
            } else {
//                feedMenuItem.setIcon(R.drawable.light_red);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDetach() {
        // Unregister since the activity is about to be closed.
        Log.d("search_debug", "onDetach: ");

        LocalBroadcastManager.getInstance(getContext()).unregisterReceiver(mFeedReceiver);
        super.onDetach();
        mListener = null;
        mAddSymbol = null;
    }

//    @OnClick({R.id.left_arrow, R.id.right_arrow})
//    public void pagerChange(View v) {
//
//        if (v.getId() == R.id.left_arrow) {
//
//            if (mPager.getCurrentItem() == mPager.getAdapter().getCount() - 1) {
//                mPager.setCurrentItem(0, true);
//            } else {
//                mPager.setCurrentItem(mPager.getCurrentItem() + 1, true);
//            }
//
//        } else {
//
//            if (mPager.getCurrentItem() == 0) {
//                mPager.setCurrentItem(mPager.getAdapter().getCount() - 1, true);
//            } else {
//                mPager.setCurrentItem(mPager.getCurrentItem() - 1, true);
//            }
//
//        }
////        onPageFinished(mPager.getCurrentItem());
//    }

    private boolean symbolAlreadyAdded(Symbol symToCompare) {


        for (int j = 0; j < MyMainActivity.marketResponse.getResponse().getSymbols().size(); j++) {

            MarketSymbol existingSym = MyMainActivity.marketResponse.getResponse().getSymbols().get(j);

//            Log.d("updateFeed", "symToCompare: " + symToCompare.getSymbol() + " "
//                    + " " + symToCompare.getMarket() + " " + symToCompare.getExchangeCode());

//            Log.d("updateFeed", "existingSym: " + existingSym.getSymbol() + " "
//                    + " " + existingSym.getMarket() + " " + existingSym.getExchangeCode());

            if (symToCompare.getSymbol().equals(existingSym.getSymbol())
                    && symToCompare.getMarket().equals(existingSym.getMarket())
                    && symToCompare.getExchangeCode().equals(existingSym.getExchangeCode())) {


                return true;

            }

        }

        return false;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnMarketFragmentListener) {
            mListener = (OnMarketFragmentListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnMarketFragmentListener");
        }

        if (context instanceof OnSymbolRequest) {
            mAddSymbol = (OnSymbolRequest) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnSymbolAddRequest");
        }
    }

    @SuppressWarnings("deprecation")
    @Override
    public void onAttach(Activity context) {
        super.onAttach(context);
        if (context instanceof OnMarketFragmentListener) {
            mListener = (OnMarketFragmentListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnMarketFragmentListener");
        }

        if (context instanceof OnSymbolRequest) {
            mAddSymbol = (OnSymbolRequest) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnSymbolAddRequest");
        }
    }

    public void removeMarket() {

        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (postionToRemove > -1) {

//                    marketSymbolList.remove(postionToRemove);
                    MyMainActivity.marketResponse.getResponse().getSymbols().remove(postionToRemove);

//                    marketSymbolList =  new ArrayList<>( MainActivity.marketResponse.getResponse().getSymbols());

                    marketAdapter = new MarketAdapter(getActivity(), MyMainActivity.marketResponse.getResponse().getSymbols(), linearLayoutManager, MarketFragment.this);

                    marketListView.setAdapter(marketAdapter);

                    HSnackBar.showMsg(marketListView, "Successfully removed.");
                }
            }
        });
    }

    public void addMarketItem(final MarketSymbol sym) {

        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {

//                marketSymbolList.add(sym);

                MyMainActivity.marketResponse.getResponse().getSymbols().add(sym);

//                marketSymbolList =  new ArrayList<>( MainActivity.marketResponse.getResponse().getSymbols());

                Collections.sort(MyMainActivity.marketResponse.getResponse().getSymbols(), new Comparator<MarketSymbol>() {
                    @Override
                    public int compare(MarketSymbol e1, MarketSymbol e) {
                        return e1.getSymbol().compareTo(e.getSymbol());
                    }
                });
                marketAdapter = new MarketAdapter(getActivity(), MyMainActivity.marketResponse.getResponse().getSymbols(), linearLayoutManager, MarketFragment.this);

                marketListView.setAdapter(marketAdapter);

                HSnackBar.showMsg(marketListView, sym.getSymbol() + " Successfully added.");

                Util.hideKeyboard(getActivity());

                etSearch.setText("");
                listSearch_view.setVisibility(View.GONE);
            }
        });

    }

    public void cancelSearch(View view) {
        listSearch_view.setVisibility(View.GONE);
    }

    public void onFeedReceived(String resp) {

        try {
            JsonParser parser = new JsonParser();
            JsonObject json = parser.parse(resp).getAsJsonObject();

            if (json.get("code").getAsString().equals("200")) {

                JsonObject response = json.getAsJsonObject("response");

                JsonArray exchangesArr = response.getAsJsonArray("exchanges");

                JsonArray symbolsArr = response.getAsJsonArray("symbols");

                if (exchangesArr.size() > 0) {

                    Type listType = new TypeToken<List<Exchange>>() {
                    }.getType();

                    List<Exchange> exchangeList = new Gson().fromJson(exchangesArr, listType);

//                    Log.d("feed exchanges", "" + new Gson().toJson(exchangeList, listType));

                    if (exchangeList != null && exchangeList.size() > 0) {

                        try {
                            pagerAdapter.updateFeed(exchangeList);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                }

                if (symbolsArr.size() > 0) {

                    Type listType = new TypeToken<List<MarketSymbol>>() {
                    }.getType();

                    List<MarketSymbol> symbolList = new Gson().fromJson(symbolsArr, listType);

//                    Log.d("feed symbols", "" + new Gson().toJson(symbolList, listType));

                    if (symbolList != null && symbolList.size() > 0) {

                        marketAdapter.updateFeed(symbolList);
                    }

                }


            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showOptionsMenu(final MarketSymbol sym, final int position) {

        final String[] stringArray = MyMainActivity.optionItems.toArray(new String[0]);

        Util.hideKeyboard(requireActivity());

        AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
        alert.setTitle("Actions for " + sym.getSymbol());
        alert.setItems(stringArray, (dialog, which) -> {


            if (stringArray[which].equals("Delete")) {

                AlertDialog.Builder alert1 = new AlertDialog.Builder(getActivity());
                alert1.setTitle("Delete");
                alert1.setMessage("Do you want to delete " + sym.getName() + "?");
                alert1.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                alert1.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();

                        if (mAddSymbol != null) {
                            mAddSymbol.onSymbolDeleteRequest(sym);

                            MarketFragment.postionToRemove = position;

//                                Log.d("onSymbolDeleteRequest", "position: " + position);

                        }
                    }
                });

                alert1.show();
            } else {
                if (mListener != null) {
                    mListener.onMarketFragmentListener(which, sym);
                }
            }


        } );

        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.dismiss();
            }
        });
        alert.show();
    }

    @Override
    public void onMarketItemClick(View caller, MarketSymbol mItem, int position, boolean openTrade) {
        if (openTrade) {
            ((MyMainActivity) getActivity()).goToTrade(mItem);
        } else {
//            Log.d("MarketFrag", new Gson().toJson(mItem, MarketSymbol.class));
            showOptionsMenu(mItem, position);
        }
    }

    public interface OnMarketFragmentListener {
        void onMarketFragmentListener(int which, MarketSymbol sym);
    }

    public interface OnSymbolRequest {
        void onSymbolAddRequest(Symbol symbol);

        void onSymbolDeleteRequest(MarketSymbol symbol);
    }

    @Override
    public void onPause() {
        super.onPause();
        etSearch.setText("");
        Log.d("search_debug", "onPause: ");
    }

}
