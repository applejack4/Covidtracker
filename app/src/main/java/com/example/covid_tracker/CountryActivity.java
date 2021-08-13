package com.example.covid_tracker;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import Models.CoronaModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CountryActivity extends AppCompatActivity {
    ArrayList<CoronaModel> list;
    RecyclerView recyclerView;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_country);
        list = new ArrayList<>();
        recyclerView = (RecyclerView) findViewById(R.id.Recycler_view_Countries);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading");
        progressDialog.setCancelable(false);
        progressDialog.show();


        CountryAdapter countryAdapter = new CountryAdapter(getApplicationContext(), list);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(countryAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        ApiInterface.geturlInterface().get_Country_data().enqueue(new Callback<ArrayList<CoronaModel>>() {
            @Override
            public void onResponse(Call<ArrayList<CoronaModel>> call, Response<ArrayList<CoronaModel>> response) {
                list.clear();
                list.addAll(response.body());
                countryAdapter.notifyDataSetChanged();
                progressDialog.dismiss();

            }

            @Override
            public void onFailure(Call<ArrayList<CoronaModel>> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(CountryActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}