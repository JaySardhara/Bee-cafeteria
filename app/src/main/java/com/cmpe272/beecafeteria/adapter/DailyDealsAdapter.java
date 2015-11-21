package com.cmpe272.beecafeteria.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cmpe272.beecafeteria.R;
import com.cmpe272.beecafeteria.modelResponse.DailyDeals;

import java.util.Collections;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Rushil on 11/20/2015.
 */
public class DailyDealsAdapter extends RecyclerView.Adapter<DailyDealsAdapter.MyViewHolder> {
    List<DailyDeals> data = Collections.emptyList();
    private LayoutInflater inflater;
    private Context context;

    public DailyDealsAdapter(Context context, List<DailyDeals> data) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.data = data;
    }

    public void delete(int position) {
        data.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.adapter_daily_deals, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        DailyDeals deals = data.get(position);
        holder.title.setText(deals.getDailyDealTitle());
        holder.descripion.setText(deals.getDailyDealDescription());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.title)
        TextView title;

        @Bind(R.id.description)
        TextView descripion;

        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
