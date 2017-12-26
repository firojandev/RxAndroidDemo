package com.example.rxandroiddemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.rxandroiddemo.model.DataResponse;
import com.example.rxandroiddemo.model.TestUser;
import com.example.rxandroiddemo.network.AndroidRetrofitHelper;
import com.example.rxandroiddemo.network.RequestService;
import com.example.rxandroiddemo.utils.MyLog;
import com.example.rxandroiddemo.utils.ToastUtils;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import io.realm.Realm;

public class StarterActivity extends AppCompatActivity {

    private StarterActivity activity = this;

    public static final String TAG = "StarterActivity";

    Realm realm;

    private CompositeDisposable mCompositeDisposable;
    private RequestService requestService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_starter);

        initialize();
    }

    public void initialize(){
        realm = Realm.getDefaultInstance();
        requestService = new AndroidRetrofitHelper().requestService(activity);
        mCompositeDisposable = new CompositeDisposable();
    }


    public void goNext(View view){
       TestUser testUser = realm.where(TestUser.class).equalTo("name","Altaf").findFirst();

       if (testUser != null){
           MainActivity.start(activity);
       }else{
           connectToServer();
       }

    }


    public void connectToServer(){
        mCompositeDisposable.add(requestService.getUserInfo()
                .subscribeOn(Schedulers.io())  // Run on a background thread
                .observeOn(AndroidSchedulers.mainThread()) // Be notified on the main thread
                .subscribeWith(new DisposableObserver<TestUser>() {
                    @Override
                    public void onComplete() {
                        MyLog.show(TAG,"onComplete");
                    }

                    @Override
                    public void onError(Throwable e) {
                        MyLog.show(TAG,"onError:"+e.toString());
                        ToastUtils.shortToast("Error happen! Please try later");
                    }

                    @Override
                    public void onNext(TestUser testUser) {
                        MyLog.show(TAG,"onNext:"+testUser.getName());
                        if (testUser.getName().equals("Altaf")){
                            parseResponse(testUser);
                            MainActivity.start(activity);
                        }else{
                            ToastUtils.shortToast("Invalid user credentials!");
                        }
                    }
                }));
    }

    public void parseResponse(final TestUser testUser){
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm r) {
                r.insertOrUpdate(testUser);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        mCompositeDisposable.clear();
    }
}
