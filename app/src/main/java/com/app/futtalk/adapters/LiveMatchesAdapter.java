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
import com.app.futtalk.models.LiveMatch;
import com.bumptech.glide.Glide;

import java.util.List;

public class LiveMatchesAdapter extends RecyclerView.Adapter<LiveMatchesAdapter.MyHolder>{

    private Context context;
    private List<LiveMatch> liveMatchList;


    public LiveMatchesAdapter(Context context, List<LiveMatch> liveMatchList) {
        this.context = context;
        this.liveMatchList = liveMatchList;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_view_live_match_home, parent, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        LiveMatch liveMatch = liveMatchList.get(holder.getAdapterPosition());

        holder.tvHomeTeamName.setText(liveMatch.getHomeTeam().getName());
        holder.tvAwayTeamName.setText(liveMatch.getAwayTeam().getName());
        holder.tvLiveMinutes.setText(liveMatch.getMinutes() + "'");
        holder.tvDate.setText(liveMatch.getDate());
        holder.tvVenueName.setText(liveMatch.getVenue());
        holder.tvLeagueName.setText(liveMatch.getLeagueName());
        holder.tvScore.setText(liveMatch.getHomeTeamScore() + " : " + liveMatch.getAwayTeamScore());
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
