package com.example.florian.nerdbasecalculator;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private Button button;
    private EditText sourceBase;
    private EditText sourceNumber;
    private EditText targetBase;
    private TextView result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onStart() {
        super.onStart();

        result = (TextView) findViewById(R.id.result);
        button = (Button) findViewById(R.id.button);
        sourceBase = (EditText) findViewById(R.id.sourceBase);
        sourceNumber = (EditText) findViewById(R.id.sourceNumber);
        targetBase = (EditText) findViewById(R.id.targetBase);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculate();
            }
        });

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.test);
        getSupportActionBar().setTitle(R.string.app_name);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        calculate();
        if(result.getText().toString().equals(getResources().getString(R.string.invalid))){
            result.setText(R.string.result);
        }
    }

    private void calculate() {
        String calcResult;
        int targetBaseValue = 0;
        int sourceBaseValue = 0;
        String source = "";
        try {
            sourceBaseValue = Integer.parseInt(sourceBase.getText().toString());
            source = sourceNumber.getText().toString();
            targetBaseValue = Integer.parseInt(targetBase.getText().toString());

            calcResult = Converter.calcNewBase(sourceBaseValue, source, targetBaseValue);
        } catch (Exception e) {
            calcResult = null;
        }

        if (calcResult == null) {
            result.setText(R.string.invalid);
        } else {
            String text = String.format("%s<sub>%s</sub> = %s<sub>%s</sub>", source, sourceBaseValue, calcResult, targetBaseValue);
            result.setText(Html.fromHtml(text));
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        calculate();
        if(result.getText().toString().equals(getResources().getString(R.string.invalid))){
            result.setText(R.string.result);
        }
    }
}
