package com.example.myapplication.Payment.Model;

public class Payment {
    private String date;
    private Integer amount;

    public Payment(String date, Integer amount) {
        this.date = date;
        this.amount = amount;
    }

    public Payment() {
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }
}
