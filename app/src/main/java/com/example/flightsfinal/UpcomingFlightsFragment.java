package com.example.flightsfinal;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.flightsfinal.Classes.Flight;
import com.example.flightsfinal.Classes.FlightRepository;
import com.example.flightsfinal.Interfaces.RepositoryCallback;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class UpcomingFlightsFragment extends Fragment {


    public UpcomingFlightsFragment() {
        super(R.layout.fragment_upcoming_flights);
    }

    private FlightRepository rep;

    private CountDownTimer countDownTimer;
    private             SimpleDateFormat sdf;

    private LinearLayout cardNextFlight;
    private TextView tvNextDestination;
    private TextView tvNextDate;
    private TextView tvCountdown;
    private RecyclerView rvUpcomingFlights;

    private void UpdateUpcomingFlights()
    {
        rep.getUpcomingFlights(new RepositoryCallback<List<Flight>>() {
            @Override
            public void onResult(List<Flight> result) {
                if (result != null && !result.isEmpty()) {
                    Flight close = result.get(0);
                    tvNextDestination.setText(close.getDestination());
                    String formattedDate = sdf.format(new Date(close.getDepartureTimestamp()));
                    tvNextDate.setText(formattedDate);
                    long now = System.currentTimeMillis();
                    long millisUntilFlight = close.getDepartureTimestamp() - now;

                    if (countDownTimer != null) {
                        countDownTimer.cancel();
                    }

                    if (millisUntilFlight > 0) {
                        countDownTimer = new CountDownTimer(millisUntilFlight, 1000) {
                            @Override
                            public void onTick(long millisLeft) {
                                long days = TimeUnit.MILLISECONDS.toDays(millisLeft);
                                long hours = TimeUnit.MILLISECONDS.toHours(millisLeft) % 24;
                                long minutes = TimeUnit.MILLISECONDS.toMinutes(millisLeft) % 60;
                                long seconds = TimeUnit.MILLISECONDS.toSeconds(millisLeft) % 60;

                                String text = "עוד " + days + " ימים " + hours + ":" + minutes + ":" + seconds;
                                tvCountdown.setText(text);
                            }

                            @Override
                            public void onFinish() {
                                tvCountdown.setText("הטיסה המריאה!");
                            }
                        }.start();
                    }

                } else {
                    // Runs when the database has 0 upcoming flights
                    tvNextDestination.setText("אין טיסות קרובות");
                }
            }
        });
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rep = new FlightRepository(requireContext());

        cardNextFlight = view.findViewById(R.id.card_next_flight);
        tvNextDestination = view.findViewById(R.id.tv_next_destination);
        tvNextDate = view.findViewById(R.id.tv_next_date);
        tvCountdown = view.findViewById(R.id.tv_countdown);
        rvUpcomingFlights = view.findViewById(R.id.rv_upcoming_flights);

        sdf = new SimpleDateFormat("dd/MM/yyyy • HH:mm", Locale.getDefault());

        cardNextFlight.setOnClickListener(v ->
                startActivity(new Intent(requireContext(), FlightDetailsActivity.class)));
    }

    @Override
    public void onResume() {
        super.onResume();
        // Runs EVERY TIME this screen appears (first load + whenever you return from another Activity).
        // Use for:
        // 1. Fetching/refreshing upcoming flights from FlightRepository
        // 2. Starting the CountDownTimer for the closest flight
        UpdateUpcomingFlights();


    }


    @Override
    public void onPause() {
        super.onPause();
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
    }
}