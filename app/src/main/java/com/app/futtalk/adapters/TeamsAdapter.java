package com.app.futtalk.adapters;

import static com.app.futtalk.utils.DbReferences.USERS;
import static com.app.futtalk.utils.FirebaseUtils.CURRENT_USER;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.futtalk.R;
import com.app.futtalk.activties.FixturesActivity;
import com.app.futtalk.activties.FeedPostActivity;
import com.app.futtalk.activties.PlayersActivity;
import com.app.futtalk.models.Team;
import com.app.futtalk.utils.DbReferences;
import com.app.futtalk.utils.Utils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class TeamsAdapter extends RecyclerView.Adapter<TeamsAdapter.MyHolder> {

    private Context context;
    private List<Team> teamsList;
    private int rowLayout;

    public TeamsAdapter(Context context, List<Team> teamsList, int rowLayout) {
        this.context = context;
        this.rowLayout = rowLayout;
        this.teamsList = teamsList;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent, false);
        return new MyHolder(view);    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        Team team = teamsList.get(holder.getAdapterPosition());

        holder.tvTeamName.setText(team.getName());
        Utils.setPicture(context, holder.ivTeamLogo, team.getLogo());
        holder.btnLiveFeed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, FeedPostActivity.class);
                intent.putExtra("team", team);
                context.startActivity(intent);
            }
        });
        holder.btnPlayers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, PlayersActivity.class);
                intent.putExtra("team", team);
                context.startActivity(intent);
            }
        });
        holder.btnFixtures.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, FixturesActivity.class);
                intent.putExtra("team", team);
                context.startActivity(intent);
            }
        });

        holder.ivFavoriteTeamButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage("are you sure you want to unfollow " + team.getName() + " ?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                ProgressDialog progressDialog = Utils.getProgressDialog(context, "Removing");
                                FirebaseDatabase.getInstance().getReference(USERS).child(CURRENT_USER.getId()).child(DbReferences.FOLLOW_TEAMS).child(String.valueOf(team.getId())).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {

                                        progressDialog.dismiss();
                                        if (!task.isSuccessful()) {
                                            Toast.makeText(context, "Failed to remove the team", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();

            }
        });
    }

    @Override
    public int getItemCount() {
        return teamsList.size();
    }
    class MyHolder extends RecyclerView.ViewHolder {
        public View btnLiveFeed;
        public View btnPlayers;
        public View btnFixtures;
        ImageView ivTeamLogo, ivFavoriteTeamButton;

        TextView tvTeamName;

        MyHolder(View itemView) {
            super(itemView);
            ivTeamLogo = itemView.findViewById(R.id.iv_league_logo);
            tvTeamName = itemView.findViewById(R.id.tv_league_name);
            btnLiveFeed = itemView.findViewById(R.id.btnLiveFeed);
            btnPlayers = itemView.findViewById(R.id.btnPlayers);
            btnFixtures = itemView.findViewById(R.id.btnFixtures);
            ivFavoriteTeamButton= itemView.findViewById(R.id.btnUnfollowLeague);
        }

    }
}
