package com.aruniverse.bme.finalproj;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

public class ViewPatientActivity extends AppCompatActivity {

    GraphView graphView;
    LineGraphSeries<DataPoint> data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_patient);

        Intent intent = getIntent();
        String name = intent.getStringExtra("patient");
        setTitle(name+"'s data");

        graphView = (GraphView)findViewById(R.id.graphView);
        data = new LineGraphSeries<>(new DataPoint[]{});

        int[] frequencies = intent.getIntArrayExtra("frequency");
        int[] volume = intent.getIntArrayExtra("values");

        for(int i=0; i<frequencies.length; i++){
            data.appendData(new DataPoint(frequencies[i], volume[i]), true, 100);
        }

        graphView.addSeries(data);
    }
}
