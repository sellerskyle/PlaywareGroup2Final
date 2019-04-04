package com.example.miniproject;

import com.livelife.motolibrary.AntData;

import java.util.ArrayList;

public class ChordsSynth {
   private ArrayList<Sound> synthSounds;

    public ChordsSynth() {

        synthSounds = new ArrayList<>();
        Sound one = new Sound(R.raw.one, AntData.LED_COLOR_RED, "I");
        Sound two = new Sound(R.raw.two, AntData.LED_COLOR_ORANGE, "ii");
        Sound three = new Sound(R.raw.three, AntData.LED_COLOR_GREEN, "iii");
        Sound four = new Sound(R.raw.four, AntData.LED_COLOR_BLUE, "IV");
        Sound five = new Sound(R.raw.five, AntData.LED_COLOR_INDIGO, "V");
        Sound six = new Sound(R.raw.six, AntData.LED_COLOR_VIOLET, "vi");
        Sound seven = new Sound(R.raw.seven, AntData.LED_COLOR_WHITE, "vii dim");

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
}
