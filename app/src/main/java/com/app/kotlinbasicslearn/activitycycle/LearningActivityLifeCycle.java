package com.app.kotlinbasicslearn.activitycycle;

import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.app.kotlinbasicslearn.R;

public class LearningActivityLifeCycle extends AppCompatActivity {


    //called once activity is created
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_learning_lifecyle);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Handle configuration changes here
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            Log.d("TAG", "onConfigurationChanged: LANDSCAPE");
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            Log.d("TAG", "onConfigurationChanged: PORTRAIT");
        }

    }

    //    When: Activity is becoming visible but not interactive yet.
//    Use for: Registering broadcast receivers, analytics "screen start".

    @Override
    protected void onStart() {
        super.onStart();
    }

    //🛠 Production Tip: Avoid UI updates here — do those in onResume().
    //   called when activity is in foreground & interactive (Visible to user)

    //   Use for: Start animations, camera, live data fetching, listeners.
    @Override
    protected void onResume() {
        super.onResume();
    }

    //🛠 Production Tip: Ensure resources like sensors, GPS, etc. start here.


    //When: Another activity comes partially over it (e.g., dialog, call).
    //Activity is in background (not visible to user)
    //Use for: Pause things that should not run in background — animations, camera, etc.
    @Override
    protected void onPause() {
        super.onPause();
    }
    //🛠 Production Tip: Save unsaved user input here, stop unnecessary processes.


    //called when activity is no longer visible (e.g user just put this app in bg nd it might stop)
    //When: Activity is fully in background or not visible.
    //Use for: Release UI resources, stop audio, unregister listeners.
    @Override
    protected void onStop() {
        super.onStop();
    }
//    🛠 Production Tip: Avoid saving data here — do that in onPause() instead.


    //When: Activity is coming back from onStop() to visible.
    // Use for: Restore things you paused in onStop().

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    //🛠 Rarely used, but useful for debugging lifecycle.


//    When: Activity is finishing or destroyed by the system.

    //    Use for: Clean up memory leaks, cancel jobs, etc.
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
    //🛠 Don’t rely on this for final data saving — it may never be called if OS kills app!
}