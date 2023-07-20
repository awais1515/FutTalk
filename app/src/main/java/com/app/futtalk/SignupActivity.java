package com.app.futtalk;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignupActivity extends AppCompatActivity {

    // Todo implement all the signup screen activity and user our EasyTodo App as a reference
    Context context;

    private EditText etName;

    private EditText etEmail;

    private EditText etPassword;

    private EditText etConfirmPassword;

    private Button btnSignup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        init();
        setListeners();
    }

    private void init() {
        context = this;
        etName = findViewById(R.id.etName);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        etConfirmPassword = findViewById(R.id.etConfirmPassword);
        btnSignup = findViewById(R.id.btnSignUp);

    }

    private void setListeners() {
        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isDataValid()) {
                    //if data is valid then create account
                    createAccountWithFirebase();
                }


            }
        });

    }
    private boolean isDataValid(){
    String name = etName.getText().toString().trim();
    String email= etEmail.getText().toString().trim();
    String password= etPassword.getText().toString().trim();
    String confirmPasword= etConfirmPassword.getText().toString().trim();

    if(name.isEmpty()){
        etName.setError("Please enter your Name");
    }
    if(name.length()<3){
        etName.setError("Your Password must be at least 3 characters");
        etName.requestFocus();
        return false;
    }
    if(email.isEmpty()){
        etEmail.setError("Please enter your Email");
        etEmail.requestFocus();
        return false;
    }
    if(Patterns.EMAIL_ADDRESS.matcher(email).matches()==false){
        etEmail.setError("Please provide a valid Email");
        etEmail.requestFocus();
        return false;
    }

    if(password.isEmpty()){
        etPassword.setError("Please fill in your Password");
        etPassword.requestFocus();
        return false;
    }
    if (password.length()<6){
        etPassword.setError("Password must be more than 6 characters");
        etPassword.requestFocus();
        return false;
    }
    if (confirmPasword.isEmpty()){
        etConfirmPassword.setError("Please Confirm you Password");
        etConfirmPassword.requestFocus();
        return false;
    }
    if (confirmPasword.matches(password)== false){
        etConfirmPassword.setError("Passwords do no match");
        etConfirmPassword.requestFocus();
        return false;
    }
    return true;
    }
    private void createAccountWithFirebase(){
        String name = etName.getText().toString().trim();
        String email= etEmail.getText().toString().trim();
        String password= etPassword.getText().toString().trim();
        String confirmPasword= etConfirmPassword.getText().toString().trim();
        FirebaseAuth mAuth= FirebaseAuth.getInstance();


        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            User user= new User();
                            user.setName(name);
                            user.setEmail(email);
                            user.setId(mAuth.getUid());
                            FirebaseDatabase firebaseDatabase= FirebaseDatabase.getInstance();
                            DatabaseReference databaseReference= firebaseDatabase.getReference("users").child(mAuth.getUid());

                            databaseReference.setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        Intent intent = new Intent(context, MainActivity.class);
                                        startActivity(intent);
                                        printToastMessage("Account Successfully created");
                                    } else{
                                        printToastMessage("Failed to print user data, please contact support");
                                    }
                                }
                            });
                        }else{
                            printToastMessage("Failed to create account, please contact support");
                        }
                    }
                });
    }
    private void printToastMessage(String message){
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }
}