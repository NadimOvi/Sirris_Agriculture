package com.example.myapplication.DashboardSpinnerModel;

public class Object {
    private Integer field_id;
    private String date;
    private Integer temp;
    private Integer humidity;
    private Integer moisture;
    private Integer water_flow;
    private Integer water_liter;
    private String water_level;
    private Integer soil_temp;
    private Integer valve_status;
    private String crop_name;
    private Integer profile_id;
    private Integer org_id;
    private Integer op_id;
    private Integer ipcu_id;

    private double ph;
    private double na;
    private double p;
    private double k;

    public Object() {
    }

    public Object(Integer field_id, String date, Integer temp, Integer humidity, Integer moisture, Integer water_flow, Integer water_liter, String water_level, Integer soil_temp, Integer valve_status, String crop_name, Integer profile_id, Integer org_id, Integer op_id, Integer ipcu_id, double ph, double na, double p, double k) {
        this.field_id = field_id;
        this.date = date;
        this.temp = temp;
        this.humidity = humidity;
        this.moisture = moisture;
        this.water_flow = water_flow;
        this.water_liter = water_liter;
        this.water_level = water_level;
        this.soil_temp = soil_temp;
        this.valve_status = valve_status;
        this.crop_name = crop_name;
        this.profile_id = profile_id;
        this.org_id = org_id;
        this.op_id = op_id;
        this.ipcu_id = ipcu_id;
        this.ph = ph;
        this.na = na;
        this.p = p;
        this.k = k;
    }

    public Integer getField_id() {
        return field_id;
    }

    public void setField_id(Integer field_id) {
        this.field_id = field_id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Integer getTemp() {
        return temp;
    }

    public void setTemp(Integer temp) {
        this.temp = temp;
    }

    public Integer getHumidity() {
        return humidity;
    }

    public void setHumidity(Integer humidity) {
        this.humidity = humidity;
    }

    public Integer getMoisture() {
        return moisture;
    }

    public void setMoisture(Integer moisture) {
        this.moisture = moisture;
    }

    public Integer getWater_flow() {
        return water_flow;
    }

    public void setWater_flow(Integer water_flow) {
        this.water_flow = water_flow;
    }

    public Integer getWater_liter() {
        return water_liter;
    }

    public void setWater_liter(Integer water_liter) {
        this.water_liter = water_liter;
    }

    public String getWater_level() {
        return water_level;
    }

    public void setWater_level(String water_level) {
        this.water_level = water_level;
    }

    public Integer getSoil_temp() {
        return soil_temp;
    }

    public void setSoil_temp(Integer soil_temp) {
        this.soil_temp = soil_temp;
    }

    public Integer getValve_status() {
        return valve_status;
    }

    public void setValve_status(Integer valve_status) {
        this.valve_status = valve_status;
    }

    public String getCrop_name() {
        return crop_name;
    }

    public void setCrop_name(String crop_name) {
        this.crop_name = crop_name;
    }

    public Integer getProfile_id() {
        return profile_id;
    }

    public void setProfile_id(Integer profile_id) {
        this.profile_id = profile_id;
    }

    public Integer getOrg_id() {
        return org_id;
    }

    public void setOrg_id(Integer org_id) {
        this.org_id = org_id;
    }

    public Integer getOp_id() {
        return op_id;
    }

    public void setOp_id(Integer op_id) {
        this.op_id = op_id;
    }

    public Integer getIpcu_id() {
        return ipcu_id;
    }

    public void setIpcu_id(Integer ipcu_id) {
        this.ipcu_id = ipcu_id;
    }

    public double getPh() {
        return ph;
    }

    public void setPh(double ph) {
        this.ph = ph;
    }

    public double getNa() {
        return na;
    }

    public void setNa(double na) {
        this.na = na;
    }

    public double getP() {
        return p;
    }

    public void setP(double p) {
        this.p = p;
    }

    public double getK() {
        return k;
    }

    public void setK(double k) {
        this.k = k;
    }
}
