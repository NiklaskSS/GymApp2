package com.example.gymapp;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

public class CreditsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credits);

        // Enable the "Up" button to go back to MainActivity
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    // Handle the "Up" button press
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
