package com.example.miniproject;

import com.livelife.motolibrary.AntData;

import java.util.ArrayList;

public class Melody implements Instrument{
    private ArrayList<Sound> synthSounds;
    private ArrayList<Sound> loops;
    private  boolean isLooped = false;

    public Melody() {
        synthSounds = new ArrayList<>();
        loops = new ArrayList<>();
        Sound one = new Sound(R.raw.mone, AntData.LED_COLOR_RED, "1");
        Sound two = new Sound(R.raw.mtwo, AntData.LED_COLOR_ORANGE, "2");
        Sound three = new Sound(R.raw.mthree, AntData.LED_COLOR_GREEN, "3");
        Sound four = new Sound(R.raw.mfour, AntData.LED_COLOR_BLUE, "4");
        Sound five = new Sound(R.raw.mfive, AntData.LED_COLOR_INDIGO, "5");
        Sound six = new Sound(R.raw.msix, AntData.LED_COLOR_VIOLET, "6");
        //Sound seven = new Sound(R.raw.seven, AntData.LED_COLOR_WHITE, "7");

        Sound loop1 = new Sound(R.raw.melodyloop1, AntData.LED_COLOR_RED, "Loop 1");
        Sound loop2 = new Sound(R.raw.melodyloop2, AntData.LED_COLOR_GREEN, "Loop 2");
        Sound loop3 = new Sound(R.raw.melodyloop3, AntData.LED_COLOR_BLUE, "Loop 3");
        loops.add(loop1);
        loops.add(loop2);
        loops.add(loop3);

        synthSounds.add(one);
        synthSounds.add(five);
        synthSounds.add(two);
        synthSounds.add(three);
//        synthSounds.add(two);
//        synthSounds.add(three);
//        synthSounds.add(seven);
    }

    public Sound get(int index) {
        return synthSounds.get(index);
    }

    @Override
    public Sound getLoop(int index) {
        return loops.get(index);
    }

    public boolean isLooped() {return isLooped;}
}
