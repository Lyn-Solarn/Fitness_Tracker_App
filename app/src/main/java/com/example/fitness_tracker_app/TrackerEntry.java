package com.example.fitness_tracker_app;

public class TrackerEntry {
    private Workout workout;
    private Time duration;

    public TrackerEntry(Workout workout, Time duration) {
        this.workout = workout;
        this.duration = duration;
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
