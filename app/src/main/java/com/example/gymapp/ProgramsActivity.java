package com.example.gymapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class ProgramsActivity extends AppCompatActivity {

    private ArrayList<Program> programList;
    private ProgramAdapter adapter;
    private Gson gson;

    private static final String PREFS_NAME = "GymAppPrefs";
    private static final String PROGRAMS_LIST_KEY = "ProgramsList";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_programs);

        gson = new Gson();
        loadPrograms();

        RecyclerView recyclerView = findViewById(R.id.programsRecyclerView);
        adapter = new ProgramAdapter(programList, this::onProgramClicked, this::deleteProgram);
        recyclerView.setAdapter(adapter);

        FloatingActionButton fab = findViewById(R.id.fabAddProgram);
        fab.setOnClickListener(view -> showAddProgramDialog());
    }

    private void onProgramClicked(Program program) {
        Intent intent = new Intent(this, ProgramDetailActivity.class);
        intent.putExtra("PROGRAM_ID", program.getId());
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Refresh the list when returning to this activity
        loadPrograms();
        adapter.updatePrograms(programList);
    }

    private void showAddProgramDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("New Workout Program");

        LayoutInflater inflater = getLayoutInflater();
        android.view.View dialogView = inflater.inflate(R.layout.dialog_add_program, null);
        final EditText programNameEditText = dialogView.findViewById(R.id.editTextProgramName);
        builder.setView(dialogView);

        builder.setPositiveButton("Create", (dialog, which) -> {
            String programName = programNameEditText.getText().toString().trim();
            if (!programName.isEmpty()) {
                Program newProgram = new Program(programName, new ArrayList<>());
                programList.add(newProgram);
                savePrograms();
                adapter.notifyItemInserted(programList.size() - 1);
            }
        });
        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());
        builder.show();
    }

    private void deleteProgram(int position) {
        programList.remove(position);
        savePrograms();
        adapter.notifyItemRemoved(position);
    }

    private void loadPrograms() {
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        String json = prefs.getString(PROGRAMS_LIST_KEY, null);
        Type type = new TypeToken<ArrayList<Program>>() {}.getType();
        programList = gson.fromJson(json, type);

        if (programList == null) {
            programList = new ArrayList<>();
        }
    }

    private void savePrograms() {
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        String json = gson.toJson(programList);
        editor.putString(PROGRAMS_LIST_KEY, json);
        editor.apply();
    }
}
