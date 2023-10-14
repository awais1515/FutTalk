package com.app.futtalk.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.app.futtalk.R;
import com.app.futtalk.models.Reply;
import com.app.futtalk.models.Report;
import com.app.futtalk.models.User;
import com.app.futtalk.utils.References;
import com.app.futtalk.utils.Utils;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class ReportsAdapter extends RecyclerView.Adapter<ReportsAdapter.MyHolder> {

    private Context context;

    private int rowLayout;

    private List<Report> reports;

    public ReportsAdapter(Context context, List<Report> reports, int rowLayout) {
        this.context = context;
        this.rowLayout = rowLayout;
        this.reports = reports;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        Report report= reports.get(holder.getAdapterPosition());
        holder.tvReply.setText(report.getReason());
        holder.tvTimeAgo.setText(Utils.getTimeAgo(report.getDateTime()));


        FirebaseDatabase.getInstance().getReference(References.USERS).child(report.getReporterId()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user= snapshot.getValue(User.class);
                holder.tvUsername.setText(user.getFirstName());
                Utils.setPicture(context, holder.ivProfilePicture, user.getProfileUrl());}

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    @Override
    public int getItemCount() {return reports.size();}

    class MyHolder extends RecyclerView.ViewHolder {

        ImageView ivProfilePicture;

        TextView tvTimeAgo, tvUsername;

        TextView tvReply;

        MyHolder(View itemView) {
            super(itemView);
            ivProfilePicture = itemView.findViewById(R.id.iv_profile_picture);
            tvUsername = itemView.findViewById(R.id.tvProfileName);
            tvReply = itemView.findViewById(R.id.tvReason);
            tvTimeAgo = itemView.findViewById(R.id.tvTimePassed);
        }

    }


}
