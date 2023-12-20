package com.example.wonder_trip_project;

import static com.example.wonder_trip_project.Utils.showLog;
import static com.example.wonder_trip_project.Utils.showToast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class TileViewActivity extends AppCompatActivity {
    String userId, journalId, date, rate, title, text, image;
    TextView titleBox, dateBox, rateBox, textBox;
    ImageView imageBox;
    Uri imageUri;
    FloatingActionButton editButton, deleteButton;
    RecyclerView recyclerView = findViewById(R.id.activity2recycleView);
    DatabaseReference journalRef;
    MyAdapter adapter;
    ArrayList<JournalModel> journalList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tile_view);



        titleBox = findViewById(R.id.viewJournaTitleId);
        dateBox = findViewById(R.id.dateTextView);
        rateBox = findViewById(R.id.rateTextView);
        textBox = findViewById(R.id.journalTextView);
        imageBox = findViewById(R.id.tileViewImage);
        editButton = findViewById(R.id.editContentFab);
        deleteButton = findViewById(R.id.deleteContentFab);

        journalList = new ArrayList<>();


        Intent intent = getIntent();
        userId = intent.getStringExtra("userId");
        journalId = intent.getStringExtra("journalId");
        journalRef = FirebaseDatabase.getInstance().getReference("users/" + userId + "/journals");

        journalRef.child(journalId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                title = snapshot.child("title").getValue(String.class);
                date = snapshot.child("date").getValue(String.class);
                rate = snapshot.child("rate").getValue(String.class);
                text = snapshot.child("text").getValue(String.class);
                image = snapshot.child("imageUrl").getValue(String.class);

                titleBox.setText(title);
                dateBox.setText(date);
                rateBox.setText(rate);
                textBox.setText(text);


                Glide.with(TileViewActivity.this)
                        .load(image)
                        .into(imageBox);
                editButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        editJournalBtn(title, date, rate, text);
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RecyclerView.ViewHolder viewHolder = recyclerView.findContainingViewHolder(view);
                if (viewHolder != null) {
                    int position = viewHolder.getAbsoluteAdapterPosition();
                    deleteJournalBtn(journalId, position);
                } else {
                    // Handle the case where the ViewHolder is not found
                    showLog("ViewHolder is not found");
                }
            }
        });

    }

    public void deleteJournalBtn(String journalId, int position){
        journalRef.child(journalId).removeValue().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                // Optional: Delete image from storage
                if (image != null) {
                    StorageReference imageRef = FirebaseStorage.getInstance().getReferenceFromUrl(image);
                    imageRef.delete().addOnCompleteListener(task2 -> {
                        if (task2.isSuccessful()) {
                            // Image deleted successfully
                        } else {
                            // Handle image deletion error
                        }
                    });
                } else {
                    showToast(getApplicationContext(), "Image is not selected properly");
                }

                // Update RecyclerView adapter
                adapter.removeItem(position);

                // Display success message
                showToast(getApplicationContext(),"Journal deleted successfully");
            } else {
                // Handle database deletion error
                showToast(getApplicationContext(), "Something Went Wrong!");
            }
        });
    }
    public void editJournalBtn(String title, String date, String rate, String text){
        Intent intent = new Intent(getApplicationContext(), AddActivity.class);
        intent.putExtra("title", title);
        intent.putExtra("date", date);
        intent.putExtra("rate", rate);
        intent.putExtra("text", text);
        startActivity(intent);

    }
}