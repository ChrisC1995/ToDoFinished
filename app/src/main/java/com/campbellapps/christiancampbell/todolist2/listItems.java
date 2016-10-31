package com.campbellapps.christiancampbell.todolist2;

import java.util.Date;

import com.google.gson.annotations.SerializedName;

public class listItems implements Comparable<listItems> {

    @SerializedName("title")
    private String title;

    @SerializedName("text")
    private String text;

    @SerializedName("date")
    private String date;

    @SerializedName("category")
    private String category;

    @SerializedName("currentDate")
    private Date currentDate;

    @SerializedName("day")
    private String day;

    @SerializedName("month")
    private String month;

    @SerializedName("time")
    private String time;




    public listItems(String title, String text, String date, String category, Date currentDate, String day, String month, String time){
        this.title = title;
        this.text = text;
        this.date = date;
        this.category = category;
        this.currentDate = currentDate;
        this.day = day;
        this.month = month;
        this.time = time;
        //constructor of listItems


    }

    public String getTitle() {
        return title;
    } // getters and setters for constructor items.

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getCategory(){
        return category;
    }

    public void setCategory(String category){
        this.category = category;
    }

    public Date getCurrentDate() {
        return currentDate;
    }

    public void setCurrentDate(Date currentDate) {
        this.currentDate = currentDate;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public int compareTo(listItems another) { // compares to other notes
 // sorts note by category. Compares one category to another category
     return getCategory().compareTo(another.getCategory());
    }

}
