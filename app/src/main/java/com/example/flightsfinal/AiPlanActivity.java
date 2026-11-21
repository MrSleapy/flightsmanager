package com.example.flightsfinal;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class AiPlanActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ai_plan);

        findViewById(R.id.btn_close).setOnClickListener(v -> finish());
    }
}
