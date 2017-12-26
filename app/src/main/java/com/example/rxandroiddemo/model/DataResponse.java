package com.example.rxandroiddemo.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import io.realm.RealmList;
import io.realm.RealmObject;

/**
 * Created by altaf.sil on 12/14/17.
 */

public class DataResponse extends RealmObject {

    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("message")
    @Expose
    private String message;


    @SerializedName("data")
    @Expose
    private RealmList<Android> androidVersionsList = new RealmList<>();


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Android> getAndroidVersionsList() {
        return androidVersionsList;
    }

    public void setAndroidVersionsList(RealmList<Android> androidVersionsList) {
        this.androidVersionsList = androidVersionsList;
    }
}
