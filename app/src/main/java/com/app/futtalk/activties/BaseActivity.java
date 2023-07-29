package com.app.futtalk.activties;

import android.app.ProgressDialog;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class BaseActivity extends AppCompatActivity {

    private ProgressDialog progressDialog;
    public void showToastMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    public void showProgressDialog(String message) {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(this);
            progressDialog.setCancelable(false);
        } else {
            progressDialog.setMessage(message);
            progressDialog.show();
        }
    }

    public void closeProgressDialog() {
        if (progressDialog!= null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    private void printLogMessage(String message){
        Log.d("FutTalk", message);
    }
}
