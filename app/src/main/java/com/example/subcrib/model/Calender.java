package com.example.subcrib.model;


import android.net.Uri;

public class Calender {

    public Uri getCalender() {
        return calender;
    }

    public void setCalender(Uri calender) {
        this.calender = calender;
    }

    public Calender(Uri calender) {
        this.calender = calender;
    }

    private Uri calender;
}
