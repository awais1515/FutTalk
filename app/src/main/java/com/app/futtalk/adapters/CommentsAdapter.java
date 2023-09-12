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
import com.app.futtalk.activties.RepliesActivity;
import com.app.futtalk.models.Comment;
import com.app.futtalk.models.FeedPost;
import com.app.futtalk.models.Team;
import com.app.futtalk.models.User;
import com.app.futtalk.utils.References;
import com.app.futtalk.utils.Utils;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class CommentsAdapter extends RecyclerView.Adapter<CommentsAdapter.MyHolder>{

    private Context context;

    private int rowLayout;

    private List<Comment> commentList;

    private Team team;
    private FeedPost feedPost;



    public CommentsAdapter(Context context, List<Comment> commentList, Team team, FeedPost feedPost, int rowLayout) {
        this.context = context;
        this.rowLayout = rowLayout;
        this.commentList = commentList;
        this.feedPost = feedPost;
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
        Comment comment = commentList.get(holder.getAdapterPosition());
        holder.tvComment.setText(comment.getText());
        holder.tvTimeAgo.setText(Utils.getTimeAgo(comment.getDateTime()));
        holder.tvViewReplies.setText(getTextForReplies(comment.getReplies().size()));
        FirebaseDatabase.getInstance().getReference(References.USERS).child(comment.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);
                holder.tvUsername.setText(user.getFirstName());
                Utils.setPicture(context, holder.ivProfilePicture, user.getProfileUrl());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        holder.tvViewReplies.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(context, RepliesActivity.class);
                intent.putExtra("team", team);
                intent.putExtra("feedPost", feedPost);
                intent.putExtra("comment", comment);
                context.startActivity(intent);
            }
        });


    }

    private String getTextForReplies(int count) {
        if (count <= 1) {
            return count + " Reply";
        } else {
            return count + " Replies";
        }
    }
    @Override
    public int getItemCount() {return commentList.size();}

    class MyHolder extends RecyclerView.ViewHolder {

        ImageView ivProfilePicture;

        TextView tvTimeAgo, tvUsername, tvViewReplies;

        TextView tvComment;

        MyHolder(View itemView) {
            super(itemView);
            ivProfilePicture = itemView.findViewById(R.id.iv_profile_picture);
            tvUsername = itemView.findViewById(R.id.tvProfileName);
            tvComment = itemView.findViewById(R.id.tvCommentText);
            tvViewReplies = itemView.findViewById(R.id.tvRepliesCount);
            tvTimeAgo = itemView.findViewById(R.id.tvTimePassed);
        }

    }

}
