package com.example.flightsfinal.Classes;

// One row of the GROUP BY summary query: a category and its total in shekels
public class CategoryTotal {

    private ExpenseCategory category;
    private double total;

    // Empty constructor required by Room
    public CategoryTotal() {
    }

    public ExpenseCategory getCategory() { return category; }
    public void setCategory(ExpenseCategory category) { this.category = category; }

    public double getTotal() { return total; }
    public void setTotal(double total) { this.total = total; }
}
