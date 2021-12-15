package com.example.bbcalculator.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.sql.SQLDataException;

public class DBManager {
    private SQLiteDatabase db;
    private DBHelper dbH;
    private Context context;


    public DBManager(Context ctx) {
        context = ctx;
    }

    public DBManager open() throws SQLDataException {
        dbH = new DBHelper(context);
        db = dbH.getWritableDatabase();

        return this;
    }

    public void close() {
        dbH.close();
    }


    public void insertBMI(
            String Name,
            int Height,
            int Weight,
            String BMI
    ) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DBHelper.KEY_NAME, Name);
        contentValues.put(DBHelper.KEY_HEIGHT, Height);
        contentValues.put(DBHelper.KEY_WEIGHT, Weight);
        contentValues.put(DBHelper.KEY_BMI, BMI);

        db.insert(DBHelper.TABLE_BMITable, null, contentValues);
    }

    public void updateBMI(
            int Id,
            String Name,
            int Height,
            int Weight,
            String BMI
    ) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DBHelper.KEY_NAME, Name);
        contentValues.put(DBHelper.KEY_HEIGHT, Height);
        contentValues.put(DBHelper.KEY_WEIGHT, Weight);
        contentValues.put(DBHelper.KEY_BMI, BMI);

        db.update(DBHelper.TABLE_BMITable, contentValues, DBHelper.KEY_ID + " = " + Id, null);
    }

    public void deleteBMI(int Id) {
        db.delete(DBHelper.TABLE_BMITable, DBHelper.KEY_ID + " = " + Id, null);
    }


    public void insertBMR(
            String Name,
            int Age,
            int Sex,
            int Height,
            int Weight,
            String BMR
    ) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DBHelper.KEY_NAME, Name);
        contentValues.put(DBHelper.KEY_AGE, Age);
        contentValues.put(DBHelper.KEY_SEX, Sex);
        contentValues.put(DBHelper.KEY_HEIGHT, Height);
        contentValues.put(DBHelper.KEY_WEIGHT, Weight);
        contentValues.put(DBHelper.KEY_BMR, BMR);

        db.insert(DBHelper.TABLE_BMRTable, null, contentValues);
    }

    public void updateBMR(
            int Id,
            String Name,
            int Age,
            int Sex,
            int Height,
            int Weight,
            String BMR
    ) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DBHelper.KEY_NAME, Name);
        contentValues.put(DBHelper.KEY_AGE, Age);
        contentValues.put(DBHelper.KEY_SEX, Sex);
        contentValues.put(DBHelper.KEY_HEIGHT, Height);
        contentValues.put(DBHelper.KEY_WEIGHT, Weight);
        contentValues.put(DBHelper.KEY_BMR, BMR);

        db.update(DBHelper.TABLE_BMRTable, contentValues, DBHelper.KEY_ID + " = " + Id, null);
    }

    public void deleteBMR(int Id) {
        db.delete(DBHelper.TABLE_BMRTable, DBHelper.KEY_ID + " = " + Id, null);
    }
}
