package com.example.flightsfinal.Interfaces;

// Generic callback that returns a result from the repository to the main (UI) thread
public interface RepositoryCallback<T> {
    void onResult(T result);
}
