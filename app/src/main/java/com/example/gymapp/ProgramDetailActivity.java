package com.example.gymapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.EditText;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class ProgramDetailActivity extends AppCompatActivity {

    private ArrayList<Program> allPrograms;
    private Program currentProgram;
    private ExerciseAdapter adapter;
    private Gson gson;

    private static final String PREFS_NAME = "GymAppPrefs";
    private static final String PROGRAMS_LIST_KEY = "ProgramsList";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_program_detail);

        gson = new Gson();
        loadPrograms();

        long programId = getIntent().getLongExtra("PROGRAM_ID", -1);
        currentProgram = findProgramById(programId);

        if (currentProgram != null) {
            setTitle(currentProgram.getName());
            RecyclerView recyclerView = findViewById(R.id.exercisesRecyclerView);
            adapter = new ExerciseAdapter(currentProgram.getExercises(), this::deleteExercise);
            recyclerView.setAdapter(adapter);
        }

        findViewById(R.id.fabAddExercise).setOnClickListener(v -> showAddExerciseDialog());
    }

    private void showAddExerciseDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Add New Exercise");
        LayoutInflater inflater = getLayoutInflater();
        android.view.View dialogView = inflater.inflate(R.layout.dialog_add_exercise, null);
        final EditText exerciseNameEditText = dialogView.findViewById(R.id.editTextExerciseName);
        final EditText repsEditText = dialogView.findViewById(R.id.editTextReps);
        builder.setView(dialogView);

        builder.setPositiveButton("Add", (dialog, which) -> {
            String name = exerciseNameEditText.getText().toString().trim();
            String reps = repsEditText.getText().toString().trim();
            if (!name.isEmpty() && !reps.isEmpty()) {
                currentProgram.getExercises().add(new Exercise(name, reps));
                savePrograms();
                adapter.notifyItemInserted(currentProgram.getExercises().size() - 1);
            }
        });
        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());
        builder.show();
    }

    private void deleteExercise(int position) {
        currentProgram.getExercises().remove(position);
        savePrograms();
        adapter.notifyItemRemoved(position);
    }

    private void loadPrograms() {
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        String json = prefs.getString(PROGRAMS_LIST_KEY, null);
        Type type = new TypeToken<ArrayList<Program>>() {}.getType();
        allPrograms = gson.fromJson(json, type);
        if (allPrograms == null) {
            allPrograms = new ArrayList<>();
        }
    }

    private void savePrograms() {
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        String json = gson.toJson(allPrograms);
        editor.putString(PROGRAMS_LIST_KEY, json);
        editor.apply();
    }

    private Program findProgramById(long id) {
        for (Program p : allPrograms) {
            if (p.getId() == id) {
                return p;
            }
        }
        return null;
    }
}
