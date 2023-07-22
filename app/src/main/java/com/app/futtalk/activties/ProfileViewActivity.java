package com.app.futtalk.activties;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.app.futtalk.R;
import com.google.firebase.auth.FirebaseAuth;

public class ProfileViewActivity extends BaseActivity {

    Context context;
    ImageButton closeButton;
    Button btnLogOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_profile);
        init();
        setListeners();
    }

    private void init(){
        context= this;
        closeButton=findViewById(R.id.closeViewProfileButton);
        btnLogOut=findViewById(R.id.btnLogout);
    }

    private void setListeners(){
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(context, MainActivity.class);
                startActivity(intent);


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
}