package com.example.covid_tracker;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiInterface {

    public static Retrofit retrofit;

    public static UrlInterface geturlInterface(){
        if(retrofit == null){
            retrofit = new Retrofit.Builder().baseUrl(UrlInterface.base_url).addConverterFactory(GsonConverterFactory.create()).build();
        }
        return retrofit.create(UrlInterface.class);
    }

}
