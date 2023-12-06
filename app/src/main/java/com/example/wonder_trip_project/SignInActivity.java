package com.example.wonder_trip_project;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class SignInActivity extends AppCompatActivity {
    EditText txtUsername, txtEmail, txtPassword, txtPassword2, txtDOB;
    Button btnSignUp;
    ImageView imgProfileView;
    FloatingActionButton btnImagePicker;

//    FirebaseAuth mAuth;
//    ProgressBar progBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        if (getSupportActionBar() != null) {
            Objects.requireNonNull(getSupportActionBar()).setBackgroundDrawable(new ColorDrawable(getColor(R.color.blue)));
        }

        DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference("users"); // Push the datas to "users" node
        String userId = usersRef.push().getKey(); // Generates a unique key for the new user

        txtUsername = findViewById(R.id.txtRegUsername);
        txtEmail = findViewById(R.id.txtRegEmail);
        txtPassword = findViewById(R.id.txtRegPassword);
        txtPassword2 = findViewById(R.id.txtRegPassword2);
        txtDOB = findViewById(R.id.txtRegDOB);
        btnSignUp = findViewById(R.id.btnSignUp);
        imgProfileView = findViewById(R.id.imgView);
        btnImagePicker = findViewById(R.id.btnImagePicker);
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

        btnImagePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImagePicker.with(SignInActivity.this)
                        .crop()	    			//Crop image(Optional), Check Customization for more option
                        .compress(1024)			//Final image size will be less than 1 MB(Optional)
                        .maxResultSize(1080, 1080)	//Final image resolution will be less than 1080 x 1080(Optional)
                        .start();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        assert data != null;
//        Uri uri = data.getData();
//        imgProfileView.setImageURI(uri);
        if (resultCode == RESULT_OK) {
            Uri uri = data.getData();
            imgProfileView.setImageURI(uri);
        } else {
            // Handle the case where the image picker did not return a valid result
            Toast.makeText(this, "Image selection canceled", Toast.LENGTH_SHORT).show();
        }
    }
}