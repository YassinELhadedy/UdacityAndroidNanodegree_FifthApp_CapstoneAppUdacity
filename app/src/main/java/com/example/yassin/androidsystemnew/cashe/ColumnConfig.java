package com.example.yassin.androidsystemnew.cashe;



public class ColumnConfig {
    //TABLES NAME
    public static final String SUBJECT_TABLE_1 = "SubjectName";
    public static final String WEEK_TABLE_2 = "WeekTable";

    public static final String KEY_ID = "_id";

   //Table Columns
    public static final String SUBJECTNAMETABLE_1 = "SubjectName";
    public static final String SUBJECTLECNO_1 = "SubjectLecNo";
    public static final String STUDENT_NO_2 = "StudentNo";
    public static final String LEC_NO_2 = "LecNo";




    //Create Table
    static final String CREATE_TABLE_SUBJECT = "CREATE TABLE " + SUBJECT_TABLE_1 + "("
            + KEY_ID + " INTEGER PRIMARY KEY, " + SUBJECTNAMETABLE_1 +
            " TEXT, " + SUBJECTLECNO_1 + " TEXT  );";

    static final String CREATE_TABLE_WEEK = "CREATE TABLE " + WEEK_TABLE_2 + "("
            + KEY_ID + " INTEGER PRIMARY KEY, " + SUBJECTNAMETABLE_1 +
            " TEXT, "+ LEC_NO_2 + " TEXT, " + STUDENT_NO_2 + " TEXT  );";




}
