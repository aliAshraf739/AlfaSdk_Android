package com.example.alfasdk.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.alfasdk.Models.Event;
import com.example.alfasdk.R;
import com.hudomju.swipe.adapter.RecyclerViewAdapter;

import java.util.List;


/**
 * Developed by Hasham.Tahir on 1/29/2016.
 */
public class EventAdapter extends RecyclerView.Adapter<EventAdapter.EventViewHolder> {

    // Declare Variables
    Context mContext;
    private final List<Event> arraylist;

    public EventAdapter(Context context) {
        mContext = context;
        this.arraylist = Event.getAllEvents(context);
    }

    @NonNull
    @Override
    public EventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_events_list, parent, false);
        return new EventViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EventViewHolder holder, int position) {
        Event obj = arraylist.get(position);
        holder.dateTime.setText(obj.getDateTimeString());
        holder.body.setText(obj.getBody());
    }

    @Override
    public int getItemCount() {
        return arraylist.size();
    }

    public static class EventViewHolder extends RecyclerView.ViewHolder{
        TextView dateTime, body;
        public EventViewHolder(@NonNull View itemView) {
            super(itemView);
            dateTime=itemView.findViewById(R.id.text1);
            body=itemView.findViewById(R.id.text2);
        }
    }
}
