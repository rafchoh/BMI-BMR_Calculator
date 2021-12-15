package com.example.bbcalculator.bmr;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.example.bbcalculator.R;
import com.example.bbcalculator.databinding.ActivityBmrmainBinding;
import com.example.bbcalculator.db.DBManager;
import com.example.bbcalculator.db.Sex;

public class BMRMain extends AppCompatActivity {
    DBManager dbM;
    protected ActivityBmrmainBinding b;
    private Sex sex = null;


    private void goToRegBMR() {
        Intent i = new Intent(BMRMain.this, BMRList.class);
        startActivity(i);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.activity_bmrmain, null);
        setContentView(view);
        b = ActivityBmrmainBinding.bind(view);

        dbM = new DBManager(this);

        if (b.result1.getText().toString().trim().equals("")) {
            b.btnSave1.setEnabled(false);
        }

        b.calc1.setOnClickListener(v -> {
            String aStr = b.editAge1.getText().toString();
            String heStr = b.editHeight1.getText().toString();
            String weStr = b.editWeight1.getText().toString();
            float bmr = 0;

            if (!(aStr.isEmpty() || heStr.isEmpty() || weStr.isEmpty())) {
                float aValue = Float.parseFloat(aStr);
                float heValue = Float.parseFloat(heStr);
                float weValue = Float.parseFloat(weStr);

                if (b.genderSwitch1.isChecked()) {
                    sex = Sex.male;
                    bmr = (float) ((heValue * 6.25) + (weValue * 9.99) - (aValue * 4.92) + 5); //maje
                } else {
                    sex = Sex.female;
                    bmr = (float) ((heValue * 6.25) + (weValue * 9.99) - (aValue * 4.92) - 161); //jeni
                }
            } else {
                Toast.makeText(getApplicationContext(), "Fields cannot be empty!", Toast.LENGTH_SHORT).show();
            }

            String BMRRes = (int) (bmr * 1.375) + " cal";   // за слабо-активен тип хора
            b.result1.setText(BMRRes);

            if (b.result1.getText().toString().trim().equals("")) {
                b.btnSave1.setEnabled(false);
            } else {
                b.btnSave1.setEnabled(true);
            }
        });

        b.btnSave1.setOnClickListener(v -> {
            try {
                dbM.open();
                dbM.insertBMR(
                        b.editName1.getText().toString(),
                        sex.rawValue(),
                        (int) (Float.parseFloat(b.editAge1.getText().toString())),
                        (int) (Float.parseFloat(b.editHeight1.getText().toString())),
                        (int) (Float.parseFloat(b.editWeight1.getText().toString())),
                        b.result1.getText().toString());
            } catch (Exception e) {
                Toast.makeText(BMRMain.this, "Something's wrong!" + e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
            } finally {
                dbM.close();

                b.editName1.setText("");
                b.editAge1.setText("");
                b.genderSwitch1.setChecked(false);
                b.editHeight1.setText("");
                b.editWeight1.setText("");
                b.result1.setText("");

                if (b.result1.getText().toString().trim().equals("")) {
                    b.btnSave1.setEnabled(false);
                } else {
                    b.btnSave1.setEnabled(true);
                }
            }
        });

        b.btnReg1.setOnClickListener(v -> goToRegBMR());
    }
}