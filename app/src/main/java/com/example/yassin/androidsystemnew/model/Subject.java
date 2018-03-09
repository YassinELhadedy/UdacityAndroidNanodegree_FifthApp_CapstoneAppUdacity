package com.example.yassin.androidsystemnew.model;



public class Subject {
    private String name;
    private String lecNo;

    public Subject(String name, String lecNo) {
        this.name = name;
        this.lecNo = lecNo;
    }

    public String getName() {
        return name;
    }

    public String getLecNo() {
        return lecNo;
    }
}
