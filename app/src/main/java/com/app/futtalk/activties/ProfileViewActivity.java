package com.app.futtalk.activties;

import static com.app.futtalk.utils.FirebaseUtils.CURRENT_USER;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.futtalk.R;
import com.bumptech.glide.Glide;
import com.google.android.material.chip.ChipGroup;
import com.google.firebase.auth.FirebaseAuth;

public class ProfileViewActivity extends BaseActivity {

    Context context;
    ImageButton closeButton;
    Button btnLogOut;

    private TextView tvName;
    private TextView tvEmail;
    private TextView tvBio;
    private TextView tvAboutMeTitle;
    private TextView tvFavouritesTitle;
    private TextView tvPosts;
    private TextView tvLikes;
    private ImageView ivProfilePic;
    private ChipGroup chipGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_profile);
        init();
        setCurrentUserData();
        setListeners();

    }

    private void init(){
        context = this;
        tvName = findViewById(R.id.tvName);
        tvEmail = findViewById(R.id.tvEmail);
        tvBio = findViewById(R.id.tv_profile_bio);
        tvAboutMeTitle = findViewById(R.id.tvTitleAboutMe);
        tvPosts = findViewById(R.id.tvPosts);
        tvLikes = findViewById(R.id.tvLikes);
        tvFavouritesTitle = findViewById(R.id.tvTitleFavoriteTeams);
        ivProfilePic = findViewById(R.id.ivProfilePic);
        closeButton = findViewById(R.id.closeViewProfileButton);
        btnLogOut = findViewById(R.id.btnLogout);
        chipGroup = findViewById(R.id.chipGroup);
    }

    private void setListeners(){
        findViewById(R.id.btnEditProfile).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, EditProfileActivity.class);
                startActivity(intent);
            }
        });
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent intent= new Intent(context, LoginActivity.class);
                startActivity(intent);
                finish();

            }
        });
    }

    private void setCurrentUserData() {
        tvName.setText(CURRENT_USER.getName());
        tvEmail.setText(CURRENT_USER.getEmail());

        if (CURRENT_USER.getBio() != null && !CURRENT_USER.getBio().isEmpty()) {
            tvBio.setText(CURRENT_USER.getBio());
        } else {
            tvAboutMeTitle.setVisibility(View.GONE);
            tvBio.setVisibility(View.GONE);
        }

        if(CURRENT_USER.getFavourites().size() > 0) {
            // Todo show favourites here
        } else {
            tvFavouritesTitle.setVisibility(View.GONE);
            chipGroup.setVisibility(View.GONE);
        }


        if (CURRENT_USER.getProfileUrl() != null) {
            if (!CURRENT_USER.getProfileUrl().isEmpty()) {
                Glide.with(context)
                        .load(CURRENT_USER.getProfileUrl())
                        .centerCrop()
                        .into(ivProfilePic);
            }

        }
    }
}