package com.example.rxandroiddemo;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.rxandroiddemo.model.Android;
import com.example.rxandroiddemo.network.AndroidRetrofitHelper;
import com.example.rxandroiddemo.model.DataResponse;
import com.example.rxandroiddemo.network.RequestService;
import com.example.rxandroiddemo.utils.MyLog;
import com.example.rxandroiddemo.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

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

    List<Android> aLists = new ArrayList<>();

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

        requestService = new AndroidRetrofitHelper().requestService();

        mCompositeDisposable = new CompositeDisposable();
    }

    public void loadJson(){
//Alternate way
//        mCompositeDisposable.add(requestService.getResponse()
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribeOn(Schedulers.io())
//                .map(new Function<DataResponse,DataResponse>() {
//                    @Override
//                    public DataResponse apply(
//                            @io.reactivex.annotations.NonNull final DataResponse dataResponse)
//                            throws Exception {
//
//                        Log.e("DataResponse","apply Called 1");
//
//                        return dataResponse;
//                    }
//                })
//                .subscribe(new Consumer<DataResponse>() {
//                    @Override
//                    public void accept(DataResponse dataResponse) throws Exception {
//                        Log.e("DataResponse","subscribe Called 2");
//
//                        handleResponse(dataResponse);
//                    }
//                }));

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
                        ToastUtils.shortToast("Error happen!. Please try later");
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

            aLists.clear();
            aLists.addAll(dataResponse.getAndroidVersionsList());


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
