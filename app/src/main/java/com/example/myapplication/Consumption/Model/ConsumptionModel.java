package com.example.myapplication.Consumption.Model;

public class ConsumptionModel {
    private String day;
    private Double consumption;
    private Double cost;

    public ConsumptionModel(String day, Double consumption, Double cost) {
        this.day = day;
        this.consumption = consumption;
        this.cost = cost;
    }

    public ConsumptionModel() {
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public Double getConsumption() {
        return consumption;
    }

    public void setConsumption(Double consumption) {
        this.consumption = consumption;
    }

    public Double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }
}
