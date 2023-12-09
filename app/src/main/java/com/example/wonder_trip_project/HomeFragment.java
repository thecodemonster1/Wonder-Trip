package com.example.wonder_trip_project;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageException;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.database.ValueEventListener;


import java.io.File;
import java.io.IOException;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    private ImageView homeFragmentTopBarProfileImage;
    private TextView homeFragmentTopBarProfileText;
    private StorageReference storageRef;
    private DatabaseReference usersRef;
    private String userId;




    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
//    private static final String ARG_PARAM1 = "param1";
//    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String regUsername;
    private String regPassword;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param regUsername Parameter 1.
     * @param regPassword Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String regUsername, String regPassword) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString("regUsername", regUsername);
        args.putString("regPassword", regPassword);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (args != null) {
            regUsername = args.getString("regUsername");
            regPassword = args.getString("regPassword");


            // Now you have the username and password in HomeFragment
            // Do whatever you need to do with this data
            Log.d("MyApp", "username: " + regUsername );
            Log.d("MyApp", "password: " + regPassword );

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);

        // Initialize your views
        homeFragmentTopBarProfileImage = rootView.findViewById(R.id.homeFragmentTopBarProfileImage);
        homeFragmentTopBarProfileText = rootView.findViewById(R.id.homeFragmentTopBarProfileText);

//        Bundle args = getArguments();
//        if (args != null) {
//            regUsername = args.getString("regUsername");
//            regPassword = args.getString("regPassword");
////            Log.d("MyApp", "username: " + regUsername );
////            Log.d("MyApp", "password: " + regPassword );
//
//            // Now you have the username and password in HomeFragment
//            // Do whatever you need to do with this data
//
//
//        }

        FirebaseDatabase.getInstance().getReference("users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot userSnapshot : snapshot.getChildren()) {
                    String storedUsername = userSnapshot.child("username").getValue(String.class);
                    String storedPassword = userSnapshot.child("password").getValue(String.class);
                    String userId = userSnapshot.getKey();

                    Log.d("MyApp", "storedUsername: " + storedUsername);
                    Log.d("MyApp", "storedPassword: " + storedPassword);
                    Log.d("MyApp", "storedUserId: " + userId);


                    // Now you have the username and email for each user
                    // Do whatever you need to do with this data

//                        if (regUsername.equals(storedUsername) ){ //&& storedPassword.equals(regPassword)
//                            Log.d("MyApp2", "username: " + regUsername );
//                            Log.d("MyApp2", "password: " + regPassword );
//                            Log.d("MyApp2", "userID: " + userId );
//                        }
//                    try {
//                        File localFile = File.createTempFile(userId + "", ".jpg");
//                        storageRef.getFile(localFile)
//                                .addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
//                                    @Override
//                                    public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
//                                        Bitmap bitmap = BitmapFactory.decodeFile(localFile.getAbsolutePath());
//                                        homeFragmentTopBarProfileImage.setImageBitmap(bitmap);
//                                    }
//                                }).addOnFailureListener(new OnFailureListener() {
//                                    @Override
//                                    public void onFailure(@NonNull Exception e) {
//                                        if (e instanceof StorageException) {
//                                            ((StorageException) e).getErrorCode();
//                                        }// Object does not exist
//                                        Toast.makeText(getActivity(), "Failed to retrieve...", Toast.LENGTH_SHORT).show();
//                                    }
//                                });
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                    // Do something with the userId
//                    showToast("UserID: " + userId);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("MyApp", "Database Error: " + error.getMessage());
            }
        });


//        usersRef.orderByChild("username").equalTo(regUsername).addListenerForSingleValueEvent(new ValueEventListener() {
//
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
//                    // Check if the password matches
//
//                    // This code is for testing purpose
//                    String storedUsername = userSnapshot.child("username").getValue(String.class);
//                    String storedPassword = userSnapshot.child("password").getValue(String.class);
//                    Log.d("MyApp", "storedUsername: " + storedUsername);
//                    Log.d("MyApp", "storedPassword: " + storedPassword);
//
//                        String storedPassword = userSnapshot.child("password").getValue(String.class);
//                        assert regPassword != null;
//                        if (regPassword.equals(storedPassword)) {
//                            // Found a match
//                            userId = userSnapshot.getKey();
//
//                            // Add your image retrieval code here
//                            // Image Retrieval Code
//                            storageRef = FirebaseStorage.getInstance().getReference("images/" + userId + ".jpg");
//                            showToast("Path: " + storageRef);
//                            try {
//                                File localFile = File.createTempFile(userId + "", ".jpg");
//                                storageRef.getFile(localFile)
//                                        .addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
//                                            @Override
//                                            public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
//                                                Bitmap bitmap = BitmapFactory.decodeFile(localFile.getAbsolutePath());
//                                                homeFragmentTopBarProfileImage.setImageBitmap(bitmap);
//                                            }
//                                        }).addOnFailureListener(new OnFailureListener() {
//                                            @Override
//                                            public void onFailure(@NonNull Exception e) {
//                                                if (e instanceof StorageException) {
//                                                    ((StorageException) e).getErrorCode();
//                                                }// Object does not exist
//                                                Toast.makeText(getActivity(), "Failed to retrieve...", Toast.LENGTH_SHORT).show();
//                                            }
//                                        });
//                            } catch (IOException e) {
//                                e.printStackTrace();
//                            }
//                            // Do something with the userId
//                            showToast("UserID: " + userId);
//
//                            break;  // Assuming there is only one match
//                        }
//                }
//            }
//
//            //                @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//                // Handle errors
//                Log.e("MyApp", "Database Error: " + databaseError.getMessage());
//            }
//        });


        return rootView;
    }

    private void showToast(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    public void onClickableLayoutClick_goto_view_tile(){

    }
}