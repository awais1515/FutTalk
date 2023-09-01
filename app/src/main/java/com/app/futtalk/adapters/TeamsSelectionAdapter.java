package com.app.futtalk.adapters;

import android.app.ProgressDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.futtalk.R;
import com.app.futtalk.fragments.TeamsFragment;
import com.app.futtalk.models.Team;
import com.app.futtalk.utils.DbReferences;
import com.app.futtalk.utils.FirebaseUtils;
import com.app.futtalk.utils.Utils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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
                ProgressDialog progressDialog = new ProgressDialog(context);
                progressDialog.setCancelable(false);
                progressDialog.setTitle("Adding..");
                progressDialog.show();
                FirebaseDatabase.getInstance().getReference().child(DbReferences.USERS).child(FirebaseUtils.CURRENT_USER.getId()).child(DbReferences.FOLLOW_TEAMS).child(String.valueOf(team.getId())).setValue(team).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            progressDialog.dismiss();
                            teamsSelectionList.remove(holder.getAdapterPosition());
                            notifyDataSetChanged();
                        } else {
                            progressDialog.dismiss();
                            Toast.makeText(context, "Failed follow the team", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }

    public void setTeamsSelectionList(List<Team> teamsSelectionList) {
        this.teamsSelectionList = teamsSelectionList;
        notifyDataSetChanged();
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
