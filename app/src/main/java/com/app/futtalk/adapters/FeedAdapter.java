package com.app.futtalk.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.futtalk.R;
import com.app.futtalk.activties.CommentsActivity;
import com.app.futtalk.models.FeedPost;
import com.app.futtalk.models.Team;
import com.app.futtalk.models.User;
import com.app.futtalk.utils.DbReferences;
import com.app.futtalk.utils.FirebaseUtils;
import com.app.futtalk.utils.Utils;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class FeedAdapter extends RecyclerView.Adapter<FeedAdapter.MyHolder>{
    private Context context;
    private List<FeedPost> feedPosts;
    private Team team;
    private int rowLayout;


    public FeedAdapter(Context context, Team team, List<FeedPost> feedPosts, int rowLayout) {
        this.context = context;
        this.rowLayout = rowLayout;
        this.feedPosts = feedPosts;
        this.team = team;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        FeedPost feedPost = feedPosts.get(holder.getAdapterPosition());
        holder.tvStory.setText(feedPost.getText());
        holder.tvTimeAgo.setText(Utils.getTimeAgo(feedPost.getDateTime()));
        holder.tvLikes.setText(String.valueOf(feedPost.getLikes().size()));
        holder.tvComments.setText(String.valueOf(feedPost.getComments().size()));
        FirebaseDatabase.getInstance().getReference(DbReferences.USERS).child(feedPost.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);
                holder.tvUserName.setText(user.getName());
                Utils.setPicture(context, holder.ivUserPicture, user.getProfileUrl());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        holder.tvComments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, CommentsActivity.class);
                intent.putExtra("team", team);
                intent.putExtra("feedPost", feedPost);
                context.startActivity(intent);
            }
        });

        holder.tvLikes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // this user has already liked that post and now he wants to unlike it
                if (feedPost.getLikes().contains(FirebaseUtils.CURRENT_USER.getId())) {
                    feedPost.getLikes().remove(FirebaseUtils.CURRENT_USER.getId());
                    notifyDataSetChanged();
                } else {
                    // this user id isn't in the likes list so here he wants to like the post
                    feedPost.getLikes().add(FirebaseUtils.CURRENT_USER.getId());
                    notifyDataSetChanged();
                }
                FirebaseDatabase.getInstance().getReference(DbReferences.FEED).child(team.getName()).child(feedPost.getId()).setValue(feedPost);
            }
        });
    }

    @Override
    public int getItemCount() {
        return feedPosts.size();
    }

    class MyHolder extends RecyclerView.ViewHolder {
        ImageView ivUserPicture;
        TextView tvTimeAgo, tvUserName, tvStory, tvLikes, tvComments;
        MyHolder(View itemView) {
            super(itemView);
            ivUserPicture = itemView.findViewById(R.id.ivProfilePic);
            tvTimeAgo = itemView.findViewById(R.id.tvTimeAgo);
            tvUserName = itemView.findViewById(R.id.tvName);
            tvStory = itemView.findViewById(R.id.tvStory);
            tvLikes = itemView.findViewById(R.id.tvLikes);
            tvComments = itemView.findViewById(R.id.tvComments);
        }

    }
}
