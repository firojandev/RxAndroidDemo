package com.squaregroup.rxandroiddemo.custom;

/**
 * Created by altaf.sil on 12/14/17.
 */

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class MyRetrofitHelper {

    public MyDataService getMyDataService() {
        final Retrofit retrofit = createRetrofit();
        return retrofit.create(MyDataService.class);
    }


    private OkHttpClient createOkHttpClient() {
        final OkHttpClient.Builder httpClient =
                new OkHttpClient.Builder();
        httpClient.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                final Request original = chain.request();
                final HttpUrl originalHttpUrl = original.url();

                final HttpUrl url = originalHttpUrl.newBuilder()
                        .build();

                // Request customization: add request headers
                final Request.Builder requestBuilder = original.newBuilder()
                        .url(url);

                final Request request = requestBuilder.build();
                return chain.proceed(request);
            }
        });

        return httpClient.build();
    }

    private Retrofit createRetrofit() {

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        return new Retrofit.Builder()
                .baseUrl("https://jsonblob.com/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(createOkHttpClient())
                .build();
    }
}
