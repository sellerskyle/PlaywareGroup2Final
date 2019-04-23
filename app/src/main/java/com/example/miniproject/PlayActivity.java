package com.example.miniproject;

//This is almost copy-pasted from kyle's project. I only extended it so that there's more coloured-squares with the instruments.

import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;

import com.livelife.motolibrary.AntData;
import com.livelife.motolibrary.MotoConnection;
import com.livelife.motolibrary.MotoSound;
import com.livelife.motolibrary.OnAntEventListener;

public class PlayActivity extends AppCompatActivity implements OnAntEventListener {

    MotoConnection connection = MotoConnection.getInstance();
    MotoSound sound = MotoSound.getInstance();

    TextView player1;
    TextView player2;
    TextView player3;
    TextView player4;

    int playernum = 3; //Change it to the actual number of players selected.

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
        setContentView(R.layout.activity_play);
        connection.registerListener(this);
        // This part is copied and pasted. I think this originally called the instruments. Since this may now be subjugated to
        // A different class, we may delete these called methods.
        kick = MediaPlayer.create(this, R.raw.kick707);
        snare = MediaPlayer.create(this, R.raw.snare707);
        closedHat = MediaPlayer.create(this, R.raw.closedhat707);
        openHat = MediaPlayer.create(this, R.raw.openhat707);

        loop = MediaPlayer.create(this, R.raw.deadmau5chords);
        loop.setLooping(true);
        //The squares where it says what instrument is currently playing are called player1, player2 and so on.
        player1 = findViewById(R.id.player1);
        player2 = findViewById(R.id.player2);
        player3 = findViewById(R.id.player3);
        player4 = findViewById(R.id.player4);

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

        //This is the part of the code that implemented the "play" melody on the drums.

//        playButton = findViewById(R.id.playButton);
//        playButton.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View view) {
//                loop.start();
//            }
//        });

//        stopButton = findViewById(R.id.stopButton);
//        stopButton.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View view) {
//                loop.stop();
//                loop.prepareAsync();
//            }
//        });


