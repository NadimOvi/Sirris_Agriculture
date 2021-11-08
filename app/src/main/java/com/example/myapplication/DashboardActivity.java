package com.example.myapplication;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.myapplication.Consumption.ConsumptionActivity;
import com.example.myapplication.CropProfile.CropProfileActivity;
import com.example.myapplication.DashboardSpinnerModel.MainObjectField;
import com.example.myapplication.DashboardSpinnerModel.Object;
import com.example.myapplication.Payment.PaymentHistoryActivity;
import com.example.myapplication.WeatherReport.WeatherReportActivity;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import pl.pawelkleczkowski.customgauge.CustomGauge;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import softpro.naseemali.ShapedNavigationView;
import softpro.naseemali.ShapedViewSettings;


public class DashboardActivity extends AppCompatActivity {

    Handler handler = new Handler();
    Runnable runnable;

   /* NavigationView nav;*/
    private ShapedNavigationView nav;

    ActionBarDrawerToggle toggle;
    DrawerLayout drawerLayout;
    Spinner spinner;
    ArrayAdapter<String> adapter;
    ArrayList<String> listItems;
    int Id;
    String temp;

    private String userId,userPassword,full_name;
    private Integer custId;
            Integer total_fields;

            private Double balance, water_rate;
    private double water_buttonSrate;

    SharedPreferences sp;
    SharedPreferences.Editor sped;
    TextView tempShow,humidityShow,moisureShow,flowShow,consumptionShow,waterLevelShow,profileNameID,
            totalField,totalBalance,soilLevelShow,rateText,cropName,phosphorusTxt,phLevelShow,n2LevelShow,potassiumShow;
    Switch switchButton;

    RelativeLayout cropProfileButton;

    private ProgressDialog progressDialog;
    //set Spinner
    String URL="http://us.infrmtx.com/sirris/app/list_fields.php?cust_id=";
    ArrayList<String> FieldName;
    ArrayList<Integer> fieldArrayId;
    Integer field_Id;
    Integer fieldId;
    Integer profile_Id;
    Integer valveStatus;
    int i;
    /*String cust_id= String.valueOf(1);*/

    private CustomGauge tempGauge, humidityGauge, flowGauge, waterLevelGauge, soilTempGauge, moistureGauge, consumptionGauge,
            phosphorusGauge,phLevelGauge,n2LevelGauge,potassiumGauge;

