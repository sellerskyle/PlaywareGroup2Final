package com.example.miniproject;

import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.livelife.motolibrary.AntData;
import com.livelife.motolibrary.MotoConnection;
import com.livelife.motolibrary.MotoSound;
import com.livelife.motolibrary.OnAntEventListener;

public class DrumsActivity extends AppCompatActivity implements OnAntEventListener {

    MotoConnection connection = MotoConnection.getInstance();
    MotoSound sound = MotoSound.getInstance();

    TextView drumView;
    Button backButton;
    Button playButton;
    Button stopButton;
    ConstraintLayout drumLayout;

    MediaPlayer kick;
    MediaPlayer snare;
    MediaPlayer closedHat;
    MediaPlayer openHat;
    MediaPlayer loop;

    int tilesConnected = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drums);
        connection.registerListener(this);

        kick = MediaPlayer.create(this, R.raw.kick707);
        snare = MediaPlayer.create(this, R.raw.snare707);
        closedHat = MediaPlayer.create(this, R.raw.closedhat707);
        openHat = MediaPlayer.create(this, R.raw.openhat707);

        loop = MediaPlayer.create(this, R.raw.deadmau5chords);
        loop.setLooping(true);

        drumView = findViewById(R.id.drumView);
        drumLayout = findViewById(R.id.drumLayout);

//        backButton = findViewById(R.id.backButton);
//        backButton.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View view) {
//                connection.setAllTilesBlink(2, AntData.LED_COLOR_WHITE);
//                connection.unregisterListener(DrumsActivity.this);
//                Intent i = new Intent(DrumsActivity.this, MainActivity.class);
//                startActivity(i);
//            }
//        });

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

        for(Integer i : connection.connectedTiles) {
            switch(i % 4){
                case 1:
                    connection.setTileColor(AntData.LED_COLOR_BLUE,i);
                    connection.setTileColorRelease(AntData.LED_COLOR_BLUE,i);
                    break;
                case 2:
                    connection.setTileColor(AntData.LED_COLOR_RED,i);
                    connection.setTileColorRelease(AntData.LED_COLOR_RED,i);
                    break;
                case 3:
                    connection.setTileColor(AntData.LED_COLOR_ORANGE,i);
                    connection.setTileColorRelease(AntData.LED_COLOR_ORANGE,i);
                    break;
                case 0:
                    connection.setTileColor(AntData.LED_COLOR_WHITE,i);
                    connection.setTileColorRelease(AntData.LED_COLOR_WHITE,i);
                    break;
                default:

            }
        }
    }

    @Override


    public void onMessageReceived(byte[] bytes, long l) {
        if (AntData.getCommand(bytes) == AntData.EVENT_PRESS) {
            //Use this when updating the text
            if (AntData.getId(bytes) % 4 == 1) {
                kick.start();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        drumView.setText("Kick");
                        drumLayout.setBackgroundColor(Color.parseColor("#AED6F1"));
                    }
                });

            } else if (AntData.getId(bytes) % 4 == 2) {
                snare.start();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        drumView.setText("Snare");
                        drumLayout.setBackgroundColor(Color.parseColor("#F1948A"));
                    }
                });
            } else if (AntData.getId(bytes) % 4 == 3) {
                closedHat.start();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        drumView.setText("Closed HiHat");
                        drumLayout.setBackgroundColor(Color.parseColor("#ffe066"));
                    }
                });
            } else if (AntData.getId(bytes) % 4 == 0) {
                openHat.start();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        drumView.setText("Open HiHat");
                        drumLayout.setBackgroundColor(Color.parseColor("#ffffff"));
                    }
                });
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
        connection.registerListener(DrumsActivity.this);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        connection.registerListener(DrumsActivity.this);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        connection.unregisterListener(this);
    }
}
