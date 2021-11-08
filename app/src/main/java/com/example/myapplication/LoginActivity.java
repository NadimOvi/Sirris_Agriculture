package com.example.myapplication;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import com.example.myapplication.Model.ProfileInfoModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {

    private EditText userIdText,userPasswordText;
    private Button userLoginButton;

    private SharedPreferences sp;
    private SharedPreferences.Editor sped;
    //Set Variable
    private String userId,userPassword;
    public static boolean isSignedIn = false;
    Integer custId, userPostId;
    private ProgressDialog progressDialog;
    String token;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initialize();
        sp = getApplicationContext().getSharedPreferences("GTR", MODE_PRIVATE);
        sped = sp.edit();

        if (isConnected()) {
            prcGetRemember();
        } else {
            Toast.makeText(getApplicationContext(), "No internet Connection", Toast.LENGTH_SHORT).show();
        }

        userLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isConnected()) {
                    validationUser();
                } else {
                    Toast.makeText(getApplicationContext(), "No internet Connection", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private boolean isConnected() {

        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            //we are connected to a network
            return true;
        } else
            return false;

    }

    private void initialize() {
        userIdText = findViewById(R.id.userIdText);
        userPasswordText = findViewById(R.id.userPasswordText);
        userLoginButton = findViewById(R.id.userLoginButton);

        progressDialog = new ProgressDialog(LoginActivity.this);
        progressDialog.setMessage("Please Wait...");
        progressDialog.setCancelable(false);
    }

    private void prcSetRemember() {
        String strRemember = "";
        strRemember = "Remember";
        sped.putString("user", userIdText.getText().toString().trim());
        sped.putString("password", userPasswordText.getText().toString().trim());
        sped.putString("Remember", strRemember);
        sped.commit();
    }
    private void prcGetRemember() {
        progressDialog.dismiss();
        if (sp.contains("user")) {
            String AndroidId = "";
            userIdText.setText(sp.getString("user", ""));
            userPasswordText.setText(sp.getString("password", ""));
            AndroidId = sp.getString("Remember", "");
            if (AndroidId.length() != 0) {
            }
            prcValidateUser("Auto");
        }
    }

    private void prcValidateUser(String Flag) {
        //Validating User :: Using Async Task
        try {
            validationUser();

        } catch (Exception ex) {
            Log.d("ValUser", ex.getMessage());
        }
    }
    private void validationUser() {

        userId = userIdText.getText().toString().trim();
        userPassword = userPasswordText.getText().toString().trim();

        if (userId.isEmpty()) {
            userIdText.setError("User Id is empty");
            userIdText.requestFocus();
            return;
        } else if (userPassword.isEmpty()) {
            userPasswordText.setError("Password is empty");
            userPasswordText.requestFocus();
            return;


        }else{
            userLogin();
        }
    }
/*    private void userLogin(){
        startActivity(new Intent(MainActivity.this, DashboardActivity.class)
                .putExtra("PhoneNumber", phoneNumberText)
                .putExtra("AndroidId", Android_ID));
        prcSetRemember();
        finish();
    }*/

    private void loginValidation() {
        userId = userIdText.getText().toString().trim();
        userPassword = userPasswordText.getText().toString().trim();

        if (userId.isEmpty()) {
            userIdText.setError("User Id is empty");
            userIdText.requestFocus();
            return;
        }else if(userPassword.isEmpty()){
            userPasswordText.setError("Password is empty");
            userPasswordText.requestFocus();
            return;
        }else{
            /*Intent intent = new Intent(MainActivity.this, DashboardActivity.class);
            intent.putExtra("phoneNumber", phoneNumber);
            intent.putExtra("mainNumber", mainNumber);
            intent.putExtra("email",email);
            startActivity(intent);*/
            /*serverCall();*/
        }
    }

    private void userLogin() {
        progressDialog.show();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://us.infrmtx.com/sirris/app/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();


        Api api = retrofit.create(Api.class);
        Call<ProfileInfoModel> call =api.getLogin(userId,userPassword);
        call.enqueue(new Callback<ProfileInfoModel>() {
            @Override
            public void onResponse(Call<ProfileInfoModel> call, Response<ProfileInfoModel> response) {
                ProfileInfoModel profileInfoModel= response.body();
                Boolean request = false;
                if (response.isSuccessful()){
                    if (profileInfoModel!=null) {

                        custId = profileInfoModel.getCust_id();
                        userPostId = profileInfoModel.getUser_id();
                        Double balance = profileInfoModel.getBalance();
                        String full_name = profileInfoModel.getFull_name();
                        Double water_rate = profileInfoModel.getWater_rate();
                        Integer total_fields = profileInfoModel.getTotal_fields();
                        progressDialog.dismiss();

                        ///Send Channel Id
                        postNotificationChanel();
                        startActivity(new Intent(LoginActivity.this, DashboardActivity.class)
                                .putExtra("user", userId)
                                .putExtra("password", userPassword)
                                .putExtra("custId", custId)
                                .putExtra("balance", balance)
                                .putExtra("full_name", full_name)
                                .putExtra("water_rate", water_rate)
                                .putExtra("total_fields", total_fields)
                        );
                        prcSetRemember();
                        finish();

                    }else if(request){
                        progressDialog.dismiss();
                        Toast.makeText(LoginActivity.this, "Error", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    progressDialog.dismiss();
                    Toast.makeText(LoginActivity.this, "Bad Request", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ProfileInfoModel> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(LoginActivity.this, "Server cannot access", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void postNotificationChanel() {
        FirebaseInstanceId.getInstance().getInstanceId().addOnCompleteListener(new OnCompleteListener<InstanceIdResult>()
        {
            @Override
            public void onComplete(@NonNull Task<InstanceIdResult> task)
            {
                if (task.isSuccessful())
                {
                    token=task.getResult().getToken();
                    Log.i("token ",token);
                    postChanelId();
                }
            }
        });
    }

    private void postChanelId() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://us.infrmtx.com/sirris/app/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Api api = retrofit.create(Api.class);
        Call<String> call =api.setChanelId(userPostId,token);

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()){
                   /* Toast.makeText(LoginActivity.this, token, Toast.LENGTH_SHORT).show();*/
                }else{
                    /*Toast.makeText(LoginActivity.this, "Something Missing", Toast.LENGTH_SHORT).show();*/
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(LoginActivity.this, "Failed to access", Toast.LENGTH_SHORT).show();
            }
        });

    }
}