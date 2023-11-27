package com.example.calculatoractivity;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    TextView resultTv, solutionTv;
    Button buttonC, buttonAC, buttonDivide, buttonMultiply;
    Button button7, button8, button9, buttonMinus;
    Button button4, button5, button6, buttonAdd;
    Button button1, button2, button3, buttonEquals;
    Button button0, buttonPoint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        resultTv = findViewById(R.id.result_tv);
        solutionTv = findViewById(R.id.solution_tv);

        buttonC = findViewById(R.id.btnC);
        buttonAC = findViewById(R.id.btnClear);
        buttonDivide = findViewById(R.id.btn_Divide);
        buttonMultiply = findViewById(R.id.btnMul);
        button7 = findViewById(R.id.btn7);
        button8 = findViewById(R.id.btn8);
        button9 = findViewById(R.id.btn9);
        buttonMinus = findViewById(R.id.btn_Minus);
        button4 = findViewById(R.id.btn4);
        button5 = findViewById(R.id.btn5);
        button6 = findViewById(R.id.btn6);
        buttonAdd = findViewById(R.id.btn_Add);
        button1 = findViewById(R.id.btn1);
        button2 = findViewById(R.id.btn2);
        button3 = findViewById(R.id.btn3);
        buttonEquals = findViewById(R.id.btnEquals);
        button0 = findViewById(R.id.btn0);
        buttonPoint = findViewById(R.id.btnPoint);

        setClickListeners();
    }

    private void setClickListeners() {
        buttonC.setOnClickListener(this);
        buttonAC.setOnClickListener(this);
        buttonDivide.setOnClickListener(this);
        buttonMultiply.setOnClickListener(this);
        button7.setOnClickListener(this);
        button8.setOnClickListener(this);
        button9.setOnClickListener(this);
        buttonMinus.setOnClickListener(this);
        button4.setOnClickListener(this);
        button5.setOnClickListener(this);
        button6.setOnClickListener(this);
        buttonAdd.setOnClickListener(this);
        button1.setOnClickListener(this);
        button2.setOnClickListener(this);
        button3.setOnClickListener(this);
        buttonEquals.setOnClickListener(this);
        button0.setOnClickListener(this);
        buttonPoint.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Button button = (Button) view;
        String buttonText = button.getText().toString();
        String dataToCalculate = resultTv.getText().toString();
        String solutionText = solutionTv.getText().toString();

        float maxSize = 60; // Set your maximum text size here
        float minSize = 30; // Set your minimum text size here
        int maxLength = 10;

        if (dataToCalculate.length() > maxLength) {
            float textSize = maxSize - (dataToCalculate.length() - maxLength) * 3;
            textSize = Math.max(minSize, textSize); // Ensure the text size doesn't go below the minimum size
            resultTv.setTextSize(textSize);
        } else {
            resultTv.setTextSize(maxSize); // Reset text size to maximum size
        }

        if (buttonText.equals("AC")) {
            resultTv.setText("0");
            solutionTv.setText("");
            return;
        }

        if (buttonText.equals("=")) {
            if (!dataToCalculate.isEmpty()) {
                String finalResult = getResult(dataToCalculate);
                if (!finalResult.equals("Err")) {
                    solutionTv.setText(dataToCalculate);
                    resultTv.setText(finalResult);
                } else {
                    solutionTv.setText("Err");
                    resultTv.setText("Err");
                }
            }
            return;
        }

        if (buttonText.equals("C")) {
            if (!dataToCalculate.isEmpty()) {
                dataToCalculate = dataToCalculate.substring(0, dataToCalculate.length() - 1);
                if (dataToCalculate.isEmpty()) {
                    if (!solutionText.isEmpty()) {
                        resultTv.setText(solutionText);
                        solutionTv.setText("");
                        return;
                    }
                    resultTv.setText("0");
                    return;
                }
            }
        } else {
            if (dataToCalculate.equals("0")) {
                dataToCalculate = buttonText;
            } else {
                dataToCalculate = dataToCalculate + buttonText;
            }
        }

        resultTv.setText(dataToCalculate);
    }









    String getResult(String data) {
        try {
            String finalResult = evaluateExpression(data);
            if (!finalResult.equals("Err")) {
                double resultValue = Double.parseDouble(finalResult);
                if (resultValue == (long) resultValue) {
                    return String.format("%d", (long) resultValue);
                } else {
                    return String.format("%.5f", resultValue).replaceAll("0*$", "").replaceAll("\\.$", "");
                }
            } else {
                return "Err";
            }
        } catch (Exception e) {
            return "Err";
        }
    }


    String evaluateExpression(String expression) {
        try {
            expression = expression.replaceAll("\\s+", "");
            String[] numbers = expression.split("[+\\-*/]");
            char[] operators = expression.replaceAll("[0-9.]", "").toCharArray();

            double result = Double.parseDouble(numbers[0]);
            for (int i = 1; i < numbers.length; i++) {
                double num = Double.parseDouble(numbers[i]);
                char operator = operators[i - 1];

                switch (operator) {
                    case '+':
                        result += num;
                        break;
                    case '-':
                        result -= num;
                        break;
                    case '*':
                        result *= num;
                        break;
                    case '/':
                        if (num == 0) {
                            return "Err";
                        }
                        result /= num;
                        break;
                    default:
                        return "Err";
                }
            }

            return String.valueOf(result);
        } catch (Exception e) {
            return "Err";
        }
    }
}
