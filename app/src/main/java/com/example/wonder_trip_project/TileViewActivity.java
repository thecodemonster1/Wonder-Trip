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

public class TileViewActivity extends AppCompatActivity {

    String userId, journalId, date, rate, title, text;
    TextView titleBox, dateBox, rateBox, textBox;
    DatabaseReference journalRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tile_view);

        titleBox = findViewById(R.id.viewJournaTitleId);
        dateBox = findViewById(R.id.dateTextView);
        rateBox = findViewById(R.id.rateTextView);
        textBox = findViewById(R.id.journalTextView);

        Intent intent = getIntent();
        userId = intent.getStringExtra("userId");
        journalId = intent.getStringExtra("journalId");
        journalRef = FirebaseDatabase.getInstance().getReference("users/"+userId+"/journals");

        journalRef.child(journalId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                title = snapshot.child("title").getValue(String.class);
                date = snapshot.child("date").getValue(String.class);
                rate = snapshot.child("rate").getValue(String.class);
                text = snapshot.child("text").getValue(String.class);

                titleBox.setText(title);
                dateBox.setText(date);
                rateBox.setText(rate);
                textBox.setText(text);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}