package com.example.myapplication.Consumption.Adapter;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;


public class ConsumptionDataViewHolder extends RecyclerView.ViewHolder {

    TextView dayShow,consumptionShow,costShow;
    LinearLayout consumptionId;

    public ConsumptionDataViewHolder(View itemView) {

        super(itemView);

        dayShow=itemView.findViewById(R.id.dayShow);
        consumptionShow=itemView.findViewById(R.id.consumptionShow);
        costShow=itemView.findViewById(R.id.costShow);
        consumptionId=itemView.findViewById(R.id.consumptionId);

    }

}
