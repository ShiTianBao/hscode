package com.td.model;

public class SearchModel {

    private String hscode;
    private String description;
    private String added;
    private String general;
    private String favor;

    public String getHscode() {
        return hscode;
    }

    public String getDescription() {
        return description;
    }

    public String getAdded() {
        return added;
    }

    public String getFavor() {
        return favor;
    }

    public String getGeneral() {
        return general;
    }

    public void setHscode(String hscode) {
        this.hscode = hscode;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setAdded(String added) {
        this.added = added;
    }

    public void setFavor(String favor) {
        this.favor = favor;
    }

    public void setGeneral(String general) {
        this.general = general;
    }

    @Override
    public String toString() {
        return "SearchModel{" +
                "hscode='" + hscode + '\'' +
                ", description='" + description + '\'' +
                ", add='" + added + '\'' +
                ", favor='" + favor + '\'' +
                '}';
    }
}
