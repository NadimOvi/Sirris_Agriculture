package com.example.myapplication.WeatherReport.Adapter;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;

public class WeatherDataViewHolder extends RecyclerView.ViewHolder {

    TextView dayShow,tempShow,humidityShow,moistureShow,waterFlow,waterLitreShow,waterLevelShow,stemp;
    LinearLayout weatherId;

    public WeatherDataViewHolder(@NonNull View itemView) {
        super(itemView);

        dayShow=itemView.findViewById(R.id.dayShow);
        tempShow=itemView.findViewById(R.id.tempShow);
        humidityShow=itemView.findViewById(R.id.humidityShow);
        moistureShow=itemView.findViewById(R.id.moistureShow);
        waterFlow=itemView.findViewById(R.id.waterFlow);
        waterLitreShow=itemView.findViewById(R.id.waterLitreShow);
        waterLevelShow=itemView.findViewById(R.id.waterLevelShow);
        stemp=itemView.findViewById(R.id.stemp);
        weatherId=itemView.findViewById(R.id.weatherId);


    }
}
