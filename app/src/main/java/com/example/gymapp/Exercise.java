package com.example.gymapp;

import android.os.Parcel;
import android.os.Parcelable;

public class Exercise implements Parcelable {
    private String name;
    private String reps;

    public Exercise(String name, String reps) {
        this.name = name;
        this.reps = reps;
    }

    // Getters
    public String getName() { return name; }
    public String getReps() { return reps; }

    // --- Parcelable Implementation ---
    protected Exercise(Parcel in) {
        name = in.readString();
        reps = in.readString();
    }

    public static final Creator<Exercise> CREATOR = new Creator<Exercise>() {
        @Override
        public Exercise createFromParcel(Parcel in) {
            return new Exercise(in);
        }

        @Override
        public Exercise[] newArray(int size) {
            return new Exercise[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(reps);
    }
}
