package com.example.fitness_tracker_app;

import java.util.ArrayList;
import java.util.List;

public class Tracker {
    private final List<TrackerEntry> log = new ArrayList<>();

    //TO-DO
    public void addEntry(TrackerEntry entry) {
        log.add(entry);
    }

    public TrackerEntry getLastEntry() {
        return log.getLast();
    }

    public TrackerEntry getEntry(int index) {
        return log.get(index);
    }

    public void removeEntry(int index) {
        try {
            log.remove(index);
        } catch (Exception e) {
            System.out.println("Invalid index at " + index);
        }
    }

    public int getSize() {
        return log.size();
    }

    @Override
    public String toString() {
        StringBuilder returnString = new StringBuilder();
        int count = 1;
        for (TrackerEntry item : log) {
            returnString.append(count++).append(". ").append(item).append("\n");
        }
        return returnString.toString();
    }
}
