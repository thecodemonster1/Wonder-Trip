package com.example.wonder_trip_project;

import android.os.Build;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddFragment extends Fragment {

    private TextView dateTextView;
    private DatePicker datePicker;
    private ImageView datePickImage;
    private NumberPicker numberPicker;
    private TextView txtContentRate;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AddFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AddFragment newInstance(String param1, String param2) {
        AddFragment fragment = new AddFragment();
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
        View view = inflater.inflate(R.layout.fragment_add, container, false);

        // Initialize views
        dateTextView = view.findViewById(R.id.dateTextView);
        datePicker = view.findViewById(R.id.datePicker);
        datePickImage = view.findViewById(R.id.txtContentDate);
        numberPicker = view.findViewById(R.id.numberPicker);
        txtContentRate = view.findViewById(R.id.txtContentRate);

        // Set initial visibility
        datePicker.setVisibility(View.GONE);

        // Set click listener for dateTextView
        datePickImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onDateClick(v);
            }
        });



        txtContentRate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onNumberPicker(v);
            }
        });

        return view;
    }

    // Handle click on Number Picker
    public void onNumberPicker(View view) {

        // Set the decimal values as strings
        String[] displayedValues = {"0.5", "1.0", "1.5", "2.0", "2.5", "3.0", "3.5", "4.0", "4.5", "5.0"};

        // Set the Values
        numberPicker.setMinValue(1);
        numberPicker.setMaxValue(displayedValues.length - 1);
        numberPicker.setDisplayedValues(displayedValues);
        numberPicker.setValue(1);

        // Toggle visibility of the NumberPicker
        if (numberPicker.getVisibility() == View.VISIBLE) {
            numberPicker.setVisibility(View.GONE);
        } else {
            numberPicker.setVisibility(View.VISIBLE);

            // Set a listener to react to value changes
            numberPicker.setOnValueChangedListener((picker, oldVal, newVal) -> {
                // Handle the selected value
                // Convert the selected index to the corresponding decimal value
                double selectedValue = Double.parseDouble(displayedValues[newVal]);

                // newVal contains the currently selected number
                txtContentRate.setText(String.valueOf(selectedValue));
            });
        }
    }

    // Handle click on date TextView
    public void onDateClick(View view) {
        // Toggle visibility of the DatePicker
        if (datePicker.getVisibility() == View.VISIBLE) {
            datePicker.setVisibility(View.GONE);
        } else {
            datePicker.setVisibility(View.VISIBLE);

            // Set a listener for date picker to update dateTextView when a date is selected
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                datePicker.setOnDateChangedListener(new DatePicker.OnDateChangedListener() {
                    @Override
                    public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        // Update dateTextView with the selected date
                        String selectedDate = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
                        dateTextView.setText(selectedDate);
                    }
                });
            }
        }
    }

}