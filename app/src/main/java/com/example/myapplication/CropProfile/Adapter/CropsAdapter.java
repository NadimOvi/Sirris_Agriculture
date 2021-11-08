package com.example.myapplication.CropProfile.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Api;
import com.example.myapplication.CropProfile.RicesModel;
import com.example.myapplication.DashboardActivity;
import com.example.myapplication.Payment.PaymentHistoryActivity;
import com.example.myapplication.R;
import com.example.myapplication.WeatherReport.Adapter.WeatherDataViewHolder;
import com.example.myapplication.WeatherReport.Model.WeatherModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CropsAdapter extends RecyclerView.Adapter<CropDataViewHolder> {
    private List<RicesModel> list;
    private Context context;
    private Integer profile_Id;
    private Integer fieldId;
    private String userId;
    private Integer crop_id;
    private Integer custId;
    private Integer total_fields;
    private Double balance;
    private String full_name;
    private Double water_rate;
    private Integer changeCropId;

    public CropsAdapter(List<RicesModel> list, Context context, Integer profile_id, Integer fieldId,
                        String userId, Integer custId, Integer total_fields, Double balance, String full_name, Double water_rate) {
        this.list = list;
        this.context = context;
        this.profile_Id = profile_id;
        this.fieldId = fieldId;
        this.userId = userId;
        this.custId = custId;
        this.total_fields = total_fields;
        this.balance = balance;
        this.full_name = full_name;
        this.water_rate = water_rate;
    }

    @NonNull
    @Override
    public CropDataViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.cropdata_view_holder,parent,false);
        return new CropDataViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CropDataViewHolder holder, int position) {
        final RicesModel ricesModel = list.get(position);
        crop_id = ricesModel.getProfile_id();

        if (position %2==0){
            holder.cropsId.setBackgroundColor(Color.parseColor("#00FFEE"));
        }else{
            holder.cropsId.setBackgroundColor(Color.parseColor("#00EDFF"));
        }

        if (crop_id.equals(profile_Id)){
            holder.riceSwitchButton.setChecked(true);
            holder.riceSwitchButton.setEnabled(false);
            holder.riceVariantName.setText(ricesModel.getCrop_name());
            holder.riceMinWaterLevel.setText(String.valueOf(ricesModel.getMin_wlevel()));
            holder.riceMaxWaterLevel.setText(String.valueOf(ricesModel.getMax_wlevel()));
            holder.riceMinMoisture.setText(String.valueOf(ricesModel.getMin_moisture()));
            holder.riceMaxMoisture.setText(String.valueOf(ricesModel.getMax_moisture()));
        } else{
            holder.riceSwitchButton.setChecked(false);
            holder.riceVariantName.setText(ricesModel.getCrop_name());
            holder.riceMinWaterLevel.setText(String.valueOf(ricesModel.getMin_wlevel()));
            holder.riceMaxWaterLevel.setText(String.valueOf(ricesModel.getMax_wlevel()));
            holder.riceMinMoisture.setText(String.valueOf(ricesModel.getMin_moisture()));
            holder.riceMaxMoisture.setText(String.valueOf(ricesModel.getMax_moisture()));
        }
        holder.riceSwitchButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                final RicesModel ricesModels = list.get(position);
                changeCropId = ricesModels.getProfile_id();
                if (isChecked){
                    holder.riceSwitchButton.setChecked(true);
                    holder.riceSwitchButton.setEnabled(true);
                   /* Toast.makeText(context, changeCropId.toString(), Toast.LENGTH_SHORT).show();*/
                    changeCrop();
                }
            }
        });


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    private void changeCrop() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://us.infrmtx.com/sirris/app/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Api api = retrofit.create(Api.class);
        Call<String> call =api.setUpdateCrop(changeCropId,fieldId);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()){
                    Toast.makeText(context, "Crop Profile Changed Successfully", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(context.getApplicationContext(), DashboardActivity.class);
                    intent.putExtra("user", userId);
                    intent.putExtra("custId", custId);
                    intent.putExtra("total_fields", total_fields);
                    intent.putExtra("balance", balance);
                    intent.putExtra("full_name", full_name);
                    intent.putExtra("water_rate", water_rate);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }else{
                    Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(context, "No data Connected", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
