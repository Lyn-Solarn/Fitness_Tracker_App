package com.example.fitness_tracker_app;

import android.os.Bundle;
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

import java.util.Collections;

public class MainActivity extends AppCompatActivity {

    // --- Views ---
    private ScrollView scroll;
    private LinearLayout vertical;
    private Spinner workoutDropdown;
    private EditText entryTimeInput;
    private Button addEntryButton;

    // --- Data model ---
    private Tracker tracker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // apply edge-to-edge padding
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets bars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(bars.left, bars.top, bars.right, bars.bottom);
            return insets;
        });

        // 1) Wire up your views
        scroll           = findViewById(R.id.scroll);
        vertical         = findViewById(R.id.vertical);
        workoutDropdown  = findViewById(R.id.workoutDropdownInput);
        entryTimeInput   = findViewById(R.id.entryTimeInput);
        addEntryButton   = findViewById(R.id.addEntryButton);

        // 2) Seed your Tracker and render its entries
        tracker = makeData();
        renderAllEntries();

        // 3) Populate the Spinner from your Workout class
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                Workout.getTypes()    // <-- returns List<String> of all the workout names
        );
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        workoutDropdown.setAdapter(spinnerAdapter);

        // 4) Handle Add-Entry clicks
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

            // 5) Build & store the new entry
            int selIndex = workoutDropdown.getSelectedItemPosition();
            Workout w     = new Workout(selIndex);
            TrackerEntry newEntry = new TrackerEntry(w, new Time(minutes));
            tracker.addEntry(newEntry);

            // 6) Show it immediately
            addEntryRow(newEntry);

            // 7) Clear inputs
            entryTimeInput.getText().clear();
            workoutDropdown.setSelection(0);
        });
    }

    /** Inflate & append all existing entries in the tracker */
    private void renderAllEntries() {
        for (int i = 0, n = tracker.getSize(); i < n; i++) {
            addEntryRow(tracker.getEntry(i));
        }
    }

    /** Build one horizontal “row” for this entry and add it to the vertical container */
    private void addEntryRow(TrackerEntry entry) {
        // a) container
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

        // b) shared cell params (equal weight)
        LinearLayout.LayoutParams cellParams = new LinearLayout.LayoutParams(
                0, ViewGroup.LayoutParams.WRAP_CONTENT, 1f
        );

        // c) three text fields
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

        // d) assemble
        line.addView(name);
        line.addView(time);
        line.addView(points);
        vertical.addView(line);
    }

    /** Your existing seed data */
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

