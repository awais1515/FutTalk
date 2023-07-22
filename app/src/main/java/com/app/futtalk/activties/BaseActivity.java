package com.app.futtalk.activties;

import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class BaseActivity extends AppCompatActivity {

    public void showToastMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private void printLogMessage(String message){
        Log.d("FutTalk", message);
    }
}
