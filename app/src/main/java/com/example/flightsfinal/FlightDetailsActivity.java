package com.example.flightsfinal;

import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

public class FlightDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flight_details);

        findViewById(R.id.btn_close).setOnClickListener(v -> finish());

        findViewById(R.id.fab_add_expense).setOnClickListener(v -> {
            AlertDialog dialog = new MaterialAlertDialogBuilder(this)
                    .setView(R.layout.dialog_add_expense)
                    .create();
            dialog.show();
            dialog.findViewById(R.id.btn_cancel).setOnClickListener(b -> dialog.dismiss());
            dialog.findViewById(R.id.btn_save).setOnClickListener(b -> dialog.dismiss());
        });
    }
}
