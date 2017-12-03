package com.aruniverse.bme.finalproj;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

public class ViewPatientData extends AppCompatActivity {

    GraphView graphView;
    LineGraphSeries<DataPoint> data;
    ArrayList<Integer> vol;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_patient);

        Intent intent = getIntent();
        String name = intent.getStringExtra("patient");
        setTitle(name+"'s data");

        graphView = (GraphView)findViewById(R.id.graphView);
        data = new LineGraphSeries<>(new DataPoint[]{});

        vol = new ArrayList<>();

        int[] frequencies = intent.getIntArrayExtra("frequency");

        ParseQuery<ParseObject> query8kHz = new ParseQuery<>("HearingTestData");
        query8kHz.whereEqualTo("Patient", name);
        query8kHz.orderByDescending("createdAt");
        query8kHz.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                for (ParseObject object: objects){
                    int val_8kHz = object.getInt("kHz_8");
                    vol.add(val_8kHz);
                    return;
                }
            }
        });

        ParseQuery<ParseObject> query10kHz = new ParseQuery<>("HearingTestData");
        query10kHz.whereEqualTo("Patient", name);
        query10kHz.orderByDescending("createdAt");
        query10kHz.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                for (ParseObject object: objects){
                    int val_10kHz = object.getInt("kHz_10");
                    vol.add(val_10kHz);
                    return;
                }
            }
        });

        ParseQuery<ParseObject> query12kHz = new ParseQuery<>("HearingTestData");
        query12kHz.whereEqualTo("Patient", name);
        query12kHz.orderByDescending("createdAt");
        query12kHz.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                for (ParseObject object: objects){
                    int val_12kHz = object.getInt("kHz_12");
                    vol.add(val_12kHz);
                    return;
                }
            }
        });

        ParseQuery<ParseObject> query14kHz = new ParseQuery<>("HearingTestData");
        query14kHz.whereEqualTo("Patient", name);
        query14kHz.orderByDescending("createdAt");
        query14kHz.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                for (ParseObject object: objects){
                    int val_14kHz = object.getInt("kHz_14");
                    vol.add(val_14kHz);
                    return;
                }
            }
        });

        ParseQuery<ParseObject> query15kHz = new ParseQuery<>("HearingTestData");
        query15kHz.whereEqualTo("Patient", name);
        query15kHz.orderByDescending("createdAt");
        query15kHz.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                for (ParseObject object: objects){
                    int val_15kHz = object.getInt("kHz_15");
                    vol.add(val_15kHz);
                    return;
                }
            }
        });

        ParseQuery<ParseObject> query16kHz = new ParseQuery<>("HearingTestData");
        query16kHz.whereEqualTo("Patient", name);
        query16kHz.orderByDescending("createdAt");
        query16kHz.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                for (ParseObject object: objects){
                    int val_16kHz = object.getInt("kHz_16");
                    vol.add(val_16kHz);
                    return;
                }
            }
        });


//        int[] volume = intent.getIntArrayExtra("values");
        int[] volume = new int[6];
        for(int i=0; i<vol.size(); i++) {
            volume[i] = vol.get(i);
        }

        for(int i=0; i<frequencies.length; i++){
            data.appendData(new DataPoint(frequencies[i], volume[i]), true, 100);
        }

        graphView.addSeries(data);
    }
}
