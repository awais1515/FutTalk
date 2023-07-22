package com.app.futtalk.activties;

import static com.app.futtalk.utils.FirebaseUtils.CURRENT_USER;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.app.ActivityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
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
    ImageView btnMenu;

    TextView tvLogout;
    HomeFragment homeFragment;
    ResultsFragment resultsFragment;
    TeamsFragment teamsFragment;
    FixturesFragment fixturesFragment;
    BottomNavigationView bottomNavigationView;
    CircleImageView ivProfilePic;
    Button btnViewProfile;
    ImageButton closeButton;
    private ActivityResultLauncher<Intent> galleryImageResultListener;
    private Uri imageFilePath;
    private TextView tvName;
    private TextView tvEmail;

    public static boolean hasGalleryPermissions(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (activity.checkSelfPermission(android.Manifest.permission.READ_MEDIA_IMAGES) == PackageManager.PERMISSION_GRANTED) {
                return true;
            } else {
                // Ask for Permission
                ActivityCompat.requestPermissions(activity, new String[]{android.Manifest.permission.READ_MEDIA_IMAGES}, 1);
                return false;
            }
        } else {
            if (activity.checkSelfPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                return true;
            } else {
                // Ask for Permission
                ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
                return false;
            }
        }

    }

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
        tvLogout= findViewById(R.id.tvLogout);
        ivProfilePic = findViewById(R.id.ivProfilePic);
        btnViewProfile = findViewById(R.id.btnViewProfile);
        closeButton = findViewById(R.id.closeButton);
        homeFragment = new HomeFragment();
        resultsFragment = new ResultsFragment();
        teamsFragment = new TeamsFragment();
        fixturesFragment = new FixturesFragment();
        loadFragment(homeFragment);

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
                drawerLayout.close();
            }
        });

            closeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, MainActivity.class);
                    startActivity(intent);
                    drawerLayout.close();
                }
            });
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.item_home) {
                    loadFragment(homeFragment);
                    return true;
                } else if (item.getItemId() == R.id.item_teams) {
                    loadFragment(teamsFragment);
                    return true;
                } else if (item.getItemId() == R.id.item_fixtures) {
                    loadFragment(fixturesFragment);
                    return true;
                } else if (item.getItemId() == R.id.item_results) {
                    loadFragment(resultsFragment);
                    return true;
                } else {
                    return false;
                }
            }
        });

        galleryImageResultListener = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getData() != null) {
                            imageFilePath = result.getData().getData();
                            Glide.with(context)
                                    .load(imageFilePath)
                                    .centerCrop()
                                    .into(ivProfilePic);

                            uploadPictureonFirebase();
                        }
                    }
                });

        ivProfilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (hasGalleryPermissions(MainActivity.this)) {
                    getImageFromGallery(galleryImageResultListener);
                }
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
    }

    private void loadFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.content, fragment);
        fragmentTransaction.commit();
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

    private void uploadPictureonFirebase() {
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