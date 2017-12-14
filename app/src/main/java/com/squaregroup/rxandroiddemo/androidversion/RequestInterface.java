package com.squaregroup.rxandroiddemo.androidversion;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;

/**
 * Created by altaf.sil on 12/14/17.
 */

public interface RequestInterface {
    @GET("5409b6cc-e08e-11e7-8b56-1394d3b6c86a/")
   // Observable<List<Android>> getVersionList();
    Observable<DataResponse> getResponse();
}
