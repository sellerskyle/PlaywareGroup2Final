package com.example.miniproject;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.livelife.motolibrary.AntData;
import com.livelife.motolibrary.MotoConnection;
import com.livelife.motolibrary.MotoSound;
import com.livelife.motolibrary.OnAntEventListener;

public class InstrumentSelectionActivity extends AppCompatActivity implements OnAntEventListener {
        MotoConnection connection = MotoConnection.getInstance();
        MotoSound sound = MotoSound.getInstance();
        int tilesConnected = 0;
        int tilesPerPlayer = 0;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chords);
        connection.registerListener(this);

        Bundle bundle =this.getIntent().getExtras();
        int numberOfPlayers = bundle.getInt("player");

        //Showing which tiles belongs to which player
        if(numberOfPlayers == 2) {
            for (Integer i : connection.connectedTiles) {
                switch (i % 7) {
                    case 1:
                        connection.setTileColor(AntData.LED_COLOR_RED, i);
                        connection.setTileColorRelease(AntData.LED_COLOR_RED, i);
                        break;
                    case 5:
                        connection.setTileColor(AntData.LED_COLOR_ORANGE, i);
                        connection.setTileColorRelease(AntData.LED_COLOR_ORANGE, i);
                        break;
                    case 6:
                        connection.setTileColor(AntData.LED_COLOR_GREEN, i);
                        connection.setTileColorRelease(AntData.LED_COLOR_GREEN, i);
                        break;
                    default:

                }
            }
        } else if(numberOfPlayers == 3) {
            tilesPerPlayer = tilesConnected/numberOfPlayers;
            for(int i = 0 ; i < numberOfPlayers; i++){
                for(int j = 1 ; j <= tilesPerPlayer; j++){
                    connection.setTileColor(AntData.LED_COLOR_GREEN, i);
                    connection.setTileColorRelease(AntData.LED_COLOR_GREEN, i);
                }

            }
            for (Integer i : connection.connectedTiles) {
                connection.setTileColor(AntData.LED_COLOR_GREEN, i);
                connection.setTileColorRelease(AntData.LED_COLOR_GREEN, i);

            }


        }
    }

    @Override
    public void onMessageReceived(byte[] bytes, long l) {

    }

    @Override
    public void onAntServiceConnected() {

    }

    @Override
    public void onNumbersOfTilesConnected(int i) {
        tilesConnected = i;
    }


}



