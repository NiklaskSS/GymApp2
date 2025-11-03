package com.example.gymapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Parcelable;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private EditText exerciseNameEditText, repsEditText, weightEditText;
    private Button saveWorkoutButton, historyButton, creditsButton, programsButton; // ADDED programsButton

    private ArrayList<Workout> workoutList;
    private Gson gson;

    private static final String PREFS_NAME = "GymAppPrefs";
    private static final String WORKOUT_LIST_KEY = "WorkoutList";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize views
        exerciseNameEditText = findViewById(R.id.exerciseNameEditText);
        repsEditText = findViewById(R.id.repsEditText);
        weightEditText = findViewById(R.id.weightEditText);
        saveWorkoutButton = findViewById(R.id.saveWorkoutButton);
        historyButton = findViewById(R.id.historyButton);
        creditsButton = findViewById(R.id.creditsButton);
        programsButton = findViewById(R.id.programsButton); // ADDED

        gson = new Gson();
        loadWorkouts();

        saveWorkoutButton.setOnClickListener(v -> saveWorkout());

        // ADDED Programs Button Click Listener
        programsButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ProgramsActivity.class);
            startActivity(intent);
        });

        historyButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, HistoryActivity.class);
            intent.putParcelableArrayListExtra("WORKOUT_LIST", workoutList);
            startActivity(intent);
        });

        creditsButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, CreditsActivity.class);
            startActivity(intent);
        });
    }

    private void loadWorkouts() {
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        String json = prefs.getString(WORKOUT_LIST_KEY, null);
        Type type = new TypeToken<ArrayList<Workout>>() {}.getType();
        workoutList = gson.fromJson(json, type);

        if (workoutList == null) {
            workoutList = new ArrayList<>();
        }
    }

    private void saveWorkout() {
        String exerciseName = exerciseNameEditText.getText().toString().trim();
        String reps = repsEditText.getText().toString().trim();
        String weight = weightEditText.getText().toString().trim();

        if (exerciseName.isEmpty() || reps.isEmpty() || weight.isEmpty()) {
            Toast.makeText(this, "Please fill out all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        String currentDate = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault()).format(new Date());
        Workout newWorkout = new Workout(exerciseName, reps, weight, currentDate);
        workoutList.add(0, newWorkout);

        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        String json = gson.toJson(workoutList);
        editor.putString(WORKOUT_LIST_KEY, json);
        editor.apply();

        exerciseNameEditText.setText("");
        repsEditText.setText("");
        weightEditText.setText("");

        Toast.makeText(this, "Workout Saved!", Toast.LENGTH_SHORT).show();
    }
}
