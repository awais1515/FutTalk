package com.app.futtalk.activties;

import static com.app.futtalk.utils.FirebaseUtils.CURRENT_USER;

import static java.io.File.createTempFile;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;

import com.app.futtalk.R;
import com.app.futtalk.models.FeedPost;
import com.app.futtalk.models.StoryTypes;
import com.app.futtalk.models.Team;
import com.app.futtalk.utils.DbReferences;
import com.app.futtalk.utils.Utils;
import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;

public class AddPostActivity extends BaseActivity {

    private Context context;
    private EditText etStory;
    private TextView tvTeamName;
    private CircleImageView ivProfilePic;
    private Button btnPublish;
    private Team team;
    private ImageView ivSelectPicture;
    private ImageView ivSelectVideo;
    private ImageView ivThumbnail;
    private ImageView ivPlay;
    private RelativeLayout rlAttachmentContainer;
    private RelativeLayout rlVideoContainer;
    private ActivityResultLauncher<Intent> imageUriFromGallery;
    private ActivityResultLauncher<Intent> videoUriFromGallery;

    private String mimeType;

    private Uri imageFilePath;

    private Uri videoFilePath;
    private VideoView videoView;
    private StoryTypes storyType = StoryTypes.TextStory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_post);
        team = (Team) getIntent().getSerializableExtra("team");
        init();
        setData();
        setListeners();
    }

    private void init() {
        context = this;
        tvTeamName = findViewById(R.id.tv_league_name);
        etStory = findViewById(R.id.etStory);
        ivProfilePic = findViewById(R.id.ivProfilePic);
        btnPublish = findViewById(R.id.btnPublish);
        ivSelectPicture = findViewById(R.id.ivSelectPicture);
        ivSelectVideo = findViewById(R.id.ivSelectVideo);
        ivThumbnail = findViewById(R.id.ivThumbnail);
        rlAttachmentContainer = findViewById(R.id.rl_attachment_container);
        rlVideoContainer = findViewById(R.id.rl_container_video_controls);
        ivPlay = findViewById(R.id.ic_play);
        videoView = findViewById(R.id.videView);
    }

    private void setListeners() {
        findViewById(R.id.ivBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnPublish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isValid()) {
                    String textStory = etStory.getText().toString().trim();
                    FeedPost feedPost = new FeedPost();
                    feedPost.setText(textStory);
                    feedPost.setDateTime(new Date().toString());
                    feedPost.setUid(CURRENT_USER.getId());
                    feedPost.setId(getKeyForStory());
                    showProgressDialog("Publishing Story");
                    if (storyType == StoryTypes.PictureStory) {
                        // we have picture with the story
                        feedPost.setStoryType(StoryTypes.PictureStory);
                        publishStoryWithPicture(feedPost);
                    } else if (storyType == StoryTypes.VideoStory) {
                        feedPost.setStoryType(StoryTypes.VideoStory);
                        publishStoryWithVideo(feedPost);
                    } else {
                        feedPost.setStoryType(StoryTypes.TextStory);
                        publishStoryText(feedPost);
                    }

                }
            }
        });

        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                rlVideoContainer.setVisibility(View.VISIBLE);
            }
        });

        imageUriFromGallery = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {

                if (result.getData() != null) {
                    storyType = StoryTypes.PictureStory;
                    imageFilePath = result.getData().getData();
                    Glide.with(context)
                            .load(imageFilePath)
                            .centerCrop()
                            .into(ivThumbnail);
                    rlAttachmentContainer.setVisibility(View.VISIBLE);
                    ivThumbnail.setVisibility(View.VISIBLE);
                    videoView.setVisibility(View.GONE);
                    rlVideoContainer.setVisibility(View.GONE);
                }
            }
        });

        videoUriFromGallery = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if (result.getData() != null) {
                    videoFilePath = result.getData().getData();
                    mimeType = getContentResolver().getType(videoFilePath);
                    if (mimeType != null && mimeType.startsWith("video/")) {
                        storyType = StoryTypes.VideoStory;
                        new GetThumbnailTask(context).execute(videoFilePath);
                    } else {
                        showToastMessage("You did not attach a video");
                    }
                } else {
                    showToastMessage("Data is null");
                }
            }
        });
        ivSelectPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getImageFromGallery(imageUriFromGallery);
            }
        });

        ivSelectVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getVideoFromGallery((videoUriFromGallery));

            }
        });
    }

    protected void getImageFromGallery(ActivityResultLauncher<Intent> getImageUriFromGallery) {
        Intent intentGallery = new Intent(Intent.ACTION_PICK);
        intentGallery.setType("image/*");
        getImageUriFromGallery.launch((intentGallery));
    }

    protected void getVideoFromGallery(ActivityResultLauncher<Intent> getVideoUriFromGallery) {
        Intent intentGallery = new Intent(Intent.ACTION_PICK);
        intentGallery.setType("video/*");
        getVideoUriFromGallery.launch(intentGallery);
    }

    private void setData() {
        tvTeamName.setText(team.getName());
        Utils.setPicture(this, ivProfilePic, CURRENT_USER.getProfileUrl());
    }

    private boolean isValid() {
        String textStory = etStory.getText().toString().trim();
        if (textStory.isEmpty()) {
            showToastMessage("Please write a story");
            return false;
        }

        if (textStory.length() < 10) {
            showToastMessage("your story must contain at least 10 characters");
            return false;
        }
        return true;
    }

    private String getKeyForStory() {
        String key = FirebaseDatabase.getInstance().getReference(DbReferences.FEED).child(team.getName()).push().getKey();
        return key;
    }

    private void publishStoryText(FeedPost feedPost) {
        FirebaseDatabase.getInstance().getReference(DbReferences.FEED).child(team.getName()).child(feedPost.getId()).setValue(feedPost).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                closeProgressDialog();
                if (task.isSuccessful()) {
                    showToastMessage("Story published successfully");
                    finish();
                } else {
                    showToastMessage("Failed to publish the story");
                }
            }
        });
    }

    private void publishStoryWithPicture(FeedPost feedPost) {
        FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
        StorageReference storageReference = firebaseStorage.getReference("PostPictures").child(feedPost.getId());
        storageReference.putFile(imageFilePath).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                if (task.isSuccessful()) {
                    storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            String url = uri.toString();
                            feedPost.setStoryImageURL(url);
                            publishStoryText(feedPost);
                        }

                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            closeProgressDialog();
                            showToastMessage("Failed to upload image");
                        }
                    });
                } else {
                    closeProgressDialog();
                    Toast.makeText(context, "Failed to upload the image", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void publishStoryWithVideo(FeedPost feedPost) {
        FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
        StorageReference storageReference = firebaseStorage.getReference("Post Videos").child(feedPost.getId());
        storageReference.putFile(videoFilePath).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                if (task.isSuccessful()) {
                    storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            String url = uri.toString();
                            feedPost.setStoryVideoURL(url);
                            publishStoryWithPicture(feedPost);
                        }

                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            closeProgressDialog();
                            showToastMessage("Failed to upload video");
                        }
                    });
                } else {
                    closeProgressDialog();
                    Toast.makeText(context, "Failed to upload the video", Toast.LENGTH_SHORT).show();
                }
            }

        });

    }

    private class GetThumbnailTask extends AsyncTask<Uri, Void, Bitmap> {
        private Context context;

        public GetThumbnailTask(Context context) {
            this.context = context;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showProgressDialog("Processing..");
        }

        @Override
        protected Bitmap doInBackground(Uri... uris) {
            Uri videoUri = uris[0];
            MediaMetadataRetriever retriever = new MediaMetadataRetriever();
            retriever.setDataSource(context, videoUri);

            Bitmap thumbnail = retriever.getFrameAtTime(0, MediaMetadataRetriever.OPTION_CLOSEST_SYNC);
            try {
                retriever.release();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            return thumbnail;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            closeProgressDialog();
            if (bitmap != null) {
                ivThumbnail.setImageBitmap(bitmap);
                rlAttachmentContainer.setVisibility(View.VISIBLE);
                rlVideoContainer.setVisibility(View.VISIBLE);
                videoView.setVisibility(View.GONE);
                ivThumbnail.setVisibility(View.VISIBLE);
                imageFilePath = getUriFromBitmap(bitmap);
                ivPlay.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (videoFilePath != null) {
                            rlVideoContainer.setVisibility(View.GONE);
                            videoView.setVideoURI(videoFilePath);
                            ivThumbnail.setVisibility(View.INVISIBLE);
                            videoView.setVisibility(View.VISIBLE);
                            videoView.start();
                        }
                    }
                });
            }
        }
    }

    private static Uri getUriFromBitmap(Bitmap bitmap) {
        Uri uri = null;
        try {
             // Create a temporary file
            File localFile = createTempFile("image", ".jpg");
            FileOutputStream fos = new FileOutputStream(localFile);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
            uri = Uri.fromFile(localFile);
        } catch (IOException e) {

        }
        return uri;
    }
}