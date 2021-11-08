package com.example.myapplication.WeatherReport.Model;

public class WeatherModel {
    private String day;
    private Double tem;
    private Double hum;
    private Double mois;
    private Double wflow;
    private Integer wtlr;
    private Double wlvl;
    private Double stemp;

    public WeatherModel() {
    }

    public WeatherModel(String day, Double tem, Double hum, Double mois, Double wflow, Integer wtlr, Double wlvl, Double stemp) {
        this.day = day;
        this.tem = tem;
        this.hum = hum;
        this.mois = mois;
        this.wflow = wflow;
        this.wtlr = wtlr;
        this.wlvl = wlvl;
        this.stemp = stemp;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public Double getTem() {
        return tem;
    }

    public void setTem(Double tem) {
        this.tem = tem;
    }

    public Double getHum() {
        return hum;
    }

    public void setHum(Double hum) {
        this.hum = hum;
    }

    public Double getMois() {
        return mois;
    }

    public void setMois(Double mois) {
        this.mois = mois;
    }

    public Double getWflow() {
        return wflow;
    }

    public void setWflow(Double wflow) {
        this.wflow = wflow;
    }

    public Integer getWtlr() {
        return wtlr;
    }

    public void setWtlr(Integer wtlr) {
        this.wtlr = wtlr;
    }

    public Double getWlvl() {
        return wlvl;
    }

    public void setWlvl(Double wlvl) {
        this.wlvl = wlvl;
    }

    public Double getStemp() {
        return stemp;
    }

    public void setStemp(Double stemp) {
        this.stemp = stemp;
    }
}
