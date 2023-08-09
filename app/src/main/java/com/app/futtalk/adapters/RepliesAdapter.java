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
import com.app.futtalk.models.User;
import com.app.futtalk.utils.DbReferences;
import com.app.futtalk.utils.Utils;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class RepliesAdapter extends RecyclerView.Adapter<RepliesAdapter.MyHolder> {

    private Context context;

    private int rowLayout;

    private List<Reply> repliesList;

    public RepliesAdapter(Context context, List<Reply> repliesList, int rowLayout) {
        this.context = context;
        this.rowLayout = rowLayout;
        this.repliesList = repliesList;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        if(holder.getAdapterPosition() % 2 == 0) {
            holder.llReplyContainer.setBackground(ContextCompat.getDrawable(context, R.drawable.fixtures_background_light));
            holder.tvReply.setTextColor(ContextCompat.getColor(context, R.color.grey_main));
            holder.tvTimeAgo.setTextColor(ContextCompat.getColor(context, R.color.grey));
        } else {
            holder.llReplyContainer.setBackground(ContextCompat.getDrawable(context, R.drawable.fixtures_background_dark));
            holder.tvReply.setTextColor(ContextCompat.getColor(context, R.color.white));
            holder.tvTimeAgo.setTextColor(ContextCompat.getColor(context, R.color.light_grey));
        }
        Reply reply= repliesList.get(holder.getAdapterPosition());
        holder.tvReply.setText(reply.getText());
        holder.tvTimeAgo.setText(Utils.getTimeAgo(reply.getDateTime()));


        FirebaseDatabase.getInstance().getReference(DbReferences.USERS).child(reply.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
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
    public int getItemCount() {return repliesList.size();}

    class MyHolder extends RecyclerView.ViewHolder {

        LinearLayout llReplyContainer;
        ImageView ivProfilePicture;

        TextView tvTimeAgo, tvUsername;

        TextView tvReply;

        MyHolder(View itemView) {
            super(itemView);
            ivProfilePicture = itemView.findViewById(R.id.iv_profile_picture);
            tvUsername = itemView.findViewById(R.id.tvProfileName);
            tvReply = itemView.findViewById(R.id.Reply_text);
            tvTimeAgo = itemView.findViewById(R.id.tvTimePassed);
            llReplyContainer = itemView.findViewById(R.id.ll_reply_container);
        }

    }


}
