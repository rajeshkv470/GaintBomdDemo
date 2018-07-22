package com.example.gaintbomddemo.network;

import com.example.gaintbomddemo.model.GaintBombResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GaintBombAPI {

    @GET("/api/search/")
    Call<GaintBombResponse> getGaintBombResponse(@Query("api_key") String apiKey,
                                                 @Query("format") String format,
                                                 @Query("query") String query,
                                                 @Query("resources") String resources,
                                                 @Query("page") int page);
}
