package com.example.miniproject;

import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.livelife.motolibrary.AntData;
import com.livelife.motolibrary.MotoConnection;
import com.livelife.motolibrary.MotoSound;
import com.livelife.motolibrary.OnAntEventListener;

public class MainActivity extends AppCompatActivity implements OnAntEventListener {

    MotoConnection connection = MotoConnection.getInstance();
    MotoSound sound = MotoSound.getInstance();

    Button paringButton;
    Button chordsButton;
    Button drumsButton;

    boolean isParing = false;

    TextView statusTextView;
    TextView counter;
    TextView chordView;

    int tilesConnected;
    int numPresses = 0;

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
        chordView = findViewById(R.id.chordView);

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

        chordsButton = findViewById(R.id.chordsButton);
        chordsButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                connection.unregisterListener(MainActivity.this);
                Intent i = new Intent(MainActivity.this, ChordsActivity.class);
                startActivity(i);
//                if(!isChords){
//                    connection.setAllTilesColor(AntData.LED_COLOR_WHITE); //TODO Redo this
//                    chordsButton.setText("Don't Something");
//                } else {
//                    connection.setAllTilesBlink(2, AntData.LED_COLOR_WHITE);
//                    chordsButton.setText("Do Something");
//                }
//                isChords = !isChords;
            }
        });

        drumsButton = findViewById(R.id.drumsButton);
        drumsButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                connection.unregisterListener(MainActivity.this);
                Intent i = new Intent(MainActivity.this, DrumsActivity.class);
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
