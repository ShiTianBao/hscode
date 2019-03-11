package com.td.model;

public class SelectModel {

    private String hscode;
    private String description;

    public String getHscode() {
        return hscode;
    }

    public void setHscode(String hscode) {
        this.hscode = hscode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "SelectModel{" +
                "hscode='" + hscode + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
