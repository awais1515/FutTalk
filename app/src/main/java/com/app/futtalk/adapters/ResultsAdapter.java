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
import com.app.futtalk.models.FixtureData;
import com.app.futtalk.models.Results;
import com.app.futtalk.utils.Utils;

import java.util.List;

public class ResultsAdapter extends RecyclerView.Adapter<ResultsAdapter.MyHolder>{

    private Context context;
    private List<FixtureData> resultsList;

    private int rowLayout;


    public ResultsAdapter(Context context, List<FixtureData> resultsList, int rowLayout) {
        this.context = context;
        this.rowLayout = rowLayout;
        this.resultsList = resultsList;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        FixtureData results = resultsList.get(holder.getAdapterPosition());


        holder.tvHomeTeamName.setText(results.getTeams().getHome().getName());
        holder.tvAwayTeamName.setText(results.getTeams().getAway().getName());
        holder.tvDate.setText(Utils.getDateFromTimestamp(results.getFixture().getDate()));
        holder.tvVenueName.setText(results.getFixture().getVenue().getName());
        holder.tvLeagueName.setText(results.getLeague().getName());
        holder.tvScore.setText(results.getGoals().getHome() + " : " + results.getGoals().getAway());
        Utils.setPicture(context,holder.ivHomeIcon,results.getTeams().getHome().getLogo());
        Utils.setPicture(context,holder.ivAwayIcon,results.getTeams().getAway().getLogo());
    }

    @Override
    public int getItemCount() {return resultsList.size();}


    class MyHolder extends RecyclerView.ViewHolder {
        ImageView ivHomeIcon;
        ImageView ivAwayIcon;
        TextView tvDate, tvHomeTeamName, tvAwayTeamName, tvVenueName, tvLeagueName, tvScore;

        MyHolder(View itemView) {
            super(itemView);
            ivHomeIcon = itemView.findViewById(R.id.ivHomeIcon);
            ivAwayIcon = itemView.findViewById(R.id.ivAwayIcon);
            tvDate = itemView.findViewById(R.id.tvDateOfFixture);
            tvHomeTeamName = itemView.findViewById(R.id.tvHomeTeamName);
            tvAwayTeamName = itemView.findViewById(R.id.tvAwayTeamName);
            tvVenueName = itemView.findViewById(R.id.tvVenueName);
            tvLeagueName = itemView.findViewById(R.id.tvLeagueName);
            tvScore = itemView.findViewById(R.id.tvScore);
        }

    }
}
