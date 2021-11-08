package com.example.myapplication.WeatherReport.Model;
import java.util.List;

public class WeatherMainModel {
    private List<WeatherModel> fields;

    public WeatherMainModel(List<WeatherModel> fields) {
        this.fields = fields;
    }

    public WeatherMainModel() {
    }

    public List<WeatherModel> getFields() {
        return fields;
    }

    public void setFields(List<WeatherModel> fields) {
        this.fields = fields;
    }
}
