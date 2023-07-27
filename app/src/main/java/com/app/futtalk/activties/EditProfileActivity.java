package com.app.futtalk.activties;

import static com.app.futtalk.utils.FirebaseUtils.CURRENT_USER;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.app.futtalk.R;
import com.app.futtalk.utils.DbReferences;
import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.Currency;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class EditProfileActivity extends BaseActivity {


    Context context;
    private Button btnAdd;
    private Button btnSave;
    private EditText etName;
    private EditText etBio;
    private EditText etFavourite;
    private TextView tvFavouritesTitle;
    private CircleImageView ivProfilePic;
    private ChipGroup chipGroup;
    private ActivityResultLauncher<Intent> getImageUriFromGallery;
    private Uri imageFilePath;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        init();
        setListeners();
        setCurrentUserData();
    }

    private void init() {
        context = this;
        btnAdd = findViewById(R.id.btn_add_favourite);
        btnSave = findViewById(R.id.btn_save);
        etName = findViewById(R.id.etName);
        etBio = findViewById(R.id.etBio);
        etFavourite = findViewById(R.id.etFavourite);
        ivProfilePic = findViewById(R.id.ivProfilePic);
        chipGroup = findViewById(R.id.chipGroup);
        tvFavouritesTitle = findViewById(R.id.tv_favourite_title);
    }

    private void setListeners() {
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!etFavourite.getText().toString().trim().isEmpty()) {
                    tvFavouritesTitle.setVisibility(View.VISIBLE);
                    Chip chip = (Chip) getLayoutInflater().inflate(R.layout.chip, null);
                    chip.setText(etFavourite.getText().toString());
                    chip.setOnCloseIconClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            chipGroup.removeView(view);
                        }
                    });
                    chipGroup.addView(chip);
                    etFavourite.clearFocus();
                    etFavourite.setText("");
                }
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isValid()) {
                    updateUserProfile();
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
    }

    private void updateUserProfile() {
        String name = etName.getText().toString().trim();
        String bio = etBio.getText().toString().trim();
        CURRENT_USER.setName(name);
        CURRENT_USER.setBio(bio);
        CURRENT_USER.getFavourites().clear();
        if (chipGroup.getChildCount() > 0) {
            for (int i = 0; i < chipGroup.getChildCount(); i++) {
                Chip chip = (Chip) chipGroup.getChildAt(i);
                CURRENT_USER.getFavourites().add(chip.getText().toString());
            }
        }

        FirebaseDatabase.getInstance().getReference(DbReferences.USERS_REFERENCE).child(CURRENT_USER.getId()).setValue(CURRENT_USER).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {

                } else {

                }
            }
        });

    }

    private boolean isValid() {

        String name = etName.getText().toString().trim();
        String bio = etBio.getText().toString().trim();

        if (name.isEmpty()) {
            etName.setError("Name can't be empty");
            etName.requestFocus();
            return false;
        }

        if (name.length() < 3) {
            etName.setError("Name should be at least 3 characters long");
            etName.requestFocus();
            return false;
        }

        if (bio.isEmpty()) {
            etBio.setError("Bio can't be empty");
            etBio.requestFocus();
            return false;
        }

        if (bio.length() < 80) {
            etBio.setError("your bio should be at least 80 characters long");
            etBio.requestFocus();
            return false;
        }

        return true;
    }

    private void setCurrentUserData() {
        etName.setText(CURRENT_USER.getName());
        etBio.setText(CURRENT_USER.getBio());
        if(CURRENT_USER.getFavourites().size() > 0) {
            // Todo show favourites here
            tvFavouritesTitle.setVisibility(View.VISIBLE);
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

    protected void getImageFromGallery(ActivityResultLauncher<Intent> getImageUriFromGallery) {
        Intent intentGallery = new Intent(Intent.ACTION_PICK);
        intentGallery.setType("image/*");
        getImageUriFromGallery.launch((intentGallery));

    }

}