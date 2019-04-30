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

import java.util.ArrayList;

public class
PlayActivity extends AppCompatActivity implements OnAntEventListener {

    MotoConnection connection = MotoConnection.getInstance();
    MotoSound sound = MotoSound.getInstance();

    TextView player1;
    TextView player2;
    TextView player3;
    TextView player4;

    int playernum; //Change it to the actual number of players selected.
    int tilesPerPlayer;


    ConstraintLayout drumLayout;

    ArrayList<MediaPlayer> mediaPlayers = new ArrayList<>();
    ArrayList<Instrument> instrumentRack = new ArrayList<>();
    ArrayList<Sound> soundBank = new ArrayList<>();
    int tilesConnected = 0;
    int tilePressed;

    int AIOneIndex;
    int AITwoIndex;
    ArrayList<MediaPlayer> loopPlayers = new ArrayList<>();
    ArrayList<Sound> loopSounds = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);
        connection.registerListener(this);
        // This part is copied and pasted. I think this originally called the instruments. Since this may now be subjugated to
        // A different class, we may delete these called methods
        Bundle bundle =this.getIntent().getExtras();
        playernum = bundle.getInt("player");
        tilesPerPlayer = bundle.getInt("tilesPerPlayer");

        //The squares where it says what instrument is currently playing are called player1, player2 and so on.
        player1 = findViewById(R.id.player1);
        player2 = findViewById(R.id.player2);
        player3 = findViewById(R.id.player3);
        player4 = findViewById(R.id.player4);

        AIOneIndex = 0;
        AITwoIndex = 0;

        String[] selectedInstruments = bundle.getStringArray("selectedInstruments");
        for(String s:selectedInstruments) {
            switch(s) {
                case "DRUMS":
                    instrumentRack.add(new Drums());
                    break;
                case "CHORDS":
                    instrumentRack.add(new ChordsSynth());
                    break;
                case "MELODY":
                    instrumentRack.add(new Melody());
                    break;
                case "BASS":
                    instrumentRack.add(new Bass());
                    break;
            }
        }
        connection.setAllTilesColor(AntData.LED_COLOR_OFF);
         for(int i = 0; i < playernum; i++){
             Instrument currentInstrument = instrumentRack.get(i);
             if (currentInstrument.isLooped()) {
                 for (int j = 0; j < tilesPerPlayer; j++) {
                     Sound currentSound = currentInstrument.get(j);
                     soundBank.add(currentSound);
//                     MediaPlayer k = MediaPlayer.create(this, currentSound.resourceID);
//                     k.setLooping(true);
//                     mediaPlayers.add(k);
                     //mediaPlayers.add(PerfectLoopMediaPlayer.create(this, currentSound.resourceID));
                     PerfectLoopMediaPlayer plmp = null;
                     mediaPlayers.add(plmp);
                 }
             } else {
                 for (int j = 0; j < tilesPerPlayer; j++) {
                     Sound currentSound = currentInstrument.get(j);
                     soundBank.add(currentSound);
                     mediaPlayers.add(MediaPlayer.create(this, currentSound.resourceID));

                 }
             }
         }

         //TODO

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
                connection.setTileColor(AntData.LED_COLOR_WHITE, 10); //Change 10 to 9 if the array starts at 0
            }
            else if (playernum == 2) {
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
                connection.setTileColor(AntData.LED_COLOR_WHITE, 10); //If array starts in 0 change to 9
                connection.setTileColor(AntData.LED_COLOR_WHITE, 9);  //If array starts in 0 change to 8
            }
        }
    }

    @Override


    public void onMessageReceived(byte[] bytes, long l) {
        tilePressed = AntData.getId(bytes) - 1;
        final int tileID = AntData.getId(bytes);
        if (playernum == 2){
            if (AntData.getCommand(bytes) == AntData.EVENT_PRESS) {

                if(tilePressed <= 4){
                    mediaPlayers.get(tilePressed).start();

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            player1.setText(soundBank.get(tilePressed).name);
                            switch ( tileID% 4) {
                                case 1:
                                    player1.setBackgroundColor(Color.parseColor("#AED6F1"));
                                    break;
                                case 2:
                                    player1.setBackgroundColor(Color.parseColor("#ed7368"));
                                    break;
                                case 3:
                                    player1.setBackgroundColor(Color.parseColor("#ffa856"));
                                    break;
                                case 0:
                                    player1.setBackgroundColor(Color.WHITE);
                                    break;
                                default:
                            }
                        }
                });
                }else if (AntData.getId(bytes) <= 8) {
                    mediaPlayers.get(tilePressed).start();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                    player2.setText(soundBank.get(tilePressed).name);
                            switch ( tileID% 4) {
                                case 1:
                                    player2.setBackgroundColor(Color.parseColor("#AED6F1"));
                                    break;
                                case 2:
                                    player2.setBackgroundColor(Color.parseColor("#ed7368"));
                                    break;
                                case 3:
                                    player2.setBackgroundColor(Color.parseColor("#ffa856"));
                                    break;
                                case 0:
                                    player2.setBackgroundColor(Color.WHITE);
                                    break;
                                default:
                            }
                        }
                    });
                }else {
                    //AI Stuff
                }
            } else if (AntData.getCommand(bytes) == AntData.EVENT_RELEASE) {
                if (tilePressed <= 8) {
                    if(instrumentRack.get(tilePressed/4).isLooped()) {
                        mediaPlayers.get(tilePressed).stop();
                        mediaPlayers.get(tilePressed).prepareAsync();
                    }
                } else {
                    //AI stuff
                }
            }
        }else{
            if (AntData.getCommand(bytes) == AntData.EVENT_PRESS) {
                if(tilePressed <= 3){
                    mediaPlayers.get(tilePressed).start();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                    player1.setText(soundBank.get(tilePressed).name);
                            switch ( tileID% 3) {
                                case 1:
                                    player1.setBackgroundColor(Color.parseColor("#AED6F1"));
                                    break;
                                case 2:
                                    player1.setBackgroundColor(Color.parseColor("#ed7368"));
                                    break;
                                case 0:
                                    player1.setBackgroundColor(Color.WHITE);
                                    break;
                                default:
                            }
                        }
                    });
                }else if (AntData.getId(bytes) <= 6) {
                    mediaPlayers.get(tilePressed).start();
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                    player2.setText(soundBank.get(tilePressed).name);
                                    switch ( tileID% 3) {
                                        case 1:
                                            player2.setBackgroundColor(Color.parseColor("#AED6F1"));
                                            break;
                                        case 2:
                                            player2.setBackgroundColor(Color.parseColor("#ed7368"));
                                            break;
                                        case 0:
                                            player2.setBackgroundColor(Color.WHITE);
                                            break;
                                        default:
                                    }
                                }
                            });
                }else if (AntData.getId(bytes) <= 9) {
                    mediaPlayers.get(tilePressed).start();
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                    player3.setText(soundBank.get(tilePressed).name);
                                            switch ( tileID% 3) {
                                                case 1:
                                                    player3.setBackgroundColor(Color.parseColor("#AED6F1"));
                                                    break;
                                                case 2:
                                                    player3.setBackgroundColor(Color.parseColor("#ed7368"));
                                                    break;
                                                case 0:
                                                    player3.setBackgroundColor(Color.WHITE);
                                                    break;
                                                default:
                                            }
                                        }
                                    });
                }else {
                    //AI Stuff
                }
            } else if (AntData.getCommand(bytes) == AntData.EVENT_RELEASE) {
                if (tilePressed <= 9) {
                    if(instrumentRack.get(tilePressed/3).isLooped()) {
                        mediaPlayers.get(tilePressed).stop();
                        mediaPlayers.get(tilePressed).prepareAsync();
                    }
                } else {
                    //AI stuff
                }
            }
        }
