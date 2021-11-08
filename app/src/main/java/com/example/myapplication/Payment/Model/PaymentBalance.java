package com.example.myapplication.Payment.Model;

public class PaymentBalance {
    Double balance;

    public PaymentBalance(Double balance) {
        this.balance = balance;
    }

    public PaymentBalance() {
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }
}
