package com.example.gymapp;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;

public class Program implements Parcelable {
    private String name;
    private ArrayList<Exercise> exercises;
    private long id;

    public Program(String name, ArrayList<Exercise> exercises) {
        this.name = name;
        this.exercises = exercises;
        this.id = System.currentTimeMillis(); // Use timestamp for a unique ID
    }

    // Getters
    public String getName() { return name; }
    public ArrayList<Exercise> getExercises() { return exercises; }
    public long getId() { return id; }

    // Setters
    public void setName(String name) { this.name = name; }
    public void setExercises(ArrayList<Exercise> exercises) { this.exercises = exercises; }


    // --- Parcelable Implementation ---
    protected Program(Parcel in) {
        name = in.readString();
        exercises = in.createTypedArrayList(Exercise.CREATOR);
        id = in.readLong();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeTypedList(exercises);
        dest.writeLong(id);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Program> CREATOR = new Creator<Program>() {
        @Override
        public Program createFromParcel(Parcel in) {
            return new Program(in);
        }

        @Override
        public Program[] newArray(int size) {
            return new Program[size];
        }
    };
}
