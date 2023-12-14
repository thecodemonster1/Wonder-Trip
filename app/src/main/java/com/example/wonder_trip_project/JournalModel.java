package com.example.wonder_trip_project;

public class JournalModel {

    String title, date, rate;
//    text

    public JournalModel() {
    }

    public JournalModel(String title, String date, String rate) {
        this.title = title;
        this.date = date;
        this.rate = rate;
//        , String text
//        this.text = text;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

//    public String getText() {
//        return text;
//    }
//
//    public void setText(String text) {
//        this.text = text;
//    }
}
