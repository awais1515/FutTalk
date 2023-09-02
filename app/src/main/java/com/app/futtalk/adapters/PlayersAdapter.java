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
import com.app.futtalk.models.Player;
import com.app.futtalk.utils.Utils;

import java.util.List;

public class PlayersAdapter extends RecyclerView.Adapter<PlayersAdapter.MyHolder>{

    private Context context;
    private List<Player> playerList;
    private int rowLayout;

    public PlayersAdapter(Context context, List<Player> playerList, int rowLayout) {
        this.context = context;
        this.rowLayout = rowLayout;
        this.playerList = playerList;
    }

    @NonNull
    @Override
    public PlayersAdapter.MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PlayersAdapter.MyHolder holder, int position) {
        Player player = playerList.get(holder.getAdapterPosition());

        holder.tvName.setText(player.getName());
        Utils.setPicture(context, holder.ivplayerImage, player.getPhoto());
        holder.tvNationality.setText(player.getNationality());
        holder.tvAge.setText(String.valueOf(player.getAge()));
        holder.tvPosition.setText(player.getPosition());


    }

    @Override
    public int getItemCount() {return playerList.size();}


    class MyHolder extends RecyclerView.ViewHolder {
        ImageView ivplayerImage;

        TextView tvName, tvAge, tvNationality, tvPosition;

        MyHolder(View itemView) {
            super(itemView);
            ivplayerImage = itemView.findViewById(R.id.player_profile_picture);
            tvName = itemView.findViewById(R.id.PlayerName);
            tvAge = itemView.findViewById(R.id.PlayerAge);
            tvNationality= itemView.findViewById(R.id.PlayerNationality);
            tvPosition = itemView.findViewById(R.id.PlayerPosition);
        }

    }
}
