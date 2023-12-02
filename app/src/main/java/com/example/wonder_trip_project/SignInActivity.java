package com.example.wonder_trip_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class SignInActivity extends AppCompatActivity {
    EditText txtUsername;
    EditText txtEmail;
    EditText txtPassword;
    EditText txtPassword2;
    EditText txtDOB;
    Button btnSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        txtUsername = findViewById(R.id.txtRegUsername);
        txtEmail = findViewById(R.id.txtRegEmail);
        txtPassword = findViewById(R.id.txtRegPassword);
        txtPassword2 = findViewById(R.id.txtRegPassword2);
        txtDOB = findViewById(R.id.txtRegDOB);
        btnSignUp = findViewById(R.id.btnSignUp);

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnSignUpHandler();
            }
        });
    }

    public void btnSignUpHandler(){
        String username = txtUsername.getText().toString();
        String email = txtEmail.getText().toString();
        String password = txtPassword.getText().toString();
        String password2 = txtPassword2.getText().toString();
        String dob = txtDOB.getText().toString();

        System.out.println("Usrname: "+username + "\nEmail: " + email + "\nPassword: " + password + "\nPassword2: " + password2 + "\nDate Of Birth: " + dob);
    }

    // Click handling method
    public void onClickableTextClick(View view) {
        // Create an Intent to navigate to the next activity
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}