//        if (AntData.getCommand(bytes) == AntData.EVENT_PRESS) {
//            //Use this when updating the text
//            if (AntData.getId(bytes) == 0) {
//                //kick.start(); //Change this to the method of the instrument
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        player1.setText("Instrument 1 sound 1");
//                        player2.setBackgroundColor(Color.parseColor("#AED6F1"));
//                    }
//                });
//
//            } else if (AntData.getId(bytes) == 1) {
//                //snare.start(); //Change this to the method of the instrument
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        player1.setText("Instrument 1 sound 2");
//                        player1.setBackgroundColor(Color.parseColor("#F1948A"));
//                    }
//                });
//            } else if (AntData.getId(bytes) == 2) {
//                //closedHat.start(); //Change this to the method of the instrument
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        player1.setText("Instrument 1 sound 3");
//                        player1.setBackgroundColor(Color.parseColor("#ffe066"));
//                    }
//                });
//            } else if (AntData.getId(bytes) == 3) {
//                if (playernum == 2) {
//                    //openHat.start(); //Change this to the method of the instrument
//                    runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            player1.setText("Instrument 1 sound 4");
//                            player1.setBackgroundColor(Color.parseColor("#ffffff"));
//                        }
//                    });
//                }
//                if (playernum == 3) {
//                    //method for 2nd instrument goes here
//                    runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            player2.setText("Instrument 2 sound 1");
//                            player2.setBackgroundColor(Color.parseColor("#AED6F1"));
//                        }
//                    });
//                }
//
//            }
//        } else if (AntData.getId(bytes) == 4) {
//            if (playernum == 2) {
//                //openHat.start();
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        player2.setText("Instrument 2 sound 1");
//                        player2.setBackgroundColor(Color.parseColor("#F1948A"));
//                    }
//                });
//            }
//            if (playernum == 3) {
//                //method for 2nd instrument
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        player2.setText("Instrument 2 sound 2");
//                        player2.setBackgroundColor(Color.parseColor("#AED6F1"));
//                    }
//                });
//            }
//        } else if (AntData.getId(bytes) == 5) {
//            if (playernum == 2) {
//                //method for 2nd instrument
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        player2.setText("Instrument 2 sound 2");
//                        player2.setBackgroundColor(Color.parseColor("#AED6F1"));
//                    }
//                });
//            }
//            if (playernum == 3) {
//                //method for 2nd instrument
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        player2.setText("Instrument 2 sound 3");
//                        player2.setBackgroundColor(Color.parseColor("#ffe066"));
//                    }
//                });
//            }
//        } else if (AntData.getId(bytes) == 6) {
//            if (playernum == 2) {
//                //method for 2nd instrument
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        player2.setText("Instrument 2 sound 3");
//                        player2.setBackgroundColor(Color.parseColor("#ffe066"));
//                    }
//                });
//            }
//            if (playernum == 3) {
//                //method for 3rd instrument
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        player3.setText("Instrument 3 sound 2");
//                        player3.setBackgroundColor(Color.parseColor("#F1948A"));
//                    }
//                });
//            }
//        } else if (AntData.getId(bytes) == 7) {
//            if (playernum == 2) {
//                //method for 2nd instrument
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        player2.setText("Instrument 2 sound 4");
//                        player2.setBackgroundColor(Color.parseColor("#ffffff"));
//                    }
//                });
//            }
//            if (playernum == 3) {
//                //method for 3rd instrument
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        player3.setText("Instrument 3 sound 2");
//                        player3.setBackgroundColor(Color.parseColor("#F1948A"));
//                    }
//                });
//            }
//        } else if (AntData.getId(bytes) == 8) {
//            if (playernum == 3) {
//                //method for 3rd instrument
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        player3.setText("Instrument 3 sound 3");
//                        player3.setBackgroundColor(Color.parseColor("#ffe066"));
//                    }
//                });
//            }
//        }
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
