package com.example.fitness_tracker_app;

import java.io.Serializable;

public class Time implements Serializable {
    private int minutes;

    public Time(int minutes) {
        this.minutes = minutes;
    }

    public int getMinutes() {
        return minutes;
    }

    public void setMinutes(int minutes) {
        this.minutes = minutes;
    }

    public int toHours() {
        return minutes/60;
    }

    @Override
    public String toString() {
        return toHours() + " hr " + minutes%60 + " min";
    }
}
