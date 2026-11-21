package com.example.flightsfinal;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class UpcomingFlightsFragment extends Fragment {

    public UpcomingFlightsFragment() {
        super(R.layout.fragment_upcoming_flights);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.findViewById(R.id.card_next_flight).setOnClickListener(v ->
                startActivity(new Intent(requireContext(), FlightDetailsActivity.class)));
    }
}
