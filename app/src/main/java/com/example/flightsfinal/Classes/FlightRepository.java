package com.example.flightsfinal.Classes;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import com.example.flightsfinal.Databases.AppDatabase;
import com.example.flightsfinal.Interfaces.ExpenseDao;
import com.example.flightsfinal.Interfaces.FlightDao;
import com.example.flightsfinal.Interfaces.RepositoryCallback;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

// Runs every database operation on a background thread
// and returns the result to the main (UI) thread
public class FlightRepository {

    private final FlightDao flightDao;
    private final ExpenseDao expenseDao;

    // Single background thread for all database work
    private final ExecutorService executor = Executors.newSingleThreadExecutor();

    // Posts results back to the main (UI) thread
    private final Handler mainHandler = new Handler(Looper.getMainLooper());

    public FlightRepository(Context context) {
        AppDatabase db = AppDatabase.getInstance(context);
        flightDao = db.flightDao();
        expenseDao = db.expenseDao();
    }

    // Loads the expenses of a flight into flight.expenses (runs on the background thread)
    private void loadExpenses(Flight flight) {
        if (flight != null) {
            flight.setExpenses(expenseDao.getExpensesForFlight(flight.getId()));
        }
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
            for (Flight f : flights) loadExpenses(f);
            mainHandler.post(() -> callback.onResult(flights));
        });
    }

    // Flights that already departed, most recent first
    public void getPastFlights(RepositoryCallback<List<Flight>> callback) {
        executor.execute(() -> {
            List<Flight> flights = flightDao.getPastFlights(System.currentTimeMillis());
            for (Flight f : flights) loadExpenses(f);
            mainHandler.post(() -> callback.onResult(flights));
        });
    }

    // A single flight by id, together with its expenses (null if not found)
    public void getFlightById(long id, RepositoryCallback<Flight> callback) {
        executor.execute(() -> {
            Flight flight = flightDao.getFlightById(id);
            loadExpenses(flight);
            mainHandler.post(() -> callback.onResult(flight));
        });
    }

    // --- EXPENSES ---

    // Inserts an expense and returns its new id
    public void insertExpense(Expense expense, RepositoryCallback<Long> callback) {
        executor.execute(() -> {
            long id = expenseDao.insert(expense);
            mainHandler.post(() -> callback.onResult(id));
        });
    }

    // Deletes an expense (result is null, the callback just signals it finished)
    public void deleteExpense(Expense expense, RepositoryCallback<Void> callback) {
        executor.execute(() -> {
            expenseDao.delete(expense);
            mainHandler.post(() -> callback.onResult(null));
        });
    }

    // Total per category for one flight
    public void getCategoryTotalsForFlight(long flightId, RepositoryCallback<List<CategoryTotal>> callback) {
        executor.execute(() -> {
            List<CategoryTotal> totals = expenseDao.getCategoryTotalsForFlight(flightId);
            mainHandler.post(() -> callback.onResult(totals));
        });
    }

    // Total per category across all flights (summary screen)
    public void getCategoryTotalsForAllFlights(RepositoryCallback<List<CategoryTotal>> callback) {
        executor.execute(() -> {
            List<CategoryTotal> totals = expenseDao.getCategoryTotalsForAllFlights();
            mainHandler.post(() -> callback.onResult(totals));
        });
    }

    // Total spent on one flight in shekels
    public void getTotalForFlight(long flightId, RepositoryCallback<Double> callback) {
        executor.execute(() -> {
            double total = expenseDao.getTotalForFlight(flightId);
            mainHandler.post(() -> callback.onResult(total));
        });
    }
}
