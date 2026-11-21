package com.example.flightsfinal.Interfaces;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.flightsfinal.Classes.Flight;

import java.util.List;

// Data Access Object (DAO) handles database operations
@Dao
public interface FlightDao {

    // Inserts a new flight into the flights table and returns its new id
    @Insert
    long insert(Flight flight);

    // Updates an existing flight (matched by primary key / id)
    @Update
    void update(Flight flight);

    // Deletes a specific flight from the database
    @Delete
    void delete(Flight flight);

    // Upcoming flights: departure is now or later, nearest first
    @Query("SELECT * FROM flights WHERE departureTimestamp >= :now ORDER BY departureTimestamp ASC")
    List<Flight> getUpcomingFlights(long now);

    // Past flights: departure already happened, most recent first
    @Query("SELECT * FROM flights WHERE departureTimestamp < :now ORDER BY departureTimestamp DESC")
    List<Flight> getPastFlights(long now);

    // Fetches a single flight by its id (null if not found)
    @Query("SELECT * FROM flights WHERE id = :id")
    Flight getFlightById(long id);
}
