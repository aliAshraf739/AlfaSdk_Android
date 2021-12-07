package com.example.alfasdk.Fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;


import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.alfasdk.Adapters.OrderStatsAdapter;
import com.example.alfasdk.MyMainActivity;
import com.example.alfasdk.Models.OrderStatsModel.OrderStatsResponse;
import com.example.alfasdk.Models.OrderStatsModel.OrdersList;
import com.example.alfasdk.R;
import com.example.alfasdk.Util.DividerItemDecoration;
import com.example.alfasdk.Util.HSnackBar;
import com.example.alfasdk.Util.HToast;
import com.example.alfasdk.Util.Util;

import java.util.ArrayList;
import java.util.List;


/**
 * Developed by Hasham.Tahir on 2/1/2016.
 */
public class OrderStatsFragment extends Fragment implements OrderStatsAdapter.OnListItemClickListener {

    private static int postionToRemove = -1;
//    @BindView(R.id.order_status_list)
    RecyclerView order_status_list;
//    @BindView(R.id.orderbtn)
    Button order;
//    @BindView(R.id.tradebtn)
    Button trade;
//    @BindView(R.id.textView8)
    TextView textViewError;
    OrderStatsResponse result;
    ArrayList<com.example.alfasdk.Models.OrderStatsModel.OrdersList> OrdersList, TradeList;
    OnOrderDeleteRequest deleteListener;
    int tabSelected = 1;
    private OrderStatsAdapter adapter;

    public OrderStatsFragment() {
        // Required empty public constructor
    }

    public static OrderStatsFragment newInstance() {
        return new OrderStatsFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onResume() {

        ActionBar toolbar = ((AppCompatActivity) getActivity()).getSupportActionBar();

        if (toolbar != null) {
            toolbar.setTitle("Order Status");
        }

        super.onResume();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_order_status, container, false);
//        ButterKnife.bind(this, view);
        order_status_list=view.findViewById(R.id.order_status_list);
        order=view.findViewById(R.id.orderbtn);
        trade=view.findViewById(R.id.tradebtn);
        textViewError=view.findViewById(R.id.textView8);
//        order=view.findViewdById(R.id.orderbtn);
//        trade=view.findViewdById(R.id.tradebtn);
//        textViewError=view.findViewdById(R.id.textView8);

        order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                orderPressed();
            }
        });

        trade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tradePressed();
            }
        });

        return view;
    }


//    @OnClick(R.id.orderbtn)
    public void orderPressed() {
        trade.setBackgroundResource(R.drawable.unselected);
        order.setBackgroundResource(R.drawable.selected);

        tabSelected = 1;
        setResult(result);
    }

//    @OnClick(R.id.tradebtn)
    public void tradePressed() {
        trade.setBackgroundResource(R.drawable.selected);
        order.setBackgroundResource(R.drawable.unselected);

        tabSelected = 2;
        setResult(result);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        order_status_list.setLayoutManager(new LinearLayoutManager(getActivity()));
        order_status_list.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));

        ((MyMainActivity) getActivity()).orderStatusRequest();

        textViewError.setText("No orders or trades available to display.");

    }

    public void setResult(OrderStatsResponse result) {

        this.result = result;

        if (result != null) {

            List<OrdersList> listMain = result.getResponse().getOrdersList();

            if (listMain != null && listMain.size() > 0) {


                OrdersList = new ArrayList<>();
                TradeList = new ArrayList<>();


                for (OrdersList obj : listMain) {

                    if (obj.getIdentifier().equals("TRD")) {
                        TradeList.add(obj);
                    } else if (obj.getIdentifier().equals("ORD")) {
                        OrdersList.add(obj);
                    }
                }
                textViewError.setVisibility(View.GONE);

                if (tabSelected == 1 && !(OrdersList.size() > 0)) {

                    textViewError.setVisibility(View.VISIBLE);
                    textViewError.setText("There is no outstanding Order.");

                } else if (tabSelected == 1 && !(OrdersList.size() > 0) && !(TradeList.size() > 0)) {

                    textViewError.setVisibility(View.VISIBLE);
                    textViewError.setText("No orders or trades available to display.");

                } else if (tabSelected == 2 && !(TradeList.size() > 0)) {

                    textViewError.setVisibility(View.VISIBLE);
                    textViewError.setText("There is no Trade");
                }


                if (tabSelected == 1) {
                    adapter = new OrderStatsAdapter(getActivity(), OrdersList, this);
                    order_status_list.setAdapter(adapter);

                } else {
                    adapter = new OrderStatsAdapter(getActivity(), TradeList, this);
                    order_status_list.setAdapter(adapter);

                }
            } else {
                textViewError.setVisibility(View.VISIBLE);
                textViewError.setText("No orders available to display.");
            }
        }

//        adapter.notifyDataSetChanged();
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnOrderDeleteRequest) {
            deleteListener = (OnOrderDeleteRequest) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnOrderDeleteRequest");
        }

    }

    @Override
    public void onAttach(Activity context) {
        super.onAttach(context);
        if (context instanceof OnOrderDeleteRequest) {
            deleteListener = (OnOrderDeleteRequest) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnOrderDeleteRequest");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        deleteListener = null;
    }

    @Override
    public void onListItemClick(View caller, final OrdersList mItem, final int position) {

        if (mItem.getIdentifier().equals("ORD")) {

            AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
            alert.setTitle("Cancel");
            alert.setMessage("Do you want to cancel order for " + mItem.getSymbol() + "?");
            alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();

                    proceedTradePopup(mItem);
                    postionToRemove = position;


                }
            });

            alert.show();

        }
    }

    private void proceedTradePopup(final OrdersList mItem) {

        View dialogView = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_input_pin, null);
        final EditText editText = (EditText) dialogView.findViewById(R.id.editText2);
        final CheckBox checkBox = (CheckBox) dialogView.findViewById(R.id.checkBox);

        if (MyMainActivity.preferences.getRememberPin()) {

            editText.setText(MyMainActivity.loginResponse.getResponse().getPinCode());
            checkBox.setChecked(true);
        }

        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setPositiveButton("PROCEED", new DialogInterface.OnClickListener()

                {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {


                        if (checkBox.isChecked()) {
                            MyMainActivity.preferences.setRememberPin(true);
                        } else {
                            MyMainActivity.preferences.setRememberPin(false);
                        }

                        if (editText.getText().toString().equals(MyMainActivity.loginResponse.getResponse().getPinCode())) {


                            if (deleteListener != null) {

                                deleteListener.onOrderDeleteRequest(mItem);

                            }

                        } else {
                            HToast.showMsg(getActivity(), "Invalid Pin Code.");
                        }

                        Util.hideKeyboard(getActivity());
                        dialog.dismiss();


                    }
                }

        );
        builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener()

                {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }

        );
        builder.setTitle(getString(R.string.app_name));
        builder.setMessage("Please provide your pin code.");
        builder.setView(dialogView);
        try {
            builder.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void removeItem() {
        if (postionToRemove > -1) {

            OrdersList.remove(postionToRemove);

            adapter = new OrderStatsAdapter(getActivity(), OrdersList, this);

            order_status_list.setAdapter(adapter);

            HSnackBar.showMsg(order_status_list, "Successfully removed.");

            ((MyMainActivity) getActivity()).orderStatusRequest();
        }
    }

    public interface OnOrderDeleteRequest {
        void onOrderDeleteRequest(OrdersList order);
    }
}
