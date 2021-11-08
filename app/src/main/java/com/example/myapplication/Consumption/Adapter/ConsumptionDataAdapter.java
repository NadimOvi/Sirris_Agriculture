package com.example.myapplication.Consumption.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.myapplication.Consumption.Model.ConsumptionModel;
import com.example.myapplication.R;

import java.util.List;

public class ConsumptionDataAdapter extends RecyclerView.Adapter<ConsumptionDataViewHolder> {
    private List<ConsumptionModel> list;
    private Context context;

    public ConsumptionDataAdapter(List<ConsumptionModel> list1, Context context) {
        this.list = list1;
        this.context = context;
    }

    @NonNull
    @Override
    public ConsumptionDataViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.consumption_holder_layout, viewGroup, false);
        return new ConsumptionDataViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ConsumptionDataViewHolder holder, int position) {
        final ConsumptionModel consumptionModel = list.get(position);
        if (consumptionModel != null) {

            String date = consumptionModel.getDay();
            String field = String.valueOf(consumptionModel.getConsumption());
            String cost = String.valueOf(consumptionModel.getCost());

            holder.dayShow.setText(date);
            holder.consumptionShow.setText(field);
            holder.costShow.setText(cost);
            if (position %2==0){
                holder.consumptionId.setBackgroundColor(Color.parseColor("#00FFEE"));
            }else{
                holder.consumptionId.setBackgroundColor(Color.parseColor("#00EDFF"));
            }
        }else{
            holder.dayShow.setText("");
            holder.consumptionShow.setText("");
            holder.costShow.setText("");
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

}