package com.example.bbcalculator.bmr;

import androidx.appcompat.app.AppCompatActivity;

import com.example.bbcalculator.R;
import com.example.bbcalculator.bmr.recyclerviewbmr.BMRData;
import com.example.bbcalculator.databinding.ActivityUdbmrBinding;
import com.example.bbcalculator.db.DBManager;
import com.example.bbcalculator.db.Sex;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

public class UDbmr extends AppCompatActivity {
    DBManager dbM;
    protected ActivityUdbmrBinding b;
    private Sex sex = null;
    protected int Id = 0;


    private void goToRegBMR() {
        Intent i = new Intent(UDbmr.this, BMRList.class);
        startActivity(i);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.activity_udbmr, null);
        setContentView(view);
        b = ActivityUdbmrBinding.bind(view);

        dbM = new DBManager(this);

        Bundle bundle1 = getIntent().getExtras();
        try {
            BMRData data = (BMRData) bundle1.getSerializable("bmrdata");
            Id = data.Id;
            b.editName1.setText(data.Name);
            b.editAge1.setText(String.valueOf(data.Age));
            b.resSex1.setText(String.valueOf(data.Sex));
            b.editHeight1.setText(String.valueOf(data.Height));
            b.editWeight1.setText(String.valueOf(data.Weight));
            b.result1.setText(data.BMR);
        } catch (Exception e) {
            Toast.makeText(UDbmr.this, "Something's wrong!" + e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
        } finally {
            if ((int) (Float.parseFloat(b.resSex1.getText().toString())) == 0) {
                b.genderSwitch1.setChecked(true);
            }
        }

        if (!b.calc1.isPressed()) {
            b.btnUpdate1.setEnabled(false);
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
                    bmr = (float) ((heValue * 6.25) + (weValue * 9.99) - (aValue * 4.92) + 5);
                } else {
                    sex = Sex.female;
                    bmr = (float) ((heValue * 6.25) + (weValue * 9.99) - (aValue * 4.92) - 161);
                }
            }

            String BMRRes = (int) (bmr * 1.375) + " cal";   //за слабо-активен тип хора
            b.result1.setText(BMRRes);

            if (!b.calc1.isPressed()) {
                b.btnUpdate1.setEnabled(false);
            } else {
                b.btnUpdate1.setEnabled(true);
            }
        });

        b.btnUpdate1.setOnClickListener(v -> {
            try {
                dbM.open();
                dbM.updateBMR(
                        Id,
                        b.editName1.getText().toString(),
                        sex.rawValue(),
                        (int) (Float.parseFloat(b.editAge1.getText().toString())),
                        (int) (Float.parseFloat(b.editHeight1.getText().toString())),
                        (int) (Float.parseFloat(b.editWeight1.getText().toString())),
                        b.result1.getText().toString());
            } catch (Exception e) {
                Toast.makeText(UDbmr.this, "Something's wrong!" + e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
            } finally {
                dbM.close();

                goToRegBMR();
            }
        });
    }
}