package com.example.flightsfinal.Interfaces;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.flightsfinal.Classes.CategoryTotal;
import com.example.flightsfinal.Classes.Expense;

import java.util.List;

// Data Access Object (DAO) for the expenses table
@Dao
public interface ExpenseDao {

    // Inserts a new expense and returns its new id
    @Insert
    long insert(Expense expense);

    // Deletes a specific expense
    @Delete
    void delete(Expense expense);

    // All expenses of one flight, newest first
    @Query("SELECT * FROM expenses WHERE flightOwnerId = :flightId ORDER BY id DESC")
    List<Expense> getExpensesForFlight(long flightId);

    // Total per category for one flight (GROUP BY)
    @Query("SELECT category, SUM(amountInShekels) AS total FROM expenses " +
            "WHERE flightOwnerId = :flightId GROUP BY category")
    List<CategoryTotal> getCategoryTotalsForFlight(long flightId);

    // Total per category across all flights (summary screen)
    @Query("SELECT category, SUM(amountInShekels) AS total FROM expenses GROUP BY category")
    List<CategoryTotal> getCategoryTotalsForAllFlights();

    // Total spent on one flight in shekels (0 if there are no expenses)
    @Query("SELECT COALESCE(SUM(amountInShekels), 0) FROM expenses WHERE flightOwnerId = :flightId")
    double getTotalForFlight(long flightId);
}
