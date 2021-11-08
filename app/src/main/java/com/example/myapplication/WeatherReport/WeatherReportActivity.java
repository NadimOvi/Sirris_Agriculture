package com.example.myapplication.WeatherReport;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.myapplication.Api;
import com.example.myapplication.Consumption.ConsumptionActivity;
import com.example.myapplication.DashboardActivity;
import com.example.myapplication.LoginActivity;
import com.example.myapplication.Payment.PaymentHistoryActivity;
import com.example.myapplication.R;
import com.example.myapplication.WeatherReport.Adapter.WeatherDataAdapter;
import com.example.myapplication.WeatherReport.Model.WeatherMainModel;
import com.example.myapplication.WeatherReport.Model.WeatherModel;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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

public class WeatherReportActivity extends AppCompatActivity {

    /*NavigationView nav;*/
    private ShapedNavigationView nav;
    ActionBarDrawerToggle toggle;
    DrawerLayout drawerLayout;
    ProgressDialog progressDialog;
    private TextView weatherProfileNameID,weatherFieldText,weatherBalanceText,weatherRateText,weatherFromDateTxt,weatherToDateTxt;

    private LinearLayout weatherFromDate,weatherToDate;
    private String userId,userPassword,full_name;
    SharedPreferences sp;
    SharedPreferences.Editor sped;

    Spinner spinner;
    String showFromDate,showToDate;
    private Integer custId;
    Integer total_fields;
    Double balance,water_rate;
    DatePickerDialog picker;

    private RecyclerView weatherRecyclerView;