        for (Integer i : connection.connectedTiles) {
            //If there's 3 players, each players gets 3 tiles. If there's 2 players each gets 4.
            //I assume that we always have 10 tiles.
            if (playernum == 3) {
                switch (i % 3) {
                    case 1:
                        connection.setTileColor(AntData.LED_COLOR_BLUE, i);
                        connection.setTileColorRelease(AntData.LED_COLOR_BLUE, i);
                        break;
                    case 2:
                        connection.setTileColor(AntData.LED_COLOR_RED, i);
                        connection.setTileColorRelease(AntData.LED_COLOR_RED, i);
                        break;
                    case 0:
                        connection.setTileColor(AntData.LED_COLOR_WHITE, i);
                        connection.setTileColorRelease(AntData.LED_COLOR_WHITE, i);
                        break;
                    default:
                }
                connection.setTileColor(AntData.LED_COLOR_OFF, 10); //Change 10 to 9 if the array starts at 0
            }
            if (playernum == 2) {
                switch (i % 4) {
                    case 1:
                        connection.setTileColor(AntData.LED_COLOR_BLUE, i);
                        connection.setTileColorRelease(AntData.LED_COLOR_BLUE, i);
                        break;
                    case 2:
                        connection.setTileColor(AntData.LED_COLOR_RED, i);
                        connection.setTileColorRelease(AntData.LED_COLOR_RED, i);
                        break;
                    case 3:
                        connection.setTileColor(AntData.LED_COLOR_ORANGE, i);
                        connection.setTileColorRelease(AntData.LED_COLOR_ORANGE, i);
                        break;
                    case 0:
                        connection.setTileColor(AntData.LED_COLOR_WHITE, i);
                        connection.setTileColorRelease(AntData.LED_COLOR_WHITE, i);
                        break;
                    default:
                }
                connection.setTileColor(AntData.LED_COLOR_OFF, 10); //If array starts in 0 change to 9
                connection.setTileColor(AntData.LED_COLOR_OFF, 9);  //If array starts in 0 change to 8
            }
        }
    }

    @Override


    public void onMessageReceived(byte[] bytes, long l) {
        if (AntData.getCommand(bytes) == AntData.EVENT_PRESS) {
            //Use this when updating the text
            if (AntData.getId(bytes) == 0) {
                kick.start(); //Change this to the method of the instrument
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        player1.setText("Instrument 1 sound 1");
                        player2.setBackgroundColor(Color.parseColor("#AED6F1"));
                    }
                });

            } else if (AntData.getId(bytes) == 1) {
                snare.start(); //Change this to the method of the instrument
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        player1.setText("Instrument 1 sound 2");
                        player1.setBackgroundColor(Color.parseColor("#F1948A"));
                    }
                });
            } else if (AntData.getId(bytes) == 2) {
                closedHat.start(); //Change this to the method of the instrument
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        player1.setText("Instrument 1 sound 3");
                        player1.setBackgroundColor(Color.parseColor("#ffe066"));
                    }
                });
            } else if (AntData.getId(bytes) == 3) {
                if (playernum == 2) {
                    openHat.start(); //Change this to the method of the instrument
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            player1.setText("Instrument 1 sound 4");
                            player1.setBackgroundColor(Color.parseColor("#ffffff"));
                        }
                    });
                }
                if (playernum == 3) {
                    //method for 2nd instrument goes here
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            player2.setText("Instrument 2 sound 1");
                            player2.setBackgroundColor(Color.parseColor("#AED6F1"));
                        }
                    });
                }

            }
        } else if (AntData.getId(bytes) == 4) {
            if (playernum == 2) {
                openHat.start();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        player2.setText("Instrument 2 sound 1");
                        player2.setBackgroundColor(Color.parseColor("#F1948A"));
                    }
                });
            }
            if (playernum == 3) {
                //method for 2nd instrument
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        player2.setText("Instrument 2 sound 2");
                        player2.setBackgroundColor(Color.parseColor("#AED6F1"));
                    }
                });
            }
        } else if (AntData.getId(bytes) == 5) {
            if (playernum == 2) {
                //method for 2nd instrument
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        player2.setText("Instrument 2 sound 2");
                        player2.setBackgroundColor(Color.parseColor("#AED6F1"));
                    }
                });
            }
            if (playernum == 3) {
                //method for 2nd instrument
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        player2.setText("Instrument 2 sound 3");
                        player2.setBackgroundColor(Color.parseColor("#ffe066"));
                    }
                });
            }
        } else if (AntData.getId(bytes) == 6) {
            if (playernum == 2) {
                //method for 2nd instrument
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        player2.setText("Instrument 2 sound 3");
                        player2.setBackgroundColor(Color.parseColor("#ffe066"));
                    }
                });
            }
            if (playernum == 3) {
                //method for 3rd instrument
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        player3.setText("Instrument 3 sound 2");
                        player3.setBackgroundColor(Color.parseColor("#F1948A"));
                    }
                });
            }
        } else if (AntData.getId(bytes) == 7) {
            if (playernum == 2) {
                //method for 2nd instrument
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        player2.setText("Instrument 2 sound 4");
                        player2.setBackgroundColor(Color.parseColor("#ffffff"));
                    }
                });
            }
            if (playernum == 3) {
                //method for 3rd instrument
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        player3.setText("Instrument 3 sound 2");
                        player3.setBackgroundColor(Color.parseColor("#F1948A"));
                    }
                });
            }
        } else if (AntData.getId(bytes) == 8) {
            if (playernum == 3) {
                //method for 3rd instrument
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        player3.setText("Instrument 3 sound 3");
                        player3.setBackgroundColor(Color.parseColor("#ffe066"));
                    }
                });
            }
        }
    }
        @Override
        public void onAntServiceConnected () {

        }

        @Override
        public void onNumbersOfTilesConnected ( int i){
            tilesConnected = i;
        }

        @Override
        protected void onResume () {
            super.onResume();
            connection.registerListener(PlayActivity.this);
        }

        @Override
        protected void onRestart () {
            super.onRestart();
            connection.registerListener(PlayActivity.this);
        }
        @Override
        protected void onDestroy () {
            super.onDestroy();
            connection.unregisterListener(this);
        }

}
