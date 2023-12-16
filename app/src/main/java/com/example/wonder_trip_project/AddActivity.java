package com.example.wonder_trip_project;

import static com.example.wonder_trip_project.Utils.showLog;
import static com.example.wonder_trip_project.Utils.showToast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.Calendar;

public class AddActivity extends AppCompatActivity {


    TextView dateTextView, txtContentRate;
    DatePicker datePicker;
    ImageView datePickImage, imgContentRate, imgContentJournal;
    NumberPicker numberPicker;
    FloatingActionButton saveContentBtn;
    TextInputEditText txtContentTitle, txtContentText;
    String contentTitle, dateText, contentRate, contentText, userId;
    DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        Intent intent = getIntent();
        userId = intent.getStringExtra("userId");

        // Initialize views
        datePicker = findViewById(R.id.datePicker);
        datePickImage = findViewById(R.id.txtContentDate);
        numberPicker = findViewById(R.id.numberPicker);
        dateTextView = findViewById(R.id.dateTextView);
        txtContentRate = findViewById(R.id.txtContentRate);
        txtContentText = findViewById(R.id.txtContentText);
        txtContentTitle = findViewById(R.id.txtContentTitle);
        saveContentBtn = findViewById(R.id.saveContentFab);
        imgContentRate = findViewById(R.id.imgContentRate);
        imgContentJournal = findViewById(R.id.imgContentJournal);

        setDatePicker();
        setRateNumberPicker();
        saveContents(userId);
        onImageClick_addImage();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ImagePicker.REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Uri imageUri = data.getData();
                Glide.with(this)
                        .load(imageUri)
                        .into(imgContentJournal);
                // Set the image to the ImageView using the URI
                imgContentJournal.setImageURI(imageUri);
            } else {
                // Handle error or cancellation
                showToast(this, "Image selection failed.");
            }
        }
    }


    private void setDatePicker(){

        // Get today's date
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

        // Set default date value
        String defaultDate = year + "-" + (month + 1) + "-" + dayOfMonth;
        dateTextView.setText(defaultDate);


        dateTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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

                                Calendar selectedDateCal = Calendar.getInstance();
                                selectedDateCal.set(year, monthOfYear, dayOfMonth);

                                Calendar todayCal = Calendar.getInstance();

                                // Check if selected date is before or equal to today
                                if (selectedDateCal.before(todayCal) || selectedDateCal.equals(todayCal)) {
                                    String selectedDate = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
                                    dateTextView.setText(selectedDate);
                                }
                            }
                        });
                    }
                }
            }
        });

        datePickImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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

                                Calendar selectedDateCal = Calendar.getInstance();
                                selectedDateCal.set(year, monthOfYear, dayOfMonth);

                                Calendar todayCal = Calendar.getInstance();

                                // Check if selected date is before or equal to today
                                if (selectedDateCal.before(todayCal) || selectedDateCal.equals(todayCal)) {
                                    String selectedDate = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
                                    dateTextView.setText(selectedDate);
                                }
                            }
                        });
                    }
                }
            }
        });
    }

    private void setRateNumberPicker(){

        txtContentRate.setText("5.0");
        imgContentRate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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

        imgContentJournal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImagePicker.with(AddActivity.this)
                        .crop()
                        .compress(1024)
                        .maxResultSize(1080, 1080)
                        .start();
            }
        });

//        https://firebasestorage.googleapis.com/v0/b/wondertrip0.appspot.com/o/images%2Fjournals%2FJournal_+journalId+".jpg?alt=media";

//        https://firebasestorage.googleapis.com/v0/b/wondertrip0.appspot.com/o/images%2Fjournals%2FJournal_-NlMnmT7kw7BfArpunub.jpg?alt=media
//        https://firebasestorage.googleapis.com/v0/b/wondertrip0.appspot.com/o/images%2Fjournals%2FJournal_-NlMo668BxmppSp_NJuw.jpg?alt=media
//        https://firebasestorage.googleapis.com/v0/b/wondertrip0.appspot.com/o/images%2Fjournals%2FJournal_-NlNPYMallpNS83pZdeu.jpg?alt=media
    }



}