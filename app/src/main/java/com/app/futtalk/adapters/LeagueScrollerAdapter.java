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
import com.app.futtalk.models.League;
import com.app.futtalk.utils.Utils;

import java.util.List;

public class LeagueScrollerAdapter extends RecyclerView.Adapter<LeagueScrollerAdapter.MyHolder> {

    private Context context;
    private List<League> leaguesScrollerList;
    private int rowLayout;

    public LeagueScrollerAdapter(Context context, List<League> leaguesList, int rowLayout){
        this.context = context;
        this.leaguesScrollerList= leaguesList;
        this.rowLayout= rowLayout;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        League league= leaguesScrollerList.get(holder.getAbsoluteAdapterPosition());

        holder.tvLeagueName.setText(league.getName());
        Utils.setPicture(context, holder.ivLeagueLogo, league.getLogo());

    }

    @Override
    public int getItemCount() {return leaguesScrollerList.size();}


    class MyHolder extends RecyclerView.ViewHolder{
        ImageView ivLeagueLogo;
        TextView tvLeagueName;

        MyHolder(View itemView) {
            super(itemView);
            ivLeagueLogo = itemView.findViewById(R.id.iv_league_logo);
            tvLeagueName = itemView.findViewById(R.id.tv_team_name);
        }

    }

}
