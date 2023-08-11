package com.app.futtalk.activties;

import static com.app.futtalk.utils.FirebaseUtils.CURRENT_USER;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

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
    private ImageView ivThumbnailVideo;
    private ImageView ivPlay;
    private RelativeLayout rlAttachmentContainer;
    private RelativeLayout rlVideoContainer;
    private ActivityResultLauncher<Intent> imageUriFromGallery;
    private ActivityResultLauncher<Intent> videoUriFromGallery;

    private String mimeType;

    private Uri imageFilePath;

    private Uri videoFilePath;
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
        tvTeamName = findViewById(R.id.tv_team_name);
        etStory = findViewById(R.id.etStory);
        ivProfilePic = findViewById(R.id.ivProfilePic);
        btnPublish = findViewById(R.id.btnPublish);
        ivSelectPicture = findViewById(R.id.ivSelectPicture);
        ivSelectVideo = findViewById(R.id.ivSelectVideo);
        ivThumbnail = findViewById(R.id.ivThumbnail);
        rlAttachmentContainer = findViewById(R.id.rl_attachment_container);
        rlVideoContainer = findViewById(R.id.rl_container_video);
        ivPlay= findViewById(R.id.ic_play);
        ivThumbnailVideo=findViewById(R.id.ivThumbnailVideo);
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
                    if(imageFilePath != null) {
                        // we have picture with the story
                        feedPost.setStoryType(StoryTypes.PictureStory);
                        publishStoryWithPicture(feedPost);
                    } else if (videoFilePath!= null) {
                        feedPost.setStoryType(StoryTypes.VideoStory);
                        publishStoryWithVideo(feedPost);
                    } else {
                        feedPost.setStoryType(StoryTypes.TextStory);
                        publishStoryText(feedPost);
                    }
                    
                }
            }
        });

        imageUriFromGallery = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if (result.getData() != null) {
                    imageFilePath = result.getData().getData();
                    Glide.with(context)
                            .load(imageFilePath)
                            .centerCrop()
                            .into(ivThumbnail);
                    rlAttachmentContainer.setVisibility(View.VISIBLE);
                }
            }
        });

        videoUriFromGallery= registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if(result.getData()!= null){
                    videoFilePath= result.getData().getData();
                    mimeType= getContentResolver().getType(videoFilePath);
                    if(mimeType != null && mimeType.startsWith("video/")){

                    Glide.with(context)
                            .load(videoFilePath)
                            .centerCrop()
                            .into(ivThumbnailVideo);
                    rlVideoContainer.setVisibility(View.VISIBLE);
                    ivPlay.setVisibility(View.VISIBLE);

                    ivPlay.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            MediaPlayer mediaPlayer = new MediaPlayer();
                            try {
                                mediaPlayer.setDataSource(context, videoFilePath);
                                mediaPlayer.prepare();
                                mediaPlayer.start();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    });

                }
                    else {
                        showToastMessage("You did not attach a video");
                    }
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
        Intent intentGallery= new Intent(Intent.ACTION_PICK);
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
        String key =  FirebaseDatabase.getInstance().getReference(DbReferences.FEED).child(team.getName()).push().getKey();
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

    private void publishStoryWithVideo(FeedPost feedPost){
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
                            publishStoryText(feedPost);
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
}