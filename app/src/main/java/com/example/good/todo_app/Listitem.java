package com.example.good.todo_app;

/**
 * Created by aaska on 25/12/15.
 */
public class Listitem {
    private String name,details,date,time;
    private int id;

    public Listitem(String name,String details,String date,String time,int id) {

        this.name =name;
        this.details = details;
        this.date=date;
        this.time=time;
        this.id=id;
    }

    public String getName(){return this.name;}
    public String getDetails(){return this.details;}
    public String getDate(){return this.date;}
    public String getTime(){return this.time;}
    public int getId(){return this.id;}


    public void setName(String a){this.name = a ;}
    public void setDetails(String a){this.details = a;}
    public void setDate(String a){this.date = a;}
    public void setTime(String a){this.time = a;}
    public void setId(int a){this.id=a;}


}