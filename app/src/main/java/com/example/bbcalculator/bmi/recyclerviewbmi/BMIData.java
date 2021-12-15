package com.example.bbcalculator.bmi.recyclerviewbmi;

import java.io.Serializable;

public class BMIData implements Serializable {
    public int Id;
    public String Name;
    public int Height;
    public int Weight;
    public String BMI;


    public int getId() {return Id;}
    public void setId(int id) {Id = id;}
    public String getName() {return Name;}
    public void setName(String name) {Name = name;}
    public int getHeight() {return Height;}
    public void setHeight(int height) {Height = height;}
    public int getWeight() {return Weight;}
    public void setWeight(int weight) {Weight = weight;}
    public String getBMI() {return BMI;}
    public void setBMI(String BMI) {this.BMI = BMI;}


    public BMIData(
            int Id,
            String Name,
            int Height,
            int Weight,
            String BMI
    ){
        this.Id = Id;
        this.Name = Name;
        this.Height = Height;
        this.Weight = Weight;
        this.BMI = BMI;
    }
}
