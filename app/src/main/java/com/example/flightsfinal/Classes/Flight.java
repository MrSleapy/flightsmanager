package com.example.flightsfinal.Classes;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

// Defines this class as a Room Database table
@Entity(tableName = "flights")
public class Flight {

    // --- DATABASE FIELDS ---

    // Auto-generates a unique ID for each new flight
    @PrimaryKey(autoGenerate = true)
    private long id;

    private String destination;
    private String airline;
    private String flightNumber;

    // Departure date and time in milliseconds
    private long departureTimestamp;

    // Trip length in days
    private int days;

    private String purpose;

    // Trip budget in ILS
    private double budget;

    // Trip plan text returned by the AI
    private String aiPlan;

    // --- CONSTRUCTORS ---

    // Empty constructor required by Room
    public Flight() {
    }

    // The main constructor used to create new Flight objects.
    // We omit id because Room generates it automatically.
    @Ignore
    public Flight(String destination, String airline, String flightNumber, long departureTimestamp,
                  int days, String purpose, double budget, String aiPlan) {
        this.destination = destination;
        this.airline = airline;
        this.flightNumber = flightNumber;
        this.departureTimestamp = departureTimestamp;
        this.days = days;
        this.purpose = purpose;
        this.budget = budget;
        this.aiPlan = aiPlan;
    }

    // --- GETTERS AND SETTERS ---
    // Room requires getters and setters to access private fields

    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public String getDestination() { return destination; }
    public void setDestination(String destination) { this.destination = destination; }

    public String getAirline() { return airline; }
    public void setAirline(String airline) { this.airline = airline; }

    public String getFlightNumber() { return flightNumber; }
    public void setFlightNumber(String flightNumber) { this.flightNumber = flightNumber; }

    public long getDepartureTimestamp() { return departureTimestamp; }
    public void setDepartureTimestamp(long departureTimestamp) { this.departureTimestamp = departureTimestamp; }

    public int getDays() { return days; }
    public void setDays(int days) { this.days = days; }

    public String getPurpose() { return purpose; }
    public void setPurpose(String purpose) { this.purpose = purpose; }

    public double getBudget() { return budget; }
    public void setBudget(double budget) { this.budget = budget; }

    public String getAiPlan() { return aiPlan; }
    public void setAiPlan(String aiPlan) { this.aiPlan = aiPlan; }
}
