package com.example.wonder_trip_project;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TileFragment extends Fragment {

    private TextView titleTextView;
    private TextView dateTextView;
    private TextView rateTextView;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public TileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TileFragment newInstance(String param1, String param2) {
        TileFragment fragment = new TileFragment();
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
        View view = inflater.inflate(R.layout.fragment_tile, container, false);

        titleTextView = view.findViewById(R.id.titleView_of_a_tile);
        dateTextView = view.findViewById(R.id.dateView_of_a_tile);
        rateTextView = view.findViewById(R.id.rateView_of_a_tile);

        // ... Bind additional views and setup logic

        return view;
    }

    public void setTitle(String title) {
        titleTextView.setText(title);
    }

    public void setDate(String date) {
        dateTextView.setText(date);
    }

    public void setRate(String rate) {
        rateTextView.setText(rate);
    }
}