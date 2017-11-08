package edu.bme.aruniverse.bmicalc;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.view.inputmethod.InputMethodManager;
import android.content.Context;

public class MainActivity extends AppCompatActivity {

    RadioButton usRB, siRB;
    TextView weightTV,heightTV1,heightTV2, bmiValTV;
    EditText weightET, heightET1, heightET2;
    double weightVal, heightVal1, heightVal2, bmiVal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        usRB        = (RadioButton)findViewById(R.id.radioButtonUS);
        siRB        = (RadioButton)findViewById(R.id.radioButtonSI);
        weightTV    = (TextView)findViewById(R.id.weightTextView);
        heightTV1   = (TextView)findViewById(R.id.heightTextView1);
        heightTV2   = (TextView)findViewById(R.id.heightTextView2);
        bmiValTV    = (TextView)findViewById(R.id.bmiValueTV);
        weightET    = (EditText)findViewById(R.id.weightEditText);
        heightET1   = (EditText)findViewById(R.id.heightEditText1);
        heightET2   = (EditText)findViewById(R.id.heightEditText2);


        // set default to us
        usRB.setChecked(true);
        siRB.setChecked(false);
        weightTV.setText("Your weight (lbs): ");
        heightTV1.setText("Your height (ft): ");
        heightTV2.setText("Your height (in): ");
        heightTV2.setAlpha(1f); // make heightTV2 appear
        heightET2.setAlpha(1f);
    }

    public void clickUS(View view){
        usRB.setChecked(true);
        siRB.setChecked(false);
        weightTV.setText("Your weight (lbs): ");
        heightTV1.setText("Your height (ft): ");
        heightTV2.setText("Your height (in): ");
        heightTV2.setAlpha(1f); // make heightTV2 appear
        heightET2.setAlpha(1f);
    }
    public void clickSI(View view){
        usRB.setChecked(false);
        siRB.setChecked(true);
        weightTV.setText("Your weight (kg): ");
        heightTV1.setText("Your height (cm): ");
        heightTV2.setAlpha(0f); // make heightTV2 disappear
        heightET2.setAlpha(0f);
    }

    public void calc(View view)
    {
        closeKeyboard();
        if(usRB.isChecked() && !siRB.isChecked())
        {
            if(weightET.getText().toString().trim().isEmpty()  || heightET1.getText().toString().trim().isEmpty() || heightET2.getText().toString().trim().isEmpty()) {
                Toast.makeText(getApplicationContext(), "Please enter all fields", Toast.LENGTH_LONG).show();
                return;
            }
            double lbs = Double.parseDouble(weightET.getText().toString());
            double ft  = Double.parseDouble(heightET1.getText().toString());
            double in  = Double.parseDouble(heightET2.getText().toString());
            bmiVal = calcBMI(lbs, ft, in);
        }
        else if(!usRB.isChecked() && siRB.isChecked())
        {
            if(weightET.getText().toString().trim().isEmpty() || heightET1.getText().toString().trim().isEmpty()) {
                Toast.makeText(getApplicationContext(), "Please enter all fields", Toast.LENGTH_LONG).show();
                return;
            }
            double kg = Double.parseDouble(weightET.getText().toString());
            double cm = Double.parseDouble(heightET1.getText().toString());
            bmiVal = calcBMI(kg, cm);
        }

        String str = "";
        if(bmiVal < 18.5)
            str = "You are underweight! Your BMI is: ";
        else if(bmiVal >= 18.5 && bmiVal < 24.99)
            str = "You are at a healthy weight! Your BMI is: ";
        else if(bmiVal >= 25 && bmiVal < 29.99)
            str = "You are overweight! Your BMI is: ";
        else
            str = "You are OBESE! Your BMI is: ";
        bmiValTV.setText(str + String.format("%.2f",bmiVal));
    }

    public double calcBMI(double kg, double cm)
    {
        weightVal   = kg;
        heightVal1  = cm;
        heightVal1 /= 100; // convert to m
        return weightVal/(Math.pow(heightVal1,2));
    }

    public double calcBMI(double lbs, double ft, double in)
    {
        weightVal  = lbs;
        heightVal1 = ft;
        heightVal2 = in;
        return weightVal/(Math.pow(((heightVal1*12)+heightVal2),2)) * 703 ;
    }

    public void closeKeyboard()
    {
        InputMethodManager inputManager = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }
}
