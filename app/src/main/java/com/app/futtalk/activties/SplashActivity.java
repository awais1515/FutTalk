package com.app.futtalk.activties;

import static com.app.futtalk.utils.FirebaseUtils.CURRENT_USER;

import androidx.annotation.NonNull;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.widget.Toast;

import com.app.futtalk.R;
import com.app.futtalk.api.UpcomingFixturesListener;
import com.app.futtalk.models.FeedPost;
import com.app.futtalk.models.FixtureData;
import com.app.futtalk.models.User;
import com.app.futtalk.utils.AdsHelper;
import com.app.futtalk.utils.DataHelper;
import com.app.futtalk.utils.References;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class SplashActivity extends BaseActivity {

    private int DELAY_TIME = 2500; //2.5 seconds delay
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        context = this;
        MobileAds.initialize(context, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        AdsHelper.initAdsHelper(getApplicationContext());
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Handler handler = new Handler();

        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            DELAY_TIME = 0;
        }

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (FirebaseAuth.getInstance().getCurrentUser() != null) {
                    // The user is logged in already
                    getCurrentUserData();
                } else {
                    // The user isn't logged in
                    Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
                // printToastMessage("After 3 seconds we moved to next screen");
            }
        }, DELAY_TIME);
    }

    private void getCurrentUserData() {

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("users").child(FirebaseAuth.getInstance().getUid());
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                CURRENT_USER = snapshot.getValue(User.class);
                loadMainData();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
               showToastMessage(error.getMessage());
            }
        });
    }

    private void loadMainData() {
        DataHelper.getAllFixturesFromApi(99, new UpcomingFixturesListener() {
            @Override
            public void onUpcomingFixturesLoaded(List<FixtureData> fixtureDataList) {
                DataHelper.setSharedFixturesList(fixtureDataList);
                FirebaseDatabase.getInstance().getReference(References.FEATURED_POSTS).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        List<FeedPost> featuredPosts = new ArrayList<>();
                        for (DataSnapshot dataSnapshot: snapshot.getChildren()) {
                            FeedPost feedPost = dataSnapshot.getValue(FeedPost.class);
                            feedPost.setId(dataSnapshot.getKey());
                            featuredPosts.add(feedPost);
                        }
                        DataHelper.setSharedFeaturedPostsList(featuredPosts);
                        Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }

            @Override
            public void onFailure(String message) {
                Toast.makeText(context, "Failed to load Data", Toast.LENGTH_LONG).show();
            }
        });
    }
}