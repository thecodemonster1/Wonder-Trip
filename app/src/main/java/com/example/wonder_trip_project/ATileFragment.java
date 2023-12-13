package com.example.wonder_trip_project;

import static com.example.wonder_trip_project.Utils.showLog;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ATileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ATileFragment extends Fragment {

    private String journalId;

    DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ATileFragment(String journalId) {
        // Required empty public constructor
        this.mParam1 = journalId;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ATileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ATileFragment newInstance(String param1, String param2) {
        ATileFragment fragment = new ATileFragment(param1);
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_a_tile, container, false);

        // Retriving the every journalsId from the firebase
        DatabaseReference journalsRef = rootRef.child("users").child(mParam1).child("journals");
        ArrayList<String> journalIds = new ArrayList<>();
        journalsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                String[] journalIdArr = new String[(int) snapshot.getChildrenCount()]; // Pre-allocate space based on child count

                for (DataSnapshot childSnapshot : snapshot.getChildren()) {
                    String journalId = childSnapshot.getKey();
                    journalIds.add(journalId);
                }

//                for (String journalId: journalIds){
//                for (int i = 0; i < journalIds.size(); i++){
                    showLog("Journal ID: " + journalIds);
//                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                showLog("Error retrieving data: " + error.getMessage());
            }
        });


        return view;
    }
}