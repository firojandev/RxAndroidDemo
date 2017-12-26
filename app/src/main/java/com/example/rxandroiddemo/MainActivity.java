package com.example.rxandroiddemo;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.rxandroiddemo.adapter.MyDataAdapter;
import com.example.rxandroiddemo.network.AndroidRetrofitHelper;
import com.example.rxandroiddemo.model.DataResponse;
import com.example.rxandroiddemo.network.RequestService;
import com.example.rxandroiddemo.utils.MyLog;
import com.example.rxandroiddemo.utils.ToastUtils;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;

import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    private final static String TAG = "MainActivity";

    private MainActivity activity = this;

    private CompositeDisposable mCompositeDisposable;

    private RequestService requestService;

    private TextView mOutputTextView;

    private RecyclerView mRecyclerView;

    private MyDataAdapter myDataAdapter;


    //https://jsonblob.com/9f488cdb-e0af-11e7-8b56-ff10fe99343b
    //https://jsonblob.com/api/9f488cdb-e0af-11e7-8b56-ff10fe99343b

    public static void start(Activity activity){
        Intent intent = new Intent(activity,MainActivity.class);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initialize();

        loadJson();
    }

    public void initialize(){

        mOutputTextView = (TextView) findViewById(R.id.textViewResult);
        mRecyclerView = (RecyclerView) findViewById(R.id.mRecyclerView);

        myDataAdapter = new MyDataAdapter(activity);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(myDataAdapter);


        requestService = new AndroidRetrofitHelper().requestService(activity);

        mCompositeDisposable = new CompositeDisposable();


        //Just test purpose onclick

        mOutputTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TestActivity.start(activity);
            }
        });

    }


    public void loadJson(){

        mCompositeDisposable.add(requestService.getResponse()
                .subscribeOn(Schedulers.io())  // Run on a background thread
                .observeOn(AndroidSchedulers.mainThread()) // Be notified on the main thread
                .subscribeWith(new DisposableObserver<DataResponse>() {
                    @Override
                    public void onComplete() {
                       MyLog.show(TAG,"onComplete");
                    }

                    @Override
                    public void onError(Throwable e) {
                        MyLog.show(TAG,"onError:"+e.toString());
                        mOutputTextView.setText("Error happen! Please try later");
                        ToastUtils.shortToast("Error happen! Please try later");
                    }

                    @Override
                    public void onNext(DataResponse value) {
                        MyLog.show(TAG,"onNext:"+value.toString());
                        parseResponse(value);
                    }
                }));
    }

    private void parseResponse(DataResponse dataResponse) {

        if (dataResponse.getStatus().equals("OK")){

            mOutputTextView.setText(dataResponse.getMessage());

            myDataAdapter.updateList(dataResponse.getAndroidVersionsList());

        }else{
            mOutputTextView.setText(dataResponse.getMessage());
            ToastUtils.shortToast(dataResponse.getMessage());
        }

    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        mCompositeDisposable.clear();
    }
}
