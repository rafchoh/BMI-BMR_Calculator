package com.example.bbcalculator.bmr;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bbcalculator.R;
import com.example.bbcalculator.bmr.recyclerviewbmr.BMRData;
import com.example.bbcalculator.bmr.recyclerviewbmr.RecyclerBMR;
import com.example.bbcalculator.db.DBHelper;
import com.example.bbcalculator.db.DBManager;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Toast;

import java.util.ArrayList;

public class BMRList extends AppCompatActivity {
    SQLiteDatabase db;
    DBHelper dbH;
    DBManager dbM;
    RecyclerView rvProgram1;
    RecyclerBMR recyclerBMR;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bmrlist);

        dbH = new DBHelper(this);
        dbM = new DBManager(this);

        rvProgram1 = findViewById(R.id.rvProgram1);

        displayData();
        rvProgram1.setLayoutManager(new LinearLayoutManager(this));

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                try {
                    dbM.open().deleteBMR(((RecyclerBMR.ViewHolder) viewHolder).bmrData.Id);
                } catch (Exception e) {
                    Toast.makeText(BMRList.this, "Something's wrong!" + e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                } finally {
                    dbM.close();
                    displayData();
                }
            }
        }).attachToRecyclerView(rvProgram1);
    }

    private void displayData() {
        db = dbH.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM " + DBHelper.TABLE_BMRTable + "", null);
        ArrayList<BMRData> rData = new ArrayList<>();

        while (cursor.moveToNext()) {
            int rowId1 = cursor.getInt(0);
            String rowName1 = cursor.getString(1);
            int rowAge1 = cursor.getInt(2);
            int rowSex1 = cursor.getInt(3);
            int rowHeight1 = cursor.getInt(4);
            int rowWeight1 = cursor.getInt(5);
            String rowBMR1 = cursor.getString(6);
            rData.add(new BMRData(rowId1, rowName1, rowAge1, rowSex1, rowHeight1, rowWeight1, rowBMR1));
        }
        cursor.close();
        recyclerBMR = new RecyclerBMR(this, R.layout.single_itembmr, rData, db);
        rvProgram1.setAdapter(recyclerBMR);
    }
}