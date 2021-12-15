package com.example.bbcalculator.bmi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.example.bbcalculator.databinding.ActivityBmimainBinding;
import com.example.bbcalculator.db.DBManager;
import com.example.bbcalculator.R;

public class BMIMain extends AppCompatActivity {
    DBManager dbM;
    protected ActivityBmimainBinding b;


    private void goToRegBMI() {
        Intent i = new Intent(BMIMain.this, BMIList.class);
        startActivity(i);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bmimain);
        View view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.activity_bmimain, null);
        setContentView(view);
        b = ActivityBmimainBinding.bind(view);

        dbM = new DBManager(this);

        if (b.result.getText().toString().trim().equals("")) {
            b.btnSave.setEnabled(false);
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

                if (b.result.getText().toString().trim().equals("")) {
                    b.btnSave.setEnabled(false);
                } else {
                    b.btnSave.setEnabled(true);
                }
            }
        });

        b.btnSave.setOnClickListener(v -> {
            try {
                dbM.open();
                dbM.insertBMI(
                        b.editName.getText().toString(),
                        (int) (Float.parseFloat(b.editHeight.getText().toString())),
                        (int) (Float.parseFloat(b.editWeight.getText().toString())),
                        b.result.getText().toString());
            } catch (Exception e) {
                Toast.makeText(BMIMain.this, "Something's wrong!" + e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
            } finally {
                dbM.close();

                b.editName.setText("");
                b.editHeight.setText("");
                b.editWeight.setText("");
                b.result.setText("");
                b.resultInfo.setText("");

                if (b.result.getText().toString().trim().equals("")) {
                    b.btnSave.setEnabled(false);
                } else {
                    b.btnSave.setEnabled(true);
                }
            }
        });

        b.btnReg.setOnClickListener(v -> goToRegBMI());
    }

}