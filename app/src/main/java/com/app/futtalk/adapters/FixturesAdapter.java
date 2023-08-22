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
import com.app.futtalk.models.UpcomingFixture;
import com.app.futtalk.utils.Utils;

import java.util.List;

public class FixturesAdapter extends RecyclerView.Adapter<FixturesAdapter.MyHolder>{

    private Context context;
    private List<UpcomingFixture> upcomingFixtureList;

    private int rowLayout;


    public FixturesAdapter(Context context, List<UpcomingFixture> upcomingFixtureList, int rowLayout) {
        this.context = context;
        this.rowLayout = rowLayout;
        this.upcomingFixtureList = upcomingFixtureList;
    }

    @NonNull
    @Override
    public FixturesAdapter.MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        UpcomingFixture upcomingFixture = upcomingFixtureList.get(holder.getAdapterPosition());

        holder.tvHomeTeamName.setText(upcomingFixture.getTeams().getHome().getName());
        holder.tvAwayTeamName.setText(upcomingFixture.getTeams().getAway().getName());
        holder.tvTime.setText(Utils.getTimeFromTimestamp(upcomingFixture.getFixture().getDate(),upcomingFixture.getFixture().getTimezone()));
        holder.tvDate.setText(Utils.getDateFromTimestamp(upcomingFixture.getFixture().getDate()));
        holder.tvVenueName.setText(upcomingFixture.getFixture().getVenue().getName());
        Utils.setPicture(context, holder.ivHomeIcon, upcomingFixture.getTeams().getHome().getLogo());
        Utils.setPicture(context, holder.ivAwayIcon, upcomingFixture.getTeams().getAway().getLogo());
    }

    @Override
    public int getItemCount() {
        return upcomingFixtureList.size();
    }

    class MyHolder extends RecyclerView.ViewHolder {
        ImageView ivHomeIcon;
        ImageView ivAwayIcon;
        TextView tvDate, tvHomeTeamName, tvAwayTeamName, tvTime, tvVenueName;

        MyHolder(View itemView) {
            super(itemView);
            ivHomeIcon = itemView.findViewById(R.id.ivHomeIcon);
            ivAwayIcon = itemView.findViewById(R.id.ivAwayIcon);
            tvDate = itemView.findViewById(R.id.tvDateOfFixture);
            tvHomeTeamName = itemView.findViewById(R.id.tvHomeTeamName);
            tvAwayTeamName = itemView.findViewById(R.id.tvAwayTeamName);
            tvTime = itemView.findViewById(R.id.tvTime);
            tvVenueName = itemView.findViewById(R.id.tvVenueName);
        }

    }
}
