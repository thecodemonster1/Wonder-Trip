package com.example.wonder_trip_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.example.wonder_trip_project.R;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.example.wonder_trip_project.databinding.ActivityHomeBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeActivity extends AppCompatActivity {

    ActivityHomeBinding binding;

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


    public void onClickableLayoutClick_goto_view_tile(View view) {
        replaceFragment(new AddFragment(), R.id.add);
        Log.d("MyApp", "No any errors in Fragment Changing");

    }
}