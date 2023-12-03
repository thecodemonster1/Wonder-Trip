package com.example.wonder_trip_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

public class SignInActivity extends AppCompatActivity {
    EditText txtUsername;
    EditText txtEmail;
    EditText txtPassword;
    EditText txtPassword2;
    EditText txtDOB;
    Button btnSignUp;
    ImageButton imgButton;

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
        imgButton = findViewById(R.id.imgButton);

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnSignUpHandler();
            }
        });

        imgButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

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

        if (!username.isEmpty() && !email.isEmpty() && !password.isEmpty() && !password2.isEmpty() && !dob.isEmpty()){
//            Toast.makeText(MainActivity.this, "Username: " + username + " Password: " + password, Toast.LENGTH_SHORT).show();
            if (password.equals(password2)){
                Toast.makeText(SignInActivity.this, "Successfully Signed Up", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(SignInActivity.this, MainActivity.class);
                startActivity(intent);
            }else {
                Toast.makeText(SignInActivity.this, "Passwords are doesn't match", Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(SignInActivity.this, "You missed on or more field to fill", Toast.LENGTH_SHORT).show();
        }

    }


//    private void openImagePicker() {
//        Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//        startActivityForResult(intent, PICK_IMAGE_REQUEST);
//    }
//
//    // Handle the result of the image picker
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
//            // Handle the selected image URI, e.g., display it in an ImageView
//            Uri selectedImageUri = data.getData();
//            ImageView imageView = findViewById(R.id.imgButton);
//            imageView.setImageURI(selectedImageUri);
//        }
//    }
//
//    private static final int PICK_IMAGE_REQUEST = 1;
}