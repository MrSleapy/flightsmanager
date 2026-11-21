package com.example.flightsfinal.Databases;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.flightsfinal.Classes.Expense;
import com.example.flightsfinal.Classes.Flight;
import com.example.flightsfinal.Interfaces.ExpenseDao;
import com.example.flightsfinal.Interfaces.FlightDao;

// Identifies this class as a Room Database, linking it to Flight and Expense
@Database(entities = {Flight.class, Expense.class}, version = 2)
public abstract class AppDatabase extends RoomDatabase {

    // Instance variable for the Singleton pattern
    private static AppDatabase instance;

    // Abstract methods that Room will implement automatically to provide the DAOs
    public abstract FlightDao flightDao();
    public abstract ExpenseDao expenseDao();

    /**
     * Singleton pattern ensures only one instance of the database exists at a time,
     * preventing memory leaks and database lock issues.
     * No allowMainThreadQueries(): all queries run on a background thread (see FlightRepository).
     */
    public static synchronized AppDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, "flights_database")
                    .fallbackToDestructiveMigration() // Wipes and rebuilds DB if version number changes
                    .build();
        }
        return instance;
    }
}
