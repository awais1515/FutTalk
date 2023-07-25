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
import com.app.futtalk.models.Players;
import com.app.futtalk.models.UpcomingMatch;

import java.util.List;

public class PlayersAdapter extends RecyclerView.Adapter<PlayersAdapter.MyHolder>{

    private Context context;
    private List<Players> playersList;
    private int rowLayout;

    public PlayersAdapter(Context context, List<Players> playersList, int rowLayout) {
        this.context = context;
        this.rowLayout = rowLayout;
        this.playersList = playersList;
    }

    @NonNull
    @Override
    public PlayersAdapter.MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PlayersAdapter.MyHolder holder, int position) {
        Players players = playersList.get(holder.getAdapterPosition());

        holder.tvName.setText(players.getName());
        holder.tvNationality.setText(players.getName());
        holder.tvAge.setText(players.getAge());
        holder.tvPosition.setText(players.getPosition());


    }

    @Override
    public int getItemCount() {return playersList.size();}


    class MyHolder extends RecyclerView.ViewHolder {
        ImageView ivplayerImage;

        TextView tvName, tvAge, tvNationality, tvPosition;

        MyHolder(View itemView) {
            super(itemView);
            ivplayerImage = itemView.findViewById(R.id.player_profile_picture);
            tvName = itemView.findViewById(R.id.PlayerName);
            tvAge = itemView.findViewById(R.id.PlayerAge);
            tvNationality= itemView.findViewById(R.id.PlayerNationality);
            tvPosition = itemView.findViewById(R.id.PLayerPosition);
        }

    }
}
