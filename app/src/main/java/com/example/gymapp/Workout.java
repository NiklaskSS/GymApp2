package com.example.gymapp;

import android.os.Parcel;
import android.os.Parcelable;

// 1. Add "implements Parcelable" to your class definition
public class Workout implements Parcelable {
    private String exerciseName;
    private String reps;
    private String weight;
    private String date;

    // Your existing constructor
    public Workout(String exerciseName, String reps, String weight, String date) {
        this.exerciseName = exerciseName;
        this.reps = reps;
        this.weight = weight;
        this.date = date;
    }

    // Your existing getter methods
    public String getExerciseName() {
        return exerciseName;
    }

    public String getReps() {
        return reps;
    }

    public String getWeight() {
        return weight;
    }

    public String getDate() {
        return date;
    }

    // --- START OF PARCELABLE IMPLEMENTATION ---

    // 2. Add a constructor that reads from a Parcel.
    // The order of reading (e.g., readString()) MUST match the order of writing in writeToParcel().
    protected Workout(Parcel in) {
        exerciseName = in.readString();
        reps = in.readString();
        weight = in.readString();
        date = in.readString();
    }

    // 3. Add the CREATOR field. This is boilerplate code that generates instances of your object.
    public static final Creator<Workout> CREATOR = new Creator<Workout>() {
        @Override
        public Workout createFromParcel(Parcel in) {
            return new Workout(in);
        }

        @Override
        public Workout[] newArray(int size) {
            return new Workout[size];
        }
    };

    // 4. Implement the describeContents() method. You can usually just return 0.
    @Override
    public int describeContents() {
        return 0;
    }

    // 5. Implement the writeToParcel() method. This writes the object's data to the Parcel.
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(exerciseName);
        dest.writeString(reps);
        dest.writeString(weight);
        dest.writeString(date);
    }

    // --- END OF PARCELABLE IMPLEMENTATION ---
}