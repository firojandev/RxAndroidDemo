package com.squaregroup.rxandroiddemo.network;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by altaf.sil on 12/14/17.
 */

public interface CityService {
    @GET("citiesJSON")
    Single<CityResponse> queryGeonames(@Query("north") double north, @Query("south") double south,
                                       @Query("east") double east, @Query("west") double west, @Query("lang") String lang);
}
