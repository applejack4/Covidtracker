package com.example.covid_tracker;

import java.util.ArrayList;

import Models.CoronaModel;
import retrofit2.Call;
import retrofit2.http.GET;

public interface UrlInterface {
    static final String base_url = "https://corona.lmao.ninja/v2/";

    @GET("countries")
    Call<ArrayList<CoronaModel>> get_Country_data();
}
