package com.example.wonder_trip_project;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
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
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.Objects;

public class SignInActivity extends AppCompatActivity {
    EditText txtUsername, txtEmail, txtPassword, txtPassword2, txtDOB, txtPhoneNum;
    Button btnSignUp;
    ImageView imgProfileView;
    FloatingActionButton btnImagePicker;
    String message;

//    FirebaseAuth mAuth;
//    ProgressBar progBar;

    DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference("users"); // Push the datas to "users" node
    String userId = usersRef.push().getKey(); // Generates a unique key for the new user

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        if (getSupportActionBar() != null) {
            Objects.requireNonNull(getSupportActionBar()).setBackgroundDrawable(new ColorDrawable(getColor(R.color.blue)));
        }

//=================================Input Fields (End)=================================
        txtUsername = findViewById(R.id.txtRegUsername);
        txtEmail = findViewById(R.id.txtRegEmail);
        txtPassword = findViewById(R.id.txtRegPassword);
        txtPassword2 = findViewById(R.id.txtRegPassword2);
        txtDOB = findViewById(R.id.txtRegDOB);
        btnSignUp = findViewById(R.id.btnSignUp);
        imgProfileView = findViewById(R.id.imgView);
        btnImagePicker = findViewById(R.id.btnImagePicker);
        txtPhoneNum = findViewById(R.id.txtPhoneNum);
//        progBar = findViewById(R.id.progressBar);

//=================================Input Fields (End)=================================


        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//=================================Input Variables (Begin)=================================
                String username = txtUsername.getText().toString();
                String email = txtEmail.getText().toString();
                String password = txtPassword.getText().toString();
                String password2 = txtPassword2.getText().toString();
                String dob = txtDOB.getText().toString();
                String phone = "+94"+txtPhoneNum.getText().toString();
//        progBar.setVisibility(View.VISIBLE);
//=================================Input Variables (End)=================================

//                System.out.println("Usrname: "+username + "\nEmail: " + email + "\nPassword: " + password + "\nPassword2: " + password2 + "\nDate Of Birth: " + dob);

                if (!username.isEmpty() && !email.isEmpty() && !password.isEmpty() && !password2.isEmpty() && !dob.isEmpty()){
                    if (password.equals(password2)){
                        Toast.makeText(SignInActivity.this, "Signing Up Successful", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(SignInActivity.this, MainActivity.class);

                        // Pushing the users data
                        usersRef.child(userId).child("username").setValue(username);
                        usersRef.child(userId).child("email").setValue(email);
                        usersRef.child(userId).child("password").setValue(password);
                        usersRef.child(userId).child("dob").setValue(dob);
                        usersRef.child(userId).child("phone").setValue(phone);

                        showLog("UserId: "+userId);
                        Toast.makeText(SignInActivity.this, "userID: "+userId, Toast.LENGTH_SHORT).show();
                        startActivity(intent);
                    }else {
                        Toast.makeText(SignInActivity.this, "Passwords are doesn't match", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(SignInActivity.this, "You missed one or more field to fill", Toast.LENGTH_SHORT).show();
                }

                if (ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(SignInActivity.this, new String[]{android.Manifest.permission.SEND_SMS}, 1);
                } else {
                    // send message
                    message = "Assalaamu alaikkum, world";
                    SmsManager smsManager = SmsManager.getDefault();
                    smsManager.sendTextMessage(phone, null, message, null, null);
                    showLog("Phone: "+phone+", Message: "+message);
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

//=================================Input Fields, Variables (Begin)=================================

        txtUsername = findViewById(R.id.txtRegUsername);
        txtEmail = findViewById(R.id.txtRegEmail);
        txtPassword = findViewById(R.id.txtRegPassword);
        txtDOB = findViewById(R.id.txtRegDOB);

        String username = txtUsername.getText().toString();
        String email = txtEmail.getText().toString();
        String password = txtPassword.getText().toString();
        String dob = txtDOB.getText().toString();

//=================================Input Fields, Variables (End)=================================

        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();

        System.out.println("userID: "+userId);

        assert data != null;
        if (resultCode == RESULT_OK) { // with error handling
            Uri uri = data.getData();
            imgProfileView.setImageURI(uri); // This code is setting the image to image view

//=================================Image Uploading Code (Begin)=================================
            // Use a unique identifier for each image, e.g., user's UID or a random string
            String imageName = (userId+".jpg");
            StorageReference imageRef = storageRef.child("images/profile/" + imageName);

            assert uri != null;
            UploadTask uploadTask = imageRef.putFile(uri);

            uploadTask.addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(@NonNull UploadTask.TaskSnapshot taskSnapshot) {
                    // Calculate the progress percentage
                    double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();

                    // Update your UI or log the progress
                    // For example, update a progress bar
                    int progressInt = (int) progress;
//                    progressBar.setProgress(progressInt);
                }
            });
//=================================Image Uploading Code (End)=================================

        } else {
            // Handle the case where the image picker did not return a valid result
            Toast.makeText(this, "Image selection canceled", Toast.LENGTH_SHORT).show();
        }
    }

    public void showLog(String msg){
        Log.d("MyApp", msg);
    }

    public void welcomeMessage(){
        // Get Firebase Messaging instance
        FirebaseMessaging messaging = FirebaseMessaging.getInstance();

        // Set Firebase Messaging sender ID (replace with your server key)
//        messaging.setFirebaseMessagingSenderId("BA1uRy74CP_EAmtYWu5Ou82OC61y5bz7PoW284BZwHmG0pI6lhNth3h3Wi69MIcqR16KGTub0FGiHMNgAkKhhaI");
//        messaging.setFirebaseMessagingSenderId("BA1uRy74CP_EAmtYWu5Ou82OC61y5bz7PoW284BZwHmG0pI6lhNth3h3Wi69MIcqR16KGTub0FGiHMNgAkKhhaI");

        // Subscribe to the "welcome" topic for receiving welcome messages
        messaging.subscribeToTopic("welcome from wonder-trip");

    }
}