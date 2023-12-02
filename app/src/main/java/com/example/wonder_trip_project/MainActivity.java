package com.example.wonder_trip_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    EditText txtUsername;
    EditText txtPassword;
    Button btnLogIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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

    public void onClickableTextClick2(View view) {
        // Create an Intent to navigate to the next activity
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }

    public void btnLogInHandler() {
        String username = txtUsername.getText().toString();
        String password = txtPassword.getText().toString();

        Toast.makeText(MainActivity.this, "Username: " + username + " Password: " + password, Toast.LENGTH_SHORT).show();
    }
}