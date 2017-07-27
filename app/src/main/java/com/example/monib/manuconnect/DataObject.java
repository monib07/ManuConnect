package com.example.monib.manuconnect;


import static android.R.attr.id;

public class DataObject {

    public int getId;
    private int Id;
    private String Title;
    private String Desciption;
    private  String Date;


    public DataObject() {
    }

    public DataObject(int id,String title, String desciption,String date) {
        Id=id;
        Title = title;
        Desciption = desciption;
        Date=date;
    }


    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getDesciption() {
        return Desciption;
    }

    public void setDesciption(String desciption) {
        Desciption = desciption;
    }

    public String  getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public int getId() {
        return id;
    }
}
