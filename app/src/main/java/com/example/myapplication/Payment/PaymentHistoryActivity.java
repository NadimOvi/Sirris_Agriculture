package com.example.myapplication.Payment;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Api;
import com.example.myapplication.Consumption.ConsumptionActivity;
import com.example.myapplication.DashboardActivity;
import com.example.myapplication.LoginActivity;
import com.example.myapplication.MainActivity;
import com.example.myapplication.Payment.Adapter.DataAdapter;
import com.example.myapplication.Payment.Model.Payment;
import com.example.myapplication.Payment.Model.PaymentMainModel;
import com.example.myapplication.R;
import com.example.myapplication.WeatherReport.WeatherReportActivity;
import com.google.android.material.navigation.NavigationView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import softpro.naseemali.ShapedNavigationView;
import softpro.naseemali.ShapedViewSettings;

public class PaymentHistoryActivity extends AppCompatActivity {

/*    NavigationView nav;*/
    private ShapedNavigationView nav;
    ActionBarDrawerToggle toggle;
    DrawerLayout drawerLayout;
    SharedPreferences sp;
    SharedPreferences.Editor sped;

    private TextView profileNameID,fieldText,balanceText,rateText,fromDateTxt,toDateTxt;

    LinearLayout fromDate,toDate;
    private String userId,userPassword,full_name;
    String showFromDate,showToDate;
    private Integer custId;
    Integer total_fields;
    Double balance,water_rate;
    DatePickerDialog picker;
    ProgressDialog progressDialog;

