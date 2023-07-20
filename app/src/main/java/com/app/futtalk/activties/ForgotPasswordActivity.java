package com.app.futtalk.activties;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.app.futtalk.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPasswordActivity extends AppCompatActivity {

    EditText etEmail;

    Button btnSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        init();
        setListeners();
    }

    private void init(){
        etEmail.findViewById(R.id.etEmail);
        btnSubmit.findViewById(R.id.btnSubmit);

    }
    private void setListeners(){
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email= etEmail.getText().toString().trim();

                if(email.isEmpty()){
                    etEmail.setError("Please enter your Email");
                    etEmail.requestFocus();
                    return;
                }
                if(Patterns.EMAIL_ADDRESS.matcher(email).matches()==false){
                    etEmail.setError("Please enter a valid Email address");
                    etEmail.requestFocus();
                    return;
                }
                sendPasswordResetRequest(email);
            }
        });

    }
    private void sendPasswordResetRequest(String email){
        btnSubmit.setEnabled(false);
        FirebaseAuth mAuth= FirebaseAuth.getInstance();

        mAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                btnSubmit.setEnabled(true);
                if(task.isSuccessful()){
                    showDialog();
                } else{
                    Toast.makeText(ForgotPasswordActivity.this, "Invalid Email address", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
private void showDialog(){
    AlertDialog.Builder builder= new AlertDialog.Builder(this)
            .setMessage("A password reset link has been sent to your Email")
            .setCancelable(false)
            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            });
    AlertDialog confirmDialog =builder.create();
    confirmDialog.show();
}
}