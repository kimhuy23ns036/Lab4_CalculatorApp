package com.example.calculator;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import com.google.android.material.button.MaterialButton;
import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {

    private static final char ADD = '+', SUB = '-', MUL = '*', DIV = '/', MOD = '%';
    private char currentOp;
    private double firstVal = Double.NaN, secondVal;
    private TextView inputDisplay, outputDisplay;
    private DecimalFormat format = new DecimalFormat("#.########");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        inputDisplay = findViewById(R.id.input);
        outputDisplay = findViewById(R.id.output);

        setupNumberButtons();

        findViewById(R.id.add).setOnClickListener(v -> setOp(ADD, "+"));
        findViewById(R.id.subtract).setOnClickListener(v -> setOp(SUB, "-"));
        findViewById(R.id.multiply).setOnClickListener(v -> setOp(MUL, "x"));
        findViewById(R.id.division).setOnClickListener(v -> setOp(DIV, "/"));
        findViewById(R.id.percent).setOnClickListener(v -> setOp(MOD, "%"));

        findViewById(R.id.btnPoint).setOnClickListener(v -> inputDisplay.append("."));

        findViewById(R.id.clear).setOnClickListener(v -> {
            if (inputDisplay.length() > 0) {
                inputDisplay.setText(inputDisplay.getText().subSequence(0, inputDisplay.length() - 1));
            } else {
                reset();
            }
        });

        findViewById(R.id.equal).setOnClickListener(v -> {
            calculate();
            outputDisplay.setText(format.format(firstVal));
            inputDisplay.setText("");
            currentOp = '0';
        });

        findViewById(R.id.off).setOnClickListener(v -> finish());
    }

    private void setupNumberButtons() {
        int[] ids = {
                R.id.btn0, R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn4,
                R.id.btn5, R.id.btn6, R.id.btn7, R.id.btn8, R.id.btn9
        };

        for (int i = 0; i < ids.length; i++) {
            int num = i;
            findViewById(ids[i]).setOnClickListener(v -> inputDisplay.append(String.valueOf(num)));
        }
    }

    private void setOp(char op, String symbol) {
        calculate();
        currentOp = op;
        outputDisplay.setText(format.format(firstVal) + symbol);
        inputDisplay.setText("");
    }

    private void calculate() {
        if (!Double.isNaN(firstVal)) {
            try {
                secondVal = Double.parseDouble(inputDisplay.getText().toString());
            } catch (Exception e) {
                return;
            }

            switch (currentOp) {
                case ADD: firstVal += secondVal; break;
                case SUB: firstVal -= secondVal; break;
                case MUL: firstVal *= secondVal; break;
                case DIV:
                    if (secondVal == 0) {
                        outputDisplay.setText("Error: /0");
                        firstVal = Double.NaN;
                        return;
                    }
                    firstVal /= secondVal;
                    break;
                case MOD: firstVal %= secondVal; break;
            }
        } else {
            try {
                firstVal = Double.parseDouble(inputDisplay.getText().toString());
            } catch (Exception ignored) {}
        }
    }

    private void reset() {
        firstVal = Double.NaN;
        secondVal = Double.NaN;
        inputDisplay.setText("");
        outputDisplay.setText("");
    }
}
