package com.example.fitness_tracker_app;

import java.util.ArrayList;

public class Workout {
    private static final ArrayList<String> types = new ArrayList<>();
    private static final ArrayList<Integer> multipliers = new ArrayList<>();

    private final int index;

    static {
        types.add("Running");
        multipliers.add(2);
        types.add("Walking");
        multipliers.add(1);
        types.add("Swimming");
        multipliers.add(3);
        types.add("Cycling");
        multipliers.add(3);
        types.add("Weightlifting");
        multipliers.add(5);
    }

    public Workout(int index) {
        this.index = index;
    }

    public String getType() {
        return types.get(index);
    }

    public int getPointsMultiplier() {
        return multipliers.get(index);
    }
}
