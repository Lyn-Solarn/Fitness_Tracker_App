package com.example.fitness_tracker_app;

import android.os.Bundle;
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

        for (int i = 0; i < t.getSize(); i++) {
            TrackerEntry entry = t.getEntry(i);
            LinearLayout line = new LinearLayout(this);
            TextView name = new TextView(this);
            name.setText(entry.getWorkout().getType());
            TextView time = new TextView(this);
            time.setText(entry.getDuration().toString());
            TextView points = new TextView(this);
            points.setText("" + entry.getPointsEarned());
            line.addView(name);
            line.addView(time);
            line.addView(points);
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

