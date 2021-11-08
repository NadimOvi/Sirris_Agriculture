package com.example.myapplication.DashboardSpinnerModel;

import java.util.List;

public class MainObjectField {
    private List<Object> fields;

    public MainObjectField(List<Object> fields) {
        this.fields = fields;
    }

    public MainObjectField() {
    }

    public List<Object> getFields() {
        return fields;
    }

    public void setFields(List<Object> fields) {
        this.fields = fields;
    }
}
