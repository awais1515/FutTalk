package com.app.futtalk.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.app.futtalk.R;
import com.app.futtalk.activties.CommentsActivity;
import com.app.futtalk.activties.SendReportActivity;
import com.app.futtalk.models.FeedPost;
import com.app.futtalk.models.StoryTypes;
import com.app.futtalk.models.Team;
import com.app.futtalk.models.User;
import com.app.futtalk.utils.DataHelper;
import com.app.futtalk.utils.References;
import com.app.futtalk.utils.FirebaseUtils;
import com.app.futtalk.utils.Utils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.ui.StyledPlayerView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;


import java.util.List;

public class FeedAdapter extends RecyclerView.Adapter<FeedAdapter.MyHolder> {
    private Context context;
    private List<FeedPost> feedPosts;
    private Team team;
    private int rowLayout;
   // private ExoPlayer player;


    public FeedAdapter(Context context, Team team, List<FeedPost> feedPosts, ExoPlayer player, int rowLayout) {
        this.context = context;
        this.rowLayout = rowLayout;
        this.feedPosts = feedPosts;
      //  this.player = player;
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

        if(feedPost.getStoryType() == StoryTypes.VideoStory) {
            // Release the previous player if any
            holder.releasePlayer();
            // Initialize and set up the player
            holder.initializePlayer();
            holder.preparePlayer(feedPost.getStoryVideoURL());
        }


        holder.tvStory.setText(feedPost.getText());
        holder.tvTimeAgo.setText(Utils.getTimeAgo(feedPost.getDateTime()));
        holder.tvLikes.setText(String.valueOf(feedPost.getLikes().size()));
        holder.tvComments.setText(String.valueOf(feedPost.getComments().size()));

        if (feedPost.getStoryType() == StoryTypes.PictureStory) {
            holder.rlAttachmentContainer.setVisibility(View.VISIBLE);
            holder.ivStoryImage.setVisibility(View.VISIBLE);
            holder.ivPlay.setVisibility(View.GONE);
            holder.switchFeatured.setVisibility(View.GONE);
        } else if (feedPost.getStoryType() == StoryTypes.VideoStory) {
            holder.rlAttachmentContainer.setVisibility(View.VISIBLE);
            holder.ivStoryImage.setVisibility(View.VISIBLE);
            holder.ivPlay.setVisibility(View.VISIBLE);
            if (DataHelper.isAdmin()) {
                holder.switchFeatured.setVisibility(View.VISIBLE);
            }
        } else {
            holder.rlAttachmentContainer.setVisibility(View.GONE);
            holder.switchFeatured.setVisibility(View.GONE);
        }

        if (DataHelper.isAdmin() || DataHelper.isOwner(feedPost.getUid())) {
            holder.ivDelete.setVisibility(View.VISIBLE);
            holder.ivDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showConfirmationDialog(feedPost, holder.getAbsoluteAdapterPosition());
                }
            });
        } else {
            holder.ivDelete.setVisibility(View.GONE);
        }

        if (feedPost.getStoryType() == StoryTypes.PictureStory || feedPost.getStoryType() == StoryTypes.VideoStory) {
            //Utils.setPicture(context, holder.ivStoryImage, feedPost.getStoryImageURL());
            if (feedPost.getStoryImageURL() != null && !feedPost.getStoryImageURL().isEmpty()) {
                Glide.with(context)
                        .load(feedPost.getStoryImageURL())
                        .addListener(new RequestListener<Drawable>() {
                            @Override
                            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                return false;
                            }

                            @Override
                            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                holder.ivStoryImage.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                                    @Override
                                    public void onGlobalLayout() {
                                        holder.ivStoryImage.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                                        int height = holder.ivStoryImage.getHeight();
                                        holder.playerView.setMinimumHeight(height);
                                        Log.d("abc", "Height = " + height);
                                    }
                                });
                                return false;
                            }
                        })
                        .into(holder.ivStoryImage);
            }
        }

        holder.ivPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (feedPost.getStoryType() == StoryTypes.VideoStory) {
                    holder.ivStoryImage.setVisibility(View.INVISIBLE);
                    holder.ivPlay.setVisibility(View.GONE);
                    holder.playerView.setVisibility(View.VISIBLE);
                    holder.handlePlayClick();
                }
            }
        });

        holder.tvReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, SendReportActivity.class);
                intent.putExtra("post",feedPost);
                context.startActivity(intent);
            }
        });

        holder.switchFeatured.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    FirebaseDatabase.getInstance().getReference(References.FEATURED_POSTS).child(feedPost.getId()).setValue(feedPost);
                } else {
                    FirebaseDatabase.getInstance().getReference(References.FEATURED_POSTS).child(feedPost.getId()).removeValue();
                }
            }
        });

        FirebaseDatabase.getInstance().getReference(References.USERS).child(feedPost.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);
                holder.tvUserName.setText(user.getName());
                Utils.setPicture(context, holder.ivUserPicture, user.getProfileUrl());
                // Utils.setVideo(context, holder.ivUserPicture, user.getProfileUrl());
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
                FirebaseDatabase.getInstance().getReference(References.FEED).child(team.getName()).child(feedPost.getId()).setValue(feedPost);
            }
        });
    }


    private void showConfirmationDialog(FeedPost feedPost, int index) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage("Are you sure you want to delete this post");

        // Add buttons
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                FirebaseDatabase.getInstance().getReference(References.FEED).child(team.getName()).child(feedPost.getId()).removeValue();
                feedPosts.remove(index);
                notifyDataSetChanged();
                if (feedPost.getStoryType() == StoryTypes.TextStory) {
                    // we have to do nothing here
                }
                else if (feedPost.getStoryType() == StoryTypes.PictureStory) {
                    FirebaseStorage.getInstance().getReference(References.STORAGE_PICTURES).child(feedPost.getId()).delete();
                } else {
                    FirebaseStorage.getInstance().getReference(References.STORAGE_PICTURES).child(feedPost.getId()).delete();
                    FirebaseStorage.getInstance().getReference(References.STORAGE_VIDEOS).child(feedPost.getId()).delete();
                    FirebaseDatabase.getInstance().getReference(References.FEATURED_POSTS).child(feedPost.getId()).removeValue();

                }
            }
        });


        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Do nothing or handle the cancel action
                dialog.dismiss();
            }
        });

        // Create and show the dialog
        AlertDialog dialog = builder.create();
        dialog.show();
    }



    @Override
    public int getItemCount() {
        return feedPosts.size();
    }

    class MyHolder extends RecyclerView.ViewHolder {
        ImageView ivUserPicture;
        TextView tvTimeAgo, tvUserName, tvStory, tvLikes, tvComments;
        ImageView ivStoryImage, ivPlay, ivDelete, tvReport;
        StyledPlayerView playerView;
        RelativeLayout rlAttachmentContainer;
        Switch switchFeatured;
        private ExoPlayer player;

        MyHolder(View itemView) {
            super(itemView);
            ivUserPicture = itemView.findViewById(R.id.ivProfilePic);
            tvTimeAgo = itemView.findViewById(R.id.tvTimeAgo);
            tvUserName = itemView.findViewById(R.id.tvName);
            tvStory = itemView.findViewById(R.id.tvStory);
            tvLikes = itemView.findViewById(R.id.tvLikes);
            tvComments = itemView.findViewById(R.id.tvComments);
            ivStoryImage = itemView.findViewById(R.id.ivThumbnail);
            rlAttachmentContainer = itemView.findViewById(R.id.rl_attachment_container);
            ivPlay = itemView.findViewById(R.id.ic_play);
            playerView = itemView.findViewById(R.id.player_view);
            switchFeatured = itemView.findViewById(R.id.switchFeatured);
           // tvDelete = itemView.findViewById(R.id.tvDelete);
            ivDelete = itemView.findViewById(R.id.delete_your_post);
            tvReport = itemView.findViewById(R.id.tvReport);
        }

        public void initializePlayer() {
            if (player == null) {
                player = new ExoPlayer.Builder(context).build();
                playerView.setPlayer(player);
            }
        }

        public void preparePlayer(String videoUrl) {
            MediaItem mediaItem = MediaItem.fromUri(videoUrl);
            player.setMediaItem(mediaItem);
            player.prepare();
            player.setPlayWhenReady(false);
        }

        public void releasePlayer() {
            if (player != null) {
                player.release();
                player = null;
            }
        }

        public void handlePlayClick() {
            if (player != null) {
                if (player.getPlayWhenReady()) {
                    player.setPlayWhenReady(false); // Pause the video
                } else {
                    player.setPlayWhenReady(true); // Play the video
                }
            }
        }


    }


}
