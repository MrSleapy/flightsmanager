package com.example.flightsfinal;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNav = findViewById(R.id.bottom_nav);
        bottomNav.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            Fragment fragment;
            if (id == R.id.nav_upcoming) {
                fragment = new UpcomingFlightsFragment();
            } else if (id == R.id.nav_past) {
                fragment = new PastFlightsFragment();
            } else if (id == R.id.nav_summary) {
                fragment = new SummaryFragment();
            } else if (id == R.id.nav_settings) {
                fragment = new SettingsFragment();
            } else {
                return false;
            }
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .commit();
            return true;
        });

        findViewById(R.id.fab_add).setOnClickListener(v ->
                startActivity(new Intent(this, AddEditFlightActivity.class)));
    }
}
