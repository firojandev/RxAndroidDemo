package com.example.rxandroiddemo;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.rxandroiddemo.model.Data;
import com.example.rxandroiddemo.utils.ToastUtils;

import io.realm.Realm;
import io.realm.RealmResults;

public class RealmTestActivity extends AppCompatActivity {

    private RealmTestActivity activity = this;
    public static final String TAG = "RealmTestActivity";

    Realm realm;

    public static void start(Activity activity){
        Intent intent = new Intent(activity,RealmTestActivity.class);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_realm_test);
        realm = Realm.getDefaultInstance();

    }

    public void insert(View view){
        Data datas = new Data();

        datas.setId(89);
        datas.setTitle("Altaf");

        realm.beginTransaction();

        realm.insertOrUpdate(datas);

        ToastUtils.shortToast("Data inserted");
    }

    public void save(View view){
      if (realm.isInTransaction()){
          realm.commitTransaction();
          Log.e(TAG,"Saved done");
          ToastUtils.shortToast("Saved done");
      }else{
          ToastUtils.shortToast("Not in transaction mode");
      }
    }

    public void cancel(View view){
        if (realm.isInTransaction()){
            realm.cancelTransaction();
            Log.e(TAG,"Save canceled ");
            ToastUtils.shortToast("Save canceled");
        }else{
            ToastUtils.shortToast("Not in transaction mode");
        }
    }

    public void view(View view){
        Data data =  realm.where(Data.class).findFirst();

        if (data != null){
            Log.e(TAG,"Data Id:"+data.getId()+" name:"+data.getTitle());
            ToastUtils.shortToast("Data Id:"+data.getId()+" name:"+data.getTitle());
        }else{
            Log.e(TAG,"Data null");
            ToastUtils.shortToast("No record found");
        }
    }

    public void delete(View view){
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                RealmResults<Data> result = realm.where(Data.class).equalTo("id",89).findAll();
                result.deleteAllFromRealm();

                ToastUtils.shortToast("Record deleted");
            }
        });
    }

}
