package com.example.myapplication.CropProfile;

import java.util.List;

public class RiceCropModels {
    private List<RicesModel> crops;

    public RiceCropModels() {
    }

    public RiceCropModels(List<RicesModel> crops) {
        this.crops = crops;
    }

    public List<RicesModel> getCrops() {
        return crops;
    }

    public void setCrops(List<RicesModel> crops) {
        this.crops = crops;
    }
}
