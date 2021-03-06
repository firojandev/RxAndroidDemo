package com.example.rxandroiddemo.network;

import com.example.rxandroiddemo.model.DataResponse;
import com.example.rxandroiddemo.model.TestUser;

import io.reactivex.Observable;
import retrofit2.http.GET;

/**
 * Created by altaf.sil on 12/14/17.
 */

public interface RequestService {
    @GET("9f488cdb-e0af-11e7-8b56-ff10fe99343b/")
   // Observable<List<Android>> getVersionList();
    Observable<DataResponse> getResponse();


    @GET("0101282c-ea22-11e7-8f8e-efee6d63ee14/")
    Observable<TestUser> getUserInfo();
}
