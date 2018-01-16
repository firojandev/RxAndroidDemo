package com.example.rxandroiddemo.model;

import io.realm.RealmObject;

/**
 * Created by altaf.sil on 1/4/18.
 */

public class Data extends RealmObject{
    private int id;
    private String title;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
