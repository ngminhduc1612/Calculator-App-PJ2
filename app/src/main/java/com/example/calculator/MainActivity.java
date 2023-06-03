package com.example.calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public class MainActivity extends AppCompatActivity {

    TextView workingTV;
    TextView resultTV;

    String texts = "";
    String formula = "";
    String tempFormula = "";

    Double right = null;
    boolean checkRight = false;  // check ngoac ben phai cua mu
    Double left = null;
    boolean checkLeft = false;   // check ngoac ben trai cua mu


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initTextViews();
    }

    private void initTextViews(){
        workingTV = (TextView) findViewById(R.id.workingTV);
        resultTV = (TextView) findViewById(R.id.resultTV);
    }

    private void setWorkings(String value){
        texts = texts + value;
        workingTV.setText(texts);
    }

    public void clearClick(View view) {
        workingTV.setText("");
        texts = "";
        resultTV.setText("");
        left_p = true;
        checkRight = false;
    }

    public void moduleClick(View view) {
        setWorkings("%");
    }

    public void equalClick(View view) {
        Double result = null;
        ScriptEngine engine = new ScriptEngineManager().getEngineByName("rhino");
        checkPowerOf();
        try {
            result = (double)engine.eval(formula);
        } catch (ScriptException e) {
            Toast.makeText(this, "Invalid Input", Toast.LENGTH_SHORT).show();
        }

        if (result != null)
            resultTV.setText(String.valueOf(result.doubleValue()));
    }

    private void checkPowerOf() {
        ArrayList<Integer> indexOfPowers = new ArrayList<>();
        for(int i = 0; i < texts.length(); i++)
        {
            if (texts.charAt(i) == '^')
                indexOfPowers.add(i);
        }

        formula = texts;
        tempFormula = texts;
        for(Integer index: indexOfPowers)
        {
            changeFormula(index);
        }
        formula = tempFormula;
    }

    private void changeFormula(Integer index) {
        String numberLeft = "";
        String numberRight = "";

        for(int i = index + 1; i< texts.length(); i++)
        {
            if(isNumeric(texts.charAt(i)))
                numberRight = numberRight + texts.charAt(i);
            else
                break;
        }

        if (texts.charAt(index+1) == '('){
            for(int i = index + 2; i < texts.length(); i++){
                if (texts.charAt(i) == ')')
                    break;
                numberRight = numberRight + texts.charAt(i);
            }
            ScriptEngine engine = new ScriptEngineManager().getEngineByName("rhino");
            try {
                right = (double)engine.eval(numberRight);
            } catch (ScriptException e) {
                Toast.makeText(this, "Invalid Input", Toast.LENGTH_SHORT).show();
            }
            numberRight = "("+numberRight+")";
            checkRight = true;
        }

        for(int i = index - 1; i >= 0; i--)
        {
            if(isNumeric(texts.charAt(i)))
                numberLeft = texts.charAt(i) + numberLeft;
            else
                break;
        }

        if (texts.charAt(index-1) == ')'){
            for(int i = index - 2; i >= 0; i--){
                if (texts.charAt(i) == '(')
                    break;
                numberLeft = texts.charAt(i) + numberLeft;
            }
            ScriptEngine engine = new ScriptEngineManager().getEngineByName("rhino");
            try {
                left = (double)engine.eval(numberLeft);
            } catch (ScriptException e) {
                Toast.makeText(this, "Invalid Input", Toast.LENGTH_SHORT).show();
            }
            numberLeft = "("+numberLeft+")";
            checkLeft = true;
        }
        String original = numberLeft + "^" + numberRight;
        String changed;
        if(checkRight){
            changed = "Math.pow("+numberLeft+","+right+")";
        }
        else if (checkLeft) {
            changed = "Math.pow("+left+","+numberRight+")";
        }
        else if (checkRight&&checkLeft){
            changed = "Math.pow("+left+","+right+")";
        }
        else {
            changed = "Math.pow(" + numberLeft + "," + numberRight + ")";
        }
        tempFormula = tempFormula.replace(original,changed);
    }


    public void allClearClick(View view) {
        setWorkings("^");
    }


    public boolean isNumeric(char c){
        if((c <= '9' && c >= '0') || c == '.')
            return true;

        return false;
    }

    boolean left_p = true;   //bien ghi nho co dau ngoac ben trai
    public void parenthesisClick(View view) {
        if(left_p){
            setWorkings("(");
            left_p = false;
        }else{
            setWorkings(")");
            left_p = true;
        }
    }

    public void divClick(View view) {
        setWorkings("/");
    }

    public void mulClick(View view) {
        setWorkings("*");
    }

    public void addClick(View view) {
        setWorkings("+");
    }

    public void subClick(View view) {
        setWorkings("-");
    }

    public void dotClick(View view) {
        setWorkings(".");
    }

    public void sevenClick(View view) {
        setWorkings("7");
    }

    public void eightClick(View view) {
        setWorkings("8");
    }

    public void nineClick(View view) {
        setWorkings("9");
    }

    public void fourClick(View view) {
        setWorkings("4");
    }

    public void fiveClick(View view) {
        setWorkings("5");
    }

    public void sixClick(View view) {
        setWorkings("6");
    }

    public void oneClick(View view) {
        setWorkings("1");
    }

    public void twoClick(View view) {
        setWorkings("2");
    }

    public void threeClick(View view) {
        setWorkings("3");
    }

    public void zeroClick(View view) {
        setWorkings("0");
    }
}