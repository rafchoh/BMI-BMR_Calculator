package com.example.bbcalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.example.bbcalculator.bmi.BMIMain;
import com.example.bbcalculator.bmr.BMRMain;
import com.example.bbcalculator.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    protected ActivityMainBinding b;


    private void GoToBMI() {
        Intent i = new Intent(MainActivity.this, BMIMain.class);
        startActivity(i);
    }

    private void GoToBMR() {
        Intent i = new Intent(MainActivity.this, BMRMain.class);
        startActivity(i);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.activity_main, null);
        setContentView(view);
        b = ActivityMainBinding.bind(view);

        b.bMIButton.setOnClickListener(v -> GoToBMI());
        b.bMRButton.setOnClickListener(v -> GoToBMR());
    }
}