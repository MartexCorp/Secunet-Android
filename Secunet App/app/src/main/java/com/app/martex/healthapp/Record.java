package com.app.martex.healthapp;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Martex on 2/12/2018.
 */

public class Record {
    private String date;
    private String hospital;
    private String hash;

    public Record() {}

    public Record(String date, String hospital, String hash) {
        this.date = date;
        this.hospital = hospital;
        this.hash = hash;
    }



    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getHospital() {
        return hospital;
    }

    public void setHospital(String hospital) {
        this.hospital = hospital;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }
}
