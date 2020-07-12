package com.example.dell.covid19_india;

/**
 * Created by Dell on 19-06-2020.
 */

public class DistrictModel {

    private String districtName , confirmed , active , recovered , deceased ,todayConfirmed , todaydeceased , todayrecovered;

    public DistrictModel(String districtName, String confirmed, String active, String recovered, String deceased, String todayConfirmed, String todaydeceased, String todayrecovered) {
        this.districtName = districtName;
        this.confirmed = confirmed;
        this.active = active;
        this.recovered = recovered;
        this.deceased = deceased;
        this.todayConfirmed = todayConfirmed;
        this.todaydeceased = todaydeceased;
        this.todayrecovered = todayrecovered;
    }

    public String getDeceased() {
        return deceased;
    }

    public void setDeceased(String deceased) {
        this.deceased = deceased;
    }

    public String getDistrictName() {
        return districtName;
    }

    public String getTodayConfirmed() {
        return todayConfirmed;
    }

    public void setTodayConfirmed(String todayConfirmed) {
        this.todayConfirmed = todayConfirmed;
    }

    public String getTodaydeceased() {
        return todaydeceased;
    }

    public void setTodaydeceased(String todaydeceased) {
        this.todaydeceased = todaydeceased;
    }

    public String getTodayrecovered() {
        return todayrecovered;
    }

    public void setTodayrecovered(String todayrecovered) {
        this.todayrecovered = todayrecovered;
    }

    public void setDistrictName(String districtName) {
        this.districtName = districtName;
    }

    public String getConfirmed() {
        return confirmed;
    }

    public void setConfirmed(String confirmed) {
        this.confirmed = confirmed;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

    public String getRecovered() {
        return recovered;
    }

    public void setRecovered(String recovered) {
        this.recovered = recovered;
    }
}
