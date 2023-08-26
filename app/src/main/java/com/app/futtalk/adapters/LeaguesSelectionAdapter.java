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

public class LeaguesSelectionAdapter extends RecyclerView.Adapter<LeaguesSelectionAdapter.MyHolder> {

    private Context context;
    private List<League> leaguesList;
    private int rowLayout;


    public LeaguesSelectionAdapter(Context context, List<League> leaguesList, int rowLayout){
        this.context = context;
        this.leaguesList= leaguesList;
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
        League league = leaguesList.get(holder.getAbsoluteAdapterPosition());
        holder.tvLeagueName.setText(league.getName());
        Utils.setPicture(context, holder.ivLeagueLogo, league.getLogo());

        holder.btnFollow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        holder.ivRemoveLeague.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });


    }

    @Override
    public int getItemCount() {return leaguesList.size();}

    class MyHolder extends RecyclerView.ViewHolder{
        public View btnFollow;

        ImageView ivLeagueLogo, ivRemoveLeague;

        TextView tvLeagueName;

        MyHolder(View itemView) {
            super(itemView);
            ivLeagueLogo = itemView.findViewById(R.id.iv_league_logo);
            tvLeagueName = itemView.findViewById(R.id.tv_league_name);
            btnFollow = itemView.findViewById(R.id.btnFollowLeague);
            ivRemoveLeague = itemView.findViewById(R.id.btnUnfollowLeague);

        }
    }

}
