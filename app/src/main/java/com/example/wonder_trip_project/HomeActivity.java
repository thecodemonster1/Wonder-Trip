package com.example.wonder_trip_project;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.wonder_trip_project.databinding.ActivityHomeBinding;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class HomeActivity extends AppCompatActivity {

    ActivityHomeBinding binding;
    ImageView imgProfileView;


    DatabaseReference contentRef = FirebaseDatabase.getInstance().getReference("journals"); // Push the datas to "users" node
    String contentId = contentRef.push().getKey();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        replaceFragment(new HomeFragment(), R.id.home);

        binding.bottomNavigationView.setOnItemSelectedListener(item -> {

            int itemId = item.getItemId();
            // switch case is not working... But if conditions are...
            if (itemId == R.id.home) {
                replaceFragment(new HomeFragment(), itemId);
            } else if (itemId == R.id.profile) {
                replaceFragment(new ProfileFragment(), itemId);
            } else if (itemId == R.id.add) {
                replaceFragment(new AddFragment(), itemId);
            } else if (itemId == R.id.settings) {
                replaceFragment(new SettingsFragment(), itemId);
            }

            return true;
        });
    }


    private void replaceFragment(Fragment fragment, int itemId) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

        binding.bottomNavigationView.getMenu().findItem(itemId).setChecked(true);
    }


    public void onLayoutClick_goto_view_tile(View view) {
        replaceFragment(new HomeTileViewFragment(), R.id.home);
        Log.d("MyApp", "No any errors in Fragment Changing");
    }

    public void onButtonClick_Goto_add(View view) {
        replaceFragment(new AddFragment(), R.id.add);
        Log.d("MyApp", "No any errors in Fragment Changing");
    }

    public void onImageClick_addImage(View view){
        ImagePicker.with(HomeActivity.this)
                .crop()	    			//Crop image(Optional), Check Customization for more option
                .compress(1024)			//Final image size will be less than 1 MB(Optional)
                .maxResultSize(1080, 1080)	//Final image resolution will be less than 1080 x 1080(Optional)
                .start();
    }

    public void onButtonClick_saveContents(View view) {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, 1);
        Log.d("MyApp", "Working Save Content FAB");

    }
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


//=================================Input Fields, Variables (End)=================================

        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();

        Log.d("Image", "userID: "+contentId);

        assert data != null;
        if (resultCode == RESULT_OK) { // with error handling
            Uri uri = data.getData();
            imgProfileView.setImageURI(uri); // This code is setting the image to image view

//=================================Image Uploading Code (Begin)=================================
            // Use a unique identifier for each image, e.g., user's UID or a random string
            String imageName = ("Journal_"+contentId+".jpg");
            StorageReference imageRef = storageRef.child("images/journals/" + imageName);

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
            Log.d("Image", "Image selection canceled");
        }
    }

}