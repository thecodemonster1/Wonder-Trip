package com.example.wonder_trip_project;

import static com.example.wonder_trip_project.Utils.showLog;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class AddActivity extends AppCompatActivity {


    TextView dateTextView, txtContentRate;
    DatePicker datePicker;
    ImageView datePickImage;
    NumberPicker numberPicker;
    FloatingActionButton saveContentBtn;
    TextInputEditText txtContentTitle, txtContentText;
    HomeFragment homeFragment;
    String contentTitle, dateText, contentRate, contentText, userId2;
    DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);


        // Initialize views
        datePicker = findViewById(R.id.datePicker);
        datePickImage = findViewById(R.id.txtContentDate);
        numberPicker = findViewById(R.id.numberPicker);
        dateTextView = findViewById(R.id.dateTextView);
        txtContentRate = findViewById(R.id.txtContentRate);
        txtContentText = findViewById(R.id.txtContentText);
        txtContentTitle = findViewById(R.id.txtContentTitle);
        saveContentBtn = findViewById(R.id.saveContentFab);
    }


    private void setDatePicker(){
        datePickImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
        });
    }

    private void setRateNumberPicker(){
        txtContentRate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
        });
    }

    private void saveContents(String userId){
        saveContentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                contentTitle = txtContentTitle.getText().toString();
                dateText = dateTextView.getText().toString();
                contentRate = txtContentRate.getText().toString();
                contentText = txtContentText.getText().toString();

//                imageUpload(imgContent, uri);
                // Check if userId is not null
                if (userId != null) {
//                    showLog("AddFragment_userId_from onCreateView_inside if() : "+ userId);
                    // Update the reference to the correct location in the database
                    DatabaseReference journalsRef = rootRef.child("users").child(userId).child("journals");

                    // Use push() to generate a unique key for the new journal entry
                    String journalId = journalsRef.push().getKey();

//                    homeFragment = HomeFragment.newInstance()

                    // Create a new entry in the "journals" node under the specific user
                    journalsRef.child(journalId).child("title").setValue(contentTitle);
                    journalsRef.child(journalId).child("text").setValue(contentText);
                    journalsRef.child(journalId).child("date").setValue(dateText);
                    journalsRef.child(journalId).child("rate").setValue(contentRate);





                    showLog("if input fields are working, "+
                            "\nuserId: " + userId +
                            "\ncontentTitle: " + contentTitle +
                            "\ndateText: " + dateText +
                            "\ncontentRate: " + contentRate +
                            "\ncontentText: " + contentText);

                } else {
                    // Handle the case where userId is null
                    showLog("userId is null", "e");
                }

//        Log.d("MyApp", "Title: "+title+"\nJournal Text: "+journal);
                Log.d("MyApp", "Working Save Content FAB");

            }
        });
    }

    public void imageUpload(@NonNull ImageView imgContent, Uri uri, String userId) {
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

        // Call this method from your fragment passing the required parameters
        // For example, in your fragment code:
        // imageUpload(imgContent, uri);
    }

    public void onImageClick_addImage(){
        ImagePicker.with(AddActivity.this)
                .crop()	    			//Crop image(Optional), Check Customization for more option
                .compress(1024)			//Final image size will be less than 1 MB(Optional)
                .maxResultSize(1080, 1080)	//Final image resolution will be less than 1080 x 1080(Optional)
                .start();
    }



}