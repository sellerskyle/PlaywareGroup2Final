package com.example.miniproject;
import android.content.Intent;

import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.livelife.motolibrary.MotoConnection;
import com.livelife.motolibrary.MotoSound;
import com.livelife.motolibrary.OnAntEventListener;



public class MainActivity extends AppCompatActivity implements OnAntEventListener {

    MotoConnection connection = MotoConnection.getInstance();
    MotoSound sound = MotoSound.getInstance();

    Button paringButton;
    Button TwoPlayerButton = findViewById(R.id.TwoPlayerButton);
    Button ThreePlayerButton = findViewById(R.id.ThreePlayerButton);


    boolean isParing = false;

    TextView statusTextView;

    int tilesConnected;
    int numberOfPlayers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sound.initializeSounds(this);

        connection.startMotoConnection(MainActivity.this);
        connection.saveRfFrequency(26); //(Group No.)*10+6
        connection.setDeviceId(2); //Your group number
        connection.registerListener(MainActivity.this);

        statusTextView = findViewById(R.id.statusTextView);

        paringButton = findViewById(R.id.paringButton);
        paringButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if(!isParing){
                    connection.pairTilesStart();
                    paringButton.setText("Stop Paring");
                } else {
                    connection.pairTilesStop();
                    paringButton.setText("Start Paring");
                }
                isParing = !isParing;
            }
        });

        TwoPlayerButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                numberOfPlayers = 2;
                connection.unregisterListener(MainActivity.this);
                Intent i = new Intent(MainActivity.this, InstrumentSelectionActivity.class);
                i.putExtra("player",numberOfPlayers)
                startActivity(i);
            }
        });

        ThreePlayerButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                numberOfPlayers = 3;
                connection.unregisterListener(MainActivity.this);
                Intent i = new Intent(MainActivity.this, InstrumentSelectionActivity.class);
                i.putExtra("player",numberOfPlayers)
                startActivity(i);
            }
        });
    }

    @Override
    public void onMessageReceived(byte[] bytes, long l) {

    }

    @Override
    public void onAntServiceConnected() {

    }

    @Override
    public void onNumbersOfTilesConnected(int i) {
        statusTextView.setText(i + "connected tile(s)");
        tilesConnected = i;
    }

    @Override
    protected void onResume() {
        super.onResume();
        connection.registerListener(MainActivity.this);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        connection.registerListener(MainActivity.this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        connection.stopMotoConnection();
        connection.unregisterListener(MainActivity.this);
    }
}
