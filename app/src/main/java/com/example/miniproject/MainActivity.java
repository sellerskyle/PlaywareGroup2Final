package com.example.miniproject;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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
    Button instrumentSelection;

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

        instrumentSelection = findViewById(R.id.instrumentselection);
        instrumentSelection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                connection.unregisterListener(MainActivity.this);
                Intent i = new Intent(MainActivity.this, InstrumentSelectionActivity.class);
                startActivity(i);
            }
        });

//        Spinner instrumentSelection = (Spinner) findViewById(R.id.spinner);
//        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.instrument_options
//                , R.layout.support_simple_spinner_dropdown_item);
//
//        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
//
//        instrumentSelection.setAdapter(adapter);

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
