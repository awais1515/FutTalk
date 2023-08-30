package com.app.futtalk.adapters;

import static com.app.futtalk.utils.FirebaseUtils.CURRENT_USER;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.futtalk.R;
import com.app.futtalk.models.FixtureData;
import com.app.futtalk.models.LiveMatch;
import com.app.futtalk.utils.Utils;
import com.bumptech.glide.Glide;

import java.util.List;

public class LiveMatchesAdapter extends RecyclerView.Adapter<LiveMatchesAdapter.MyHolder>{

    private Context context;
    private List<FixtureData> liveMatchList;

    private int rowLayout;


    public LiveMatchesAdapter(Context context, List<FixtureData> liveMatchList, int rowLayout) {
        this.context = context;
        this.rowLayout = rowLayout;
        this.liveMatchList = liveMatchList;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        FixtureData liveMatch = liveMatchList.get(holder.getAdapterPosition());

        holder.tvHomeTeamName.setText(liveMatch.getTeams().getHome().getName());
        holder.tvAwayTeamName.setText(liveMatch.getTeams().getAway().getName());
        holder.tvLiveMinutes.setText(liveMatch.getFixture().getStatus().getElapsed().toString());
        holder.tvDate.setText(Utils.getDateFromTimestamp(liveMatch.getFixture().getDate()));
        holder.tvVenueName.setText(liveMatch.getFixture().getVenue().getName());
        holder.tvLeagueName.setText(liveMatch.getLeague().getName());
        holder.tvScore.setText(liveMatch.getGoals().getHome() + " : " + liveMatch.getGoals().getAway());
        Utils.setPicture(context,holder.ivHomeIcon,liveMatch.getTeams().getHome().getLogo());
        Utils.setPicture(context,holder.ivAwayIcon,liveMatch.getTeams().getAway().getLogo());
    }

    @Override
    public int getItemCount() {
        return liveMatchList.size();
    }

    class MyHolder extends RecyclerView.ViewHolder {
        ImageView ivHomeIcon;
        ImageView ivAwayIcon;
        TextView tvScore, tvDate, tvHomeTeamName, tvAwayTeamName, tvLiveMinutes, tvVenueName, tvLeagueName;

        MyHolder(View itemView) {
            super(itemView);
            ivHomeIcon = itemView.findViewById(R.id.ivHomeIcon);
            ivAwayIcon = itemView.findViewById(R.id.ivAwayIcon);
            tvScore = itemView.findViewById(R.id.tvScore);
            tvDate = itemView.findViewById(R.id.tvDateOfFixture);
            tvHomeTeamName = itemView.findViewById(R.id.tvHomeTeamName);
            tvAwayTeamName = itemView.findViewById(R.id.tvAwayTeamName);
            tvLiveMinutes = itemView.findViewById(R.id.tvLiveMinutes);
            tvVenueName = itemView.findViewById(R.id.tvVenueName);
            tvLeagueName = itemView.findViewById(R.id.tvLeagueName);
        }

    }
}
