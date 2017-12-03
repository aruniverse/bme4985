package com.aruniverse.bme.finalproj;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

public class Doctor extends AppCompatActivity {

    ListView listView;
    ArrayList<String> patients;
    ArrayAdapter arrayAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor);
        
        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        setTitle(name+"'s patients");
        
        listView = findViewById(R.id.listView);
        patients = new ArrayList<>();
        arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, patients);

        ParseQuery<ParseUser> query = ParseUser.getQuery();
        query.whereNotEqualTo("username",ParseUser.getCurrentUser().getUsername());
        query.addAscendingOrder("username");
        query.findInBackground(new FindCallback<ParseUser>() {
            @Override
            public void done(List<ParseUser> objects, ParseException e) {
                for (ParseUser user:objects){
                    patients.add(user.getUsername());
                }
                listView.setAdapter(arrayAdapter);
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(Doctor.this, ViewPatientData.class);
                String patientName = patients.get(i);
                intent.putExtra("patient", patientName);

                int[] frequencies = {8000, 10000, 12000, 14000, 15000, 16000};
                intent.putExtra("frequency", frequencies);
                int[] volume = {2, 10, 8, 11, 5, 14};                           // we can randomize these 6 values to range from 0 - 15
                intent.putExtra("values", volume);                        // and not get values from the server
                startActivity(intent);
            }
        });
    }
}
