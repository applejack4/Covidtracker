package com.example.covid_tracker;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import Models.CoronaModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private TextView Total_confirm, Total_active, Total_recovered, Total_deaths, Total_tests, Country_name;
    private TextView Today_Recovered, Today_confirmed, Today_deaths, Today_active, Updated_date;
    private PieChart pieChart;


    ArrayList<CoronaModel> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();

        ApiInterface.geturlInterface().get_Country_data().enqueue(new Callback<ArrayList<CoronaModel>>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(Call<ArrayList<CoronaModel>> call, Response<ArrayList<CoronaModel>> response) {
                list = new ArrayList<>();
                list.addAll(response.body());

                String cn = getIntent().getStringExtra("Country_name");
                Country_name.setText(cn);
                for(int i = 0; i < list.size(); i++){
                 if(list.get(i).getCountry().equals(cn)){
                     Total_active.setText(list.get(i).getActive().toString());
                     Today_active.setText("+" +"("+list.get(i).getTodayCases()+")" +" Cases");
                     Today_deaths.setText("+" +"("+list.get(i).getTodayDeaths().toString()+")" +"Deaths");
                     Total_deaths.setText(list.get(i).getDeaths().toString());
                     Total_recovered.setText(list.get(i).getRecovered().toString());
                     Today_Recovered.setText("+" +"("+list.get(i).getTodayRecovered()+")" +"Recovered");
                     Total_tests.setText(list.get(i).getTests());
                     Total_active.setText(list.get(i).getActive());
                     Total_confirm.setText(list.get(i).getActive());

                     SetText(list.get(i).getUpdated());

                     String confirm = list.get(i).getActive();
                     String active = list.get(i).getActive();
                     String recovered = list.get(i).getRecovered();
                     String deaths  = list.get(i).getDeaths();
                     String test_t = list.get(i).getTests();

                     pieChart.addPieSlice(new PieModel("confirm",Float.parseFloat(confirm) , getResources().getColor(R.color.yellow)));
                     pieChart.addPieSlice(new PieModel("active", Float.parseFloat(active), getResources().getColor(R.color.pie_blue)));
                     pieChart.addPieSlice(new PieModel("recovered", Float.parseFloat(recovered), getResources().getColor(R.color.pie_green)));
                     pieChart.addPieSlice(new PieModel("deaths", Float.parseFloat(deaths), getResources().getColor(R.color.red_pie)));
                     pieChart.addPieSlice(new PieModel("tests", Float.parseFloat(test_t), getResources().getColor(R.color.Orange)));
                     pieChart.startAnimation();
                 }
                }
            }

            @Override
            public void onFailure(Call<ArrayList<CoronaModel>> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Error" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        Country_name.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, CountryActivity.class)));

    }
    void init(){
        Total_confirm = (TextView) findViewById(R.id.Total_confirm);
        Total_active = (TextView) findViewById(R.id.Total_active);
        Total_recovered = (TextView) findViewById(R.id.Total_recovered);
        Total_deaths = (TextView) findViewById(R.id.Total_death);
        Today_Recovered = (TextView) findViewById(R.id.Today_recovered);
        Today_confirmed = (TextView) findViewById(R.id.Today_confirm);
        Today_deaths = (TextView) findViewById(R.id.Today_death);
        Today_active = (TextView) findViewById(R.id.Today_active);
        Total_tests = (TextView) findViewById(R.id.Under_test);
        pieChart = (PieChart) findViewById(R.id.pieChart);
        Updated_date = (TextView) findViewById(R.id.Updated_date);
        Country_name = (TextView) findViewById(R.id.Cname);

    }

    private void SetText(String Updated){
        DateFormat dateFormat = new SimpleDateFormat("MM DD YYYY");

//        long millisecond = Long.parseLong(Updated);

        Calendar calendar = Calendar.getInstance();
//        calendar.setTimeInMillis(millisecond);

        Updated_date.setText("Updated at " + calendar.getTime());

    }

}