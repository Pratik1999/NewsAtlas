package com.example.sunny.maps1.Retrofit;

import com.example.sunny.maps1.models.NewsPojo;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;


public interface EndPoints {
    String s="top-headlines?language=en&apiKey=663e5e8230274775adb63973e2057557";
    @GET(s)
    Call<NewsPojo> getAllNews(@Query("country") String country);

}