    private RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("");
        setContentView(R.layout.activity_payment_history);
        sp = getApplicationContext().getSharedPreferences("GTR", MODE_PRIVATE);
        sped = sp.edit();
        initializeViews();
        postMethodCall();
    }
    private void initializeViews() {

        profileNameID = findViewById(R.id.profileNameID);
        fieldText = findViewById(R.id.fieldText);
        balanceText = findViewById(R.id.balanceText);
        rateText = findViewById(R.id.rateText);
        fromDate = findViewById(R.id.fromDate);
        toDate = findViewById(R.id.toDate);
        fromDateTxt = findViewById(R.id.fromDateTxt);
        toDateTxt = findViewById(R.id.toDateTxt);

        Intent i=getIntent();
        userId=i.getStringExtra("user");
        userPassword=i.getStringExtra("password");
        custId=i.getIntExtra("custId",0);
        balance=i.getDoubleExtra("balance",0);
        total_fields=i.getIntExtra("total_fields",0);
        water_rate=i.getDoubleExtra("water_rate",0);
        full_name=i.getStringExtra("full_name");

        recyclerView=findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        String FullName = full_name;
        String ToatalFields = String.valueOf(total_fields);
        String Balance = String.valueOf(balance);
        String Rate = String.valueOf(water_rate);

        progressDialog = new ProgressDialog(PaymentHistoryActivity.this);
        progressDialog.setMessage("Please Wait...");
        progressDialog.setCancelable(false);
        profileNameID.setText(FullName);
        fieldText.setText(ToatalFields);
        balanceText.setText(Balance);
        rateText.setText(Rate);


        SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy");
        String currentDateandTime = sdf.format(new Date());

        Date cdate= null;
        try {
            cdate = sdf.parse(currentDateandTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar now2= Calendar.getInstance();
        now2.add(Calendar.DATE, -7);
        String beforedate=now2.get(Calendar.DATE)+"/"+(now2.get(Calendar.MONTH) + 1)+"/"+now2.get(Calendar.YEAR);
        Date BeforeDate1= null;
        try {
            BeforeDate1 = sdf.parse(beforedate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        String last = String.valueOf(cdate.compareTo(BeforeDate1));
        /*  Toast.makeText(this, currentDateandTime, Toast.LENGTH_SHORT).show();*/
        fromDateTxt.setText(beforedate);

        toDateTxt.setText(currentDateandTime);





        fromDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fromDatePost();
            }
        });
        toDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toDatePost();
            }
        });

        Toolbar toolbar=(Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /*nav=(NavigationView)findViewById(R.id.navmenu);*/
        nav= findViewById(R.id.navmenu);
        nav.getSettings().setShapeType(ShapedViewSettings.ROUNDED_RECT);
        drawerLayout=(DrawerLayout)findViewById(R.id.drawer);

        toggle=new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.open,R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        nav.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem)
            {
                switch (menuItem.getItemId())
                {
                    case R.id.menu_dashboard :
                       /* Toast.makeText(getApplicationContext(),"Dashboard Panel is Open",Toast.LENGTH_LONG).show();*/
                        drawerLayout.closeDrawer(GravityCompat.START);
                        startActivity(new Intent(PaymentHistoryActivity.this, DashboardActivity.class)
                                .putExtra("user", userId)
                                .putExtra("custId", custId)
                                .putExtra("total_fields", total_fields)
                                .putExtra("balance", balance)
                                .putExtra("full_name", full_name)
                                .putExtra("water_rate", water_rate));
                        break;

                    case R.id.menu_payment :
                        /*Toast.makeText(getApplicationContext(),"Payment Panel is Open",Toast.LENGTH_LONG).show();*/
                        drawerLayout.closeDrawer(GravityCompat.START);
                        startActivity(new Intent(PaymentHistoryActivity.this, MainActivity.class)
                                .putExtra("user", userId)
                                .putExtra("custId", custId)
                                .putExtra("total_fields", total_fields)
                                .putExtra("balance", balance)
                                .putExtra("full_name", full_name)
                                .putExtra("water_rate", water_rate));
                        break;

                    case R.id.menu_history :
                        /*Toast.makeText(getApplicationContext(),"Payment History Panel is Open",Toast.LENGTH_LONG).show();*/
                        drawerLayout.closeDrawer(GravityCompat.START);
                        startActivity(new Intent(PaymentHistoryActivity.this, PaymentHistoryActivity.class)
                                .putExtra("user", userId)
                                .putExtra("custId", custId)
                                .putExtra("total_fields", total_fields)
                                .putExtra("balance", balance)
                                .putExtra("full_name", full_name)
                                .putExtra("water_rate", water_rate));
                        break;

                    case R.id.report_menu_spinner :
                        /*Toast.makeText(getApplicationContext(),"Reports Panel is Open",Toast.LENGTH_LONG).show();*/
                        drawerLayout.closeDrawer(GravityCompat.START);
                        startActivity(new Intent(PaymentHistoryActivity.this, WeatherReportActivity.class)
                                .putExtra("user", userId)
                                .putExtra("custId", custId)
                                .putExtra("total_fields", total_fields)
                                .putExtra("balance", balance)
                                .putExtra("full_name", full_name)
                                .putExtra("water_rate", water_rate));
                        break;

                    case R.id.report_menu_consumption :
                       /* Toast.makeText(getApplicationContext(),"Consumption Panel is Open",Toast.LENGTH_LONG).show();*/
                        drawerLayout.closeDrawer(GravityCompat.START);
                        startActivity(new Intent(PaymentHistoryActivity.this, ConsumptionActivity.class)
                                .putExtra("user", userId)
                                .putExtra("custId", custId)
                                .putExtra("total_fields", total_fields)
                                .putExtra("balance", balance)
                                .putExtra("full_name", full_name)
                                .putExtra("water_rate", water_rate));
                        break;

                    case R.id.logOut :
                       /* Toast.makeText(getApplicationContext(),"Log Out",Toast.LENGTH_LONG).show();*/
                        signout();
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                }

                return true;
            }
        });
    }

    private void fromDatePost() {
        final Calendar cldr = Calendar.getInstance();
        int day = cldr.get(Calendar.DAY_OF_MONTH);
        int month = cldr.get(Calendar.MONTH);
        int year = cldr.get(Calendar.YEAR);
        // date picker dialog
        picker = new DatePickerDialog(PaymentHistoryActivity.this,
                android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        showFromDate= year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
                        fromDateTxt.setText(showFromDate);
                    }
                }, year, month, day);
        picker.show();
    }

    private void toDatePost() {
        final Calendar cldr = Calendar.getInstance();
        int day = cldr.get(Calendar.DAY_OF_MONTH);
        int month = cldr.get(Calendar.MONTH);
        int year = cldr.get(Calendar.YEAR);
        // date picker dialog
        picker = new DatePickerDialog(PaymentHistoryActivity.this,
                android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        showToDate= year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
                        toDateTxt.setText(showToDate);
                        validationDate();
                    }
                }, year, month, day);
        picker.show();
    }
    private void validationDate(){
        if (showFromDate==null) {
            fromDateTxt.setError("From Date is Empty");
            fromDateTxt.requestFocus();
            return;
        } else if (showToDate==null) {
            toDateTxt.setError("To date is Empty");
            toDateTxt.requestFocus();
            return;


        }else{
            progressDialog.show();
            datePost();
        }
    }

    private void datePost() {
        progressDialog.dismiss();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://us.infrmtx.com/sirris/app/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Api api = retrofit.create(Api.class);
        Call<PaymentMainModel> call= api.getShowPaymentDateHistory(custId,showFromDate,showToDate);
        call.enqueue(new Callback<PaymentMainModel>() {
            @Override
            public void onResponse(Call<PaymentMainModel> call, Response<PaymentMainModel> response) {

                List<Payment> list1 =response.body().getPayments();
                for (Payment payment :list1){
                    /*   PaymentMainModel payments = response.body();*/
                    showIt(response.body().getPayments());
                    /*Toast.makeText(PaymentHistoryActivity.this, "Success", Toast.LENGTH_SHORT).show();*/
                }
            }

            @Override
            public void onFailure(Call<PaymentMainModel> call, Throwable t) {
              /*  Toast.makeText(PaymentHistoryActivity.this, "Failed", Toast.LENGTH_SHORT).show();*/
            }
        });
    }


    private void postMethodCall() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://us.infrmtx.com/sirris/app/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Api api = retrofit.create(Api.class);
        Call<PaymentMainModel> call= api.getShowPaymentHistory(custId);
        call.enqueue(new Callback<PaymentMainModel>() {

            @Override
            public void onResponse(Call<PaymentMainModel> call, Response<PaymentMainModel> response) {

                Payment pay = new Payment();
                Integer payments = pay.getAmount();
                List<Payment> list1 =response.body().getPayments();

                for (Payment payment :list1){
                   if(payments==null){
                       showIt(response.body().getPayments());
                    }else {
                       showIt(response.body().getPayments());
                   }
                }
            }

            @Override
            public void onFailure(Call<PaymentMainModel> call, Throwable t) {
               /* Toast.makeText(PaymentHistoryActivity.this, "False", Toast.LENGTH_SHORT).show();*/
            }
        });
    }

    private void showIt(List<Payment>  list1) {


        DataAdapter dataAdapter = new DataAdapter(list1,getApplicationContext());
        recyclerView.setAdapter(dataAdapter);
    }
    private void signout() {
        sped.putString("user", "");
        sped.putString("password", "");
        sped.putString("Remember", "");
        sped.commit();

        Toast.makeText(PaymentHistoryActivity.this,"Logout Successful", Toast.LENGTH_LONG).show();
        startActivity(new Intent(PaymentHistoryActivity.this, LoginActivity.class));
        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(PaymentHistoryActivity.this, DashboardActivity.class)
                .putExtra("user", userId)
                .putExtra("custId", custId)
                .putExtra("total_fields", total_fields)
                .putExtra("balance", balance)
                .putExtra("full_name", full_name)
                .putExtra("water_rate", water_rate));

    }
}