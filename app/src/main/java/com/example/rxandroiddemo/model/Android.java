package com.example.rxandroiddemo.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;

/**
 * Created by altaf.sil on 12/14/17.
 */

public class Android  extends RealmObject{
    @SerializedName("ver")
    @Expose
    private String ver;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("api")
    @Expose
    private String api;

    public String getVer() {
        return ver;
    }

    public void setVer(String ver) {
        this.ver = ver;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getApi() {
        return api;
    }

    public void setApi(String api) {
        this.api = api;
    }
}
