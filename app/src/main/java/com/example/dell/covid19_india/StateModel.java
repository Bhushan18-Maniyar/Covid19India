package com.example.dell.covid19_india;

/**
 * Created by BBM on 08-05-2020.
 */

public class StateModel {
    private String state, confimed, active, recovered, deaths, todaysconfimed, todaysdeath, todaysrecovered ;

    public StateModel() {
    }

    public StateModel(String state, String confirmed, String active, String recovered, String deaths, String todaysconfirmed, String todaysdeath, String todaysrecovered) {

        this.state = state;
        this.confimed = confirmed;
        this.active = active;
        this.recovered = recovered;
        this.deaths = deaths;
        this.todaysconfimed = todaysconfirmed;
        this.todaysdeath = todaysdeath;
        this.todaysrecovered = todaysrecovered;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getConfirmed() {
        return confimed;
    }

    public void setConfirmed(String confimed) {
        this.confimed = confimed;
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

    public String getDeaths() {
        return deaths;
    }

    public void setDeaths(String deaths) {
        this.deaths = deaths;
    }

    public String getTodaysconfirmed() {
        return todaysconfimed;
    }

    public void setTodaysconfimed(String todaysconfimed) {
        this.todaysconfimed = todaysconfimed;
    }

    public String getTodaysdeath() {
        return todaysdeath;
    }

    public void setTodaysdeath(String todaysdeath) {
        this.todaysdeath = todaysdeath;
    }

    public String getTodaysrecovered() {
        return todaysrecovered;
    }

    public void setTodaysrecovered(String todaysrecovered) {
        this.todaysrecovered = todaysrecovered;
    }
}
