package com.example.miniproject;

import com.livelife.motolibrary.AntData;

import java.util.ArrayList;

public class Bass implements Instrument{
    private ArrayList<Sound> sounds;
    private ArrayList<Sound> loops;
    private  boolean isLooped = false;

    public Bass() {

        sounds = new ArrayList<>();
        Sound one = new Sound(R.raw.bone, AntData.LED_COLOR_RED, "1");
        //Sound two = new Sound(R.raw.two, AntData.LED_COLOR_ORANGE, "2");
        //Sound three = new Sound(R.raw.three, AntData.LED_COLOR_GREEN, "3");
        Sound four = new Sound(R.raw.bfour, AntData.LED_COLOR_BLUE, "4");
        Sound five = new Sound(R.raw.bfive, AntData.LED_COLOR_INDIGO, "5");
        Sound six = new Sound(R.raw.bsix, AntData.LED_COLOR_VIOLET, "6");
        //Sound seven = new Sound(R.raw.seven, AntData.LED_COLOR_WHITE, "7");

        Sound loop1 = new Sound(R.raw.bassloop1, AntData.LED_COLOR_RED, "Loop 1");
        Sound loop2 = new Sound(R.raw.bassloop2, AntData.LED_COLOR_GREEN, "Loop 2");
        Sound loop3 = new Sound(R.raw.bassloop3, AntData.LED_COLOR_BLUE, "Loop 3");
        loops.add(loop1);
        loops.add(loop2);
        loops.add(loop3);

        sounds.add(one);
        sounds.add(five);
        sounds.add(four);
        sounds.add(six);
//        sounds.add(two);
//        sounds.add(three);
//        sounds.add(seven);
    }

    public Sound get(int index) {
        return sounds.get(index);
    }

    @Override
    public Sound getLoop(int index) {
        return loops.get(index);
    }

    public boolean isLooped() {return isLooped;}
}
