package com.example.wonder_trip_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.example.wonder_trip_project.databinding.ActivityMainBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageException;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    EditText txtUsername, txtPassword;
    Button btnLogIn;
    ImageView imageView3ActivityMain;
//    ActivityMainBinding binding;
    HomeFragment homeFragment;

    // Get a reference to the "users" node in the database
    DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference("users");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        binding = ActivityMainBinding.inflate(getLayoutInflater());
//        setContentView(binding.getRoot());
        setContentView(R.layout.activity_main);

        txtUsername = findViewById(R.id.txtUsername);
        txtPassword = findViewById(R.id.txtPassword);
        btnLogIn = findViewById(R.id.btnLogIn);
        imageView3ActivityMain = findViewById(R.id.imageView3ActivityMain);

        // Inflate the another_layout.xml
        View anotherLayout = getLayoutInflater().inflate(R.layout.home_fragment_top_bar, null);

        // Find the TextView in the inflated layout
        TextView anotherTextView = anotherLayout.findViewById(R.id.homeFragmentTopBarProfileText);

        // Set the text for the TextView
        anotherTextView.setText("Updated text for another TextView");

        // Now you can add the inflated layout to your main layout if needed
        // For example, if you have a LinearLayout in your main layout:
        ConstraintLayout mainLayout = findViewById(R.layout.activity_home);
        mainLayout.addView(anotherLayout);

        btnLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//        progBar.setVisibility(View.VISIBLE);
                String username = txtUsername.getText().toString();
                String password = txtPassword.getText().toString();

                usersRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        // This method is called once with the initial value and again whenever data at this location is updated.
                        for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                            // Get individual user details
                            String regUsername = userSnapshot.child("username").getValue(String.class);
                            String regEmail = userSnapshot.child("email").getValue(String.class);
                            String regPassword = userSnapshot.child("password").getValue(String.class);

//
                            // Something wrong here on this if condition message... Consider later 👇
                            // I found the wrong in here this code inside a for loop thats why...

//                            if ((!username.equals(regUsername)) || (!password.equals(regPassword))){
//                                Toast.makeText(getApplicationContext(), "Username or Password Doesn't match", Toast.LENGTH_SHORT).show();
//                            }
                            if (username.equals(regUsername) && password.equals(regPassword)){
                                if (username.isEmpty() || password.isEmpty()){
                                    Toast.makeText(getApplicationContext(), "Can't LogIn Field(s) are empty", Toast.LENGTH_SHORT).show();
                                }else{
                                    Toast.makeText(getApplicationContext(), "Loggedin as "+username, Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(getApplicationContext(), HomeActivity.class);

                                    // Pass data to HomeFragment
                                    homeFragment = HomeFragment.newInstance(username, password);
                                    getSupportFragmentManager().beginTransaction()
                                            .replace(R.id.frame_layout, homeFragment)
                                            .addToBackStack(null)
                                            .commit();
//                                    // Image Retriving Code ====== We can't do this on this activity
//                                    storageRef = FirebaseStorage.getInstance().getReference("images/"+userId+".jpg");
//
//                                    try {
//                                        File localFile = File.createTempFile(userId+"",".jpg");
//                                        storageRef.getFile(localFile)
//                                                .addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
//                                                    @Override
//                                                    public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
//
//
//                                                        Bitmap bitmap = BitmapFactory.decodeFile(localFile.getAbsolutePath());
//                                                        binding.homeFragmentTopBarProfileImage.setImageBitmap(bitmap); // homeFragmentTopBarProfileImage
//                                                    }
//                                                }).addOnFailureListener(new OnFailureListener() {
//                                                    @Override
//                                                    public void onFailure(@NonNull Exception e) {
//                                                        if (e instanceof StorageException && ((StorageException) e).getErrorCode() == StorageException.ERROR_OBJECT_NOT_FOUND) {
//                                                            // Object does not exist
//                                                            // ...
//                                                        } else {
//                                                            // Other storage-related errors
//                                                            // ...
//                                                        }
//
//                                                        Toast.makeText(MainActivity.this, "Failed to retrive...", Toast.LENGTH_SHORT).show();
//
//                                                    }
//                                                });
//                                    }catch (IOException e){
//                                        e.printStackTrace();
//                                    }

                                    startActivity(intent);
                                    finish();
                                }
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        // Failed to read value
                        Log.w("Firebase", "Failed to read value.", databaseError.toException());
                    }
                });
            }
        });
    }

    // Click handling method
    public void onClickableTextClick_Goto_sign_in(View view) {
        Intent intent = new Intent(this, SignInActivity.class);
        startActivity(intent);
    }
}