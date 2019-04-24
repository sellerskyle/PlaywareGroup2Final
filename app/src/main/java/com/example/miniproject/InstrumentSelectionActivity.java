package com.example.miniproject;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import com.livelife.motolibrary.AntData;
import com.livelife.motolibrary.MotoConnection;
import com.livelife.motolibrary.MotoSound;
import com.livelife.motolibrary.OnAntEventListener;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

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

public class InstrumentSelectionActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instrument_selection);

        Spinner player1 = (Spinner) findViewById(R.id.player1selection);
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.instrument_options, android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        player1.setPrompt("Choose an instrument!");
        player1.setAdapter(adapter1);
        player1.setOnItemSelectedListener(this);

        Spinner player2 = (Spinner) findViewById(R.id.player2selection);
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this, R.array.instrument_options, android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        player2.setPrompt("Choose an instrument!");
        player2.setAdapter(adapter2);
        player2.setOnItemSelectedListener(this);

        Spinner player3 = (Spinner) findViewById(R.id.player3selection);
        ArrayAdapter<CharSequence> adapter3 = ArrayAdapter.createFromResource(this, R.array.instrument_options, android.R.layout.simple_spinner_item);
        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        player3.setPrompt("Choose an instrument!");
        player3.setAdapter(adapter3);
        player3.setOnItemSelectedListener(this);

    }


    /** Link this activity to next using the code below **/

    @Override
    public void onItemSelected(AdapterView<?> spinner, View view, int i, long l) {

        int id = spinner.getId();
        switch (id) {
            case R.id.player1:
                break;

            case R.id.player2:
                break;

            case R.id.player3:
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}



