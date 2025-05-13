package com.example.fitness_tracker_app;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.Serializable;
import java.util.Collections;

public class MainActivity extends AppCompatActivity implements Serializable{

    // Views
    private ScrollView scroll;
    private LinearLayout vertical;
    private Spinner workoutDropdown;
    private EditText entryTimeInput;
    private Button addEntryButton;

    private Button viewStatsButton;

    public TrackerEntry newEntry;

    public static final UserStatistics user = new UserStatistics("user");

    private Tracker tracker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // Add padding for formatting
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets bars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(bars.left, bars.top, bars.right, bars.bottom);
            return insets;
        });

        // Setting up input and output views
        scroll           = findViewById(R.id.scroll);
        vertical         = findViewById(R.id.vertical);
        workoutDropdown  = findViewById(R.id.workoutDropdownInput);
        entryTimeInput   = findViewById(R.id.entryTimeInput);
        addEntryButton   = findViewById(R.id.addEntryButton);
        viewStatsButton  = findViewById(R.id.statButton);

        // Getting the user's tracker and displaying all of their entries
        tracker = user.getWorkoutTracker();
        if (tracker == null) {
            Log.e("MainActivity", "Tracker is null after initialization");
        } else {
            Log.d("MainActivity", "Tracker initialized successfully");
        }
        Log.d("MainActivity", "Tracker initialized");
        renderAllEntries();

        // Displaying workout options in dropdown menu
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                Workout.getTypes()    // returns a list of all the workout names
        );
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        workoutDropdown.setAdapter(spinnerAdapter);

        // Add entry button
        addEntryButton.setOnClickListener(v -> {
            String minutesText = entryTimeInput.getText().toString().trim();
            if (minutesText.isEmpty()) {
                entryTimeInput.setError("Enter minutes");
                return;
            }

            int minutes;
            try {
                minutes = Integer.parseInt(minutesText);
            } catch (NumberFormatException e) {
                entryTimeInput.setError("Must be a number");
                return;
            }

            // Add new entry to the user's Tracker
            int selIndex = workoutDropdown.getSelectedItemPosition();
            Workout w     = new Workout(selIndex);
            newEntry = new TrackerEntry(w, new Time(minutes));
            tracker.addEntry(newEntry);

            // Display new entry and clear inputs
            addEntryRow(newEntry);
            entryTimeInput.getText().clear();
            workoutDropdown.setSelection(0);
        });

        // View stat screen button
        viewStatsButton.setOnClickListener(v -> {
            Intent statIntent = new Intent(MainActivity.this, Stat_page.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("TRACK", tracker);
            bundle.putSerializable("KEY", user);
            statIntent.putExtras(bundle);
            Log.d("MainActivity", "Passing tracker: " + tracker);
            startActivity(statIntent);

        });
    }

    // Displays each entry in the tracker
    private void renderAllEntries() {
        for (int i = 0, n = tracker.getSize(); i < n; i++) {
            addEntryRow(tracker.getEntry(i));
        }
    }

    // Displays a new tracker entry
    private void addEntryRow(TrackerEntry entry) {
        // Setting up new LinearLayout for each line
        LinearLayout line = new LinearLayout(this);
        LinearLayout.LayoutParams rowParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );
        line.setLayoutParams(rowParams);
        line.setOrientation(LinearLayout.HORIZONTAL);
        line.setGravity(Gravity.CENTER_HORIZONTAL);
        int pad = (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, 8, getResources().getDisplayMetrics());
        line.setPadding(pad, pad, pad, pad);

        // Setting up text fields
        LinearLayout.LayoutParams cellParams = new LinearLayout.LayoutParams(
                0, ViewGroup.LayoutParams.WRAP_CONTENT, 1f
        );

        TextView name = new TextView(this);
        name.setLayoutParams(cellParams);
        name.setGravity(Gravity.CENTER);
        name.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
        name.setText(entry.getWorkout().getType());

        TextView time = new TextView(this);
        time.setLayoutParams(cellParams);
        time.setGravity(Gravity.CENTER);
        time.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
        time.setText(entry.getDuration().toString());

        TextView points = new TextView(this);
        points.setLayoutParams(cellParams);
        points.setGravity(Gravity.CENTER);
        points.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
        points.setText(String.valueOf(entry.getPointsEarned()));

        // Add views to the line and add line to the display
        line.addView(name);
        line.addView(time);
        line.addView(points);
        vertical.addView(line);
    }

    // Testing data
    public Tracker makeData() {
        Workout run   = new Workout(0);
        Workout walk  = new Workout(1);
        Tracker t     = new Tracker();
        t.addEntry(new TrackerEntry(run,  new Time(60)));
        t.addEntry(new TrackerEntry(run,  new Time(45)));
        t.addEntry(new TrackerEntry(walk, new Time(30)));
        return t;
    }
}

