package com.example.alfasdk.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.example.alfasdk.Models.MarginModel.CustodyList;
import com.example.alfasdk.R;

import java.util.ArrayList;


/**
 * Developed by Hasham.Tahir on 7/18/2016.
 */
public class CustodyAdapter extends BaseAdapter {

    Context mContext;
    LayoutInflater inflater;
    private ArrayList<CustodyList> custodyList;

    public CustodyAdapter(Context context, ArrayList<CustodyList> custodyList) {
        mContext = context;

        this.custodyList = custodyList;

        inflater = LayoutInflater.from(mContext);

    }

    @Override
    public int getCount() {
        return custodyList.size();
    }

    @Override
    public CustodyList getItem(int position) {
        return custodyList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(final int position, View view, ViewGroup parent) {

        ViewHolder holder;
        if (view != null) {
            holder = (ViewHolder) view.getTag();
        } else {
            view = inflater.inflate(R.layout.symbols_list_item, parent, false);
            holder = new ViewHolder(view);
            view.setTag(holder);
        }

        if (position % 2 == 1) {
            view.setBackgroundColor(ContextCompat.getColor(mContext, R.color.greyBar));
        } else {
            view.setBackgroundColor(ContextCompat.getColor(mContext, R.color.white));
        }


        CustodyList obj = custodyList.get(position);

        holder.symbol_symbol.setText(obj.getSymbol());
        holder.symbol_market.setText(obj.getBalance());
        holder.symbol_exchange.setText(obj.getAmount());


        return view;
    }


    public class ViewHolder {

//        @BindView(R.id.symbol_symbol)
        TextView symbol_symbol;
//        @BindView(R.id.symbol_market)
        TextView symbol_market;
//        @BindView(R.id.symbol_exchange)
        TextView symbol_exchange;


        public ViewHolder(View view) {
//            ButterKnife.bind(this, view);
            symbol_symbol=view.findViewById(R.id.symbol_symbol);
            symbol_market=view.findViewById(R.id.symbol_market);
            symbol_exchange=view.findViewById(R.id.symbol_exchange);
        }

    }
}
