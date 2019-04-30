package com.example.miniproject;

import com.livelife.motolibrary.AntData;

import java.util.ArrayList;

public class ChordsSynth implements Instrument{
    private ArrayList<Sound> synthSounds;
    private ArrayList<Sound> loops;
    private  boolean isLooped = true;

    public ChordsSynth() {

        synthSounds = new ArrayList<>();
        loops = new ArrayList<>();
        Sound one = new Sound(R.raw.one, AntData.LED_COLOR_RED, "I");
        Sound two = new Sound(R.raw.two, AntData.LED_COLOR_ORANGE, "ii");
        Sound three = new Sound(R.raw.three, AntData.LED_COLOR_GREEN, "iii");
        Sound four = new Sound(R.raw.four, AntData.LED_COLOR_BLUE, "IV");
        Sound five = new Sound(R.raw.five, AntData.LED_COLOR_INDIGO, "V");
        Sound six = new Sound(R.raw.six, AntData.LED_COLOR_VIOLET, "vi");
        Sound seven = new Sound(R.raw.seven, AntData.LED_COLOR_WHITE, "vii dim");

        Sound loop1 = new Sound(R.raw.chordloop1, AntData.LED_COLOR_RED, "Loop 1");
        Sound loop2 = new Sound(R.raw.chordloop2, AntData.LED_COLOR_GREEN, "Loop 2");
        Sound loop3 = new Sound(R.raw.chordloop3, AntData.LED_COLOR_BLUE, "Loop 3");
        loops.add(loop1);
        loops.add(loop2);
        loops.add(loop3);


        synthSounds.add(one);
        synthSounds.add(five);
        synthSounds.add(four);
        synthSounds.add(six);
        synthSounds.add(two);
        synthSounds.add(three);
        synthSounds.add(seven);
    }

    public Sound get(int index) {
        return synthSounds.get(index);
    }
    public Sound getLoop(int index) {return loops.get(index);}
    public boolean isLooped() {return isLooped;}
}
