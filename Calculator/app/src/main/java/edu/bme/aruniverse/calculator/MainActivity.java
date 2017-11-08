package edu.bme.aruniverse.calculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    String equation="";
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView=(TextView)findViewById(R.id.textView);
    }

    public void b0(View view){
        equation=equation+"0";
        textView.setText(equation);
    }
    public void b1(View view){
        equation=equation+"1";
        textView.setText(equation);
    }
    public void b2(View view){
        equation=equation+"2";
        textView.setText(equation);
    }
    public void b3(View view){
        equation=equation+"3";
        textView.setText(equation);
    }
    public void b4(View view){
        equation=equation+"4";
        textView.setText(equation);
    }
    public void b5(View view){
        equation=equation+"5";
        textView.setText(equation);
    }
    public void b6(View view){
        equation=equation+"6";
        textView.setText(equation);
    }
    public void b7(View view){
        equation=equation+"7";
        textView.setText(equation);
    }
    public void b8(View view){
        equation=equation+"8";
        textView.setText(equation);
    }
    public void b9(View view){
        equation=equation+"9";
        textView.setText(equation);
    }
    public void bCalc(View view){
        double answer;
        answer=eval(equation);
        textView.setText(equation+"="+Double.toString(answer));
        //textView.setText(Double.toString(answer));
    }
    public void bPlus(View view){
        equation=equation+"+";
        textView.setText(equation);
    }
    public void bMinus(View view){
        equation=equation+"-";
        textView.setText(equation);
    }
    public void bMultiply(View view){
        equation=equation+"*";
        textView.setText(equation);
    }
    public void bDivide(View view){
        equation=equation+"/";
        textView.setText(equation);
    }
    public void bClear(View view){
        equation="";
        textView.setText(equation);
    }
    public void bPow(View view){
        equation=equation+"^";
        textView.setText(equation);
    }
    public void bLeftPar(View view){
        equation=equation+"(";
        textView.setText(equation);
    }
    public void bRightPar(View view){
        equation=equation+")";
        textView.setText(equation);
    }
    public void bDecimal(View view){
        equation=equation+".";
        textView.setText(equation);
    }
    public static double eval(final String str) {
        return new Object() {
            int pos = -1, ch;

            void nextChar() {
                ch = (++pos < str.length()) ? str.charAt(pos) : -1;
            }

            boolean eat(int charToEat) {
                while (ch == ' ') nextChar();
                if (ch == charToEat) {
                    nextChar();
                    return true;
                }
                return false;
            }

            double parse() {
                nextChar();
                double x = parseExpression();
                if (pos < str.length()) throw new RuntimeException("Unexpected: " + (char)ch);
                return x;
            }

            // Grammar:
            // expression = term | expression `+` term | expression `-` term
            // term = factor | term `*` factor | term `/` factor
            // factor = `+` factor | `-` factor | `(` expression `)`
            //        | number | functionName factor | factor `^` factor

            double parseExpression() {
                double x = parseTerm();
                for (;;) {
                    if      (eat('+')) x += parseTerm(); // addition
                    else if (eat('-')) x -= parseTerm(); // subtraction
                    else return x;
                }
            }

            double parseTerm() {
                double x = parseFactor();
                for (;;) {
                    if      (eat('*')) x *= parseFactor(); // multiplication
                    else if (eat('/')) x /= parseFactor(); // division
                    else return x;
                }
            }

            double parseFactor() {
                if (eat('+')) return parseFactor(); // unary plus
                if (eat('-')) return -parseFactor(); // unary minus

                double x;
                int startPos = this.pos;
                if (eat('(')) { // parentheses
                    x = parseExpression();
                    eat(')');
                } else if ((ch >= '0' && ch <= '9') || ch == '.') { // numbers
                    while ((ch >= '0' && ch <= '9') || ch == '.') nextChar();
                    x = Double.parseDouble(str.substring(startPos, this.pos));
                } else if (ch >= 'a' && ch <= 'z') { // functions
                    while (ch >= 'a' && ch <= 'z') nextChar();
                    String func = str.substring(startPos, this.pos);
                    x = parseFactor();
                    if (func.equals("sqrt")) x = Math.sqrt(x);
                    else if (func.equals("sin")) x = Math.sin(Math.toRadians(x));
                    else if (func.equals("cos")) x = Math.cos(Math.toRadians(x));
                    else if (func.equals("tan")) x = Math.tan(Math.toRadians(x));
                    else throw new RuntimeException("Unknown function: " + func);
                } else {
                    throw new RuntimeException("Unexpected: " + (char)ch);
                }

                if (eat('^')) x = Math.pow(x, parseFactor()); // exponentiation

                return x;
            }
        }.parse();
    }
}