    //set Spinner
    String URL="http://us.infrmtx.com/sirris/app/list_fields.php?cust_id=";
    ArrayList<String> FieldName;
    ArrayList<Integer> fieldArrayId;
    Integer field_Id;
    Integer fieldId;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_report);
        setTitle("");

        sp = getApplicationContext().getSharedPreferences("GTR", MODE_PRIVATE);
        sped = sp.edit();
        initializeViews();
    }

    private void initializeViews() {
        weatherProfileNameID = findViewById(R.id.weatherProfileNameID);
        weatherFieldText = findViewById(R.id.weatherFieldText);
        weatherBalanceText = findViewById(R.id.weatherBalanceText);
        weatherRateText = findViewById(R.id.weatherRateText);
        weatherFromDate = findViewById(R.id.weatherFromDate);
        weatherToDate = findViewById(R.id.weatherToDate);
        weatherFromDateTxt = findViewById(R.id.weatherFromDateTxt);
        weatherToDateTxt = findViewById(R.id.weatherToDateTxt);

        progressDialog = new ProgressDialog(WeatherReportActivity.this);
        progressDialog.setMessage("Please Wait...");
        progressDialog.setCancelable(false);

        ///Spinner
        spinner = findViewById(R.id.weatherSpinner);
        FieldName =new ArrayList<>();
        fieldArrayId=new ArrayList<>();

        Intent i=getIntent();
        userId=i.getStringExtra("user");
        userPassword=i.getStringExtra("password");
        custId=i.getIntExtra("custId",0);
        balance=i.getDoubleExtra("balance",0);
        total_fields=i.getIntExtra("total_fields",0);
        water_rate=i.getDoubleExtra("water_rate",0);
        full_name=i.getStringExtra("full_name");

        weatherRecyclerView=findViewById(R.id.weatherRecyclerView);
        weatherRecyclerView.setHasFixedSize(true);
        weatherRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        String FullName = full_name;
        String ToatalFields = String.valueOf(total_fields);
        String Balance = String.valueOf(balance);
        String Rate = String.valueOf(water_rate);

        weatherProfileNameID.setText(FullName);
        weatherFieldText.setText(ToatalFields);
        weatherBalanceText.setText(Balance);
        weatherRateText.setText(Rate);


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
        weatherFromDateTxt.setText(beforedate);

        weatherToDateTxt.setText(currentDateandTime);



        weatherFromDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fromDatePost();
            }
        });
        weatherToDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toDatePost();
            }
        });

        Toolbar toolbar=(Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /*nav=(NavigationView)findViewById(R.id.navmenu);*/
        nav=findViewById(R.id.navmenu);
        nav.getSettings().setShapeType(ShapedViewSettings.ROUNDED_RECT);

        drawerLayout=(DrawerLayout)findViewById(R.id.drawer);

        toggle=new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.open,R.string.close);

        drawerLayout.addDrawerListener(toggle);

        toggle.syncState();
        this.getSupportActionBar().setHomeButtonEnabled(true);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_menu_24);

        nav.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem)
            {
                switch (menuItem.getItemId())
                {
                    case R.id.menu_dashboard :
                        /*Toast.makeText(getApplicationContext(),"Dashboard Panel is Open",Toast.LENGTH_LONG).show();*/
                        drawerLayout.closeDrawer(GravityCompat.START);
                        startActivity(new Intent(WeatherReportActivity.this, DashboardActivity.class)
                                .putExtra("user", userId)
                                .putExtra("custId", custId)
                                .putExtra("total_fields", total_fields)
                                .putExtra("balance", balance)
                                .putExtra("full_name", full_name)
                                .putExtra("water_rate", water_rate));
                        break;

                    case R.id.menu_payment :
                       /* Toast.makeText(getApplicationContext(),"Payment Panel is Open",Toast.LENGTH_LONG).show();*/
                        drawerLayout.closeDrawer(GravityCompat.START);
                        startActivity(new Intent(WeatherReportActivity.this, PaymentHistoryActivity.class)
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
                        startActivity(new Intent(WeatherReportActivity.this, PaymentHistoryActivity.class)

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
                        startActivity(new Intent(WeatherReportActivity.this, WeatherReportActivity.class)
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
                        startActivity(new Intent(WeatherReportActivity.this, ConsumptionActivity.class)
                                .putExtra("user", userId)
                                .putExtra("custId", custId)
                                .putExtra("total_fields", total_fields)
                                .putExtra("balance", balance)
                                .putExtra("full_name", full_name)
                                .putExtra("water_rate", water_rate));
                        break;

                    case R.id.logOut :
                      /*  Toast.makeText(getApplicationContext(),"Log Out",Toast.LENGTH_LONG).show();*/
                        signout();
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                }

                return true;
            }
        });
        //set Spinner
        fieldList(URL+custId);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String name=   spinner.getItemAtPosition(spinner.getSelectedItemPosition()).toString();
                /*String id=   spinner.getItemAtPosition(spinner.getSelectedItemPosition()).toString();*/
                fieldId = fieldArrayId.get(i);
               /* Toast.makeText(getApplicationContext(),name,Toast.LENGTH_LONG).show();*/
                /*Toast.makeText(DashboardActivity.this, fieldId, Toast.LENGTH_SHORT).show();*/
                progressDialog.show();
                listShow();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // DO Nothing here
            }
        });

    }

    private void fromDatePost() {
        final Calendar cldr = Calendar.getInstance();
        int day = cldr.get(Calendar.DAY_OF_MONTH);
        int month = cldr.get(Calendar.MONTH);
        int year = cldr.get(Calendar.YEAR);
        // date picker dialog
        picker = new DatePickerDialog(WeatherReportActivity.this,
                android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        showFromDate= year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
                        weatherFromDateTxt.setText(showFromDate);
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
        picker = new DatePickerDialog(WeatherReportActivity.this,
                android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        showToDate= year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
                        weatherToDateTxt.setText(showToDate);
                        validationDate();
                    }
                }, year, month, day);
        picker.show();
    }

    private void validationDate(){
        if (showFromDate==null) {
            weatherFromDateTxt.setError("From Date is Empty");
            weatherFromDateTxt.requestFocus();
            return;
        } else if (showToDate==null) {
            weatherToDateTxt.setError("To date is Empty");
            weatherToDateTxt.requestFocus();
            return;


        }else{
            datePost();
        }
    }

    private void showIt(List<WeatherModel>  list1) {
        WeatherDataAdapter weatherDataAdapter = new WeatherDataAdapter(list1,getApplicationContext());
        weatherRecyclerView.setAdapter(weatherDataAdapter);
    }

    private void fieldList(String url) {
        RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest=new StringRequest(Request.Method.GET, url, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    JSONObject jsonObject=new JSONObject(response);
                    JSONArray jsonArray=jsonObject.getJSONArray("fields");
                    for(int i=0;i<jsonArray.length();i++){
                        JSONObject jsonObject1=jsonArray.getJSONObject(i);
                        String country=jsonObject1.getString("field_name");

                        field_Id = Integer.valueOf(jsonObject1.getString("field_id"));

                        FieldName.add(country);
                        fieldArrayId.add(field_Id);
                    }

                    spinner.setAdapter(new ArrayAdapter<String>(WeatherReportActivity.this, android.R.layout.simple_spinner_dropdown_item, FieldName));
                }catch (JSONException e){e.printStackTrace();}
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        int socketTimeout = 30000;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(policy);
        requestQueue.add(stringRequest);
    }


    private void listShow() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://us.infrmtx.com/sirris/app/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();


        Api api = retrofit.create(Api.class);
        Call<WeatherMainModel> call= api.getShowWeatherReport(fieldId);
        call.enqueue(new Callback<WeatherMainModel>() {
            @Override
            public void onResponse(Call<WeatherMainModel> call, Response<WeatherMainModel> response) {
                List<WeatherModel> list1 =response.body().getFields();
                if (list1!=null){
                    for (WeatherModel weatherModel :list1){
                        progressDialog.dismiss();
                        showIt(response.body().getFields());
                    }
                }else{
                      Toast.makeText(WeatherReportActivity.this, "No data is Found", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<WeatherMainModel> call, Throwable t) {
                Toast.makeText(WeatherReportActivity.this, "No data is Found", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });
    }
    private void signout() {
        sped.putString("user", "");
        sped.putString("password", "");
        sped.putString("Remember", "");
        sped.commit();

      /*  Toast.makeText(WeatherReportActivity.this,"Logout Successful", Toast.LENGTH_LONG).show();*/
        startActivity(new Intent(WeatherReportActivity.this, LoginActivity.class));
        finish();
    }
    private void datePost() {
        progressDialog.show();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://us.infrmtx.com/sirris/app/")
                .addConverterFactory(GsonConverterFactory.create())

                .build();

        Api api = retrofit.create(Api.class);
        Call<WeatherMainModel> call= api.getShowWeatherReportDate(fieldId,showFromDate,showToDate);
        call.enqueue(new Callback<WeatherMainModel>() {
            @Override
            public void onResponse(Call<WeatherMainModel> call, Response<WeatherMainModel> response) {

                List<WeatherModel> list1 =response.body().getFields();
                for (WeatherModel weatherModel :list1){
                    progressDialog.dismiss();
                    showIt(response.body().getFields());

                }
            }

            @Override
            public void onFailure(Call<WeatherMainModel> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(WeatherReportActivity.this, "No data is Found", Toast.LENGTH_SHORT).show();
            }
        });
    }
}