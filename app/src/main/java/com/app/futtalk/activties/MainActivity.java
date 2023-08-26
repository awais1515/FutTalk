package com.app.futtalk.activties;

import static com.app.futtalk.utils.FirebaseUtils.CURRENT_USER;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.app.futtalk.R;
import com.app.futtalk.fragments.FixturesFragment;
import com.app.futtalk.fragments.HomeFragment;
import com.app.futtalk.fragments.ResultsFragment;
import com.app.futtalk.fragments.TeamsFragment;
import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends BaseActivity {
    Context context;
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle actionBarDrawerToggle;
    ActivityResultLauncher<Intent> getImageUriFromGallery;
    ImageView btnMenu;

    TextView tvLogout;
    TextView tvScreenTitle;
    HomeFragment homeFragment;
    ResultsFragment resultsFragment;
    TeamsFragment teamsFragment;
    FixturesFragment fixturesFragment;
    BottomNavigationView bottomNavigationView;
    CircleImageView ivProfilePic;
    Button btnViewProfile;
    ImageView btnAddNewTeam;
    ImageView btnAddNewLeague;
    ImageButton closeButton;
    //private ActivityResultLauncher<Intent> galleryImageResultListener;
    private Uri imageFilePath;
    private TextView tvName;
    private TextView tvEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        setCurrentUserData();
        setListeners();
        setupDrawer();
    }

    private void init() {
        context = this;
        drawerLayout = findViewById(R.id.drawer_layout);
        btnMenu = findViewById(R.id.btnMenu);
        bottomNavigationView = findViewById(R.id.bottomNavigation);
        tvName = findViewById(R.id.tvName);
        tvEmail = findViewById(R.id.tvEmail);
        tvLogout = findViewById(R.id.tvLogout);
        tvScreenTitle = findViewById(R.id.tvScreenTitle);
        ivProfilePic = findViewById(R.id.ivProfilePic);
        btnViewProfile = findViewById(R.id.btnViewProfile);
        btnAddNewTeam= findViewById(R.id.btnAddNewTeam);
        btnAddNewLeague = findViewById(R.id.btnAddNewLeague);
        closeButton = findViewById(R.id.closeButton);
        homeFragment = new HomeFragment();
        resultsFragment = new ResultsFragment();
        teamsFragment = new TeamsFragment();
        fixturesFragment = new FixturesFragment();
        loadFragment(homeFragment, getString(R.string.home_frag_title));

    }

    private void setListeners() {
        btnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.open();
            }
        });

        btnViewProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ProfileViewActivity.class);
                startActivity(intent);
            }
        });

            closeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    drawerLayout.close();
                }
            });
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.item_home) {
                    btnAddNewTeam.setVisibility(View.GONE);
                    btnAddNewLeague.setVisibility(View.GONE);
                    loadFragment(homeFragment, getString(R.string.home_frag_title));
                    return true;
                } else if (item.getItemId() == R.id.item_teams) {
                    btnAddNewTeam.setVisibility(View.VISIBLE);
                    btnAddNewLeague.setVisibility(View.GONE);
                    loadFragment(teamsFragment, getString(R.string.teams_frag_title));
                    return true;
                } else if (item.getItemId() == R.id.item_fixtures) {
                    btnAddNewTeam.setVisibility(View.GONE);
                    btnAddNewLeague.setVisibility(View.VISIBLE);
                    loadFragment(fixturesFragment, getString(R.string.fixtures_frag_title));
                    return true;
                } else if (item.getItemId() == R.id.item_results) {
                    btnAddNewTeam.setVisibility(View.GONE);
                    btnAddNewLeague.setVisibility(View.GONE);
                    loadFragment(resultsFragment, getString(R.string.results_frag_title));
                    return true;
                } else {
                    return false;
                }
            }
        });

        getImageUriFromGallery = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if (result.getData() != null) {
                    imageFilePath = result.getData().getData();
                    Glide.with(context)
                            .load(imageFilePath)
                            .centerCrop()
                            .into(ivProfilePic);
                    uploadPictureOnFirebase();
                }
            }
        });

        ivProfilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getImageFromGallery(getImageUriFromGallery);
            }
        });

        tvLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent intent= new Intent(context, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
        findViewById(R.id.tvAboutUs).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, AboutUsActivity.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.tvContactUs).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ContactUsActivity.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.tvPrivacyPolicy).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, PrivacyPolicyActivity.class);
                startActivity(intent);
            }
        });

        btnAddNewTeam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(context, TeamSelectionActivity.class);
                startActivity(intent);
            }
        });

        btnAddNewLeague.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, LeaguesSelectionActivity.class);
                startActivity(intent);
            }
        });
    }

    private void loadFragment(Fragment fragment, String title) {
        setTitle(title);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.content, fragment);
        fragmentTransaction.commit();
    }

    private void setTitle(String title) {
        tvScreenTitle.setText(title);
    }

    private void setupDrawer() {
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
    }

    protected void getImageFromGallery(ActivityResultLauncher<Intent> getImageUriFromGallery) {
        Intent intentGallery = new Intent(Intent.ACTION_PICK);
        intentGallery.setType("image/*");
        getImageUriFromGallery.launch((intentGallery));

    }


    private void uploadPictureOnFirebase() {
        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Uploading Picture...");
        progressDialog.show();
        FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
        StorageReference storageReference = firebaseStorage.getReference("ProfilePicture").child(CURRENT_USER.getId());
        storageReference.putFile(imageFilePath).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                if (task.isSuccessful()) {
                    storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            String URL = uri.toString();
                            CURRENT_USER.setProfileUrl(URL);
                            FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                            DatabaseReference databaseReference = firebaseDatabase. getReference("users").child(CURRENT_USER.getId());
                            databaseReference.setValue(CURRENT_USER).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        showToastMessage("Profile picture updated successfully");
                                        progressDialog.dismiss();
                                    } else {
                                        showToastMessage("Failed to upload profile picture");
                                        progressDialog.dismiss();
                                    }
                                }
                            });
                        }

                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                        }
                    });
                } else {
                    progressDialog.dismiss();
                    Toast.makeText(context, "Failed to upload the image", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void setCurrentUserData() {
        tvName.setText(CURRENT_USER.getName());
        tvEmail.setText(CURRENT_USER.getEmail());

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