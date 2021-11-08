package com.example.myapplication.Payment.Adapter;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;


public class DataViewHolder extends RecyclerView.ViewHolder {

    TextView dateShow,amountShow,dateTimeShow;
    LinearLayout paymentHistoryId;

    public DataViewHolder(@NonNull View itemView) {
        super(itemView);
        dateShow=itemView.findViewById(R.id.dateShow);
        amountShow=itemView.findViewById(R.id.amountShow);
        dateTimeShow=itemView.findViewById(R.id.dateTimeShow);
        paymentHistoryId=itemView.findViewById(R.id.paymentHistoryId);
    }
}
