package com.aruniverse.bme.finalproj;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.List;

public class ViewPatientData extends AppCompatActivity {

    LinearLayout linearLayout;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_patient);

        Intent intent = getIntent();
        String name = intent.getStringExtra("patient");
        setTitle(name+"'s Hearing Test Results");

        linearLayout = (LinearLayout) findViewById(R.id.linearLayout);

        ParseQuery<ParseObject> query = new ParseQuery<>("image");
        query.whereEqualTo("undername", name);
        query.orderByDescending("createdAt");
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                for (ParseObject object: objects){
                    ParseFile file = (ParseFile) object.get("image");
                    file.getDataInBackground(new GetDataCallback() {
                        @Override
                        public void done(byte[] data, ParseException e) {
                            if (data != null){
                                Bitmap bitmap = BitmapFactory.decodeByteArray(data,0,data.length);
                                imageView = new ImageView(getApplicationContext());
                                imageView.setImageBitmap(bitmap);
                                linearLayout.addView(imageView);
                                Toast.makeText(ViewPatientData.this, "yes", Toast.LENGTH_LONG).show();
                            } else if (data==null){
                                Toast.makeText(ViewPatientData.this, "nope", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                    return;
                }
            }
        });
    }
}