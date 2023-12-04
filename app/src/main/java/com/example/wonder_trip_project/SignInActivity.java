package com.example.wonder_trip_project;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignInActivity extends AppCompatActivity {
    EditText txtUsername, txtEmail, txtPassword, txtPassword2, txtDOB;
    Button btnSignUp;
    ImageButton imgButton;
    FirebaseAuth mAuth;
//    ProgressBar progBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference("users"); // Push the datas to "users" node
        String userId = usersRef.push().getKey(); // Generates a unique key for the new user

        txtUsername = findViewById(R.id.txtRegUsername);
        txtEmail = findViewById(R.id.txtRegEmail);
        txtPassword = findViewById(R.id.txtRegPassword);
        txtPassword2 = findViewById(R.id.txtRegPassword2);
        txtDOB = findViewById(R.id.txtRegDOB);
        btnSignUp = findViewById(R.id.btnSignUp);
        imgButton = findViewById(R.id.imgButton);
//        progBar = findViewById(R.id.progressBar);


        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = txtUsername.getText().toString();
                String email = txtEmail.getText().toString();
                String password = txtPassword.getText().toString();
                String password2 = txtPassword2.getText().toString();
                String dob = txtDOB.getText().toString();
//        progBar.setVisibility(View.VISIBLE);

                System.out.println("Usrname: "+username + "\nEmail: " + email + "\nPassword: " + password + "\nPassword2: " + password2 + "\nDate Of Birth: " + dob);

                if (!username.isEmpty() && !email.isEmpty() && !password.isEmpty() && !password2.isEmpty() && !dob.isEmpty()){
                    if (password.equals(password2)){
                        Toast.makeText(SignInActivity.this, "Signing Up Successful", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(SignInActivity.this, MainActivity.class);

                        // Pushing the users data
                        usersRef.child(userId).child("username").setValue(username);
                        usersRef.child(userId).child("email").setValue(email);
                        usersRef.child(userId).child("password").setValue(password);
                        usersRef.child(userId).child("dob").setValue(dob);

                        startActivity(intent);
                    }else {
                        Toast.makeText(SignInActivity.this, "Passwords are doesn't match", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(SignInActivity.this, "You missed on or more field to fill", Toast.LENGTH_SHORT).show();
                }

            }
        });

//        imgButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//            }
//        });
    }
}