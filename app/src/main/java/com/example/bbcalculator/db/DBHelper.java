package com.example.bbcalculator.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {
    public static final String DBNAME = "CalculatorDB.db";
    public static final int DBVERSION = 2;

    public static final String TABLE_BMITable = "BMITable";
    public static final String TABLE_BMRTable = "BMRTable";


    public static final String KEY_ID = "Id";
    public static final String KEY_NAME = "Name";
    public static final String KEY_HEIGHT = "Height";
    public static final String KEY_WEIGHT = "Weight";

    public static final String KEY_BMI = "BMI";

    public static final String KEY_AGE = "Age";
    public static final String KEY_SEX = "Sex";
    public static final String KEY_BMR = "BMR";


    private static final String CREATE_BMITable = "CREATE TABLE " + TABLE_BMITable + "("
            + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + KEY_NAME + " TEXT, "
            + KEY_HEIGHT + " INTEGER, "
            + KEY_WEIGHT + " INTEGER, "
            + KEY_BMI + " TEXT)";

    private static final String CREATE_BMRTable = "CREATE TABLE " + TABLE_BMRTable + "("
            + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + KEY_NAME + " TEXT, "
            + KEY_AGE + " INTEGER, "
            + KEY_SEX + " INTEGER, "
            + KEY_HEIGHT + " INTEGER, "
            + KEY_WEIGHT + " INTEGER, "
            + KEY_BMR + " TEXT)";

    public DBHelper(@Nullable Context context) {
        super(context, DBNAME, null, DBVERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_BMITable);
        db.execSQL(CREATE_BMRTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BMITable);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BMRTable);

        onCreate(db);
    }
}