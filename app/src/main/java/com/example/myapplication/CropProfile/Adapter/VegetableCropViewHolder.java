package com.example.myapplication.CropProfile.Adapter;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;

public class VegetableCropViewHolder extends RecyclerView.ViewHolder {
    TextView vegetableVariantName,vegetableMinWaterLevel,vegetableMaxWaterLevel,vegetableMinMoisture,vegetableMaxMoisture;
    Switch vegetableSwitchButton;
    LinearLayout vegetablesId;
    public VegetableCropViewHolder(@NonNull View itemView) {
        super(itemView);
        vegetableVariantName = itemView.findViewById(R.id.vegetableVariantName);
        vegetableMinWaterLevel = itemView.findViewById(R.id.vegetableMinWaterLevel);
        vegetableMaxWaterLevel = itemView.findViewById(R.id.vegetableMaxWaterLevel);
        vegetableMinMoisture = itemView.findViewById(R.id.vegetableMinMoisture);
        vegetableMaxMoisture = itemView.findViewById(R.id.vegetableMaxMoisture);
        vegetableSwitchButton = itemView.findViewById(R.id.vegetableSwitchButton);
        vegetablesId = itemView.findViewById(R.id.vegetablesId);
    }
}
