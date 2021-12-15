package com.example.bbcalculator.db;

public enum Sex {
    male,
    female;

    public int rawValue(){
        if(this == male){
            return 0;
        }
        return 1;
    }
}
