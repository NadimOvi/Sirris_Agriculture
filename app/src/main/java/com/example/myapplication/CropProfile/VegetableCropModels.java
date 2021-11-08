package com.example.myapplication.CropProfile;

import java.util.List;

public class VegetableCropModels {
    private List<VegetableModels> crops;

    public VegetableCropModels(List<VegetableModels> crops) {
        this.crops = crops;
    }

    public VegetableCropModels() {
    }

    public List<VegetableModels> getCrops() {
        return crops;
    }

    public void setCrops(List<VegetableModels> crops) {
        this.crops = crops;
    }
}
