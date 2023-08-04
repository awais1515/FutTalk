package com.app.futtalk.adapters;

import android.content.Context;
import android.content.Intent;
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
import com.app.futtalk.activties.ViewRepliesActivity;
import com.app.futtalk.models.Comment;
import com.app.futtalk.models.LiveMatch;
import com.app.futtalk.models.User;
import com.app.futtalk.utils.DbReferences;
import com.app.futtalk.utils.FirebaseUtils;
import com.app.futtalk.utils.Utils;
import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class CommentsAdapter extends RecyclerView.Adapter<CommentsAdapter.MyHolder>{

    private Context context;

    private int rowLayout;

    private List<Comment> commentList;



    public CommentsAdapter(Context context, List<Comment> commentList, int rowLayout) {
        this.context = context;
        this.rowLayout = rowLayout;
        this.commentList = commentList;
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
        holder.etComment.setText(comment.getText());
        holder.tvTimeAgo.setText(Utils.getTimeAgo(comment.getDateTime()));
       // holder.tvViewReplies.setText("View replies");
        FirebaseDatabase.getInstance().getReference(DbReferences.USERS).child(comment.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user= snapshot.getValue(User.class);
                holder.tvUsername.setText(user.getName());
                Utils.setPicture(context, holder.ivProfilePicture, user.getProfileUrl());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        holder.tvViewReplies.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(context, ViewRepliesActivity.class);
                context.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {return commentList.size();}

    class MyHolder extends RecyclerView.ViewHolder {

        ImageView ivProfilePicture;

        TextView tvTimeAgo, tvUsername;
        RelativeLayout tvViewReplies;

        EditText etComment;

        MyHolder(View itemView) {
            super(itemView);
            ivProfilePicture = itemView.findViewById(R.id.iv_profile_picture);
            tvUsername = itemView.findViewById(R.id.tvProfileName);
            etComment = itemView.findViewById(R.id.Comment_text);
            tvViewReplies = itemView.findViewById(R.id.btnViewReplies);
            tvTimeAgo = itemView.findViewById(R.id.timePassed);
        }

    }

}
