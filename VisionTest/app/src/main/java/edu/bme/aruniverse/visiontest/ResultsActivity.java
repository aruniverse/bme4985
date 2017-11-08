package edu.bme.aruniverse.visiontest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.view.View;

public class ResultsActivity extends AppCompatActivity {

    TextView textView;
    int correct, total;
    Button back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);
        back = (Button)findViewById(R.id.button2);
        correct = getIntent().getIntExtra("CORRECT", 0);
        total   = getIntent().getIntExtra("TOTAL", 0);
        textView = (TextView)findViewById(R.id.textView);
        textView.setText(String.format("You got %d out of %d correct", correct, total));

        back.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent();
                intent.putExtra("RESET", true);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }
}
