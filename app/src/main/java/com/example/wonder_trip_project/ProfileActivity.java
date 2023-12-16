package com.example.wonder_trip_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfileActivity extends AppCompatActivity {

    TextView username, email, dob;
    String userId, dbUsername, dbEmail, dbDob;
    DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("users");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        username = findViewById(R.id.profileUsername);
        email = findViewById(R.id.profileEmail);
        dob = findViewById(R.id.profileDob);

        Intent intent = getIntent();
        userId = intent.getStringExtra("userId");


        userRef.child(userId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                dbUsername = snapshot.child("username").getValue(String.class);
                dbEmail = snapshot.child("email").getValue(String.class);
                dbDob = snapshot.child("dob").getValue(String.class);

                username.setText(dbUsername);
                email.setText(dbEmail);
                dob.setText(dbDob);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}