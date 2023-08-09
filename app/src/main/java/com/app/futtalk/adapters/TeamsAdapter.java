package com.app.futtalk.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.futtalk.R;
import com.app.futtalk.activties.FixturesActivity;
import com.app.futtalk.activties.FeedPostActivity;
import com.app.futtalk.activties.PlayersActivity;
import com.app.futtalk.models.Team;

import java.util.List;

public class TeamsAdapter extends RecyclerView.Adapter<TeamsAdapter.MyHolder> {

    private Context context;
    private List<Team> teamsList;

    private int rowLayout;



    public TeamsAdapter(Context context, List<Team> teamsList, int rowLayout) {
        this.context = context;
        this.rowLayout = rowLayout;
        this.teamsList = teamsList;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent, false);
        return new MyHolder(view);    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        Team team = teamsList.get(holder.getAdapterPosition());

        holder.tvTeamName.setText(team.getName());

        /* Glide.with(context)
                .load(Team.getLogo())
                .centerCrop()
                .into(holder.ivTeamLogo);
        */
        holder.btnLiveFeed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, FeedPostActivity.class);
                intent.putExtra("team", team);
                context.startActivity(intent);
            }
        });
        holder.btnPlayers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, PlayersActivity.class);
                context.startActivity(intent);
            }
        });
        holder.btnFixtures.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, FixturesActivity.class);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {return teamsList.size();}


    class MyHolder extends RecyclerView.ViewHolder {
        public View btnLiveFeed;
        public View btnPlayers;
        public View btnFixtures;
        ImageView ivTeamLogo;

        TextView tvTeamName;

        MyHolder(View itemView) {
            super(itemView);
            ivTeamLogo = itemView.findViewById(R.id.iv_team_logo);
            tvTeamName = itemView.findViewById(R.id.tv_team_name);
            btnLiveFeed = itemView.findViewById(R.id.btnLiveFeed);
            btnPlayers = itemView.findViewById(R.id.btnPlayers);
            btnFixtures = itemView.findViewById(R.id.btnFixtures);
        }

    }
}
