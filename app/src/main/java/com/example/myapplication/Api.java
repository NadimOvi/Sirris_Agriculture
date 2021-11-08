package com.example.myapplication;


import com.example.myapplication.Consumption.Model.ConsumptionMainModel;
import com.example.myapplication.CropProfile.RiceCropModels;
import com.example.myapplication.CropProfile.VegetableCropModels;
import com.example.myapplication.DashboardSpinnerModel.MainObjectField;
import com.example.myapplication.Model.MainFieldListShow;
import com.example.myapplication.Model.ProfileInfoModel;
import com.example.myapplication.Payment.Model.PaymentBalance;
import com.example.myapplication.Payment.Model.PaymentMainModel;
import com.example.myapplication.WeatherReport.Model.WeatherMainModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface Api {

    @GET("login.php")
    Call<ProfileInfoModel> getLogin(@Query("user") String userName,
                                    @Query("pass") String userPassword);

    //FieldList
    @GET("list_fields.php")
    Call<MainFieldListShow> getData(@Query("cust_id") Integer custId);

    //FieldList
    @GET("dashboard.php")
    Call<MainObjectField> getShowFieldData(@Query("field_id") Integer fieldId);

    ///Valve slider
    @GET("slider.php")
    Call<String> getShowSlider(@Query("field_id") Integer custId,
                                             @Query("switch") Integer fieldId);

    //Payment
    @POST("new_payment.php")
    Call<String> getPaymentStatusShow(@Query("cust_id") Integer custId,
                                      @Query("amount") Integer amount,
                                      @Query("transaction_id") String transaction_id,
                                      @Query("status") Integer status);

    //Payment Change
    @GET("balance.php")
    Call<PaymentBalance> getBalanceStatusShow(@Query("cust_id") Integer custId);

    //Payment History
    @GET("payment_history.php")
    Call<PaymentMainModel> getShowPaymentHistory(@Query("cust_id") Integer custId);

    //Payment History with Date
    @GET("payment_history.php")
    Call<PaymentMainModel> getShowPaymentDateHistory(@Query("cust_id") Integer custId,
                                                     @Query("from") String from,
                                                     @Query("to") String to);

    //Weather Report
    @GET("weather_report.php")
    Call<WeatherMainModel> getShowWeatherReport(@Query("field_id") Integer fieldId);

    //Weather Report with Date
    @GET("weather_report.php")
    Call<WeatherMainModel> getShowWeatherReportDate(/*@Header("Content-Type: application/json") String authorization,*/
            @Query("field_id") Integer fieldId,
            @Query("from") String from,
            @Query("to") String to);

    //Consumption Report
    @GET("consumption_report.php")
    Call<ConsumptionMainModel> getShowConsumptionReport(@Query("cust_id") Integer custId,
                                                        @Query("field_id") Integer fieldId);

    //Consumption Report with Date
    @GET("consumption_report.php")
    Call<ConsumptionMainModel> getShowConsumptionReportDate(@Query("cust_id") Integer custId,
                                                            @Query("field_id") Integer fieldId,
                                                            @Query("from") String from,
                                                            @Query("to") String to);

    //Notification Channel ID
    @POST("updatetoken.php")
    Call<String> setChanelId(@Query("user_id") Integer custId,
                                      @Query("token") String amount);

    //Crop Profile
    @GET("crops.php")
    Call<RiceCropModels> getRiceCropsName(@Query("cat_id") Integer catId);
    @GET("crops.php")
    Call<VegetableCropModels> getVegetableCropsName(@Query("cat_id") Integer catId);

    ///Update Crop
    @POST("update_crop.php")
    Call<String> setUpdateCrop(@Query("crop_id") Integer crop_id,
                             @Query("field_id") Integer field_id);
}
