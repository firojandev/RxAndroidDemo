package com.squaregroup.rxandroiddemo;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.squaregroup.rxandroiddemo.model.Android;
import com.squaregroup.rxandroiddemo.network.AndroidRetrofitHelper;
import com.squaregroup.rxandroiddemo.model.DataResponse;
import com.squaregroup.rxandroiddemo.network.RequestService;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    private CompositeDisposable mCompositeDisposable;

    private RequestService requestService;

    private TextView mOutputTextView;

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
        mCompositeDisposable.add(requestService.getResponse()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .map(new Function<DataResponse,DataResponse>() {
                    @Override
                    public DataResponse apply(
                            @io.reactivex.annotations.NonNull final DataResponse dataResponse)
                            throws Exception {

                        Log.e("DataResponse","apply Called 1");

                        return dataResponse;
                    }
                })
                .subscribe(new Consumer<DataResponse>() {
                    @Override
                    public void accept(DataResponse dataResponse) throws Exception {
                        Log.e("DataResponse","subscribe Called 2");

                        handleResponse(dataResponse);
                    }
                }));
    }

    private void handleResponse(DataResponse dataResponse) {

        Log.e("DataResponse","handleResponse Called 3");

        final StringBuilder output = new StringBuilder();
        for (final Android android : dataResponse.getAndroidVersionsList()) {
            output.append(android.getName()).append("\n");
        }

        mOutputTextView.setText(dataResponse.getStatus()+" "+dataResponse.getMessage()+"\n"+output.toString());


    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        mCompositeDisposable.clear();
    }
}
