package com.example.myapplication.Consumption.Model;

import java.util.List;

public class ConsumptionMainModel {
    private List<ConsumptionModel> consumption;

    public ConsumptionMainModel(List<ConsumptionModel> consumption) {
        this.consumption = consumption;
    }

    public ConsumptionMainModel() {
    }

    public List<ConsumptionModel> getConsumption() {
        return consumption;
    }

    public void setConsumption(List<ConsumptionModel> consumption) {
        this.consumption = consumption;
    }
}
