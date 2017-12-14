package com.squaregroup.rxandroiddemo.network;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by altaf.sil on 12/14/17.
 */

public class AndroidRetrofitHelper {
    public static final String BASE_URL = "https://jsonblob.com/api/";

    public RequestService requestService() {
        final Retrofit retrofit = createRetrofit();
        return retrofit.create(RequestService.class);
    }

    private Retrofit createRetrofit() {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }



}
