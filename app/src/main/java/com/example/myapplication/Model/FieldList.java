package com.example.myapplication.Model;

public class FieldList {
    private Integer field_id;
    private String field_name;

    public FieldList() {
    }

    public FieldList(Integer field_id, String field_name) {
        this.field_id = field_id;
        this.field_name = field_name;
    }

    public Integer getField_id() {
        return field_id;
    }

    public void setField_id(Integer field_id) {
        this.field_id = field_id;
    }

    public String getField_name() {
        return field_name;
    }

    public void setField_name(String field_name) {
        this.field_name = field_name;
    }
}
