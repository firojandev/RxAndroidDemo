package com.squaregroup.rxandroiddemo;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.squaregroup.rxandroiddemo.androidversion.Android;
import com.squaregroup.rxandroiddemo.androidversion.AndroidRetrofitHelper;
import com.squaregroup.rxandroiddemo.androidversion.RequestInterface;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class AndroidVersionActivity extends AppCompatActivity {

    private CompositeDisposable mCompositeDisposable;

    private RequestInterface requestInterface;

    private TextView mOutputTextView;

    public static void start(Activity activity){
        Intent intent = new Intent(activity,AndroidVersionActivity.class);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_android_version);

        initialize();

        loadJson();
    }

    public void initialize(){

        mOutputTextView = (TextView) findViewById(R.id.textViewResult);

        requestInterface = new AndroidRetrofitHelper().requestService();

        mCompositeDisposable = new CompositeDisposable();
    }

    public void loadJson(){
        mCompositeDisposable.add(requestInterface.getVersionList()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Consumer<List<Android>>() {
                    @Override
                    public void accept(List<Android> androids) throws Exception {
                        handleResponse(androids);
                    }
                }));
    }

    private void handleResponse(List<Android> androidList) {
        final StringBuilder output = new StringBuilder();
        for (final Android android : androidList) {
            output.append(android.name).append("\n");
        }

        mOutputTextView.setText(output.toString());
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        mCompositeDisposable.clear();
    }
}
