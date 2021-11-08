package com.example.myapplication.Payment.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.myapplication.Payment.Model.Payment;
import com.example.myapplication.R;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class DataAdapter extends RecyclerView.Adapter<DataViewHolder> {
    private List<Payment> list;
    private Context context;

    public DataAdapter(List<Payment> list1, Context context) {
        this.list =list1;
        this.context= context;
    }

    @NonNull
    @Override
    public DataViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.paymenthistory_layout,viewGroup,false);
        return new DataViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DataViewHolder holder, int position) {
        final Payment payment = list.get(position);
        if (payment!=null){

            String inputDate = payment.getDate();
            String inputAmount = String.valueOf(payment.getAmount());

           /* holder.dateShow.setText(inputDate);*/
            holder.amountShow.setText(inputAmount);


            /*DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");*/
            DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            DateFormat outputFormat = new SimpleDateFormat("dd-MMM-yyyy");


            Date date = null;
            try {
                date = inputFormat.parse(inputDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            String outputDateStr = outputFormat.format(date);
            holder.dateShow.setText(outputDateStr);
            /*holder.dateShow.setText(inputDate);*/


            DateFormat outputTime = new SimpleDateFormat("HH:mm aaa");

            Date time = null;
            try {
                time = inputFormat.parse(inputDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            String outputTimeStr = outputTime.format(time);
            holder.dateTimeShow.setText(outputTimeStr);

            if (position %2==0){
                holder.paymentHistoryId.setBackgroundColor(Color.parseColor("#00EDFF"));
            }else{
                holder.paymentHistoryId.setBackgroundColor(Color.parseColor("#00FFEE"));
            }
        }else{

            String inputDate = payment.getDate();
            String inputAmount = String.valueOf(payment.getAmount());

            /* holder.dateShow.setText(inputDate);*/
            holder.amountShow.setText(inputAmount);


            /*DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");*/
            DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            DateFormat outputFormat = new SimpleDateFormat("dd-MMM-yyyy");


            Date date = null;
            try {
                date = inputFormat.parse(inputDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            String outputDateStr = outputFormat.format(date);
            holder.dateShow.setText(outputDateStr);
            /*holder.dateShow.setText(inputDate);*/


            DateFormat outputTime = new SimpleDateFormat("HH:mm aaa");

            Date time = null;
            try {
                time = inputFormat.parse(inputDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            String outputTimeStr = outputTime.format(time);
            holder.dateTimeShow.setText(outputTimeStr);
            if (position %2==0){
                holder.paymentHistoryId.setBackgroundColor(Color.parseColor("#00FFEE"));

            }else{
                holder.paymentHistoryId.setBackgroundColor(Color.parseColor("#00EDFF"));
            }
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
