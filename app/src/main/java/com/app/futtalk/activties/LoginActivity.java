package com.app.futtalk.activties;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.app.futtalk.R;
import com.app.futtalk.models.User;
import com.app.futtalk.utils.DbReferences;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LoginActivity extends BaseActivity {

    Button btnCreateAccount;
    Button btnLogin;

    Button btnGoogle_SignIn;
    EditText etEmail;
    EditText etPassword;

    TextView tvForgotPassword;

    TextView tvOtherSignInOptions;
    Context context;

    ProgressDialog progressDialog;

    // private GoogleSignInClient googleSignInClient=
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();
        setListeners();
    }

    private void init() {
        context = this;
        btnCreateAccount = findViewById(R.id.btnCreateAccount);
        btnLogin = findViewById(R.id.btnLogin);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btnGoogle_SignIn= findViewById(R.id.btnGoogleLogin);
        tvForgotPassword= findViewById(R.id.tvForgotPassword);
        tvOtherSignInOptions= findViewById(R.id.Text_Other_Options);
        progressDialog = new ProgressDialog(context);
        progressDialog.setCancelable(false);
        mAuth=FirebaseAuth.getInstance();

      /*  GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(GOOGLE_CLIENT_ID)
                .requestEmail()
                .build();

        // Initialize sign in client
        googleSignInClient = GoogleSignIn.getClient(LoginActivity.this, googleSignInOptions);
*/
    }

    private void setListeners() {

        btnCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(context, SignupActivity.class);
                startActivity(intent);
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Todo login user with the email and password, for reference check the code of our EasyTodo App
                String email=etEmail.getText().toString().trim();
                String password= etPassword.getText().toString().trim();
                if(email.isEmpty()==true){
                    etEmail.setError("Please enter your Email");
                    etEmail.requestFocus();
                }
                else if (password.isEmpty()==true){
                    etPassword.setError("Please enter your Password");
                    etPassword.requestFocus();
                }
                else{
                    FirebaseAuth mAuth= FirebaseAuth.getInstance();
                    login(email,password, mAuth);
                }

            }
        });
        tvForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(context, ForgotPasswordActivity.class);
            }
        });
        /* btnGoogle_SignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 Intent intent = googleSignInClient.getSignInIntent();
                startActivityForResult(intent, 100);
            }

        });*/
    }

    private void login(String email, String password, FirebaseAuth mAuth){
        progressDialog.setMessage("Signing in...");
        progressDialog.setCancelable(false);
        progressDialog.show();

mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
    @Override
    public void onComplete(@NonNull Task<AuthResult> task) {
        progressDialog.dismiss();
        if (task.isSuccessful()){
        Intent intent = new Intent(context, MainActivity.class);
        startActivity(intent);
        finish();
        }
        else{
            etPassword.setError("Invalid Email or Password");
        }
    }
});

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==100){
            progressDialog.setMessage("Signing in with Google");
            progressDialog.show();
            Task<GoogleSignInAccount> signInAccountTask = GoogleSignIn.getSignedInAccountFromIntent(data);
            if (signInAccountTask.isSuccessful()){
            try{
                FirebaseAuth mAuth= FirebaseAuth.getInstance();
                GoogleSignInAccount signInAccount = signInAccountTask.getResult(ApiException.class);
                if (signInAccount!= null){
                    AuthCredential credential= GoogleAuthProvider.getCredential(signInAccount.getIdToken(), null);
                    mAuth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                User user= new User();
                                user.setId(mAuth.getUid());
                                user.setEmail(mAuth.getCurrentUser().getDisplayName());
                                user.setName(mAuth.getCurrentUser().getEmail());
                                FirebaseDatabase firebaseDatabase= FirebaseDatabase.getInstance();
                                DatabaseReference databaseReference= firebaseDatabase.getReference(DbReferences.USERS).child(mAuth.getUid());
                                databaseReference.setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()){
                                            Intent intent= new Intent(context, SignupActivity.class);
                                            startActivity(intent);
                                            showToastMessage("Logged in Successfully");
                                            progressDialog.dismiss();
                                        }
                                        else {
                                            progressDialog.dismiss();
                                            showToastMessage("There was a problem updating user information");
                                        }

                                    }
                                });
                            } else{
                                progressDialog.dismiss();
                                showToastMessage("Login failed");
                            }
                        }
                    });
                }  else {
                    progressDialog.dismiss();
                    showToastMessage("Sign in account is null");
                }
            } catch (Exception e) {
                progressDialog.dismiss();
                showToastMessage(e.getMessage());
            }
            }else {
                showToastMessage(signInAccountTask.getException().toString());
                Log.d("ToastMessage", signInAccountTask.getException().toString());
            }
        }
    }


    private void printLogMessage(String message){Log.d("ToastMessage", message);
    }

}
