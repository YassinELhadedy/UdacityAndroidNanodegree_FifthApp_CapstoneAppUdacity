package com.example.yassin.androidsystemnew;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;


import com.example.yassin.androidsystemnew.cashe.DBHandler;
import com.example.yassin.androidsystemnew.frag.WeekListFrag;
import com.example.yassin.androidsystemnew.model.Subject;

import static com.example.yassin.androidsystemnew.cashe.ColumnConfig.KEY_ID;
import static com.example.yassin.androidsystemnew.cashe.ColumnConfig.LEC_NO_2;
import static com.example.yassin.androidsystemnew.cashe.ColumnConfig.STUDENT_NO_2;
import static com.example.yassin.androidsystemnew.cashe.ColumnConfig.SUBJECTLECNO_1;
import static com.example.yassin.androidsystemnew.cashe.ColumnConfig.SUBJECTNAMETABLE_1;
import static com.example.yassin.androidsystemnew.cashe.ColumnConfig.SUBJECT_TABLE_1;
import static com.example.yassin.androidsystemnew.cashe.ColumnConfig.WEEK_TABLE_2;
import static com.example.yassin.androidsystemnew.frag.WeekListFrag.weekListAdapter;
import static com.example.yassin.androidsystemnew.frag.WeekListFrag.weeklist;

public class WeekListActivity extends AppCompatActivity {
    public static final String SUBJECT_NAME = "Subject";
    public static final String LEC_NO = "LecNo";




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lec_list);

        getSupportActionBar().setTitle(getIntent().getStringExtra(SUBJECT_NAME));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Bundle bundle = new Bundle();
        bundle.putString(SUBJECT_NAME, getIntent().getStringExtra(SUBJECT_NAME));
        WeekListFrag weekListFrag = new WeekListFrag();
        weekListFrag.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment, weekListFrag, "").commit();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.week_list_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.addlec) {
            addLec(getIntent().getStringExtra(SUBJECT_NAME),""+getLastLectureAdded());
            weeklist.add(new Subject(""+(getLastLectureAdded()-1),"0"));
            weekListAdapter.notifyDataSetChanged();
            updateSubjectNameList(getIntent().getStringExtra(SUBJECT_NAME),""+weeklist.size());
            return true;
        }else {
            startActivity(new Intent(this,MainActivity.class));
        }

        return super.onOptionsItemSelected(item);
    }

    private void addLec(String subjectname,String lecNumber) {
        DBHandler dbHandler =new DBHandler(this);
        SQLiteDatabase database = dbHandler.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(SUBJECTNAMETABLE_1,subjectname);
        values.put(LEC_NO_2,lecNumber);
        database.insert(WEEK_TABLE_2,null,values);


    }
    private int getLastLectureAdded(){
        DBHandler dbHandler =new DBHandler(this);

        SQLiteDatabase database = dbHandler.getReadableDatabase();
        String []projections ={KEY_ID,SUBJECTNAMETABLE_1,LEC_NO_2,STUDENT_NO_2};
        String x = null;

        @SuppressLint("Recycle") Cursor cursor = database.query(WEEK_TABLE_2,projections,null,null,null,null,KEY_ID +" DESC","1");
        while (cursor.moveToNext()){
             x = cursor.getString(cursor.getColumnIndex(LEC_NO_2));
        }
        if(x==null){
            return 1;
        }else {
            return Integer.parseInt(x)+1;
        }




    }
    private void updateSubjectNameList(String subjectname,String size){
        DBHandler dbHandler =new DBHandler(this);
        SQLiteDatabase database = dbHandler.getWritableDatabase();
        ContentValues values =new ContentValues();
        values.put(SUBJECTLECNO_1,size);

        String selection =SUBJECTNAMETABLE_1+" = ?";
        String []selectionArgs={subjectname};
        database.update(SUBJECT_TABLE_1,values,selection,selectionArgs);

    }


}
