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

public class MainActivity extends AppCompatActivity {
    EditText txtUsername, txtPassword;
    Button btnLogIn;
    FirebaseAuth mAuth;
//    ProgressBar progBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();

        txtUsername = findViewById(R.id.txtUsername);
        txtPassword = findViewById(R.id.txtPassword);
        btnLogIn = findViewById(R.id.btnLogIn);

        btnLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnLogInHandler();
            }
        });
    }

    // Click handling method
    public void onClickableTextClick(View view) {
        // Create an Intent to navigate to the next activity
        Intent intent = new Intent(this, SignInActivity.class);
        startActivity(intent);
    }

    public void btnLogInHandler() {
//        progBar.setVisibility(View.VISIBLE);
        String username = txtUsername.getText().toString();
        String password = txtPassword.getText().toString();

        Intent intent = getIntent();
        String regUsername = intent.getStringExtra("regUsername");
        String regPassword = intent.getStringExtra("regPassword");

        if (username.equals(regUsername) && password.equals(regPassword)){
            Toast.makeText(MainActivity.this, "LogIn Successful", Toast.LENGTH_SHORT).show();
            intent = new Intent(getApplicationContext(), HomeActivity.class);
            startActivity(intent);
        }else if (username.isEmpty() || password.isEmpty()){
            Toast.makeText(this, "Can't LogIn Field(s) are empty", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, "Email or Password Doesn't match", Toast.LENGTH_SHORT).show();
        }

    }
}