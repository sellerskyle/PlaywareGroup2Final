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
        int color [] = {1,2,3,4,5}; // Red,Blue,Green,Indigo,Orange
        int AItiles = 0;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chords);
        connection.registerListener(this);

        Bundle bundle =this.getIntent().getExtras();
        int numberOfPlayers = bundle.getInt("player");

        //Showing which tiles belongs to which player
        tilesPerPlayer = tilesConnected/numberOfPlayers;

        if (tilesPerPlayer == 5){
            tilesPerPlayer = 4;
        }

        for(int i = 0 ; i < numberOfPlayers; i++){
            for(int j = 1 ; j <= tilesPerPlayer; j++){
                connection.setTileColor(color[i], i);
                connection.setTileColorRelease(color[i], i);
            }
        }

        AItiles = tilesConnected-(numberOfPlayers*tilesPerPlayer);

        for(int i = 0 ; i < AItiles; i++){
            connection.setTileColor(color[i+numberOfPlayers], (numberOfPlayers*tilesPerPlayer)+i+1);
            connection.setTileColorRelease(color[i+numberOfPlayers], (numberOfPlayers*tilesPerPlayer)+i+1);
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



