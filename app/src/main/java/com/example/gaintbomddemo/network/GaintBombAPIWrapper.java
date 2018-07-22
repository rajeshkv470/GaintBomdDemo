package com.example.gaintbomddemo.network;

import com.example.gaintbomddemo.model.GaintBombResponse;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GaintBombAPIWrapper {

    public static final String BASE_URL = "https://www.giantbomb.com/";
    public static final String API_KEY = "4bfb06470219d4d8423ab7ca46b55fb5326afa83";
    private GaintBombAPI gaintBombAPI;
    private static GaintBombAPIWrapper bombAPIWrapper;

    private GaintBombAPIWrapper() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .addInterceptor(interceptor)
                .build();
        Retrofit retrofit = new Retrofit.Builder().addConverterFactory(GsonConverterFactory
                .create()).client(okHttpClient)
                .baseUrl(BASE_URL).build();
        gaintBombAPI = retrofit.create(GaintBombAPI.class);
    }

    public static GaintBombAPIWrapper getInstance() {
        if (bombAPIWrapper == null) {
            bombAPIWrapper = new GaintBombAPIWrapper();
        }
        return bombAPIWrapper;
    }

    /**
     * @param query
     * @param callback
     * @return
     */
    public Call<GaintBombResponse> getGaintResults(String query, int page,
                                                   Callback<GaintBombResponse> callback) {
        Call<GaintBombResponse> gaintBombResponse = gaintBombAPI.getGaintBombResponse(API_KEY,
                "json", query, "game", page);
        gaintBombResponse.enqueue(callback);
        return gaintBombResponse;
    }

}
