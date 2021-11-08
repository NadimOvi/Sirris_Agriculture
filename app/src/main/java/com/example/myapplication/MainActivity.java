package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.Consumption.ConsumptionActivity;
import com.example.myapplication.Model.ProfileInfoModel;
import com.example.myapplication.Payment.Model.PaymentBalance;
import com.example.myapplication.Payment.PaymentHistoryActivity;
import com.example.myapplication.WeatherReport.WeatherReportActivity;
import com.google.android.material.navigation.NavigationView;
import com.sm.shurjopaysdk.listener.PaymentResultListener;
import com.sm.shurjopaysdk.model.RequiredDataModel;
import com.sm.shurjopaysdk.model.TransactionInfo;
import com.sm.shurjopaysdk.payment.ShurjoPaySDK;
import com.sm.shurjopaysdk.utils.SPayConstants;

import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import softpro.naseemali.ShapedNavigationView;
import softpro.naseemali.ShapedViewSettings;

public class MainActivity extends AppCompatActivity {

    private ShapedNavigationView nav;
    ActionBarDrawerToggle toggle;
    DrawerLayout drawerLayout;
    SharedPreferences sp;
    SharedPreferences.Editor sped;

    Button payNowButton;
    int price=0;
    EditText priceEditText;
    TextView show;
    String tempPrice = "10";
    private static final String TAG = "PaymentActivity";

    private String userId,userPassword,full_name;
    private Integer custId;
    Integer total_fields;
    Double balance,water_rate;

    TextView profileNameID,fieldText,balanceText,rateText;


    ////Bank Details info Var
    String transactionId,bankTxStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("");
        setContentView(R.layout.activity_main);

        payNowButton = findViewById(R.id.paynow);

        priceEditText = findViewById(R.id.amount);

        show = findViewById(R.id.show);
        profileNameID = findViewById(R.id.profileNameID);
        fieldText = findViewById(R.id.fieldText);
        balanceText = findViewById(R.id.balanceText);
        rateText = findViewById(R.id.rateText);


        Intent i=getIntent();
        userId=i.getStringExtra("user");
        userPassword=i.getStringExtra("password");
        custId=i.getIntExtra("custId",0);
        balance=i.getDoubleExtra("balance",0);
        total_fields=i.getIntExtra("total_fields",0);
        water_rate=i.getDoubleExtra("water_rate",0);
        full_name=i.getStringExtra("full_name");

        profileNameID.setText(full_name);
        fieldText.setText(String.valueOf(total_fields));
        balanceText.setText(String.valueOf(balance));
        rateText.setText(String.valueOf(water_rate));


        Intent intentBackgroundService = new Intent(this, FirebasePushNotificationClass.class);
        startService(intentBackgroundService);



        payNowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                price = (int) Double.parseDouble(priceEditText.getText().toString());
                paymentRequest(price);


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
                        startActivity(new Intent(MainActivity.this, DashboardActivity.class)
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
                        startActivity(new Intent(MainActivity.this, MainActivity.class)
                                .putExtra("user", userId)
                                .putExtra("custId", custId)
                                .putExtra("total_fields", total_fields)
                                .putExtra("balance", balance)
                                .putExtra("full_name", full_name)
                                .putExtra("water_rate", water_rate));
                        break;

                    case R.id.menu_history :
                       /* Toast.makeText(getApplicationContext(),"Payment History Panel is Open",Toast.LENGTH_LONG).show();*/
                        drawerLayout.closeDrawer(GravityCompat.START);
                        startActivity(new Intent(MainActivity.this, PaymentHistoryActivity.class)
                                .putExtra("user", userId)
                                .putExtra("custId", custId)
                                .putExtra("total_fields", total_fields)
                                .putExtra("balance", balance)
                                .putExtra("full_name", full_name)
                                .putExtra("water_rate", water_rate));
                        break;

                    case R.id.report_menu_spinner :
                      /*  Toast.makeText(getApplicationContext(),"Reports Panel is Open",Toast.LENGTH_LONG).show();*/
                        drawerLayout.closeDrawer(GravityCompat.START);
                        startActivity(new Intent(MainActivity.this, WeatherReportActivity.class)
                                .putExtra("user", userId)
                                .putExtra("custId", custId)
                                .putExtra("total_fields", total_fields)
                                .putExtra("balance", balance)
                                .putExtra("full_name", full_name)
                                .putExtra("water_rate", water_rate));
                        break;

                    case R.id.report_menu_consumption :
                      /*  Toast.makeText(getApplicationContext(),"Consumption Panel is Open",Toast.LENGTH_LONG).show();*/
                        drawerLayout.closeDrawer(GravityCompat.START);
                        startActivity(new Intent(MainActivity.this, ConsumptionActivity.class)
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

    public void paymentRequest(int price){


        String UniqId = "NOK"+getRandomUniqIdString();
        RequiredDataModel requiredDataModel = new RequiredDataModel("spaytest",  "JehPNXF58rXs", UniqId, price,"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJrZXkiOiJzcGF5dGVzdCIsImlhdCI6MTU5ODM2MTI1Nn0.cwkvdTDI6_K430xq7Iqapaknbqjm9J3Th1EiXePIEcY");


        ShurjoPaySDK.getInstance().makePayment(MainActivity.this, SPayConstants.SdkType.TEST, requiredDataModel, new PaymentResultListener() {

            @Override
            public void onSuccess(TransactionInfo transactionInfo) {
                Log.d(TAG, "onSuccess: transactionInfo = " + transactionInfo);
               /* Toast.makeText(MainActivity.this, "onSuccess: transactionInfo = " +
                        transactionInfo, Toast.LENGTH_SHORT).show();*/

                transactionId = transactionInfo.getTxID();
                String bankTxId = transactionInfo.getBankTxID();
                bankTxStatus = transactionInfo.getBankTxStatus();
                String txnAmount = String.valueOf(transactionInfo.getTxnAmount());
                String gateWay = transactionInfo.getGateWay();
                String method = transactionInfo.getMethod();
                String cardHolderName = transactionInfo.getCardHolderName();
                String card = transactionInfo.getCardNumber();

                /*postRequest();
                show.setText("Transaction Id :"+transactionId+"\n"+
                        "BankTxId :"+bankTxId+"\n"+
                        "BankTxStatus :"+bankTxStatus+"\n"+
                        "TxnAmount :"+txnAmount+"\n"+
                        "GateWay :"+gateWay+"\n"+
                        "Method :"+method+"\n"+
                        "CardHolderName :"+cardHolderName+"\n"+
                        "Card :"+card+"\n");*/

                postRequest();
            }

            @Override
            public void onFailed(String message) {
                Log.d(TAG, "onFailed: message = " + message);
               /* Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();*/
             /*   show.setText(message);*/
                Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
            }
        });

    }
    public static String getRandomUniqIdString() {
        Random rnd = new Random();
        int number = rnd.nextInt(999999);


        return String.format("%06d", number);
    }

    private void signout() {
        sped.putString("user", "");
        sped.putString("password", "");
        sped.putString("Remember", "");
        sped.commit();

       /* Toast.makeText(MainActivity.this,"Logout Successful", Toast.LENGTH_LONG).show();*/
        startActivity(new Intent(MainActivity.this, LoginActivity.class));
        finish();
    }
    private void postRequest() {


        if (bankTxStatus.equals("success")){
            Integer statusSuccessful = 1;


            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://us.infrmtx.com/sirris/app/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            Api api = retrofit.create(Api.class);
            Call<String> call =api.getPaymentStatusShow(custId,price,transactionId,statusSuccessful);
            call.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    if (response.isSuccessful()){
                        Toast.makeText(MainActivity.this, "Your Payment is Done", Toast.LENGTH_SHORT).show();
                        priceEditText.setText("");
                        changeBalancePost();

                        AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
                        alertDialog.setTitle("Alert");
                        alertDialog.setMessage("Your payment has been done");
                        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        /*dialog.dismiss();*/
                                        startActivity(new Intent(MainActivity.this, PaymentHistoryActivity.class)
                                                .putExtra("user", userId)
                                                .putExtra("custId", custId)
                                                .putExtra("total_fields", total_fields)
                                                .putExtra("balance", balance)
                                                .putExtra("full_name", full_name)
                                                .putExtra("water_rate", water_rate));
                                    }
                                });
                        alertDialog.show();
                    }
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_SHORT).show();
                }
            });


        }else{
            Integer statusFall = 0;
            Toast.makeText(this, "Payment is cancel", Toast.LENGTH_SHORT).show();
        }

    }

    private void changeBalancePost() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://informatix.asia/sirris/app/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Api api = retrofit.create(Api.class);
        Call<PaymentBalance> call = api.getBalanceStatusShow(custId);
        call.enqueue(new Callback<PaymentBalance>() {
            @Override
            public void onResponse(Call<PaymentBalance> call, Response<PaymentBalance> response) {
                if (response.isSuccessful()){
                    PaymentBalance paymentBalance = response.body();
                   String balance = String.valueOf(paymentBalance.getBalance());
                    balanceText.setText(balance);
                }else{
                    Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<PaymentBalance> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

}