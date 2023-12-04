package com.example.wonder_trip_project;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
    EditText txtUsername, txtPassword;
    Button btnLogIn;
    FirebaseAuth mAuth;
//    ProgressBar progBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Get a reference to the "users" node in the database
        DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference("users");

        mAuth = FirebaseAuth.getInstance();

        txtUsername = findViewById(R.id.txtUsername);
        txtPassword = findViewById(R.id.txtPassword);
        btnLogIn = findViewById(R.id.btnLogIn);



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

                            // Do something with the user details
//                            Toast.makeText(MainActivity.this, "Username: " + regUsername + ", Email: " + regEmail + ", Password: " + regPassword, Toast.LENGTH_SHORT).show();
                            if (username.equals(regUsername) && password.equals(regPassword)){
                                if (username.isEmpty() || password.isEmpty()){
                                    Toast.makeText(MainActivity.this, "Can't LogIn Field(s) are empty", Toast.LENGTH_SHORT).show();
                                }else{
                                    Toast.makeText(MainActivity.this, "LogIn Successful", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                                    startActivity(intent);
                                }
                            }
//                            if (!(username.equals(regUsername)) || !(password.equals(regPassword))){
//                                Toast.makeText(MainActivity.this, "Email or Password Doesn't match", Toast.LENGTH_SHORT).show();
//                            }123
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
    public void onClickableTextClick(View view) {
        // Create an Intent to navigate to the next activity
        Intent intent = new Intent(this, SignInActivity.class);
        startActivity(intent);
    }
}