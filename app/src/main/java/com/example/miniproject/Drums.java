package com.example.miniproject;

import android.media.MediaPlayer;

import com.livelife.motolibrary.AntData;

import java.util.ArrayList;

public class Drums implements Instrument{
    private ArrayList<Sound> drumSounds;
    private boolean isLooped = false;
    public Drums() {
        drumSounds = new ArrayList<>();
        Sound kick = new Sound(R.raw.kick707, AntData.LED_COLOR_BLUE, "Kick");
        Sound snare = new Sound(R.raw.snare707, AntData.LED_COLOR_RED, "Snare");
        Sound closedHat = new Sound(R.raw.closedhat707, AntData.LED_COLOR_WHITE, "Closed Hi-Hat");
        Sound openHat = new Sound(R.raw.openhat707, AntData.LED_COLOR_ORANGE, "Open Hi-Hat");

        drumSounds.add(kick);
        drumSounds.add(snare);
        drumSounds.add(closedHat);
        drumSounds.add(openHat);
    }

    public Sound get(int index) {
        return drumSounds.get(index);
    }
    public boolean isLooped() {return isLooped;}
}
