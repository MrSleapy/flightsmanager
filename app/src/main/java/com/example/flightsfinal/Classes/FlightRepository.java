package com.example.flightsfinal.Classes;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import com.example.flightsfinal.Databases.AppDatabase;
import com.example.flightsfinal.Interfaces.FlightDao;
import com.example.flightsfinal.Interfaces.RepositoryCallback;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

// Runs every database operation on a background thread
// and returns the result to the main (UI) thread
public class FlightRepository {

    private final FlightDao flightDao;

    // Single background thread for all database work
    private final ExecutorService executor = Executors.newSingleThreadExecutor();

    // Posts results back to the main (UI) thread
    private final Handler mainHandler = new Handler(Looper.getMainLooper());

    public FlightRepository(Context context) {
        flightDao = AppDatabase.getInstance(context).flightDao();
    }

    // Inserts a flight and returns its new id
    public void insert(Flight flight, RepositoryCallback<Long> callback) {
        executor.execute(() -> {
            long id = flightDao.insert(flight);
            mainHandler.post(() -> callback.onResult(id));
        });
    }

    // Updates a flight (result is null, the callback just signals it finished)
    public void update(Flight flight, RepositoryCallback<Void> callback) {
        executor.execute(() -> {
            flightDao.update(flight);
            mainHandler.post(() -> callback.onResult(null));
        });
    }

    // Deletes a flight (result is null, the callback just signals it finished)
    public void delete(Flight flight, RepositoryCallback<Void> callback) {
        executor.execute(() -> {
            flightDao.delete(flight);
            mainHandler.post(() -> callback.onResult(null));
        });
    }

    // Flights that have not departed yet, nearest first
    public void getUpcomingFlights(RepositoryCallback<List<Flight>> callback) {
        executor.execute(() -> {
            List<Flight> flights = flightDao.getUpcomingFlights(System.currentTimeMillis());
            mainHandler.post(() -> callback.onResult(flights));
        });
    }

    // Flights that already departed, most recent first
    public void getPastFlights(RepositoryCallback<List<Flight>> callback) {
        executor.execute(() -> {
            List<Flight> flights = flightDao.getPastFlights(System.currentTimeMillis());
            mainHandler.post(() -> callback.onResult(flights));
        });
    }

    // A single flight by id (null if not found)
    public void getFlightById(long id, RepositoryCallback<Flight> callback) {
        executor.execute(() -> {
            Flight flight = flightDao.getFlightById(id);
            mainHandler.post(() -> callback.onResult(flight));
        });
    }
}
