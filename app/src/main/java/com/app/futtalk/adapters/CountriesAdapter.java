package com.app.futtalk.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.futtalk.R;
import com.app.futtalk.models.Country;
import com.app.futtalk.models.Team;
import com.app.futtalk.utils.Utils;

import java.util.List;

public class CountriesAdapter extends RecyclerView.Adapter<CountriesAdapter.MyHolder> {
    private Context context;
    private List<Country> countryList;
    private int rowLayout;

    public CountriesAdapter(Context context, List<Country> countryList, int rowLayout){
        this.context = context;
        this.countryList = countryList;
        this.rowLayout = rowLayout;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        Country country = countryList.get(holder.getAbsoluteAdapterPosition());

        holder.countryName.setText(country.getName());

        Utils.setPicture(context, holder.countryFlag, country.getFlag());



    }

    @Override
    public int getItemCount() {return countryList.size();}


    class MyHolder extends RecyclerView.ViewHolder {

        ImageView countryFlag;

        TextView countryName;

        MyHolder(View itemView){
            super(itemView);
            countryFlag = itemView.findViewById(R.id.Country_flag);
            countryName = itemView.findViewById(R.id.CountryName);
        }

    }

}
