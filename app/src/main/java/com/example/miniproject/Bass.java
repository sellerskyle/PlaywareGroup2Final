package com.example.miniproject;

import com.livelife.motolibrary.AntData;

import java.util.ArrayList;

public class Bass implements Instrument{
    private ArrayList<Sound> sounds;
    private  boolean isLooped = true;

    public Bass() {

        sounds = new ArrayList<>();
        Sound one = new Sound(R.raw.one, AntData.LED_COLOR_RED, "I");
        Sound two = new Sound(R.raw.two, AntData.LED_COLOR_ORANGE, "ii");
        Sound three = new Sound(R.raw.three, AntData.LED_COLOR_GREEN, "iii");
        Sound four = new Sound(R.raw.four, AntData.LED_COLOR_BLUE, "IV");
        Sound five = new Sound(R.raw.five, AntData.LED_COLOR_INDIGO, "V");
        Sound six = new Sound(R.raw.six, AntData.LED_COLOR_VIOLET, "vi");
        Sound seven = new Sound(R.raw.seven, AntData.LED_COLOR_WHITE, "vii dim");

        sounds.add(one);
        sounds.add(five);
        sounds.add(four);
        sounds.add(six);
        sounds.add(two);
        sounds.add(three);
        sounds.add(seven);
    }

    public Sound get(int index) {
        return sounds.get(index);
    }
    public boolean isLooped() {return isLooped;}
}
