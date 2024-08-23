package com.example.calculator;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private TextView display;
    private String currentInput ="";
    private String operator="";
    private double firstValue=Double.NaN;
    private double secondValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        display=findViewById(R.id.display);

        setNumericButtonListeners();
        setOperatorButtonListeners();
        setClearButtonListeners();
        setSpecialButtonListeners();

    }
    private void setNumericButtonListeners(){
        int[] numericButtons = {
                R.id.btn0 ,R.id.btn1 ,R.id.btn2 ,R.id.btn3,
                R.id.btn4 ,R.id.btn5 ,R.id.btn6 ,R.id.btn7,
                R.id.btn8 ,R.id.btn9 ,R.id.btnDot

        };

        View.OnClickListener listener= new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Button button = (Button) view;
                currentInput += button.getText().toString();
                display.setText(currentInput);
            }
        };
        for (int id : numericButtons) {
            findViewById(id).setOnClickListener(listener);
        }

    }
    private void setOperatorButtonListeners(){
        int[] operatorButtons = {
                R.id.btnDiv ,R.id.btnMul ,R.id.btnMin ,R.id.btnAdd,
                R.id.btnEqual
        };
        View.OnClickListener listener =new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Button button = (Button) view;
                String operatorText = button.getText().toString();
                display.setText(currentInput);
                if (operatorText.equals("=")){
                    if (!Double.isNaN(firstValue)){
                        secondValue= Double.parseDouble(currentInput);
                        calculateResult();
                    }
                } else {
                    operator = operatorText;
                    if (!currentInput.isEmpty()){
                        firstValue = Double.parseDouble(currentInput);
                    }
                    currentInput ="";
                }

            }
        };
        for (int id : operatorButtons){
            findViewById(id).setOnClickListener(listener);
        }
    }
    private void setClearButtonListeners(){
        Button btnClearAll = findViewById(R.id.btnC);
        Button btnClearOne = findViewById(R.id.btnDec);

        btnClearAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearAll();
            }
        });
        btnClearOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearOneDigit();
            }
        });
    }
    private void setSpecialButtonListeners(){
        Button btnPercentage = findViewById(R.id.btnper);
        Button btnPlusMinus = findViewById(R.id.btnPlusMinus);

        btnPercentage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                applyPercentage();
            }
        });

        btnPlusMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleSign();
            }
        });
    }
    private void clearAll(){
        currentInput = "";
        firstValue = Double.NaN;
        secondValue = Double.NaN;
        operator = "";
        display.setText("0");
    }
    private void clearOneDigit(){
        if (currentInput.length() > 0) {
            currentInput = currentInput.substring(0, currentInput.length() - 1);
            display.setText(currentInput);
        }
        if (currentInput.isEmpty()){
            display.setText("0");
        }
    }
    private void applyPercentage(){
        if (!currentInput.isEmpty()){
            double value = Double.parseDouble(currentInput) / 100;
            currentInput= String.valueOf(value);
            display.setText(currentInput);
        }
    }
    private void toggleSign(){
        if (!currentInput.isEmpty()){
            double value = Double.parseDouble(currentInput);
            value = -value;
            currentInput= String.valueOf(value);
            display.setText(currentInput);
        }
    }

    private void calculateResult(){
        double result = 0.0;
        switch (operator){
            case "+":
                result = firstValue+secondValue;
                break;
            case "-":
                result = firstValue-secondValue;
                break;
            case "*":
                result = firstValue*secondValue;
                break;
            case "/":
                if (secondValue!=0){
                    result=firstValue/secondValue;
                }else {
                    display.setText("Error");
                    return;
                }
            break;

        }
        display.setText(String.valueOf(result));
        firstValue = result;
        currentInput = "";
        operator = "";

    }
}