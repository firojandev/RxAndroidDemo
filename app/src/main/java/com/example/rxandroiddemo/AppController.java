package com.example.rxandroiddemo;

import android.app.Application;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by altaf.sil on 12/14/17.
 */

public class AppController extends Application {

    public static final String TAG = AppController.class.getSimpleName();

    private static AppController mInstance;

    @Override
    public void onCreate() {
        super.onCreate();

        mInstance = this;

        Realm.init(this);
        RealmConfiguration config = new RealmConfiguration.Builder().name("firojrx.realm").build();
        Realm.setDefaultConfiguration(config);
    }

    public static synchronized AppController getInstance() {
        return mInstance;
    }

}
