package com.example.wonder_trip_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    // Click handling method
    public void onClickableTextClick(View view) {
        // Create an Intent to navigate to the next activity
        Intent intent = new Intent(this, SignInActivity.class);
        startActivity(intent);
    }

    public void onClickableTextClick2(View view) {
        // Create an Intent to navigate to the next activity
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }
}