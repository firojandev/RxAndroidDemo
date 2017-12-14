package com.squaregroup.rxandroiddemo.custom;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by altaf.sil on 12/14/17.
 */

public interface MyDataService {
    @GET("8f26912d-e07e-11e7-8b56-c31299cb4d25")
    Single<MyDataResponse> queryMyData();
}
