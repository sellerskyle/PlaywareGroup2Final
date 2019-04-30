package com.example.miniproject;

import android.media.MediaPlayer;

import com.livelife.motolibrary.AntData;

import java.util.ArrayList;

public class Drums implements Instrument{
    private ArrayList<Sound> drumSounds;
    private ArrayList<Sound> loops;
    private boolean isLooped = false;
    public Drums() {
        drumSounds = new ArrayList<>();
        Sound kick = new Sound(R.raw.kick707, AntData.LED_COLOR_BLUE, "Kick");
        Sound snare = new Sound(R.raw.snare707, AntData.LED_COLOR_RED, "Snare");
        Sound closedHat = new Sound(R.raw.closedhat707, AntData.LED_COLOR_WHITE, "Closed Hi-Hat");
        Sound openHat = new Sound(R.raw.openhat707, AntData.LED_COLOR_ORANGE, "Open Hi-Hat");

        Sound loop1 = new Sound(R.raw.drumloop1, AntData.LED_COLOR_RED, "Loop 1");
        Sound loop2 = new Sound(R.raw.drumloop2, AntData.LED_COLOR_GREEN, "Loop 2");
        Sound loop3 = new Sound(R.raw.drumloop3, AntData.LED_COLOR_BLUE, "Loop 3");
        loops.add(loop1);
        loops.add(loop2);
        loops.add(loop3);

        drumSounds.add(kick);
        drumSounds.add(snare);
        drumSounds.add(closedHat);
        drumSounds.add(openHat);
    }

    public Sound get(int index) {
        return drumSounds.get(index);
    }

    @Override
    public Sound getLoop(int index) {
        return loops.get(index);
    }

    public boolean isLooped() {return isLooped;}
}
