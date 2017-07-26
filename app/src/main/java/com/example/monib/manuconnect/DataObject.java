package com.example.monib.manuconnect;

public class DataObject {
    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

   
    private int Id;
    private String Title;
    private String Desciption;


    public DataObject() {
    }

    public DataObject(int id,String title, String desciption) {
        Id=id;
        Title = title;
        Desciption = desciption;
    }
     
    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
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
}
