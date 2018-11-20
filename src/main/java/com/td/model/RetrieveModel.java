package com.td.model;

public class RetrieveModel {
    private String hscode;
    private String description;

    public String getHscode() {
        return hscode;
    }

    public String getDescription() {
        return description;
    }

    public void setHscode(String hscode) {
        this.hscode = hscode;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "RetrieveModel{" +
                "hscode='" + hscode + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
