package com.example.bbcalculator.bmi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bbcalculator.R;
import com.example.bbcalculator.bmi.recyclerviewbmi.BMIData;
import com.example.bbcalculator.bmi.recyclerviewbmi.RecyclerBMI;
import com.example.bbcalculator.db.DBHelper;
import com.example.bbcalculator.db.DBManager;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Toast;

import java.util.ArrayList;

public class BMIList extends AppCompatActivity {
    SQLiteDatabase db;
    DBHelper dbH;
    DBManager dbM;
    RecyclerView rvProgram;
    RecyclerBMI recyclerBMI;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bmilist);

        dbH = new DBHelper(this);
        dbM = new DBManager(this);

        rvProgram = findViewById(R.id.rvProgram);

        displayData();
        rvProgram.setLayoutManager(new LinearLayoutManager(this));

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                try {
                    dbM.open().deleteBMI(((RecyclerBMI.ViewHolder) viewHolder).bmiData.Id);
                } catch (Exception e) {
                    Toast.makeText(BMIList.this, "Something's wrong!" + e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                } finally {
                    dbM.close();
                    displayData();
                }
            }
        }).attachToRecyclerView(rvProgram);
    }

    private void displayData() {
        db = dbH.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM " + DBHelper.TABLE_BMITable + "", null);
        ArrayList<BMIData> iData = new ArrayList<>();

        while (cursor.moveToNext()) {
            int rowId = cursor.getInt(0);
            String rowName = cursor.getString(1);
            int rowHeight = cursor.getInt(2);
            int rowWeight = cursor.getInt(3);
            String rowBMI = cursor.getString(4);
            iData.add(new BMIData(rowId, rowName, rowHeight, rowWeight, rowBMI));
        }
        cursor.close();
        recyclerBMI = new RecyclerBMI(this, R.layout.single_itembmi, iData, db);
        rvProgram.setAdapter(recyclerBMI);
    }
}
