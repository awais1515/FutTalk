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

public class LeaguesAdapter extends RecyclerView.Adapter<LeaguesAdapter.MyHolder> {

    private Context context;
    private List<League> leaguesList;
    private boolean isSelectionMode;
    private int rowLayout;


    public LeaguesAdapter(Context context, List<League> leaguesList, int rowLayout, boolean isSelectionMode) {
        this.context = context;
        this.leaguesList = leaguesList;
        this.rowLayout = rowLayout;
        this.isSelectionMode = isSelectionMode;
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

        if (isSelectionMode) {
            // show follow icon
            // hide remove icon
        } else {
            // hide follow icon
            // show remove icon
        }

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
    public int getItemCount() {
        return leaguesList.size();
    }

    class MyHolder extends RecyclerView.ViewHolder {
        public View btnFollow;

        ImageView ivLeagueLogo, ivRemoveLeague;

        TextView tvLeagueName;

        MyHolder(View itemView) {
            super(itemView);
            ivLeagueLogo = itemView.findViewById(R.id.iv_league_logo);
            tvLeagueName = itemView.findViewById(R.id.tv_team_name);
            btnFollow = itemView.findViewById(R.id.btnFollowLeague);
            ivRemoveLeague = itemView.findViewById(R.id.btnUnfollowLeague);

        }
    }

}
