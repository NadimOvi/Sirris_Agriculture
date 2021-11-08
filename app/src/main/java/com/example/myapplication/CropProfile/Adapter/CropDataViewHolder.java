package com.example.myapplication.CropProfile.Adapter;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;

public class CropDataViewHolder extends RecyclerView.ViewHolder{
    TextView riceVariantName,riceMinWaterLevel,riceMaxWaterLevel,riceMinMoisture,riceMaxMoisture;
    Switch riceSwitchButton;
    LinearLayout cropsId;
    public CropDataViewHolder(@NonNull View itemView) {
        super(itemView);
        riceVariantName = itemView.findViewById(R.id.riceVariantName);
        riceMinWaterLevel = itemView.findViewById(R.id.riceMinWaterLevel);
        riceMaxWaterLevel = itemView.findViewById(R.id.riceMaxWaterLevel);
        riceMinMoisture = itemView.findViewById(R.id.riceMinMoisture);
        riceMaxMoisture = itemView.findViewById(R.id.riceMaxMoisture);
        riceSwitchButton = itemView.findViewById(R.id.riceSwitchButton);
        cropsId = itemView.findViewById(R.id.cropsId);

    }
}
