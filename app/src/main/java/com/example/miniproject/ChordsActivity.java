package com.example.miniproject;

import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.livelife.motolibrary.AntData;
import com.livelife.motolibrary.MotoConnection;
import com.livelife.motolibrary.MotoSound;
import com.livelife.motolibrary.OnAntEventListener;

public class ChordsActivity extends AppCompatActivity implements OnAntEventListener {

    MotoConnection connection = MotoConnection.getInstance();
    MotoSound sound = MotoSound.getInstance();

    MediaPlayer one;
    MediaPlayer two;
    MediaPlayer three;
    MediaPlayer four;
    MediaPlayer five;
    MediaPlayer six;
    MediaPlayer seven;

    MediaPlayer loop;

    TextView chordView;
    Button backButton;
    Button playButton;
    Button stopButton;
    ConstraintLayout chordLayout;

    int tilesConnected = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chords);
        connection.registerListener(this);

        one = MediaPlayer.create(this, R.raw.one);
        two = MediaPlayer.create(this, R.raw.two);
        three = MediaPlayer.create(this, R.raw.three);
        four = MediaPlayer.create(this, R.raw.four);
        five = MediaPlayer.create(this, R.raw.five);
        six = MediaPlayer.create(this, R.raw.six);
        seven = MediaPlayer.create(this, R.raw.seven);
        loop = MediaPlayer.create(this,R.raw.drum);

        one.setLooping(true);
        two.setLooping(true);
        three.setLooping(true);
        four.setLooping(true);
        five.setLooping(true);
        six.setLooping(true);
        seven.setLooping(true);
        loop.setLooping(true);

        chordView = findViewById(R.id.chordView);
        chordLayout = findViewById(R.id.chordLayout);

//        backButton = findViewById(R.id.backButton);
//        backButton.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View view) {
//                connection.setAllTilesBlink(2, AntData.LED_COLOR_WHITE);
//                connection.unregisterListener(ChordsActivity.this);
//                Intent i = new Intent(ChordsActivity.this, MainActivity.class);
//                startActivity(i);
//            }
//        });



        for(Integer i : connection.connectedTiles) {
            switch(i % 7){
                case 1:
                    connection.setTileColor(AntData.LED_COLOR_RED,i);
                    connection.setTileColorRelease(AntData.LED_COLOR_RED,i);
                    break;
                case 5:
                    connection.setTileColor(AntData.LED_COLOR_ORANGE,i);
                    connection.setTileColorRelease(AntData.LED_COLOR_ORANGE,i);
                    break;
                case 6:
                    connection.setTileColor(AntData.LED_COLOR_GREEN,i);
                    connection.setTileColorRelease(AntData.LED_COLOR_GREEN,i);
                    break;
                case 3:
                    connection.setTileColor(AntData.LED_COLOR_BLUE,i);
                    connection.setTileColorRelease(AntData.LED_COLOR_BLUE,i);
                    break;
                case 2:
                    connection.setTileColor(AntData.LED_COLOR_INDIGO,i);
                    connection.setTileColorRelease(AntData.LED_COLOR_INDIGO,i);
                    break;
                case 4:
                    connection.setTileColor(AntData.LED_COLOR_VIOLET,i);
                    connection.setTileColorRelease(AntData.LED_COLOR_VIOLET,i);
                    break;
                case 0:
                    connection.setTileColor(AntData.LED_COLOR_WHITE,i);
                    connection.setTileColorRelease(AntData.LED_COLOR_WHITE,i);
                    break;
                default:

            }
        }
        playButton = findViewById(R.id.playButton);
        playButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                loop.start();
            }
        });

        stopButton = findViewById(R.id.stopButton);
        stopButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                loop.stop();
                loop.prepareAsync();
            }
        });
    }

    @Override


    public void onMessageReceived(byte[] bytes, long l) {
        if (AntData.getCommand(bytes) == AntData.EVENT_PRESS) {
            //Use this when updating the text
            if (AntData.getId(bytes) % 7 == 1) {
                one.start();
//                sound.playPianoSound(1);
//                sound.playPianoSound(3);
//                sound.playPianoSound(5);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        chordView.setText("I");
                        chordLayout.setBackgroundColor(Color.parseColor("#F1948A"));
                    }
                });

            } else if (AntData.getId(bytes) % 7 == 2) {
                five.start();
//                sound.playPianoSound(2);
//                sound.playPianoSound(5);
//                sound.playPianoSound(7);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        chordView.setText("V");
                        chordLayout.setBackgroundColor(Color.parseColor("#a64dff"));
                    }
                });
            } else if (AntData.getId(bytes) % 7 == 3) {
                four.start();
//                sound.playPianoSound(1);
//                sound.playPianoSound(4);
//                sound.playPianoSound(6);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        chordView.setText("IV");
                        chordLayout.setBackgroundColor(Color.parseColor("#AED6F1"));
                    }
                });
            } else if (AntData.getId(bytes) % 7 == 4) {
                System.out.println("Click Message Received");
                six.start();
//                sound.playPianoSound(1);
//                sound.playPianoSound(3);
//                sound.playPianoSound(6);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        chordView.setText("vi");
                        chordLayout.setBackgroundColor(Color.parseColor("#dd99ff"));
                    }
                });
            } else if (AntData.getId(bytes) % 7 == 5) {
                two.start();
                System.out.println("Click Message Received");
//                sound.playPianoSound(2);
//                sound.playPianoSound(4);
//                sound.playPianoSound(6);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        chordView.setText("ii");
                        chordLayout.setBackgroundColor(Color.parseColor("#ffe066"));
                    }
                });
            } else if (AntData.getId(bytes) % 7 == 6) {
                three.start();
                System.out.println("Click Message Received");
//                sound.playPianoSound(3);
//                sound.playPianoSound(5);
//                sound.playPianoSound(7);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        chordView.setText("iii");
                        chordLayout.setBackgroundColor(Color.parseColor("#99ff99"));
                    }
                });
            } else if (AntData.getId(bytes) % 7 == 0) {
                seven.start();
//                sound.playPianoSound(2);
//                sound.playPianoSound(4);
//                sound.playPianoSound(7);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        chordView.setText("vii dim");
                        chordLayout.setBackgroundColor(Color.parseColor("#ffffff"));
                    }
                });
            }

        } else if (AntData.getCommand(bytes) == AntData.EVENT_RELEASE) {
            System.out.println("Release Signal Received");
            if (AntData.getId(bytes) % 7 == 1) {
                one.stop();
                one.prepareAsync();

            } else if (AntData.getId(bytes) % 7 == 2) {
                five.stop();
                five.prepareAsync();
            } else if (AntData.getId(bytes) % 7 == 3) {
                four.stop();
                four.prepareAsync();
            } else if (AntData.getId(bytes) % 7 == 4) {
                six.stop();
                six.prepareAsync();
            } else if (AntData.getId(bytes) % 7 == 5) {
                two.stop();
                two.prepareAsync();
            } else if (AntData.getId(bytes) % 7 == 6) {
                three.stop();
                three.prepareAsync();
            } else if (AntData.getId(bytes) % 7 == 0) {
                seven.stop();
                seven.prepareAsync();
            }
        }
    }

    @Override
    public void onAntServiceConnected() {

    }

    @Override
    public void onNumbersOfTilesConnected(int i) {
        tilesConnected = i;
    }

    @Override
    protected void onResume() {
        super.onResume();
        connection.registerListener(ChordsActivity.this);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        connection.registerListener(ChordsActivity.this);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        connection.unregisterListener(this);
    }
}
