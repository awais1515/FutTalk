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
import com.app.futtalk.models.Team;
import com.app.futtalk.utils.TeamLogoUrls;
import com.bumptech.glide.Glide;

import java.util.List;

public class TeamsAdapter extends RecyclerView.Adapter<TeamsAdapter.MyHolder> {

    private Context context;
    private List<Team> teamsList;

    private int rowLayout;
    
    private View.OnClickListener liveFeedClickListener;
    private View.OnClickListener playersClickListener;
    private View.OnClickListener fixturesClickListener;


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
        Team teams = teamsList.get(holder.getAdapterPosition());

        holder.tvTeamName.setText(teams.getName());

        /* Glide.with(context)
                .load(Team.getLogo())
                .centerCrop()
                .into(holder.ivTeamLogo);
        */
        holder.btnLiveFeed.setOnClickListener(liveFeedClickListener);
        holder.btnPlayers.setOnClickListener(playersClickListener);
        holder.btnFixtures.setOnClickListener(fixturesClickListener);

    }

    @Override
    public int getItemCount() {return teamsList.size();}

    public void setLiveFeedClickListener(View.OnClickListener listener) {
        this.liveFeedClickListener = listener;
    }

    public void setPlayersClickListener(View.OnClickListener listener) {
        this.playersClickListener = listener;
    }

    public void setFixturesClickListener(View.OnClickListener listener) {
        this.fixturesClickListener = listener;
    }

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

        }

    }
}
