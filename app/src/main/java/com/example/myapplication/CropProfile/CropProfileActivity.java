package com.example.myapplication.CropProfile;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.Api;
import com.example.myapplication.Consumption.ConsumptionActivity;
import com.example.myapplication.CropProfile.Adapter.CropsAdapter;
import com.example.myapplication.CropProfile.Adapter.VegetableCropAdapter;
import com.example.myapplication.DashboardActivity;
import com.example.myapplication.LoginActivity;
import com.example.myapplication.Payment.PaymentHistoryActivity;
import com.example.myapplication.R;
import com.example.myapplication.WeatherReport.WeatherReportActivity;
import com.google.android.material.navigation.NavigationView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import softpro.naseemali.ShapedNavigationView;
import softpro.naseemali.ShapedViewSettings;

public class CropProfileActivity extends AppCompatActivity {

    private ShapedNavigationView nav;
    ActionBarDrawerToggle toggle;
    DrawerLayout drawerLayout;
    ProgressDialog progressDialog;

    private String userId,full_name;
    private Integer custId;
    Integer total_fields;
    Integer profile_Id,fieldId;
    private Double balance, water_rate;
    private TextView cropProfileNameID,cropProfileTotalField,cropProfileTotalBalance,cropProfileRateText;
    RecyclerView cropProfile_recyclerView,vegetableProfile_recyclerView;
    SharedPreferences sp;
    SharedPreferences.Editor sped;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crop_profile);
        setTitle("");
        sp = getApplicationContext().getSharedPreferences("GTR", MODE_PRIVATE);
        sped = sp.edit();
        initialise();
        riceShow();
        vegetableShow();
    }

    private void initialise() {
        Intent i=getIntent();
        userId=i.getStringExtra("user");
        custId=i.getIntExtra("custId",0);
        balance=i.getDoubleExtra("balance",0);
        total_fields=i.getIntExtra("total_fields",0);
        water_rate=i.getDoubleExtra("water_rate",0);
        full_name=i.getStringExtra("full_name");
        profile_Id=i.getIntExtra("profile_Id",0);
        fieldId=i.getIntExtra("fieldId",0);


        cropProfileNameID=findViewById(R.id.cropProfileNameID);
        cropProfileNameID.setText(full_name);
        cropProfileTotalField=findViewById(R.id.cropProfileTotalField);
        cropProfileTotalField.setText(String.valueOf(total_fields));
        cropProfileTotalBalance=findViewById(R.id.cropProfileTotalBalance);
        cropProfileTotalBalance.setText(String.valueOf(balance));
        cropProfileRateText=findViewById(R.id.cropProfileRateText);
        cropProfileRateText.setText(String.valueOf(water_rate));

        cropProfile_recyclerView=findViewById(R.id.cropProfile_recyclerView);
        cropProfile_recyclerView.setHasFixedSize(true);
        cropProfile_recyclerView.setLayoutManager(new LinearLayoutManager(this));

        vegetableProfile_recyclerView=findViewById(R.id.vegetableProfile_recyclerView);
        vegetableProfile_recyclerView.setHasFixedSize(true);
        vegetableProfile_recyclerView.setLayoutManager(new LinearLayoutManager(this));

        progressDialog = new ProgressDialog(CropProfileActivity.this);
        progressDialog.setMessage("Please Wait...");
        progressDialog.setCancelable(false);

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
                        startActivity(new Intent(CropProfileActivity.this, DashboardActivity.class)
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
                        startActivity(new Intent(CropProfileActivity.this, PaymentHistoryActivity.class)
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
                        startActivity(new Intent(CropProfileActivity.this, PaymentHistoryActivity.class)

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
                        startActivity(new Intent(CropProfileActivity.this, WeatherReportActivity.class)
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
                        startActivity(new Intent(CropProfileActivity.this, ConsumptionActivity.class)
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

    }
    private void signout() {
        sped.putString("user", "");
        sped.putString("password", "");
        sped.putString("Remember", "");
        sped.commit();

        /*  Toast.makeText(WeatherReportActivity.this,"Logout Successful", Toast.LENGTH_LONG).show();*/
        startActivity(new Intent(CropProfileActivity.this, LoginActivity.class));
        finish();
    }
    private void riceShow() {
        progressDialog.show();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://us.infrmtx.com/sirris/app/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();


        Api api = retrofit.create(Api.class);
        Call<RiceCropModels> call= api.getRiceCropsName(1);
        call.enqueue(new Callback<RiceCropModels>() {
            @Override
            public void onResponse(Call<RiceCropModels> call, Response<RiceCropModels> response) {
                List<RicesModel> list1 =response.body().getCrops();
                if (list1!=null){
                    for (RicesModel cropsModel :list1){
                        progressDialog.dismiss();
                        showIt(response.body().getCrops(),profile_Id,fieldId,userId,custId,total_fields,balance,full_name,water_rate);
                    }
                }else{
                    /*Toast.makeText(CropProfileActivity.this, "No data is Found", Toast.LENGTH_SHORT).show();*/
                }
            }

            @Override
            public void onFailure(Call<RiceCropModels> call, Throwable t) {
               /* Toast.makeText(CropProfileActivity.this, "No data is Found", Toast.LENGTH_SHORT).show();*/
                progressDialog.dismiss();
            }
        });
    }
    private void showIt(List<RicesModel> list1, Integer profile_id, Integer fieldId, String userId, Integer custId, Integer total_fields,
                        Double balance, String full_name, Double water_rate) {
        CropsAdapter cropsAdapter = new CropsAdapter(list1,getApplicationContext(),profile_id, fieldId,userId,custId,total_fields,balance,full_name,water_rate);
        cropProfile_recyclerView.setAdapter(cropsAdapter);
    }
    private void vegetableShow() {
        progressDialog.show();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://us.infrmtx.com/sirris/app/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();


        Api api = retrofit.create(Api.class);
        Call<VegetableCropModels> call= api.getVegetableCropsName(2);
        call.enqueue(new Callback<VegetableCropModels>() {
            @Override
            public void onResponse(Call<VegetableCropModels> call, Response<VegetableCropModels> response) {
                List<VegetableModels> list1 =response.body().getCrops();
                if (list1!=null){
                    for (VegetableModels cropsModel :list1){
                        progressDialog.dismiss();
                        showVegetable(response.body().getCrops(),profile_Id,fieldId,userId,custId,total_fields,balance,full_name,water_rate);
                    }
                }else{
                    /*Toast.makeText(CropProfileActivity.this, "No data is Found", Toast.LENGTH_SHORT).show();*/
                }
            }

            @Override
            public void onFailure(Call<VegetableCropModels> call, Throwable t) {
               /* Toast.makeText(CropProfileActivity.this, "No data is Found", Toast.LENGTH_SHORT).show();*/
                progressDialog.dismiss();
            }
        });
    }
    private void showVegetable(List<VegetableModels> list1, Integer profile_Id, Integer fieldId, String userId, Integer custId, Integer total_fields,
                               Double balance, String full_name, Double water_rate) {
        VegetableCropAdapter vegetableCropAdapter = new VegetableCropAdapter(list1,getApplicationContext(),profile_Id, fieldId,userId,custId,total_fields,balance,full_name,water_rate);
        vegetableProfile_recyclerView.setAdapter(vegetableCropAdapter);
    }
}