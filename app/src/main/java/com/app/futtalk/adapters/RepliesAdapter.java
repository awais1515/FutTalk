package com.app.futtalk.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.futtalk.R;
import com.app.futtalk.models.Reply;
import com.app.futtalk.models.User;
import com.app.futtalk.utils.DbReferences;
import com.app.futtalk.utils.Utils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
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
        Reply reply= repliesList.get(holder.getAdapterPosition());
        holder.etReply.setText(reply.getText());
        holder.tvTimeAgo.setText(Utils.getTimeAgo(reply.getDateTime()));


        FirebaseDatabase.getInstance().getReference(DbReferences.USERS).child(reply.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user= snapshot.getValue(User.class);
                holder.tvUsername.setText(user.getName());
                Utils.setPicture(context, holder.ivProfilePicture, user.getProfileUrl());}

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    @Override
    public int getItemCount() {return repliesList.size();}

    class MyHolder extends RecyclerView.ViewHolder {

        ImageView ivProfilePicture;

        TextView tvTimeAgo, tvUsername;

        EditText etReply;

        MyHolder(View itemView) {
            super(itemView);
            ivProfilePicture = itemView.findViewById(R.id.iv_profile_picture);
            tvUsername = itemView.findViewById(R.id.tvProfileName);
            etReply = itemView.findViewById(R.id.Reply_text);
            tvTimeAgo = itemView.findViewById(R.id.timePassed);
        }

    }


}
