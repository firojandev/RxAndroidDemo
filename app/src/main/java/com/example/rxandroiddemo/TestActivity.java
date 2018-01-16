package com.example.rxandroiddemo;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.rxandroiddemo.model.TestUser;

import io.realm.Realm;

public class TestActivity extends AppCompatActivity {

    private TestActivity activity = this;

    //Multipart image upload
    //https://futurestud.io/tutorials/retrofit-2-how-to-upload-a-dynamic-amount-of-files-to-server
    
    //http://androidkt.com/sms-retriever-api/

    //Wifi adb : ned to keep same network
    //https://plugins.jetbrains.com/plugin/7983-android-wifi-adb

    Realm realm;

    public static void start(Activity activity){
        Intent intent = new Intent(activity,TestActivity.class);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        initialize();
    }

    public void initialize(){
        realm = Realm.getDefaultInstance();

    }

    public void logout(View view){
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.deleteAll();
                finishAffinity();
            }
        });

    }
}
