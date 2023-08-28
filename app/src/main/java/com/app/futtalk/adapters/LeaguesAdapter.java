package com.app.futtalk.adapters;

import static com.app.futtalk.utils.DbReferences.USERS;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.futtalk.R;
import com.app.futtalk.models.LeagueInfo;
import com.app.futtalk.utils.DbReferences;
import com.app.futtalk.utils.FirebaseUtils;
import com.app.futtalk.utils.Utils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class LeaguesAdapter extends RecyclerView.Adapter<LeaguesAdapter.MyHolder> {

    private Context context;
    private List<LeagueInfo> leaguesList;
    private boolean isSelectionMode;
    private int rowLayout;


    public LeaguesAdapter(Context context, List<LeagueInfo> leaguesList, int rowLayout, boolean isSelectionMode) {
        this.context = context;
        this.leaguesList = leaguesList;
        this.rowLayout = rowLayout;
        this.isSelectionMode = isSelectionMode;
    }


    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType ) {
        View view = LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        LeagueInfo leagueInfo = leaguesList.get(holder.getAbsoluteAdapterPosition());
        holder.tvLeagueName.setText(leagueInfo.getLeague().getName());
        Utils.setPicture(context, holder.ivLeagueLogo, leagueInfo.getLeague().getLogo());

        if (isSelectionMode) {
            holder.btnFollow.setVisibility(View.VISIBLE);
            holder.btnRemoveLeague.setVisibility(View.GONE);

            holder.btnFollow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ProgressDialog progressDialog = new ProgressDialog(context);
                    progressDialog.setCancelable(false);
                    progressDialog.setTitle("Adding..");
                    progressDialog.show();

                    FirebaseDatabase.getInstance().getReference().child(DbReferences.USERS).child(FirebaseUtils.CURRENT_USER.getId()).child(DbReferences.FOLLOW_LEAGUES).child(String.valueOf(leagueInfo.getLeague().getId())).setValue(leagueInfo).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                progressDialog.dismiss();
                                leaguesList.remove(holder.getAdapterPosition());
                                notifyDataSetChanged();
                            } else {
                                progressDialog.dismiss();
                                Toast.makeText(context, "Failed follow this leagueInfo", Toast.LENGTH_SHORT).show();
                            }


                        }
                    });

                }
            });

        } else {
            holder.btnFollow.setVisibility(View.GONE);
            holder.btnRemoveLeague.setVisibility(View.VISIBLE);

            holder.btnRemoveLeague.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setMessage("are you sure you want to unfollow " + leagueInfo.getLeague().getName() + " ?")
                            .setCancelable(false)
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    ProgressDialog progressDialog = Utils.getProgressDialog(context, "Removing");
                                    FirebaseDatabase.getInstance().getReference(USERS).child(FirebaseUtils.CURRENT_USER.getId()).child(DbReferences.FOLLOW_LEAGUES).child(String.valueOf(leagueInfo.getLeague().getId())).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {

                                            progressDialog.dismiss();
                                            if (!task.isSuccessful()) {
                                                Toast.makeText(context, "Failed to remove leagueInfo", Toast.LENGTH_SHORT).show();
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

            holder.btnFollow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });

        }
    }

    @Override
    public int getItemCount() {
        return leaguesList.size();
    }

    public void setLeaguesList(List<LeagueInfo> leaguesList) {
        this.leaguesList = leaguesList;
        notifyDataSetChanged();
    }

    class MyHolder extends RecyclerView.ViewHolder {
        LinearLayout btnFollow, btnRemoveLeague;
        ImageView ivLeagueLogo;

        TextView tvLeagueName;

        MyHolder(View itemView) {
            super(itemView);
            ivLeagueLogo = itemView.findViewById(R.id.iv_league_logo);
            tvLeagueName = itemView.findViewById(R.id.tv_league_name);
            btnRemoveLeague = itemView.findViewById(R.id.btnUnfollow);
            btnFollow = itemView.findViewById(R.id.btnFollow);

        }
    }

}
