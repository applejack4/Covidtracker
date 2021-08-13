package com.example.covid_tracker;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Map;

import Models.CoronaModel;

public class CountryAdapter extends RecyclerView.Adapter<CountryAdapter.CountryViewHolder> {

    ArrayList<CoronaModel> list;
    Context context;

    public CountryAdapter(Context context, ArrayList<CoronaModel> list) {
        this.context = context;
        this.list = list;
    }


    @NonNull
    @org.jetbrains.annotations.NotNull
    @Override
    public CountryViewHolder onCreateViewHolder(@NonNull @org.jetbrains.annotations.NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.countries_item, parent, false);
        return new CountryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @org.jetbrains.annotations.NotNull CountryAdapter.CountryViewHolder holder, int position) {
        CoronaModel model = list.get(position);

        holder.country_cases.setText(NumberFormat.getInstance().format(Integer.parseInt(model.getCases())));
        holder.country_name.setText(model.getCountry());
        holder.sno.setText(String.valueOf(position + 1));

        Map<String, String> img = model.getCountryInfo();
        Glide.with(context).load(img.get("flag")).into(holder.flag);
        System.out.println(list.get(holder.getAdapterPosition()).getCountry() + "This is the country name");

        int pos = holder.getAdapterPosition();
        String name = list.get(pos).getCountry();

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), MainActivity.class);
                intent.putExtra("Country_name", list.get(pos).getCountry());
                System.out.println(list.get(pos).getCountry() + "Scored country name");
                v.getContext().startActivity(intent);
                ((Activity)v.getContext()).finish();
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class CountryViewHolder extends RecyclerView.ViewHolder {
        TextView country_name, sno, country_cases;
        ImageView flag;
        ArrayList<CoronaModel> c_list;


        public CountryViewHolder(@NonNull @org.jetbrains.annotations.NotNull View itemView) {
            super(itemView);

            sno = itemView.findViewById(R.id.sno);
            country_name = itemView.findViewById(R.id.Country_name);
            country_cases = itemView.findViewById(R.id.Country_Cases);
            flag = itemView.findViewById(R.id.Countryflag);
        }
    }
}