    LinearLayout cropProfileGauge;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.android_dashboard_new);
        setTitle("");

        Intent intentBackgroundService = new Intent(this, FirebasePushNotificationClass.class);
        startService(intentBackgroundService);

        initializeViews();

        progressDialog = new ProgressDialog(DashboardActivity.this);
        progressDialog.setMessage("Please Wait...");
        progressDialog.setCancelable(false);
        sp = getApplicationContext().getSharedPreferences("GTR", MODE_PRIVATE);
        sped = sp.edit();

        String FullName = full_name;
        String ToatalFields = String.valueOf(total_fields);
        String Balance = String.valueOf(balance);
        String rate = String.valueOf(water_rate);

        profileNameID.setText(FullName);
        totalField.setText(ToatalFields);
        totalBalance.setText(Balance);
        rateText.setText(rate+" /li");

        this.handler = new Handler();



    }

    private final Runnable m_Runnable = new Runnable()
    {
        public void run()

        {

            listShow();
           /* Toast.makeText(DashboardActivity.this,temp,Toast.LENGTH_SHORT).show();*/
            DashboardActivity.this.handler.postDelayed(m_Runnable,20000);
        }

    };


    private void initializeViews() {

        Intent i=getIntent();
        userId=i.getStringExtra("user");
        userPassword=i.getStringExtra("password");
        custId=i.getIntExtra("custId",0);
        balance=i.getDoubleExtra("balance",0);
        total_fields=i.getIntExtra("total_fields",0);
        water_rate=i.getDoubleExtra("water_rate",0);
        full_name=i.getStringExtra("full_name");

        rateText = findViewById(R.id.rateText);
        cropName = findViewById(R.id.cropName);
        cropProfileButton = findViewById(R.id.cropProfileButton);

        tempShow = findViewById(R.id.tempShow);
        humidityShow = findViewById(R.id.humidityShow);
        moisureShow = findViewById(R.id.moisureShow);
        flowShow = findViewById(R.id.flowShow);

        switchButton = findViewById(R.id.switchButton);
        switchButton.setChecked(true);
        /*switchButton.setEnabled(false);*/

        spinner = findViewById(R.id.spinner);
        consumptionShow = findViewById(R.id.consumptionShow);
        waterLevelShow = findViewById(R.id.waterLevelShow);

        profileNameID = findViewById(R.id.profileNameID);
        totalField = findViewById(R.id.totalField);
        totalBalance = findViewById(R.id.totalBalance);
        soilLevelShow = findViewById(R.id.soilLevelShow);
        cropProfileGauge = findViewById(R.id.cropProfileGauge);
        phosphorusTxt = findViewById(R.id.phosphorusTxt);
        phLevelShow = findViewById(R.id.phLevelShow);
        n2LevelShow = findViewById(R.id.n2LevelShow);
        potassiumShow = findViewById(R.id.potassiumShow);


        ///Gauge Meter
        tempGauge = findViewById(R.id.tempGauge);
        humidityGauge = findViewById(R.id.humidityGauge);
        flowGauge = findViewById(R.id.flowGauge);
        waterLevelGauge = findViewById(R.id.waterLevelGauge);
        soilTempGauge = findViewById(R.id.soilTempGauge);
        moistureGauge = findViewById(R.id.moistureGauge);
        consumptionGauge = findViewById(R.id.consumptionGauge);
        phosphorusGauge = findViewById(R.id.phosphorusGauge);
        phLevelGauge = findViewById(R.id.phLevelGauge);
        n2LevelGauge = findViewById(R.id.n2LevelGauge);
        potassiumGauge = findViewById(R.id.potassiumGauge);


        tempShow.setText(String.valueOf(tempGauge.getValue()));
        humidityShow.setText(String.valueOf(humidityGauge.getValue()));
        flowShow.setText(String.valueOf(flowGauge.getValue()));
        waterLevelShow.setText(String.valueOf(waterLevelGauge.getValue()));
        soilLevelShow.setText(String.valueOf(soilTempGauge.getValue()));
        moisureShow.setText(String.valueOf(moistureGauge.getValue()));
        consumptionShow.setText(String.valueOf(consumptionGauge.getValue()));
        phosphorusTxt.setText(String.valueOf(phosphorusGauge.getValue()));


        FieldName =new ArrayList<>();
        fieldArrayId=new ArrayList<>();

        Toolbar toolbar=(Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /*nav=(NavigationView)findViewById(R.id.navmenu);*/

        nav = findViewById(R.id.navmenu);
        nav.getSettings().setShapeType(ShapedViewSettings.ROUNDED_RECT);

        drawerLayout=(DrawerLayout)findViewById(R.id.drawer);

        toggle=new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.open,R.string.close);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();


        nav.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem)
            {
                switch (menuItem.getItemId())
                {
                    case R.id.menu_dashboard :
                        /*Toast.makeText(getApplicationContext(),"Dashboard Panel is Open",Toast.LENGTH_LONG).show();*/
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                    case R.id.menu_payment :
                      /*  Toast.makeText(getApplicationContext(),"Payment Panel is Open",Toast.LENGTH_LONG).show();*/
                        drawerLayout.closeDrawer(GravityCompat.START);
                        startActivity(new Intent(DashboardActivity.this, MainActivity.class)
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
                        startActivity(new Intent(DashboardActivity.this, PaymentHistoryActivity.class)
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
                        startActivity(new Intent(DashboardActivity.this, WeatherReportActivity.class)
                                .putExtra("user", userId)
                                .putExtra("custId", custId)
                                .putExtra("total_fields", total_fields)
                                .putExtra("balance", balance)
                                .putExtra("full_name", full_name)
                                .putExtra("water_rate", water_rate));
                        break;

                    case R.id.report_menu_consumption :
                        /*Toast.makeText(getApplicationContext(),"Consumption Panel is Open",Toast.LENGTH_LONG).show();*/
                        drawerLayout.closeDrawer(GravityCompat.START);
                        startActivity(new Intent(DashboardActivity.this, ConsumptionActivity.class)
                                .putExtra("user", userId)
                                .putExtra("custId", custId)
                                .putExtra("total_fields", total_fields)
                                .putExtra("balance", balance)
                                .putExtra("full_name", full_name)
                                .putExtra("water_rate", water_rate));
                        break;

                    case R.id.logOut :
                        /*Toast.makeText(getApplicationContext(),"Log Out",Toast.LENGTH_LONG).show();*/
                        signout();
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                }

                return true;
            }
        });

        switchButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    Integer switchInt = 1;
                    progressDialog.show();
                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl("http://us.infrmtx.com/sirris/app/")
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();

                    Api api = retrofit.create(Api.class);
                    Call<String> call = api.getShowSlider(fieldId,switchInt);
                    call.enqueue(new Callback<String>() {
                        @Override
                        public void onResponse(Call<String> call, Response<String> response) {
                            if (response.isSuccessful()){
                                progressDialog.dismiss();
                            /*    Toast.makeText(DashboardActivity.this, "Slider: 1", Toast.LENGTH_SHORT).show();*/
                            }else{
                                progressDialog.dismiss();
                            /*    Toast.makeText(DashboardActivity.this, "error", Toast.LENGTH_SHORT).show();*/
                            }
                        }

                        @Override
                        public void onFailure(Call<String> call, Throwable t) {
                            progressDialog.dismiss();
                            /*Toast.makeText(DashboardActivity.this, "False", Toast.LENGTH_SHORT).show();*/
                        }
                    });
                }else{
                    Integer switchInt = 0;
                    progressDialog.show();
                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl("http://us.infrmtx.com/sirris/app/")
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();

                    Api api = retrofit.create(Api.class);
                    Call<String> call = api.getShowSlider(fieldId,switchInt);
                    call.enqueue(new Callback<String>() {
                        @Override
                        public void onResponse(Call<String> call, Response<String> response) {
                            if (response.isSuccessful()){
                                progressDialog.dismiss();

                                /* Toast.makeText(DashboardActivity.this, "Slider: 0", Toast.LENGTH_SHORT).show();*/

                            }else{
                                progressDialog.dismiss();
                              /* Toast.makeText(DashboardActivity.this, "No Data error", Toast.LENGTH_SHORT).show();*/
                            }
                        }


                        @Override
                        public void onFailure(Call<String> call, Throwable t) {
                            progressDialog.dismiss();
                           /* Toast.makeText(DashboardActivity.this, "False", Toast.LENGTH_SHORT).show();*/
                        }
                    });
                }
            }
        });

        //set Spinner
        fieldList(URL+custId);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String name= spinner.getItemAtPosition(spinner.getSelectedItemPosition()).toString();
                /*String id= spinner.getItemAtPosition(spinner.getSelectedItemPosition()).toString();*/
                fieldId = fieldArrayId.get(i);
                /*Toast.makeText(getApplicationContext(),name,Toast.LENGTH_LONG).show();*/
                /*Toast.makeText(DashboardActivity.this, fieldId, Toast.LENGTH_SHORT).show();*/


                /*listShow();*/

                m_Runnable.run();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // DO Nothing here
            }
        });



        cropProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Toast.makeText(DashboardActivity.this, "Success", Toast.LENGTH_SHORT).show();*/
                startActivity(new Intent(DashboardActivity.this, CropProfileActivity.class)
                        .putExtra("user", userId)
                        .putExtra("custId", custId)
                        .putExtra("total_fields", total_fields)
                        .putExtra("balance", balance)
                        .putExtra("full_name", full_name)
                        .putExtra("water_rate", water_rate)
                        .putExtra("fieldId", fieldId)
                        .putExtra("profile_Id", profile_Id));
            }
        });
    }


    private void signout() {
        sped.putString("user", "");
        sped.putString("password", "");
        sped.putString("Remember", "");
        sped.commit();

        /*Toast.makeText(DashboardActivity.this,"Logout Successful", Toast.LENGTH_LONG).show();*/
        startActivity(new Intent(DashboardActivity.this, LoginActivity.class));
        finish();
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

                    spinner.setAdapter(new ArrayAdapter<String>(DashboardActivity.this, android.R.layout.simple_spinner_dropdown_item, FieldName));
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
        Call<MainObjectField> call= api.getShowFieldData(fieldId);
        call.enqueue(new Callback<MainObjectField>() {
            @Override
            public void onResponse(Call<MainObjectField> call, Response<MainObjectField> response) {

                List<Object> objects = response.body().getFields();
                for (Object object :objects) {

                    String fieldShowId = String.valueOf(object.getField_id());

                    temp = String.valueOf(object.getTemp());
                    Integer showTemp = object.getTemp();
                    tempShow.setText(temp+(char) 0x00B0+"C");
                    tempGauge.setEndValue(60);
                    tempGauge.setValue(showTemp);

                    String humidity = String.valueOf(object.getHumidity());
                    Integer showHumidity = object.getHumidity();
                    humidityShow.setText(humidity);
                    humidityGauge.setEndValue(100);
                    humidityGauge.setValue(showHumidity);


                    String moisture = String.valueOf(object.getMoisture());
                    Integer showMoisture = object.getMoisture();
                    moisureShow.setText(moisture);
                    moistureGauge.setEndValue(120);
                    moistureGauge.setValue(showMoisture);


                    String water_flow = String.valueOf(object.getWater_flow());
                    Integer showWaterFlow = object.getWater_flow();
                    flowShow.setText(water_flow+" mL/s");
                    flowGauge.setEndValue(10000);
                    flowGauge.setValue(showWaterFlow);

                    String phosphorus = String.valueOf(object.getP());
                   /* Integer phosporusGauge = Integer.parseInt(String.valueOf(object.getP()));*/
                    double phosporusGauge = object.getP();
                    phosphorusTxt.setText(phosphorus);
                    phosphorusGauge.setEndValue(50);
                    phosphorusGauge.setValue((int) phosporusGauge);


                    String water_liter = String.valueOf(object.getWater_liter());
                    Integer waterLiter = object.getWater_liter();
                    consumptionShow.setText(water_liter+" K/L");
                    consumptionGauge.setEndValue(20000);
                    consumptionGauge.setValue(waterLiter);

                    String water_level = object.getWater_level();
                    waterLevelShow.setText(water_level);
                    waterLevelGauge.setEndValue(60);
                    if (water_level.equals("LOW")){
                        waterLevelGauge.setValue(10);
                    }else if(water_level.equals("HIGH")){
                        waterLevelGauge.setValue(50);
                    }else if(water_level.equals("MEDIUM")){
                        waterLevelGauge.setValue(30);
                    }

                    String ph = String.valueOf(object.getPh());
                    double phGauge = object.getPh();
                    phLevelShow.setText(ph);
                    phLevelGauge.setEndValue(50);
                    phLevelGauge.setValue((int) phGauge);

                    String n2 = String.valueOf(object.getNa());
                    double n2Gauge = object.getNa();
                    n2LevelShow.setText(n2);
                    n2LevelGauge.setEndValue(50);
                    n2LevelGauge.setValue((int) n2Gauge);

                    String potassium = String.valueOf(object.getP());
                    double pGauges = object.getP();
                    potassiumShow.setText(potassium);
                    potassiumGauge.setEndValue(50);
                    potassiumGauge.setValue((int) pGauges);

                    String soil_level = String.valueOf(object.getSoil_temp());
                    Integer showSoilLevel = object.getSoil_temp();
                    soilLevelShow.setText(soil_level+(char) 0x00B0+"C");
                    soilTempGauge.setEndValue(60);
                    soilTempGauge.setValue(showSoilLevel);



                    String crop_Name = object.getCrop_name();
                    cropName.setText(crop_Name);


                    profile_Id = object.getProfile_id();
                    valveStatus = object.getValve_status();
                    if (valveStatus==1){
                        switchButton.setEnabled(true);
                        switchButton.setChecked(true);
                    }else if(valveStatus == 0){
                        switchButton.setChecked(false);
                    }else if(valveStatus == -1){
                        switchButton.setEnabled(false);
                    }



                }
            }

            @Override
            public void onFailure(Call<MainObjectField> call, Throwable t) {

               /* Toast.makeText(DashboardActivity.this, "No data found", Toast.LENGTH_SHORT).show();*/
                tempShow.setText("");
                humidityShow.setText("");
                moisureShow.setText("");
                flowShow.setText("");
                consumptionShow.setText("");
                waterLevelShow.setText("");
                soilLevelShow.setText("");
                tempGauge.setValue(0);
                humidityGauge.setValue(0);
                moistureGauge.setValue(0);
                waterLevelGauge.setValue(0);
                consumptionGauge.setValue(0);
                soilTempGauge.setValue(0);
                phosphorusGauge.setValue(0);
                phLevelGauge.setValue(0);
                n2LevelGauge.setValue(0);
                potassiumGauge.setValue(0);
            }
        });
    }
    @Override
    public void onBackPressed() {

        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.alert_dark_frame)
                .setTitle("Exit")
                .setMessage("Do you want to close this App?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent a = new Intent(Intent.ACTION_MAIN);
                        a.addCategory(Intent.CATEGORY_HOME);
                        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(a);
                        finish();
                    }

                })
                .setNegativeButton("No ", null)
                .show();



    }

}