package com.app.kotlinbasicslearn.fragmentcycle;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.app.kotlinbasicslearn.R;
import com.app.kotlinbasicslearn.databinding.ActivityJavaScreenBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class  JavaScreen extends AppCompatActivity {
     ActivityJavaScreenBinding binding;

    // ✅ Track current fragment
    private int currentSelectedItemId = R.id.navHome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityJavaScreenBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        EdgeToEdge.enable(this);


//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });

        // ✅ Load default fragment only on first creation
        if (savedInstanceState == null) {
            loadFragment(new HomeFrag());
        }

        // ✅ Replace deprecated method with setOnItemSelectedListener
        binding.bnBarHomeScreen.setOnItemSelectedListener(new BottomNavigationView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                int itemId = item.getItemId();

                // ✅ If already selected, do nothing
                if (itemId == currentSelectedItemId) {
                    return true;
                }

                Fragment selectedFragment = null;

                if (itemId == R.id.navHome) {
                    selectedFragment = new HomeFrag();
                } else if (itemId == R.id.navChat) {
                    selectedFragment = new ChatFrag();
                }

                if (selectedFragment != null) {
                    loadFragment(selectedFragment);
                    currentSelectedItemId = itemId; // ✅ update current selected
                    return true;
                }

                return false;


            }
        });
    }

    private void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(binding.flHomeScreenMain.getId(), fragment);
        // transaction.addToBackStack(null); // Optional: remove this if you don't want backstack navigation
        transaction.commit();
    }

    @Override
    public void onBackPressed() {
        // ✅ If on ChatFrag → go to HomeFrag
        if (currentSelectedItemId == R.id.navChat) {
            binding.bnBarHomeScreen.setSelectedItemId(R.id.navHome); // triggers listener
        }
        // ✅ If already on HomeFrag → exit app
        else if (currentSelectedItemId == R.id.navHome) {
            super.onBackPressed(); // exit app
        }
    }


    private String TAG = "JavaScreen";

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart: MAIN SCREEN");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume: MAIN SCREEN");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause: MAIN SCREEN");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop: MAIN SCREEN");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG, "onRestart: MAIN SCREEN");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: MAIN SCREEN");
    }
}
