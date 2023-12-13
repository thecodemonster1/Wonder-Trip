package com.example.wonder_trip_project;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageException;
import com.google.firebase.storage.StorageReference;

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
    private DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference("users");
    private int i = 0;

    // Assuming you have a Firebase reference


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
//    private static final String ARG_PARAM1 = "param1";
//    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String regUsername;
    private String regPassword;
    private String userId;

    public HomeFragment(String userId) {
        this.userId = userId;
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
    public static HomeFragment newInstance(String regUsername, String regPassword, String userId) {
        HomeFragment fragment = new HomeFragment(userId);
        Bundle args = new Bundle();
        args.putString("regUsername", regUsername);
        args.putString("regPassword", regPassword);
        args.putString("userId", userId);
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
            userId = args.getString("userId");

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);



        usersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot userSnapshot : snapshot.getChildren()) {
//                    // Initialize your variables
//                    homeFragmentTopBarProfileImage = rootView.findViewById(R.id.homeFragmentTopBarProfileImage);
//                    homeFragmentTopBarProfileText = rootView.findViewById(R.id.homeFragmentTopBarProfileText);

                    String storedUsername = userSnapshot.child("username").getValue(String.class);
                    String storedPassword = userSnapshot.child("password").getValue(String.class);
//                    Utils.showLog(storedUsername + ": "+ storedPassword);

                    if (storedUsername.equals(regUsername) && storedPassword.equals(regPassword)) {
                        userId = userSnapshot.getKey(); // Update user ID if found
//                        homeFragmentTopBarProfileText.setText(storedUsername); // Set username text  - userSnapshot.child("username").getValue(String.class)
                        Utils.showLog("Username is " + storedUsername);

//                        for (DataSnapshot journalSnapshot: userSnapshot.child("journals").getChildren()){
//                            String journalId = userSnapshot.child("journals").getValue(String.class);
//                            Utils.showLog("JournalId is " + journalSnapshot.child(journalId));
//                            // if this is working you can do whatever with journalId
//                        }

//                        imageRetrivalFunc(userId); // Download profile image
//                        firebaseJournalId(userId, i);
                        break; // Stop iterating after finding a match
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Utils.showLog("Database Error: " + error.getMessage(), "e");
            }
        });

        tileHandler(rootView);
//        firebaseContentId(userId);
//        editJournalOnButtonClick_Goto_add(rootView);
        return rootView;
    }

    private void tileHandler(@NonNull View rootView) {
        // Example: Set click listener for the first tile
        rootView.findViewById(R.id.tile1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Handle click for tile 1
                handleTileClick("tile1");
            }
        });

        // Example: Set click listener for the second tile
        rootView.findViewById(R.id.tile2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Handle click for tile 2
                handleTileClick("tile2");
            }
        });

        rootView.findViewById(R.id.tile3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Handle click for tile 3
                handleTileClick("tile3");
            }
        });

        rootView.findViewById(R.id.tile4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Handle click for tile 4
                handleTileClick("tile4");
            }
        });

        rootView.findViewById(R.id.tile5).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Handle click for tile 5
                handleTileClick("tile5");
            }
        });

        rootView.findViewById(R.id.tile6).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Handle click for tile 6
                handleTileClick("tile6");
            }
        });

        rootView.findViewById(R.id.tile7).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Handle click for tile 7
                handleTileClick("tile7");
            }
        });

        rootView.findViewById(R.id.tile8).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Handle click for tile 8
                handleTileClick("tile8");
            }
        });

    }

    private void firebaseJournalId(String userId, int a){
        DatabaseReference journalRef = usersRef.child(userId).child("journals");
        int finalA = a++;
        journalRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot userSnapshot : snapshot.getChildren()){
                    String journalId = userSnapshot.getKey();
                    Log.d("NewApp", "journalId_"+ finalA +": "+journalId);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Utils.showLog("Database Error: " + error.getMessage(), "e");

            }
        });

//        homeFragmentTopBarProfileText.setText(journalsRef);

    }

    private void imageRetrivalFunc(String userId) {
        storageRef = FirebaseStorage.getInstance().getReference("images/" + userId + ".jpg");
        try {
            File localFile = File.createTempFile(userId + "", ".jpg");
            storageRef.getFile(localFile)
                    .addOnSuccessListener(taskSnapshot -> { // Use lambda expressions for concise code
                        Bitmap bitmap = BitmapFactory.decodeFile(localFile.getAbsolutePath());
                        homeFragmentTopBarProfileImage.setImageBitmap(bitmap);
                    })
                    .addOnFailureListener(e -> {
                        if (e instanceof StorageException) {
                            ((StorageException) e).getErrorCode();
                        }
                        Utils.showLog("Failed to retrieve image...");
                    });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void editJournalOnButtonClick_Goto_add(View view) {
        view.findViewById(R.id.editContentFab).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // pass the details of the journal to AddFragment
            }
        });
//        replaceFragment(new AddFragment(userId), R.id.add);
    }

    private void handleTileClick(String tileId) {
        // TODO: Retrieve data from Firebase based on tileId
        // For example, fetch data from Firebase Database using tileId

        // Navigate to fragment_home_tile_view.xml with the fetched data
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, HomeTileViewFragment.newInstance(tileId, "Assalaamu alaikkum"));
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}