package com.example.bbcalculator.bmi;

import androidx.appcompat.app.AppCompatActivity;

import com.example.bbcalculator.R;
import com.example.bbcalculator.bmi.recyclerviewbmi.BMIData;
import com.example.bbcalculator.databinding.ActivityUdbmiBinding;
import com.example.bbcalculator.db.DBManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

public class UDbmi extends AppCompatActivity {
    DBManager dbM;
    protected ActivityUdbmiBinding b;
    protected int Id = 0;

    private void goToRegBMI() {
        Intent i = new Intent(UDbmi.this, BMIList.class);
        startActivity(i);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.activity_udbmi, null);
        setContentView(view);
        b = ActivityUdbmiBinding.bind(view);

        dbM = new DBManager(this);

        Bundle bundle = getIntent().getExtras();
        try {
            BMIData data = (BMIData) bundle.getSerializable("bmidata");
            Id = data.Id;
            b.editName.setText(data.Name);
            b.editHeight.setText(String.valueOf(data.Height));
            b.editWeight.setText(String.valueOf(data.Weight));
            b.result.setText(data.BMI);
        } catch (Exception e) {
            Toast.makeText(UDbmi.this, "Something's wrong!" + e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
        }

        if (b.resultInfo.getText().toString().trim().equals("")) {
            b.btnUpdate.setEnabled(false);
        }

        b.calc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String heStr = b.editHeight.getText().toString();
                String weStr = b.editWeight.getText().toString();

                if (!(heStr.isEmpty() || weStr.isEmpty())) {
                    float heValue = Float.parseFloat(heStr);
                    float weValue = Float.parseFloat(weStr);

                    float bmi = weValue / ((heValue / 100) * (heValue / 100));

                    displayBMI(bmi);
                } else {
                    Toast.makeText(getApplicationContext(), "Fields cannot be empty!", Toast.LENGTH_SHORT).show();
                }
            }

            public void displayBMI(float bmi) {
                String BMIRes;
                String bmiLabel = "";

                if (Float.compare(bmi, 16f) <= 0) {
                    bmiLabel = getString(R.string.severely_underweight);
                } else if (Float.compare(bmi, 16f) > 0 && Float.compare(bmi, 18.5f) <= 0) {
                    bmiLabel = getString(R.string.underweight);
                } else if (Float.compare(bmi, 18.5f) > 0 && Float.compare(bmi, 25f) <= 0) {
                    bmiLabel = getString(R.string.normal_weight);
                } else if (Float.compare(bmi, 25f) > 0 && Float.compare(bmi, 30f) <= 0) {
                    bmiLabel = getString(R.string.overweight);
                } else if (Float.compare(bmi, 30f) >= 0) {
                    bmiLabel = getString(R.string.obese);
                }

                BMIRes = bmi + "  ";
                b.result.setText(BMIRes);
                b.resultInfo.setText(bmiLabel);

                if (b.resultInfo.getText().toString().trim().equals("")) {
                    b.btnUpdate.setEnabled(false);
                } else {
                    b.btnUpdate.setEnabled(true);
                }
            }
        });

        b.btnUpdate.setOnClickListener(v -> {
            try {
                dbM.open();
                dbM.updateBMI(
                        Id,
                        b.editName.getText().toString(),
                        (int) (Float.parseFloat(b.editHeight.getText().toString())),
                        (int) (Float.parseFloat(b.editWeight.getText().toString())),
                        b.result.getText().toString());
            } catch (Exception e) {
                Toast.makeText(UDbmi.this, "Something's wrong!" + e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
            } finally {
                dbM.close();

                goToRegBMI();
            }
        });
    }
}