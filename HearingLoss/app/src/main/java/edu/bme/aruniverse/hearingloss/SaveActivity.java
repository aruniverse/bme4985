package edu.bme.aruniverse.hearingloss;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseAnalytics;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class SaveActivity extends AppCompatActivity {

    EditText username, password;
    Button signup, login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save);

        signup = (Button)findViewById(R.id.button1);
        login = (Button)findViewById(R.id.button2);
        username = (EditText)findViewById(R.id.editText1);
        password = (EditText)findViewById(R.id.editText2);

        signup.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                ParseUser user = new ParseUser();
                user.setUsername(username.getText().toString());
                user.setPassword(password.getText().toString());
                user.signUpInBackground(new SignUpCallback() {
                    @Override
                    public void done(ParseException e) {
                        if(e == null)
                            Toast.makeText(SaveActivity.this, "Successfull", Toast.LENGTH_LONG).show();
                        else
                            Toast.makeText(SaveActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
            }
        });

        login.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                String userName = username.getText().toString();
                String passWord = password.getText().toString();
                ParseUser.logInInBackground(userName, passWord, new LogInCallback() {
                    @Override
                    public void done(ParseUser user, ParseException e) {
                        if(e == null && (user.getUsername().contains("Doctor") || user.getUsername().contains("Dr"))){
                            Toast.makeText(SaveActivity.this, "Welcome Dr!", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(SaveActivity.this, Doctor.class);
//                            intent.putExtra("name", user.getUsername());
                            intent.putExtra("name", "Doctor");
                            startActivity(intent);
                        } else if(e == null && !user.getUsername().equals("Doctor")){
                            Toast.makeText(SaveActivity.this, user.getUsername(), Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(SaveActivity.this, Patient.class);
//                            intent.putExtra("name", user.getUsername());
                            intent.putExtra("name", "patient");
                            startActivity(intent);
                        } else if(e != null){
                            Toast.makeText(SaveActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });

//        ssh -i "bme4985hw4.pem" ubuntu@ec2-18-221-69-247.us-east-2.compute.amazonaws.com
//        user: "user",
//        pass: "$SsjNFzRBDpG6"
        Parse.enableLocalDatastore(this);
        Parse.initialize(new Parse.Configuration.Builder(getApplicationContext())
                .applicationId( "bd506391ff4cd4f9fe40b30186692298922386ac")
                .clientKey("184c555cb5254cffb181d811812dc2a7277fb4ad")
                .server("http://18.221.69.247:80/parse/")
                .build()
        );

        ParseAnalytics.trackAppOpenedInBackground(getIntent());
        ParseUser.enableAutomaticUser();
        ParseACL defaultACL = new ParseACL();
        defaultACL.setPublicReadAccess(true);
        defaultACL.setPublicWriteAccess(true);
        ParseACL.setDefaultACL(defaultACL, true);

    }
}
