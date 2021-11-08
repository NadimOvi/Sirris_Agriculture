package com.example.myapplication.Payment.Model;

import java.util.List;

public class PaymentMainModel {
    private List<Payment>payments;

    public PaymentMainModel(List<Payment> payments) {
        this.payments = payments;
    }

    public List<Payment> getPayments() {
        return payments;
    }

    public void setPayments(List<Payment> payments) {
        this.payments = payments;
    }

    public PaymentMainModel() {
    }
}
