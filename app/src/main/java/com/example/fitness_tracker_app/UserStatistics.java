package com.example.fitness_tracker_app;

import java.io.Serializable;

public class UserStatistics implements Serializable {
    private String username;
    private Tracker workoutTracker;

    public UserStatistics(String username, Tracker workoutTracker) {
        this.username = username;
        this.workoutTracker = workoutTracker;
    }

    public UserStatistics(String username) {
        this.username = username;
        this.workoutTracker = new Tracker();
    }

    public int totalPoints() {
        int totalPoints = 0;
        for(int i=0; i < workoutTracker.getSize(); i++) {
            totalPoints += workoutTracker.getEntry(i).getPointsEarned();
        }
        return totalPoints;
    }
    
    public Time totalTime() {
        int totalTime = 0;
        for(int i=0; i < workoutTracker.getSize(); i++) {
            totalTime += workoutTracker.getEntry(i).getDuration().getMinutes();
        }
        return new Time(totalTime);
    }

    public Time averageTime() {
        return new Time(totalTime().getMinutes()/workoutTracker.getSize());
    }

    //TO-DO
    public String mostPopularWorkout() {
        //if you don't initialize popularWorkout intellij will be angry
        String popularWorkout = workoutTracker.getEntry(0).getWorkout().getType();
        String currentWorkout;
        int highestCount = 0;
        int counter = 0;
        //this is probably a terrible way of doing this. Too bad!
        for (int i = 0; i < workoutTracker.getSize(); i++) {
            currentWorkout = workoutTracker.getEntry(i).getWorkout().getType();
            for (int k = 0; k < workoutTracker.getSize(); k++) {
                if (currentWorkout.equals(workoutTracker.getEntry(k).getWorkout().getType())) {
                    counter++;
                }
            }
            if (counter > highestCount) {
                highestCount = counter;
                popularWorkout = currentWorkout;
            }
            counter = 0;
        }
        //big o of like 5 million, but it gets the job done
        return popularWorkout;
    }

    //TO-DO
    public String leastPopularWorkout() {
        //if you don't initialize hatedWorkout intellij will be angry
        String hatedWorkout = workoutTracker.getEntry(0).getWorkout().getType();
        String currentWorkout;
        int lowestCount = Integer.MAX_VALUE;
        int counter = 0;
        //this is probably a terrible way of doing this. Too bad!
        for (int i = 0; i < workoutTracker.getSize(); i++) {
            currentWorkout = workoutTracker.getEntry(i).getWorkout().getType();
            for (int k = 0; k < workoutTracker.getSize(); k++) {
                if (currentWorkout.equals(workoutTracker.getEntry(k).getWorkout().getType())) {
                    counter++;
                }
            }
            if (counter < lowestCount) {
                lowestCount = counter;
                hatedWorkout = currentWorkout;
            }
            counter = 0;
        }
        //big o of like 5 million, but it gets the job done
        return hatedWorkout;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Tracker getWorkoutTracker() {
        return workoutTracker;
    }

    public void setWorkoutTracker(Tracker workoutTracker) {
        this.workoutTracker = workoutTracker;
    }

}
