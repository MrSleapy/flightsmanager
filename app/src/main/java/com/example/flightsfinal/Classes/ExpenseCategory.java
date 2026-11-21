package com.example.flightsfinal.Classes;

// The fixed expense categories (Room stores the enum name as text)
public enum ExpenseCategory {
    LODGING("לינה"),
    FOOD("אוכל"),
    ATTRACTIONS("אטרקציות"),
    TRANSPORT("תחבורה"),
    SHOPPING("קניות"),
    OTHER("אחר"); // anything that does not fit another category

    // Hebrew name shown to the user
    private final String label;

    ExpenseCategory(String label) {
        this.label = label;
    }

    public String getLabel() { return label; }
}
