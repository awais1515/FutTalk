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
import com.app.futtalk.utils.Utils;

import java.util.List;

public class FixturesAdapter extends RecyclerView.Adapter<FixturesAdapter.MyHolder>{

    private Context context;
    private List<FixtureData> fixtureDataList;

    private int rowLayout;


    public FixturesAdapter(Context context, List<FixtureData> fixtureDataList, int rowLayout) {
        this.context = context;
        this.rowLayout = rowLayout;
        this.fixtureDataList = fixtureDataList;
    }

    @NonNull
    @Override
    public FixturesAdapter.MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        FixtureData fixtureData = fixtureDataList.get(holder.getAdapterPosition());

        holder.tvHomeTeamName.setText(fixtureData.getTeams().getHome().getName());
        holder.tvAwayTeamName.setText(fixtureData.getTeams().getAway().getName());
        holder.tvTime.setText(Utils.getTimeFromTimestamp(fixtureData.getFixture().getDate(), fixtureData.getFixture().getTimezone()));
        holder.tvDate.setText(Utils.getDateFromTimestamp(fixtureData.getFixture().getDate()));
        holder.tvVenueName.setText(fixtureData.getFixture().getVenue().getName());
        Utils.setPicture(context, holder.ivHomeIcon, fixtureData.getTeams().getHome().getLogo());
        Utils.setPicture(context, holder.ivAwayIcon, fixtureData.getTeams().getAway().getLogo());
    }

    @Override
    public int getItemCount() {
        return fixtureDataList.size();
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
