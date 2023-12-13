package com.example.wonder_trip_project;

import static com.example.wonder_trip_project.Utils.showLog;

import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class AddFragment extends Fragment {

    private TextView dateTextView, txtContentRate;
    private DatePicker datePicker;
    private ImageView datePickImage;
    private NumberPicker numberPicker;
    FloatingActionButton saveContentBtn;
    TextInputEditText txtContentTitle, txtContentText;
    HomeFragment homeFragment;
    String contentTitle, dateText, contentRate, contentText, userId2;

//    String userId2 = "-Nl4N9BTlJb7Ycc-l3X8" ;



    DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
//
//    // Reference to the "journals" node
//    DatabaseReference journalsRef = rootRef.child("users").child("Nl4N9BTlJb7Ycc-l3X8").child("journals");

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "regUsername";
    private static final String ARG_PARAM2 = "userId";

    // TODO: Rename and change types of parameters
    private String regUsername;
    private String userId;

//    public AddFragment() {
//        // Required empty public constructor
//    }


    public AddFragment(String userId) {
        this.userId = userId;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param regUsername Parameter 1.
     * @param userId Parameter 2.
     * @return A new instance of fragment AddFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AddFragment newInstance(String regUsername, String userId) {
        AddFragment fragment = new AddFragment(userId);
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, regUsername);
        args.putString(ARG_PARAM2, userId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle args = getArguments();
        if (args != null) {
            regUsername = args.getString(ARG_PARAM1);
            userId = args.getString(ARG_PARAM2);
//            firebaseContentId(userId2);

        }
//        if (userId != null){
//            userId2 = userId;
//            Log.d("MyApp", "AddFragment_userId_from onCreate: "+ userId2);
//        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add, container, false);

        // Initialize views
        datePicker = view.findViewById(R.id.datePicker);
        datePickImage = view.findViewById(R.id.txtContentDate);
        numberPicker = view.findViewById(R.id.numberPicker);
        dateTextView = view.findViewById(R.id.dateTextView);
        txtContentRate = view.findViewById(R.id.txtContentRate);
        txtContentText = view.findViewById(R.id.txtContentText);
        txtContentTitle = view.findViewById(R.id.txtContentTitle);
        saveContentBtn = view.findViewById(R.id.saveContentFab);

//        Log.d("MyApp", "onCreateView is working");

//        contentTitle = txtContentTitle.getText().toString();
//        dateText = dateTextView.getText().toString();
//        contentRate = txtContentRate.getText().toString();
//        contentText = txtContentText.getText().toString();



//        if (userId != null){
//            userId2 = userId;
//            Log.d("MyApp", "AddFragment_userId_from onCreate: "+ userId2);
//        }
//        Log.d("MyApp", "AddFragment_userId_from outside of onClick method: "+ userId2);




//        firebaseContentId(userId);

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


        saveContentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                contentTitle = txtContentTitle.getText().toString();
                dateText = dateTextView.getText().toString();
                contentRate = txtContentRate.getText().toString();
                contentText = txtContentText.getText().toString();

//                imageUpload(imgContent, uri);
                onButtonClick_saveContents(v,userId, contentTitle, dateText, contentRate, contentText);


            }
        });

        return view;
    }

    // Handle click on Number Picker
    public void onNumberPicker(View view) {

        // Set the decimal values as strings
        String[] displayedValues = {"0.5", "1.0", "1.5", "2.0", "2.5", "3.0", "3.5", "4.0", "4.5", "5.0"};

        // Set the Values
        numberPicker.setMinValue(0);
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

    public void onButtonClick_saveContents(View view,String userId, String title, String date, String rate, String journal) {

        // Check if userId is not null
        if (userId != null) {
//                    showLog("AddFragment_userId_from onCreateView_inside if() : "+ userId);
            // Update the reference to the correct location in the database
            DatabaseReference journalsRef = rootRef.child("users").child(userId).child("journals");

            // Use push() to generate a unique key for the new journal entry
            String journalId = journalsRef.push().getKey();

//                    homeFragment = HomeFragment.newInstance()

            // Create a new entry in the "journals" node under the specific user
            journalsRef.child(journalId).child("title").setValue(title);
            journalsRef.child(journalId).child("text").setValue(journal);
            journalsRef.child(journalId).child("date").setValue(date);
            journalsRef.child(journalId).child("rate").setValue(rate);

            



            showLog("if input fields are working, "+
                    "\nuserId: " + userId +
                    "\ncontentTitle: " + title +
                    "\ndateText: " + date +
                    "\ncontentRate: " + rate +
                    "\ncontentText: " + journal);

        } else {
            // Handle the case where userId is null
            showLog("userId is null", "e");
        }

//        Log.d("MyApp", "Title: "+title+"\nJournal Text: "+journal);
        Log.d("MyApp", "Working Save Content FAB");
    }

    public void imageUpload(@NonNull ImageView imgContent, Uri uri) {
        // Assuming you have received the Uri as a parameter instead of relying on the global 'data' object

        // Set the image to the ImageView
        imgContent.setImageURI(uri);

        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();

        Log.d("MyApp", "userID: " + userId);

        // Use a unique identifier for each image, e.g., user's UID or a random string
        String imageName = ("Journal_" + userId + ".jpg");
        StorageReference imageRef = storageRef.child("images/journals/" + imageName);

        UploadTask uploadTask = imageRef.putFile(uri);

        uploadTask.addOnProgressListener(taskSnapshot -> {
            // Calculate the progress percentage
            double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();

            // Update your UI or log the progress
            // For example, update a progress bar
            int progressInt = (int) progress;
            Log.d("MyApp", "Upload progress: " + progressInt + "%");
        });

        uploadTask.addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                // Image upload successful
                Log.d("MyApp", "Image is uploaded");
            } else {
                // Image upload failed
                Log.e("MyApp", "Image upload failed: " + task.getException());
            }
        });
    }

// Call this method from your fragment passing the required parameters
// For example, in your fragment code:
// imageUpload(imgContent, uri);


}