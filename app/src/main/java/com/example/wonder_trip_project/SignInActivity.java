package com.example.wonder_trip_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class SignInActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
    }

    // Click handling method
    public void onClickableTextClick(View view) {
        // Create an Intent to navigate to the next activity
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}