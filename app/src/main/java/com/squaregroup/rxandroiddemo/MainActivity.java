package com.squaregroup.rxandroiddemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.squaregroup.rxandroiddemo.network.CityResponse;
import com.squaregroup.rxandroiddemo.network.CityService;
import com.squaregroup.rxandroiddemo.network.Geoname;
import com.squaregroup.rxandroiddemo.network.RetrofitHelper;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;


public class MainActivity extends AppCompatActivity {

    private MainActivity activity = this;

    @NonNull
    private CityService mCityService;


     @NonNull
    private CompositeDisposable mCompositeDisposable = new CompositeDisposable();

    private TextView mOutputTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mOutputTextView = (TextView) findViewById(R.id.textViewResult);

        mCityService = new RetrofitHelper().getCityService();

        requestGeonames();

    }

    @Override
    protected void onDestroy() {
        mCompositeDisposable.clear();
        super.onDestroy();
    }

    private void displayGeonames(@NonNull final List<Geoname> geonames) {
        final StringBuilder output = new StringBuilder();
        for (final Geoname geoname : geonames) {
            output.append(geoname.name).append("\n");
        }

        mOutputTextView.setText(output.toString());
    }

    private void requestGeonames() {
        mCompositeDisposable.add(mCityService.queryGeonames(44.1, -9.9, -22.4, 55.2, "de")
                .subscribeOn(Schedulers.io()) // "work" on io thread
                .observeOn(AndroidSchedulers.mainThread()) // "listen" on UIThread
                .map(new Function<CityResponse, List<Geoname>>() {
                    @Override
                    public List<Geoname> apply(
                            @io.reactivex.annotations.NonNull final CityResponse cityResponse)
                            throws Exception {

                        return cityResponse.geonames;
                    }
                })
                .subscribe(new Consumer<List<Geoname>>() {
                    @Override
                    public void accept(
                            @io.reactivex.annotations.NonNull final List<Geoname> geonames)
                            throws Exception {
                        displayGeonames(geonames);
                    }
                })
        );
    }


    public void goNext(View view){
       MyCustomActivity.start(activity);
    }

    public void clickOnOk(View view){
        AndroidVersionActivity.start(activity);

    }
}
