package com.example.bbcalculator.bmr.recyclerviewbmr;

import java.io.Serializable;

public class BMRData implements Serializable {
    public int Id;
    public String Name;
    public int Sex;
    public int Age;
    public int Height;
    public int Weight;
    public String BMR;


    public int getId() {return Id;}
    public void setId(int id) {Id = id;}
    public String getName() {return Name;}
    public void setName(String name) {Name = name;}
    public int getSex() {return Sex;}
    public void setSex(int sex) {Sex = sex;}
    public int getAge() {return Age;}
    public void setAge(int age) {Age = age;}
    public int getHeight() {return Height;}
    public void setHeight(int height) {Height = height;}
    public int getWeight() {return Weight;}
    public void setWeight(int weight) {Weight = weight;}
    public String getBMR() {return BMR;}
    public void setBMR(String BMR) {this.BMR = BMR;}


    public BMRData(
            int Id,
            String Name,
            int Sex,
            int Age,
            int Height,
            int Weight,
            String BMR
    ){
        this.Id = Id;
        this.Name = Name;
        this.Sex = Sex;
        this.Age = Age;
        this.Height = Height;
        this.Weight = Weight;
        this.BMR = BMR;
    }
}
