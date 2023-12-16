package com.example.wonder_trip_project;

import static com.example.wonder_trip_project.Utils.showLog;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    private StorageReference storageRef;
    private RecyclerView recyclerView;
    private ArrayList<JournalModel> journalList;
    private MyAdapter adapter;
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
        View view = inflater.inflate(R.layout.fragment_home, container, false);



//        tileHandler(view);
//        firebaseContentId(userId);
//        editJournalOnButtonClick_Goto_add(view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.homeRecycleView);
        journalList = new ArrayList<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new MyAdapter(getContext(), journalList);
        recyclerView.setAdapter(adapter);

        usersRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot userSnapshot : snapshot.getChildren()) {
                    // Initialize your variables
                    String storedUsername = userSnapshot.child("username").getValue(String.class);
                    String storedPassword = userSnapshot.child("password").getValue(String.class);

                    // Access the user's journals
                    DataSnapshot journalsSnapshot = userSnapshot.child("journals");

                    if (storedUsername.equals(regUsername) && storedPassword.equals(regPassword)) {

                        // Get the user ID
//                        String userId = userSnapshot.getKey();
                        showLog("userId: " + userId);
                        // Loop through each journal
//                        journalList.clear(); // Clear existing list before adding new items
                        for (DataSnapshot journalSnapshot : journalsSnapshot.getChildren()) {

                            // Retrieve journal data
                            String title = journalSnapshot.child("title").getValue(String.class);
                            String date = journalSnapshot.child("date").getValue(String.class);
                            String rate = journalSnapshot.child("rate").getValue(String.class);

                            // Create a JournalModel object
                            JournalModel journal = new JournalModel(title, date, rate);

                            // Add the journal to your list
                            journalList.add(journal);

//                            DatabaseReference journalRef = FirebaseDatabase.getInstance().getReference("users/"+userId+"/journals");
                            showLog("journalId: "+ journalSnapshot.getKey());
                            showLog("title: "+ title);
                            showLog("date: "+ date);
                            showLog("rate: "+ rate);


                        }
                        // Update your adapter with the updated journal list
                        adapter.notifyDataSetChanged();
                        break;

//                        for (DataSnapshot journalSnapshot: snapshot.child(userId).child("journals").getChildren()){
//
//                            String journalId = journalSnapshot.getKey();
//                            homeRecyclerView = view.findViewById(R.id.homeRecycleView);
//
//                            LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
//                            homeRecyclerView.setLayoutManager(layoutManager);
//
//                            FirebaseRecyclerOptions<JournalModel> options =
//                                    new FirebaseRecyclerOptions.Builder<JournalModel>()
//                                            .setQuery(usersRef.child(userId).child("journals").child(journalId), JournalModel.class)
//                                            .build();
//
//                            homeRecyclerView.setAdapter(new MainAdapter(options));
//                            showLog("journalId: " + journalSnapshot.getKey());
//                        }
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    //    private void tileHandler(@NonNull View rootView) {
//        // Example: Set click listener for the first tile
//        rootView.findViewById(R.id.title1).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                // Handle click for tile 1
//                handleTileClick("tile1");
//            }
//        });
//
//        // Example: Set click listener for the second tile
//        rootView.findViewById(R.id.tile2).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                // Handle click for tile 2
//                handleTileClick("tile2");
//            }
//        });
//
//        rootView.findViewById(R.id.tile3).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                // Handle click for tile 3
//                handleTileClick("tile3");
//            }
//        });
//
//        rootView.findViewById(R.id.tile4).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                // Handle click for tile 4
//                handleTileClick("tile4");
//            }
//        });
//
//        rootView.findViewById(R.id.tile5).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                // Handle click for tile 5
//                handleTileClick("tile5");
//            }
//        });
//
//        rootView.findViewById(R.id.tile6).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                // Handle click for tile 6
//                handleTileClick("tile6");
//            }
//        });
//
//        rootView.findViewById(R.id.tile7).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                // Handle click for tile 7
//                handleTileClick("tile7");
//            }
//        });
//
//        rootView.findViewById(R.id.tile8).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                // Handle click for tile 8
//                handleTileClick("tile8");
//            }
//        });
//
//    }

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
                showLog("Database Error: " + error.getMessage(), "e");

            }
        });

    }



    private void handleTileClick(String tileId) {
        // TODO: Retrieve data from Firebase based on tileId
        // For example, fetch data from Firebase Database using tileId

        // Navigate to fragment_home_tile_view.xml with the fetched data
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
//        fragmentTransaction.replace(R.id.frame_layout, HomeTileViewFragment.newInstance(tileId, "Assalaamu alaikkum"));
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}