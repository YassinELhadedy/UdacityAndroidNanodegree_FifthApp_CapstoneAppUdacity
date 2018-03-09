package com.example.yassin.androidsystemnew.cashe;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.example.yassin.androidsystemnew.cashe.ColumnConfig.CREATE_TABLE_SUBJECT;
import static com.example.yassin.androidsystemnew.cashe.ColumnConfig.CREATE_TABLE_WEEK;


public class DBHandler extends SQLiteOpenHelper {
    private static int DB_VERSION =1;
    private static String DB_NAME = "Attendance";


    public DBHandler(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE_SUBJECT);
        sqLiteDatabase.execSQL(CREATE_TABLE_WEEK);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
