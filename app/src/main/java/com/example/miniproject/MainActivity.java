package com.example.miniproject;
import android.content.Intent;

import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.livelife.motolibrary.AntData;
import com.livelife.motolibrary.MotoConnection;
import com.livelife.motolibrary.MotoSound;
import com.livelife.motolibrary.OnAntEventListener;



public class MainActivity extends AppCompatActivity implements OnAntEventListener {

    MotoConnection connection;
    MotoSound sound = MotoSound.getInstance();

    Button paringButton;
    ImageButton TwoPlayerButton;
    ImageButton ThreePlayerButton;

    boolean isParing = false;

    TextView statusTextView;

    int tilesConnected;
    int numberOfPlayers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTitle("JAM!");

        sound.initializeSounds(this);

        connection=MotoConnection.getInstance();
        connection.startMotoConnection(MainActivity.this);
        connection.saveRfFrequency(26); //(Group No.)*10+6
        connection.setDeviceId(2); //Your group number
        connection.registerListener(MainActivity.this);

        statusTextView = findViewById(R.id.statusTextView);

        paringButton = findViewById(R.id.paringButton);
        //tileIDs = findViewById(R.id.tileIDs);
        //tileIDs.setText(connection.);
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

        TwoPlayerButton = findViewById(R.id.TwoPlayerButton);
        TwoPlayerButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                numberOfPlayers = 2;
                connection.unregisterListener(MainActivity.this);
                Intent i = new Intent(MainActivity.this, InstrumentSelectionActivity.class);
                i.putExtra("player",numberOfPlayers);

                startActivity(i);
            }
        });

        ThreePlayerButton = findViewById(R.id.ThreePlayerButton);
        ThreePlayerButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                numberOfPlayers = 3;
                connection.unregisterListener(MainActivity.this);
                Intent i = new Intent(MainActivity.this, InstrumentSelectionActivity.class);
                i.putExtra("player",numberOfPlayers);
                startActivity(i);
            }
        });

        //connection.setAllTilesColor(AntData.LED_COLOR_BLUE);
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
