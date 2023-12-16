package com.example.wonder_trip_project;

import static com.example.wonder_trip_project.Utils.showLog;
import static com.example.wonder_trip_project.Utils.showToast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
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


    // Get a reference to the "users" node in the database
    DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference("users");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtUsername = findViewById(R.id.txtUsername);
        txtPassword = findViewById(R.id.txtPassword);
        btnLogIn = findViewById(R.id.btnLogIn);
        imageView3ActivityMain = findViewById(R.id.imageView3ActivityMain);

        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();



        btnLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//        progBar.setVisibility(View.VISIBLE);
                String username = txtUsername.getText().toString();
                String password = txtPassword.getText().toString();
                if (networkInfo == null || !networkInfo.isConnected()) {
                    // No internet connection
                    showToast(getApplicationContext(), "No Internet Connection and Try again");
                    showLog("No internet connection found while trying to access Firebase.", "e");
                    return; // Stop further execution if no internet
                }

                usersRef.addValueEventListener(new ValueEventListener() {

                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        // This method is called once with the initial value and again whenever data at this location is updated.
                        for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                            // Get individual user details
                            String regUsername = userSnapshot.child("username").getValue(String.class);
                            String regEmail = userSnapshot.child("email").getValue(String.class);
                            String regPassword = userSnapshot.child("password").getValue(String.class);
                            String userId = userSnapshot.getKey();

                            // Something wrong here on this if condition message... Consider later 👇
                            // I found the wrong in here this code inside a for loop thats why...

//                            if ((!username.equals(regUsername)) || (!password.equals(regPassword))){
//                                Toast.makeText(getApplicationContext(), "Username or Password Doesn't match", Toast.LENGTH_SHORT).show();
//                            }
                            if (username.equals(regUsername) && password.equals(regPassword)){
                                if (username.isEmpty() || password.isEmpty()){
                                    showToast(getApplicationContext(),"Can't LogIn Field(s) are empty");
                                }else{
                                    Toast.makeText(getApplicationContext(), "Welcome "+username, Toast.LENGTH_SHORT).show();
                                    showLog("UserId_On_LogIn if Working: "+userId);

                                    Intent intent = new Intent(getApplicationContext(), HomeActivity.class);

                                    intent.putExtra("userId", userId);
                                    intent.putExtra("regUsername", regUsername);
                                    intent.putExtra("regPassword", regPassword);

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