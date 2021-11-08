package com.example.myapplication.WeatherReport.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.myapplication.R;
import com.example.myapplication.WeatherReport.Model.WeatherModel;

import java.util.List;

public class WeatherDataAdapter extends RecyclerView.Adapter<WeatherDataViewHolder> {
    private List<WeatherModel> list;
    private Context context;

    public WeatherDataAdapter(List<WeatherModel> list1, Context context) {
        this.list =list1;
        this.context= context;
    }

    @NonNull
    @Override
    public WeatherDataViewHolder  onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.weatherreportholder_layout,viewGroup,false);
        return new WeatherDataViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WeatherDataViewHolder holder, int position) {
        final WeatherModel weatherModel = list.get(position);
        if (weatherModel!=null){

            String day = String.valueOf(weatherModel.getDay());
            String temp = String.valueOf(weatherModel.getTem());
            String humidity = String.valueOf(weatherModel.getHum());
            String moisture = String.valueOf(weatherModel.getMois());
            String water_level = String.valueOf(weatherModel.getWlvl());
            String waterLitre = String.valueOf(weatherModel.getWtlr());
            String waterFlow = String.valueOf(weatherModel.getWflow());
            String soilTemp = String.valueOf(weatherModel.getStemp());

            /* holder.dateShow.setText(inputDate);*/
            holder.dayShow.setText(day);
            holder.tempShow.setText(temp);
            holder.humidityShow.setText(humidity);
            holder.moistureShow.setText(moisture);
            holder.waterLevelShow.setText(water_level);
            holder.waterLitreShow.setText(waterLitre);
            holder.waterFlow.setText(waterFlow);
            holder.stemp.setText(soilTemp);

            if (position %2==0){
                holder.weatherId.setBackgroundColor(Color.parseColor("#00FFEE"));

            }else{
                holder.weatherId.setBackgroundColor(Color.parseColor("#00EDFF"));
            }

        }else{
            holder.dayShow.setText("");
            holder.tempShow.setText("");
            holder.humidityShow.setText("");
            holder.moistureShow.setText("");
            holder.waterLevelShow.setText("");
            holder.waterLitreShow.setText("");
            holder.waterFlow.setText("");
            holder.stemp.setText("");
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}