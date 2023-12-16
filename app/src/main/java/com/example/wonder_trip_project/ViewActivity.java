package com.example.wonder_trip_project;

import static com.example.wonder_trip_project.Utils.showLog;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ViewActivity extends AppCompatActivity {

    ImageView imgContent;
    String userId, regUsername, regPassword;
    RecyclerView recyclerView;

    ArrayList<JournalModel> journalList;
    MyAdapter adapter;
    DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference("users");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);

        recyclerView = findViewById(R.id.activity2recycleView);
        journalList = new ArrayList<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MyAdapter(this, journalList);
        recyclerView.setAdapter(adapter);

        Intent intent = getIntent();
        userId = intent.getStringExtra("userId");

        showLog("(Out)userId: "+userId);
        retriveJournals("-Nl4N9BTlJb7Ycc-l3X8"); // "-Nl4N9BTlJb7Ycc-l3X8"




    }


    private void retriveJournals(String userId){
        // In your onDataChange method, access all journals under the user ID:

        FirebaseDatabase.getInstance().getReference("users/"+userId+"/journals").addListenerForSingleValueEvent(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                showLog("userId: " + userId);
                for (DataSnapshot journalSnapshot : snapshot.getChildren()) {

                    // Retrieve journal data
                    String title = journalSnapshot.child("title").getValue(String.class);
                    String date = journalSnapshot.child("date").getValue(String.class);
                    String rate = journalSnapshot.child("rate").getValue(String.class);

                    // Create a JournalModel object
                    JournalModel journal = new JournalModel(title, date, rate);

                    // Add the journal to your list
                    journalList.add(journal);


//                    DatabaseReference journalRef = FirebaseDatabase.getInstance().getReference("users/"+userId+"/journals");
                    showLog("journalId: "+ journalSnapshot.getKey());

                    // Update your adapter with the updated journal list
                    adapter.notifyDataSetChanged();
//                    break;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle error
            }
        });
    }

}