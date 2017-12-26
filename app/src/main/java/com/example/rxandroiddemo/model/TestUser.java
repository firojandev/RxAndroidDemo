package com.example.rxandroiddemo.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;

/**
 * Created by altaf.sil on 12/14/17.
 */

public class TestUser extends RealmObject{

    @SerializedName("id")
    @Expose
    private int id;


    @SerializedName("name")
    @Expose
    private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
