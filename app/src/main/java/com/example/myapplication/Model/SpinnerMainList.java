package com.example.myapplication.Model;

import java.util.List;

public class SpinnerMainList {
    private List<SpinnerList> spinnerLists;

    public SpinnerMainList(List<SpinnerList> spinnerLists) {
        this.spinnerLists = spinnerLists;
    }

    public SpinnerMainList() {
    }

    public List<SpinnerList> getSpinnerLists() {
        return spinnerLists;
    }

    public void setSpinnerLists(List<SpinnerList> spinnerLists) {
        this.spinnerLists = spinnerLists;
    }
}
