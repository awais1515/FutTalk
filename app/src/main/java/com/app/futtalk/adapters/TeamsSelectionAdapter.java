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
import com.app.futtalk.fragments.TeamsFragment;
import com.app.futtalk.models.Team;
import com.app.futtalk.utils.Utils;

import java.util.List;

public class TeamsSelectionAdapter extends RecyclerView.Adapter<TeamsSelectionAdapter.MyHolder> {

    private Context context;
    private List<Team> teamsSelectionList;
    private int rowLayout;


    public TeamsSelectionAdapter(Context context, List<Team> teamsSelectionList, int rowLayout) {
        this.context = context;
        this.rowLayout = rowLayout;
        this.teamsSelectionList = teamsSelectionList;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent, false);
        return new TeamsSelectionAdapter.MyHolder(view);     }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        Team team = teamsSelectionList.get(holder.getAdapterPosition());

        holder.tvTeamName.setText(team.getName());
        Utils.setPicture(context, holder.ivTeamLogo, team.getLogo());


        holder.btnPlusAddTeam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


            }
        });
    }

    @Override
    public int getItemCount() {return teamsSelectionList.size();}

    class MyHolder extends RecyclerView.ViewHolder {

        ImageView btnPlusAddTeam;

        ImageView ivTeamLogo;

        TextView tvTeamName;


        MyHolder(View itemView) {
            super(itemView);
            ivTeamLogo = itemView.findViewById(R.id.ivTeamLogo);
            tvTeamName = itemView.findViewById(R.id.tvTeamName);
            btnPlusAddTeam = itemView.findViewById(R.id.btnPlusAddTeam);

        }
    }
}
