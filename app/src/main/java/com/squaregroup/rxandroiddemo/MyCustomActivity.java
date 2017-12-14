package com.squaregroup.rxandroiddemo;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.squaregroup.rxandroiddemo.custom.Geoname2;
import com.squaregroup.rxandroiddemo.custom.MyDataResponse;
import com.squaregroup.rxandroiddemo.custom.MyDataService;
import com.squaregroup.rxandroiddemo.custom.MyRetrofitHelper;
import com.squaregroup.rxandroiddemo.custom.UserModel;
import com.squaregroup.rxandroiddemo.network.Geoname;


import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class MyCustomActivity extends AppCompatActivity {

    private MyCustomActivity activity = this;

    @NonNull
    private MyDataService myDataService;

    @NonNull
    private CompositeDisposable mCompositeDisposable = new CompositeDisposable();

    private TextView mOutputTextView;



    public static void start(Activity activity){
        Intent intent = new Intent(activity,MyCustomActivity.class);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_custom);

        mOutputTextView = (TextView) findViewById(R.id.textViewResult);

        myDataService = new MyRetrofitHelper().getMyDataService();

        requestMyData();

    }

    @Override
    protected void onDestroy() {
        mCompositeDisposable.clear();
        super.onDestroy();
    }


    private void requestMyData() {
        mCompositeDisposable.add(myDataService.queryMyData()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Function<MyDataResponse, List<Geoname2>>() {
                    @Override
                    public List<Geoname2> apply(
                            @io.reactivex.annotations.NonNull final MyDataResponse myDataResponse)
                            throws Exception {

                        Log.e("myDataResponse",""+myDataResponse.toString());


                        return myDataResponse.geonames;
                    }
                })
                .subscribe(new Consumer<List<Geoname2>>() {
                    @Override
                    public void accept(
                            @io.reactivex.annotations.NonNull final List<Geoname2> userModels)
                            throws Exception {
                        displayUsers(userModels);
                    }
                })
        );
    }

    private void displayUsers(@NonNull final List<Geoname2> userModels) {
        final StringBuilder output = new StringBuilder();
        for (final Geoname2 userModel : userModels) {
            output.append(userModel.name).append("\n");
        }

        mOutputTextView.setText(output.toString());
    }

}
