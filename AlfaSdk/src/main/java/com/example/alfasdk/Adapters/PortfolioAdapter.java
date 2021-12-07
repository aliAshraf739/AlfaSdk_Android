package com.example.alfasdk.Adapters;

import android.content.Context;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.alfasdk.Models.PortfolioModel.Portfolio;
import com.example.alfasdk.Models.PortfolioModel.PortfolioFooter;
import com.example.alfasdk.Models.PortfolioModel.PortfolioSymbol;
import com.example.alfasdk.R;

import java.util.List;


/**
 * Developed by Hasham.Tahir on 2/3/2016.
 */
public class PortfolioAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_FOOTER = 0;
    private static final int TYPE_NORMAL = 1;
    Context mContext;
    LayoutInflater inflater;
    private List<Portfolio> arrayList;
    private int m_flag = 1;
    private OnPortofolioClickListner portofolioClickListner;

    public PortfolioAdapter(Context context, List<Portfolio> arrayList, OnPortofolioClickListner portofolioClickListner) {
        mContext = context;
        this.arrayList = arrayList;
        inflater = LayoutInflater.from(mContext);
        this.portofolioClickListner = portofolioClickListner;

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;

        switch (viewType) {
            case TYPE_NORMAL: {
                View v = LayoutInflater.from(viewGroup.getContext())
                        .inflate(R.layout.portfolio_list_item, viewGroup, false);
                viewHolder = new ViewHolder(v) {
                };

            }
            break;
            case TYPE_FOOTER: {
                View v = LayoutInflater.from(viewGroup.getContext())
                        .inflate(R.layout.acc_list_item_footer, viewGroup, false);
                viewHolder = new FooterViewHolder(v);
            }
            break;
        }

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        AccountAdapter.ViewHolder holder;
        switch (getItemViewType(position)) {
            case TYPE_NORMAL: {
                if (position % 2 == 1) {
                    viewHolder.itemView.setBackgroundColor(ContextCompat.getColor(mContext, R.color.greyBar));
                } else {
                    viewHolder.itemView.setBackgroundColor(ContextCompat.getColor(mContext, R.color.white));
                }
                final PortfolioSymbol obj = (PortfolioSymbol) arrayList.get(position);
                final ViewHolder viewHolder1 = (ViewHolder) viewHolder;
//                viewHolder1.mItem = obj;
                viewHolder1.sym.setText(obj.getSymbol());
                viewHolder1.volume.setText(obj.getVolume());
                viewHolder1.profitloss.setText(obj.getCapGainLoss());
                viewHolder1.llItem.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        portofolioClickListner.onPortfolioClick(obj);
                    }
                });
            }
            break;
            case TYPE_FOOTER: {
                FooterViewHolder footerView = (FooterViewHolder) viewHolder;
                PortfolioFooter portfolioFooter = (PortfolioFooter) arrayList.get(arrayList.size() - 1);
                footerView.sym.setText("");
                footerView.qty.setText("Total Profit/loss");
                Log.d("totalprofitloss", portfolioFooter.getTotalProfitloss());
                footerView.totalProfitloss.setText(portfolioFooter.getTotalProfitloss());
            }
            break;
        }

    }

    public interface OnPortofolioClickListner {
        void onPortfolioClick(PortfolioSymbol mItem);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (position == arrayList.size() - 1) {

            return TYPE_FOOTER;


        } else {
            return TYPE_NORMAL;

        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
//        @BindView(R.id.portfolio_symbol)
        TextView sym;
//        @BindView(R.id.portfolio_volume)
        TextView volume;
//        @BindView(R.id.portfolio_profitloss)
        TextView profitloss;
//        @BindView(R.id.llItem)
        LinearLayout llItem;
//        View mView;
//        PortfolioSymbol mItem;

        public ViewHolder(View view) {
            super(view);
            sym=view.findViewById(R.id.portfolio_symbol);
            volume=view.findViewById(R.id.portfolio_volume);
            profitloss=view.findViewById(R.id.portfolio_profitloss);
            llItem=view.findViewById(R.id.llItem);
//            ButterKnife.bind(this, view);
        }
    }

    public class FooterViewHolder extends RecyclerView.ViewHolder {
//        @BindView(R.id.acc_sym)
        TextView sym;
//        @BindView(R.id.acc_qty)
        TextView qty;
//        @BindView(R.id.acc_ammount)
        TextView totalProfitloss;

        public FooterViewHolder(View view) {
            super(view);
            sym=view.findViewById(R.id.acc_sym);
            qty=view.findViewById(R.id.acc_qty);
            totalProfitloss=view.findViewById(R.id.acc_ammount);

//            ButterKnife.bind(this, view);
        }
    }
}
