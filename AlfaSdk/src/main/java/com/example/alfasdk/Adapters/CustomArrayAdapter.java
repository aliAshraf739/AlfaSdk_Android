package com.example.alfasdk.Adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import com.example.alfasdk.Models.CityTownVillageModel.CityTownVillage;
import com.example.alfasdk.R;
import java.util.ArrayList;

public class CustomArrayAdapter extends ArrayAdapter<CityTownVillage> {
   
    private final Context mContext;
    private final ArrayList<CityTownVillage> mCityTownVillages;
    private final ArrayList<CityTownVillage> mCityTownVillagesAll;
    private final int mLayoutResourceId;

    public CustomArrayAdapter(Context context, int resource, ArrayList<CityTownVillage> CityTownVillages) {
        super(context, resource, CityTownVillages);
        this.mContext = context;
        this.mLayoutResourceId = resource;
        this.mCityTownVillages = new ArrayList<>(CityTownVillages);
        this.mCityTownVillagesAll = new ArrayList<>(CityTownVillages);
    }

    public int getCount() {
        return mCityTownVillages.size();
    }

    public CityTownVillage getItem(int position) {
        return mCityTownVillages.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        try {
            if (convertView == null) {
                LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
                convertView = inflater.inflate(mLayoutResourceId, parent, false);
            }
            CityTownVillage CityTownVillage = getItem(position);
            TextView name = (TextView) convertView.findViewById(R.id.autoText);
            name.setText(CityTownVillage.getCityName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return convertView;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            public String convertResultToString(Object resultValue) {
                return ((CityTownVillage) resultValue).getCityName();
            }

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults filterResults = new FilterResults();
                ArrayList<CityTownVillage> CityTownVillagesSuggestion = new ArrayList<>();
                if (constraint != null) {
                    for (CityTownVillage CityTownVillage : mCityTownVillagesAll) {
                        if (CityTownVillage.getCityName().toLowerCase().startsWith(constraint.toString().toLowerCase())) {
                            CityTownVillagesSuggestion.add(CityTownVillage);
                        }
                    }
                    filterResults.values = CityTownVillagesSuggestion;
                    filterResults.count = CityTownVillagesSuggestion.size();
                }
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                mCityTownVillages.clear();
                if (results != null && results.count > 0) {
                    // avoids unchecked cast warning when using mCityTownVillages.addAll((ArrayList<CityTownVillage>) results.values);
                    for (Object object : (ArrayList<?>) results.values) {
                        if (object instanceof CityTownVillage) {
                            mCityTownVillages.add((CityTownVillage) object);
                        }
                    }
                    notifyDataSetChanged();
                } else if (constraint == null) {
                    // no filter, add entire original ArrayList back in
                    mCityTownVillages.addAll(mCityTownVillagesAll);
                    notifyDataSetInvalidated();
                }
            }
        };
    }
}
