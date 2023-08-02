package com.app.futtalk.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.futtalk.R;
import com.app.futtalk.models.Comment;
import com.app.futtalk.models.LiveMatch;
import com.bumptech.glide.Glide;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class CommentsAdapter extends RecyclerView.Adapter<CommentsAdapter.MyHolder>{

    private Context context;

    private int rowLayout;

    private List<Comment> commentList;


    private CircleImageView ivProfilePicture;
    private EditText etComment;
    private TextView tvViewReplies;
    private TextView tvTime;

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
        holder.profileNameTextView.setText(comment.getUid());
        holder.tvViewReplies.setText("View replies");


    }

    @Override
    public int getItemCount() {return commentList.size();}

    class MyHolder extends RecyclerView.ViewHolder {

        ImageView ivProfilePicture;

        TextView profileNameTextView, tvViewReplies;

        EditText etComment;

        MyHolder(View itemView) {
            super(itemView);
            ivProfilePicture = itemView.findViewById(R.id.iv_profile_picture);
            TextView profileNameTextView = itemView.findViewById(R.id.tvProfileName);
            etComment = itemView.findViewById(R.id.Comment_text);
            tvViewReplies = itemView.findViewById(R.id.btnViewReplies);
            CommentsAdapter.this.tvTime = itemView.findViewById(R.id.timePassed);
        }

    }

}
