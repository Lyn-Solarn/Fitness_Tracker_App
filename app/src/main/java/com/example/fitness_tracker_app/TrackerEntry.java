package com.example.fitness_tracker_app;

import java.io.Serializable;

public class TrackerEntry extends android.app.Activity implements Serializable {
    private Workout workout;
    private Time duration;

    public TrackerEntry(Workout workout, Time duration) {
        this.workout = workout;
        this.duration = duration;
    }

    public TrackerEntry() {
        workout = new Workout(0);
        duration = new Time(0);
    }

    public int getPointsEarned() {
        return workout.getPointsMultiplier() * duration.getMinutes();
    }

    public Workout getWorkout() {
        return workout;
    }

    public void setWorkout(Workout workout) {
        this.workout = workout;
    }

    public Time getDuration() {
        return duration;
    }

    public void setDuration(Time duration) {
        this.duration = duration;
    }

    @Override
    public String toString() {
        return (workout.getType() + " - " + duration + " -=-=- Points Earned: " + getPointsEarned());
    }
}
