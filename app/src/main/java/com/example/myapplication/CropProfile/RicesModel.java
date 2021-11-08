package com.example.myapplication.CropProfile;

public class RicesModel {
    private Integer profile_id;
    private String crop_name;
    private Integer min_moisture;
    private Integer max_moisture;
    private Integer min_wlevel;
    private Integer max_wlevel;
    private Integer cat_id;

    public RicesModel() {
    }

    public RicesModel(Integer profile_id, String crop_name, Integer min_moisture, Integer max_moisture, Integer min_wlevel, Integer max_wlevel, Integer cat_id) {
        this.profile_id = profile_id;
        this.crop_name = crop_name;
        this.min_moisture = min_moisture;
        this.max_moisture = max_moisture;
        this.min_wlevel = min_wlevel;
        this.max_wlevel = max_wlevel;
        this.cat_id = cat_id;
    }

    public Integer getProfile_id() {
        return profile_id;
    }

    public void setProfile_id(Integer profile_id) {
        this.profile_id = profile_id;
    }

    public String getCrop_name() {
        return crop_name;
    }

    public void setCrop_name(String crop_name) {
        this.crop_name = crop_name;
    }

    public Integer getMin_moisture() {
        return min_moisture;
    }

    public void setMin_moisture(Integer min_moisture) {
        this.min_moisture = min_moisture;
    }

    public Integer getMax_moisture() {
        return max_moisture;
    }

    public void setMax_moisture(Integer max_moisture) {
        this.max_moisture = max_moisture;
    }

    public Integer getMin_wlevel() {
        return min_wlevel;
    }

    public void setMin_wlevel(Integer min_wlevel) {
        this.min_wlevel = min_wlevel;
    }

    public Integer getMax_wlevel() {
        return max_wlevel;
    }

    public void setMax_wlevel(Integer max_wlevel) {
        this.max_wlevel = max_wlevel;
    }

    public Integer getCat_id() {
        return cat_id;
    }

    public void setCat_id(Integer cat_id) {
        this.cat_id = cat_id;
    }
}
