package com.example.alfasdk.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.alfasdk.Models.PortfolioModel.Portfolio;
import com.example.alfasdk.Models.PortfolioModel.PortfolioSymbol;
import com.example.alfasdk.R;

import java.util.ArrayList;


public class PLSummaryAdapter extends RecyclerView.Adapter<PLSummaryAdapter.ViewHolder> {

    private final ArrayList<Portfolio> arraylist;
    private Context context;

    public PLSummaryAdapter(Context context, ArrayList<Portfolio> items) {
        arraylist = items;
        this.context = context;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.summary_items, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        final PortfolioSymbol obj = (PortfolioSymbol) arraylist.get(position);

        final ViewHolder viewHolder1 = (ViewHolder) holder;
        viewHolder1.companyName.setText(obj.getSymbol());
        viewHolder1.profitLoss.setText(obj.getCapGainLoss());
    }

    @Override
    public int getItemCount() {
        return arraylist.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

//        @BindView(R.id.profit_lossText)
        TextView profitLoss;
//        @BindView(R.id.company_nameText)
        TextView companyName;
//        @BindView(R.id.volume)
        TextView volume;
//        @BindView(R.id.avg_text)
        TextView avg;
//        @BindView(R.id.total_cost_text)
        TextView totalCost;
//        @BindView(R.id.mkt_value)
        TextView mktValue;
//        @BindView(R.id.payout_value)
        TextView payoutValue;
//        @BindView(R.id.ann_dateValue)
        TextView annDate;

        public ViewHolder(View itemView) {
            super(itemView);

            profitLoss = itemView.findViewById(R.id.profit_lossText);
            companyName = itemView.findViewById(R.id.company_nameText);
        }
    }
}