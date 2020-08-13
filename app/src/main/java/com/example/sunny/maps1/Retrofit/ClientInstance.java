package com.example.sunny.maps1.Retrofit;


import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ClientInstance {
   private static Retrofit retrofit;
    private static String BASE_URL="https://newsapi.org/v2/";
    public static Retrofit getRetrofit()
    {
        if(retrofit==null)
        {
            retrofit=new retrofit2.Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }



}
