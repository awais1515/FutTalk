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
import com.app.futtalk.models.UpcomingMatch;

import java.util.List;

public class FixturesAdapter extends RecyclerView.Adapter<FixturesAdapter.MyHolder>{

    private Context context;
    private List<UpcomingMatch> upcomingMatchList;

    private int rowLayout;


    public FixturesAdapter(Context context, List<UpcomingMatch> upcomingMatchList, int rowLayout) {
        this.context = context;
        this.rowLayout = rowLayout;
        this.upcomingMatchList = upcomingMatchList;
    }

    @NonNull
    @Override
    public FixturesAdapter.MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        UpcomingMatch upcomingMatch = upcomingMatchList.get(holder.getAdapterPosition());

        holder.tvHomeTeamName.setText(upcomingMatch.getHomeTeam().getName());
        holder.tvAwayTeamName.setText(upcomingMatch.getAwayTeam().getName());
        holder.tvTime.setText(upcomingMatch.getTime() + "'");
        holder.tvDate.setText(upcomingMatch.getDate());
        holder.tvVenueName.setText(upcomingMatch.getVenue());
       /* Glide.with(context)
                .load(liveMatch.getHomeTeam().getLogo())
                .centerCrop()
                .into(holder.ivHomeIcon);
        Glide.with(context)
                .load(liveMatch.getAwayTeam().getLogo())
                .centerCrop()
                .into(holder.ivAwayIcon);*/
    }

    @Override
    public int getItemCount() {
        return upcomingMatchList.size();
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
