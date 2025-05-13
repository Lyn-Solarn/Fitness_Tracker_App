package com.example.fitness_tracker_app;

import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    ScrollView scroll;
    LinearLayout vertical;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        scroll = (ScrollView) findViewById(R.id.scroll);
        vertical = (LinearLayout) findViewById(R.id.vertical);
        Tracker t = makeData();

        for (int i = 0; i < t.getSize(); i++) { // CHATGPT CODE INSIDE THIS LOOP TO FIX FORMATTING
            TrackerEntry entry = t.getEntry(i);

            // 1) Create a full-width horizontal row, centered:
            LinearLayout line = new LinearLayout(this);
            LinearLayout.LayoutParams rowParams = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            );
            // center the contents inside this row
            line.setGravity(Gravity.CENTER_HORIZONTAL);
            line.setLayoutParams(rowParams);
            line.setOrientation(LinearLayout.HORIZONTAL);
            // add a little padding for breathing room
            int pad = (int) TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_DIP, 8, getResources().getDisplayMetrics());
            line.setPadding(pad, pad, pad, pad);

            // helper to build each cell
            LinearLayout.LayoutParams cellParams = new LinearLayout.LayoutParams(
                    0,
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    1f   // equal weight so they spread out
            );

            TextView name = new TextView(this);
            name.setText(entry.getWorkout().getType());
            name.setLayoutParams(cellParams);
            name.setGravity(Gravity.CENTER);                    // center text within its cell
            name.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);  // 18sp is a nice readable size

            TextView time = new TextView(this);
            time.setText(entry.getDuration().toString());
            time.setLayoutParams(cellParams);
            time.setGravity(Gravity.CENTER);
            time.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);

            TextView points = new TextView(this);
            points.setText("" + entry.getPointsEarned());
            points.setLayoutParams(cellParams);
            points.setGravity(Gravity.CENTER);
            points.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);

            // 2) assemble and add to your vertical container
            line.addView(name);
            line.addView(time);
            line.addView(points);
            vertical.addView(line);
        }
    }

    public Tracker makeData() {
        Workout run = new Workout(1);
        Workout walk = new Workout(2);
        Tracker tracker = new Tracker();
        tracker.addEntry(new TrackerEntry(run, new Time(60)));
        tracker.addEntry(new TrackerEntry(run, new Time(60)));
        tracker.addEntry(new TrackerEntry(walk, new Time(60)));
        tracker.addEntry(new TrackerEntry(walk, new Time(60)));
        return tracker;
    }
}

