package com.example.myapplication.Model;

import java.util.List;

public class MainFieldListShow {
    private List<FieldList>fields;

    public MainFieldListShow(List<FieldList> fields) {
        this.fields = fields;
    }

    public MainFieldListShow() {
    }

    public List<FieldList> getFields() {
        return fields;
    }

    public void setFields(List<FieldList> fields) {
        this.fields = fields;
    }
}
