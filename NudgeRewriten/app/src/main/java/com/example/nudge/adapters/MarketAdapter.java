package com.example.nudge.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.nudge.R;
import com.example.nudge.models.MarketModel;

import java.util.List;

public class MarketAdapter extends RecyclerView.Adapter<MarketAdapter.myViewHolder> {

    List<MarketModel> markets;
    Context context;
    static int currentPos= -1;

    public MarketAdapter(List<MarketModel> markets, Context context) {
        this.markets = markets;
        this.context = context;
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater mInflater = LayoutInflater.from(context);
        View v = mInflater.inflate(R.layout.cropdetail, viewGroup, false);
        return new myViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MarketAdapter.myViewHolder myViewHolder, final int i) {

        myViewHolder.country_title.setText((i+1) + ". "+markets.get(i).getName());
        myViewHolder.price_id.setText("Selling Price: Rs. "+markets.get(i).getSellingPrice());
        myViewHolder.profit_id.setText("Profit: "+markets.get(i).getProfitPercent()+"% ");
        myViewHolder.info_id.setText(markets.get(i).getInfo());

        if(currentPos==i) {
            myViewHolder.market_child_view.setVisibility(View.VISIBLE);
        } else {
            myViewHolder.market_child_view.setVisibility(View.GONE);
        }

        myViewHolder.expand_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(currentPos == i) currentPos = -1;
                else
                    currentPos = i;
                notifyDataSetChanged();
            }
        });

        myViewHolder.marketCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(currentPos == i) currentPos = -1;
                else
                    currentPos = i;
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return markets.size();
    }

    public class myViewHolder extends RecyclerView.ViewHolder {

        TextView country_title,price_id,profit_id,expand_btn,info_id;
        ConstraintLayout market_child_view;
        CardView marketCard;

        public myViewHolder(@NonNull View itemView) {

            super(itemView);
            country_title = itemView.findViewById(R.id.country_title);
            price_id = itemView.findViewById(R.id.price_id);
            profit_id = itemView.findViewById(R.id.profit_id);
            expand_btn = itemView.findViewById(R.id.expand_btn);
            market_child_view = itemView.findViewById(R.id.market_child_view);
            info_id  = itemView.findViewById(R.id.info_id);
            marketCard = itemView.findViewById(R.id.market_card);
        }
    }
}
