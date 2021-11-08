package com.example.myapplication.Model;

public class ProfileInfoModel {
    private Integer user_id;
    private Integer cust_id;
    private Double balance;
    private String full_name;
    private double water_rate;
    private Integer total_fields;

    public ProfileInfoModel() {
    }

    public ProfileInfoModel(Integer user_id, Integer cust_id, Double balance, String full_name, double water_rate, Integer total_fields) {
        this.user_id = user_id;
        this.cust_id = cust_id;
        this.balance = balance;
        this.full_name = full_name;
        this.water_rate = water_rate;
        this.total_fields = total_fields;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public Integer getCust_id() {
        return cust_id;
    }

    public void setCust_id(Integer cust_id) {
        this.cust_id = cust_id;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public double getWater_rate() {
        return water_rate;
    }

    public void setWater_rate(double water_rate) {
        this.water_rate = water_rate;
    }

    public Integer getTotal_fields() {
        return total_fields;
    }

    public void setTotal_fields(Integer total_fields) {
        this.total_fields = total_fields;
    }
}
