package com.example.miniproject;

public interface Instrument {
    Sound get(int index);
    Sound getLoop(int index);
    boolean isLooped();
}
