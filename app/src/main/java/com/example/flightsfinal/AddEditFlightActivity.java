package com.example.flightsfinal;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class AddEditFlightActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_flight);

        findViewById(R.id.btn_close).setOnClickListener(v -> finish());
    }
}
