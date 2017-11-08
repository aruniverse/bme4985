package edu.bme.aruniverse.visiontest;

import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import android.view.inputmethod.InputMethodManager;
import android.content.Context;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ImageView imageView;
    EditText editText;
    Button button;
    int index=0, correct, id6, id8, id15, id45;
    int[] idArray, answers;
    ArrayList arr = new ArrayList();;
    float x0,x1;
    Animation moveLeft, moveRight;
    MediaPlayer mediaPlayerYes, mediaPlayerNo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView = (ImageView)findViewById(R.id.imageView);
        editText = (EditText)findViewById(R.id.editText);
        button = (Button)findViewById(R.id.button);

        id6 = imageView.getResources().getIdentifier("six","drawable",getPackageName());
        id8 = imageView.getResources().getIdentifier("eight","drawable",getPackageName());
        id15 = imageView.getResources().getIdentifier("fifteen","drawable",getPackageName());
        id45 = imageView.getResources().getIdentifier("fortyfive","drawable",getPackageName());

        correct=0;
        idArray = new int[]{id8, id45, id15, id6};
        answers = new int[]{8, 45, 15, 6};
        imageView.setImageResource(idArray[index]);

        for (int i : answers)
            arr.add(i);

        final Intent results = new Intent(MainActivity.this, ResultsActivity.class);

        mediaPlayerYes = MediaPlayer.create(this, R.raw.yes);
        mediaPlayerNo = MediaPlayer.create(this, R.raw.no);

        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                closeKeyboard();
                if(editText.getText().toString().trim().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Please enter an answer", Toast.LENGTH_LONG).show();
                    return;
                }
                int result = Integer.parseInt(editText.getText().toString());
                if(result == answers[index]){
                    Toast.makeText(MainActivity.this, "Correct!", Toast.LENGTH_SHORT).show();
                    mediaPlayerYes.start();
                    if(arr.contains(answers[index])) {
                        arr.remove(arr.indexOf(answers[index]));
                        correct++;
                    }
                } else {
                    Toast.makeText(MainActivity.this, "Wrong!", Toast.LENGTH_SHORT).show();
                    mediaPlayerNo.start();
                    if(arr.contains(answers[index])) {
                        arr.remove(arr.indexOf(answers[index]));
                    }
                }
                if(arr.isEmpty()){
                    results.putExtra("CORRECT", correct);
                    results.putExtra("TOTAL", idArray.length);
                    startActivityForResult(results, 1);
                }
            }
        });

        imageView.setOnTouchListener(new View.OnTouchListener(){
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getAction(); //0=press on the screen; 1=lift my finger; 2=press and move the mouse;
                if (action == 0)
                    x0 = event.getRawX();
                else if (action == 2) {
                    x1 = event.getRawX() - x0;
                    imageView.setTranslationX(x1);
                } else if (action == 1) {
                    if (x1 > 250) {
                        moveRight = new TranslateAnimation(x1, 1000, 0, 0);
                        moveRight.setDuration(500);
                        moveRight.setAnimationListener(new Animation.AnimationListener() {
                            @Override
                            public void onAnimationStart(Animation animation) {}

                            @Override
                            public void onAnimationEnd(Animation animation) {
                                index += 1;
                                if (index > 3)  // if we swiped right after the last image(index=3), reset to the first image at index 0
                                    index -= 4;
                                imageView.setImageResource(idArray[index]);
                                editText.setText("");
                            }

                            @Override
                            public void onAnimationRepeat(Animation animation) {}
                        });
                        imageView.startAnimation(moveRight);
                    } else if (x1 < -250) {
                        moveLeft = new TranslateAnimation(x1, -1000, 0, 0);
                        moveLeft.setDuration(500);
                        moveLeft.setAnimationListener(new Animation.AnimationListener() {
                            @Override
                            public void onAnimationStart(Animation animation) {}

                            @Override
                            public void onAnimationEnd(Animation animation) {
                                index -= 1;
                                if (index < 0) // if we swiped left after the first image, reset to the last image at index 3
                                    index += 4; // we do +4 instead of +3 because we will be adding to index=-1
                                imageView.setImageResource(idArray[index]);
                                editText.setText("");
                            }

                            @Override
                            public void onAnimationRepeat(Animation animation) {}
                        });
                        imageView.startAnimation(moveLeft);
                    }
                    imageView.setTranslationX(0);   // got rid of the else cause i want the image to always be centered
                }
                return true;
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                if(data.getBooleanExtra("RESET", true))
                    correct=0;
                for (int i : answers)
                    arr.add(i);
                }
            }
    }

    public void closeKeyboard() {
        InputMethodManager inputManager = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }
}