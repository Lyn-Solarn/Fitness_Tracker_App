package com.example.fitness_tracker_app;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import java.io.Serializable;

public class Stat_page extends AppCompatActivity implements Serializable {
    private UserStatistics userStatistics;
    private Tracker tracker;
    private TextView totalPointsText;
    //could probably be a local variable but im too lazy to change it now
    private TextView totalTimeText;
    private TextView averageTimeText;
    private TextView favoriteWorkoutText;
    private TextView hatedWorkoutText;
    private Button backButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_stat_page);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        totalPointsText = findViewById(R.id.totalPointsNumber);
        totalTimeText = findViewById(R.id.totalTimeNumber);
        averageTimeText = findViewById(R.id.averageTimeNumber);
        favoriteWorkoutText = findViewById(R.id.favoriteWorkoutText);
        hatedWorkoutText = findViewById(R.id.leastFavoriteWorkoutText);
        backButton = findViewById(R.id.backButton);


        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            //a million logs caus idk where the error is
            tracker = (Tracker) bundle.getSerializable("TRACK");
            //yes, this method is depreciated. i dont care
            if (tracker == null) {
                Log.e("Stat_page", "Tracker is null in Stat_page");
            } else {
                Log.d("Stat_page", "Tracker received: " + tracker);
            }
            userStatistics = (UserStatistics) bundle.getSerializable("KEY");
        }
        if (tracker == null) {
            //idk why tracker is null but i dont like it when it crashes
            Log.d("Stat_page", "Tracker is null");
            return;
        }
        assert userStatistics != null;
        userStatistics.setWorkoutTracker(tracker);
        totalPointsText.setText(Integer.toString(userStatistics.totalPoints()));
        //i spent a long time trying to make it work and now it wants me to use string formatting. no. im not taking risks
        totalTimeText.setText(userStatistics.totalTime().toString());
        averageTimeText.setText((userStatistics.averageTime()).toString());
        favoriteWorkoutText.setText(userStatistics.mostPopularWorkout());
        hatedWorkoutText.setText(userStatistics.leastPopularWorkout());

        backButton.setOnClickListener(v -> {
            Intent i = new Intent(Stat_page.this, MainActivity.class);
            Bundle bundle2 = new Bundle();
            bundle2.putSerializable("TRACK", tracker);
            bundle2.putSerializable("KEY", userStatistics);
            i.putExtras(bundle);
            Log.d("Stat_page", "Passing tracker: " + tracker);
            startActivity(i);
        });

    }
}