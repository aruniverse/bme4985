package com.aruniverse.bme.finalproj;

import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Toast;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.SaveCallback;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class HearingTest extends AppCompatActivity {

    Button v8000,v10000, v12000, v14000, v15000,v16000, confirm, reset, save;
    MediaPlayer mp;
    SeekBar seekBar;
    AudioManager audioManager; // control the volume
    int hz;
    GraphView graphView;
    LineGraphSeries<DataPoint> data;
    HashMap<Integer, Integer> hm;
    String patientName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hearing_test);
        setTitle("Hearing Test");
        Intent intent = getIntent();
        patientName = intent.getStringExtra("name");

        v8000 = (Button)findViewById(R.id.button1);
        v10000 = (Button)findViewById(R.id.button2);
        v12000 = (Button)findViewById(R.id.button3);
        v14000 = (Button)findViewById(R.id.button4);
        v15000 = (Button)findViewById(R.id.button5);
        v16000 = (Button)findViewById(R.id.button6);
        confirm = (Button)findViewById(R.id.buttonConfirm);
        reset = (Button)findViewById(R.id.buttonReset);
        save = (Button)findViewById(R.id.buttonSave);
        seekBar = (SeekBar)findViewById(R.id.seekBar);
        graphView = (GraphView)findViewById(R.id.graphView);
        data = new LineGraphSeries<>(new DataPoint[]{});
        hm = new HashMap<Integer, Integer>();
        final Button[] buttonList = {v8000, v10000, v12000, v14000, v15000, v16000};

        mp = MediaPlayer.create(HearingTest.this,R.raw.v8000);
        mp.stop();
//        save.setEnabled(false);
        v8000.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mp.stop();
                confirm.setText("Confirm");
                mp = MediaPlayer.create(HearingTest.this,R.raw.v8000);
                mp.setLooping(true);
                mp.start();
                hz = 8000;
            }
        });
        v10000.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mp.stop();
                confirm.setText("Confirm");
                mp = MediaPlayer.create(HearingTest.this,R.raw.v10000);
                mp.setLooping(true);
                mp.start();
                hz = 10000;
            }
        });
        v12000.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mp.stop();
                confirm.setText("Confirm");
                mp = MediaPlayer.create(HearingTest.this,R.raw.v12000);
                mp.setLooping(true);
                mp.start();
                hz = 12000;
            }
        });
        v14000.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mp.stop();
                confirm.setText("Confirm");
                mp = MediaPlayer.create(HearingTest.this,R.raw.v14000);
                mp.setLooping(true);
                mp.start();
                hz = 14000;
            }
        });
        v15000.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mp.stop();
                confirm.setText("Confirm");
                mp = MediaPlayer.create(HearingTest.this,R.raw.v15000);
                mp.setLooping(true);
                mp.start();
                hz = 15000;
            }
        });
        v16000.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mp.stop();
                confirm.setText("Confirm");
                mp = MediaPlayer.create(HearingTest.this,R.raw.v16000);
                mp.setLooping(true);
                mp.start();
                hz = 16000;
            }
        });
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mp.stop();
                int vol = seekBar.getProgress();
                confirm.setText(hz + "," + vol);
                if(hz == 8000)
                    v8000.setEnabled(false);
                if(hz == 10000)
                    v10000.setEnabled(false);
                if(hz == 12000)
                    v12000.setEnabled(false);
                if(hz == 14000)
                    v14000.setEnabled(false);
                if(hz == 15000)
                    v15000.setEnabled(false);
                if(hz == 16000)
                    v16000.setEnabled(false);
                    if(hm.isEmpty()){                                                                       // add first data point
                    hm.put(hz, vol);
                    data.appendData(new DataPoint(hz, vol), true, 100);
                    graphView.addSeries(data);
                }
                if(!hm.isEmpty()){
                    if(!hm.containsKey(hz)) {                                                               // very unlikely that key will be present but just in case
                        graphView.removeAllSeries();                                                        // reset the graph
                        hm.put(hz, vol);                                                                    // add new key(hz)
                        List<Integer> keyList = new ArrayList<Integer>(hm.keySet());                              // get list of all keys
                        Collections.sort(keyList);                                                          // sort list of keys in increasing order
                        LineGraphSeries<DataPoint> newData = new LineGraphSeries<>(new DataPoint[]{});
                        for (int i=0; i<keyList.size(); i++) {                                              // go through all keys
                            int key = keyList.get(i);                                                       // get key
                            int val = hm.get(key);                                                          // get value
                            newData.appendData(new DataPoint(key, val), true, 100); // add new data point
                        }
                        graphView.addSeries(newData);
                    }
                }
            }
        });
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(HearingTest.this, "Reset", Toast.LENGTH_LONG).show();
                mp.stop();
                confirm.setText("Confirm");
                graphView.removeAllSeries();
                hm.clear();
                for(Button b : buttonList)
                    b.setEnabled(true);
            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if (v14000.isEnabled()==false && v8000.isEnabled()==false && v10000.isEnabled()==false && v15000.isEnabled()==false && v12000.isEnabled()==false &&v16000.isEnabled()==false) {
//                    Toast.makeText(HearingTest.this, "All are clicked!", Toast.LENGTH_SHORT).show();
//                } if (v14000.isEnabled() || v8000.isEnabled() || v10000.isEnabled() || v15000.isEnabled() || v12000.isEnabled() || v16000.isEnabled()) {
//                    Toast.makeText(HearingTest.this, "Please complete all frequencies", Toast.LENGTH_SHORT).show();
//                    return;
//                }
                ParseObject patientData = new ParseObject("HearingTestData");
                patientData.put("Patient", patientName);
                List<Integer> keyList = new ArrayList<Integer>(hm.keySet());                              // get list of all keys
                Collections.sort(keyList);                                                          // sort list of keys in increasing order
                for (int i=0; i<keyList.size(); i++) {                                              // go through all keys
                    int key = keyList.get(i);                                                       // get key
                    int val = hm.get(key);                                                          // get value
                    String keyStr = "kHz_" + Integer.toString(key/1000);
                    patientData.put(keyStr, val);                                                   // add new data point
                }
                patientData.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        if(e == null){
                            Toast.makeText(HearingTest.this, "Data saved!", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(HearingTest.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });

        audioManager = (AudioManager)getSystemService(AUDIO_SERVICE);
        int maxVol = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        seekBar.setMax(maxVol);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser)
                    audioManager.setStreamVolume(AudioManager.STREAM_MUSIC,progress,1);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });
    }
}