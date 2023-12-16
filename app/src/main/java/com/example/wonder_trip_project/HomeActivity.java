package com.example.wonder_trip_project;

import static com.example.wonder_trip_project.Utils.showLog;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.wonder_trip_project.databinding.ActivityHomeBinding;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageException;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;

public class HomeActivity extends AppCompatActivity {

    ActivityHomeBinding binding;
    ImageView imgContent;
    String userId, regUsername, regPassword;
    StorageReference storageRef;
    DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference("users");


//    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Intent intent = getIntent();
        userId = intent.getStringExtra("userId");
        regUsername = intent.getStringExtra("regUsername");
        regPassword = intent.getStringExtra("regPassword");

        addJournal_btn();
        viewJournal_btn(userId);
        profile_btn();
        settings_btn();







    }



    private void imageRetrivalFunc(String userId) {
        storageRef = FirebaseStorage.getInstance().getReference("images/" + userId + ".jpg");
        try {
            File localFile = File.createTempFile(userId + "", ".jpg");
            storageRef.getFile(localFile)
                    .addOnSuccessListener(taskSnapshot -> { // Use lambda expressions for concise code
                        Bitmap bitmap = BitmapFactory.decodeFile(localFile.getAbsolutePath());
//                        homeFragmentTopBarProfileImage.setImageBitmap(bitmap);
                    })
                    .addOnFailureListener(e -> {
                        if (e instanceof StorageException) {
                            ((StorageException) e).getErrorCode();
                        }
                        showLog("Failed to retrieve image...");
                    });
        } catch (IOException e) {
            e.printStackTrace();
        }



        //        imgContent = findViewById(R.id.imgContent);
//
//        FirebaseStorage storage = FirebaseStorage.getInstance();
//        StorageReference storageRef = storage.getReference();
//
//        Log.d("MyApp", "userID: "+contentId);
//
//        assert data != null;
//        if (resultCode == RESULT_OK) { // with error handling
//            Uri uri = data.getData();
//            imgContent.setImageURI(uri); // This code is setting the image to image view
//
////=================================Image Uploading Code (Begin)=================================
//            // Use a unique identifier for each image, e.g., user's UID or a random string
//            String imageName = ("Journal_"+contentId+".jpg");
//            StorageReference imageRef = storageRef.child("images/journals/" + imageName);
//
//            assert uri != null;
//            UploadTask uploadTask = imageRef.putFile(uri);
//
//            uploadTask.addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
//                @Override
//                public void onProgress(@NonNull UploadTask.TaskSnapshot taskSnapshot) {
//                    // Calculate the progress percentage
//                    double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
//
//                    // Update your UI or log the progress
//                    // For example, update a progress bar
//                    int progressInt = (int) progress;
////                    progressBar.setProgress(progressInt);
//                    Log.d("MyApp", "Upload progress: " + progressInt + "%");
//                }
//            });
//            Log.d("MyApp", "Image is uploaded");
////=================================Image Uploading Code (End)=================================
//
//        } else {
//            // Handle the case where the image picker did not return a valid result
//            Toast.makeText(this, "Image selection canceled", Toast.LENGTH_SHORT).show();
//            Log.d("MyApp", "Image selection canceled");
//        }
    }

//    public void editJournalOnButtonClick_Goto_add() {
//        findViewById(R.id.editContentFab).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick() {
//                // pass the details of the journal to AddFragment
//            }
//        });
////        replaceFragment(new AddFragment(userId), R.id.add);
//    }


    public void onLayoutClick_goto_view_tile(View view) {
//        replaceFragment(new HomeTileViewFragment(), R.id.home);
    }

    public void saveSettingsFab_Action(View view){
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }

    private void addJournal_btn(){

    }

    private void viewJournal_btn(String userId) {
        Intent i = new Intent(getApplicationContext(), ViewActivity.class);
        i.putExtra("userId", userId);
        startActivity(i);
    }

    private void profile_btn(){

    }



    public void settings_btn() {
    }
}