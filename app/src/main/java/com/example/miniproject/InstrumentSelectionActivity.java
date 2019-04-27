package com.example.miniproject;


import android.content.Intent;
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
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;


public class InstrumentSelectionActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, OnAntEventListener {

    MotoConnection connection;// = MotoConnection.getInstance();
    MotoSound sound = MotoSound.getInstance();
    int tilesConnected = 0;
    int tilesPerPlayer = 0;
    int color [] = {1,2,3,4,5}; // Red,Blue,Green,Indigo,Orange
    int AItiles = 0;
    int numberOfPlayers;


    TextView player3Text;
    TextView player4Text;
    TextView selectionText;

    Button playButton;

    String[] selectedInstruments = {"","","",""};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instrument_selection);
        setTitle("JAM!");

        connection=MotoConnection.getInstance();
        connection.registerListener(this);

        Bundle bundle =this.getIntent().getExtras();
        numberOfPlayers = bundle.getInt("player");

        player3Text = findViewById(R.id.player3);
        player4Text = findViewById(R.id.player4);
        if (numberOfPlayers <= 2) {
            player3Text.setText("Autoplay 1 Selection:");
            player4Text.setText("Autoplay 2 Selection:");

        } else {
            player4Text.setText("Autoplay Selection:");
        }

        //Showing which tiles belongs to which player
        tilesConnected = connection.connectedTiles.size();
        tilesPerPlayer = tilesConnected / numberOfPlayers;

        if (tilesPerPlayer == 5){
            tilesPerPlayer = 4;
        }

        connection.setAllTilesColor(AntData.LED_COLOR_OFF);
        for(int i = 0 ; i < numberOfPlayers; i++){
            for(int j = 0 ; j < tilesPerPlayer; j++){
                int tileid=(tilesPerPlayer*i)+j+1;
                Log.v("Game","Id:" + tileid);
                connection.setTileColor(color[i], tileid);
            }
        }

        AItiles = tilesConnected-(numberOfPlayers*tilesPerPlayer);

        for(int i = 0 ; i < AItiles; i++){
            connection.setTileColor(color[i+numberOfPlayers], (numberOfPlayers*tilesPerPlayer)+i+1);
        }

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

        Spinner player4 = (Spinner) findViewById(R.id.player4selection);
        ArrayAdapter<CharSequence> adapter4 = ArrayAdapter.createFromResource(this, R.array.instrument_options, android.R.layout.simple_spinner_item);
        adapter4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        player4.setPrompt("Choose an instrument!");
        player4.setAdapter(adapter4);
        player4.setOnItemSelectedListener(this);

        playButton = findViewById(R.id.playButton);
        selectionText = findViewById(R.id.selectionMessage);
        playButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if(!isFull(selectedInstruments)){
                    //display text stating to select an instrument for all players
                    selectionText.setText("Choose an instrument for each player!");
                } else if(!isUnique(selectedInstruments)) {
                    //display relevant message
                    selectionText.setText("Choose a unique instrument for each player!");
                } else {
                    connection.unregisterListener(InstrumentSelectionActivity.this);
                    Intent i = new Intent(InstrumentSelectionActivity.this, PlayActivity.class);
                    i.putExtra("player", numberOfPlayers);
                    i.putExtra("tilesPerPlayer", tilesPerPlayer);
                    i.putExtra("selectedInstruments", selectedInstruments);
                    startActivity(i);
                }
            }
        });


    }


    /** Link this activity to next using the code below **/

    @Override
    public void onItemSelected(AdapterView<?> spinner, View view, int i, long l) {

        int id = spinner.getId();
        switch (id) {
            case (R.id.player1 + 1):
                selectedInstruments[0] = (String) spinner.getItemAtPosition(i);
                break;

            case (R.id.player2 + 1):
                selectedInstruments[1] = (String) spinner.getItemAtPosition(i);
                break;

            case (R.id.player3 + 1):
                selectedInstruments[2] = (String) spinner.getItemAtPosition(i);
                break;

            case (R.id.player4 + 1):
                selectedInstruments[3] = (String) spinner.getItemAtPosition(i);
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    public void onMessageReceived(byte[] bytes, long l) {

        int tileid=AntData.getId(bytes);
        Log.v("game","pressed id:"+tileid);

    }

    @Override
    public void onAntServiceConnected() {

    }

    @Override
    public void onNumbersOfTilesConnected(int i) {
        tilesConnected = i;
    }

    private boolean isFull(String[] array) {
        for(String s: array) {
            if(s.equals("")){
                return false;
            }
        }
        return true;
    }

    private boolean isUnique(String arr[])
    {
        // Put all array elements in a HashSet
        Set<String> s = new HashSet<String>(Arrays.asList(arr));

        // If all elements are distinct, size of
        // HashSet should be same array.
        return (s.size() == arr.length);
    }
}



