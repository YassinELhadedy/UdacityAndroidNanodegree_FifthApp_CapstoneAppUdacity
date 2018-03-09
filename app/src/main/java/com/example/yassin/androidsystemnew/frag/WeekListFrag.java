package com.example.yassin.androidsystemnew.frag;


import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.yassin.androidsystemnew.R;
import com.example.yassin.androidsystemnew.adapter.WeekListAdapter;
import com.example.yassin.androidsystemnew.cashe.DBHandler;
import com.example.yassin.androidsystemnew.model.Subject;

import java.util.ArrayList;
import java.util.List;

import static com.example.yassin.androidsystemnew.WeekListActivity.SUBJECT_NAME;
import static com.example.yassin.androidsystemnew.cashe.ColumnConfig.KEY_ID;
import static com.example.yassin.androidsystemnew.cashe.ColumnConfig.LEC_NO_2;
import static com.example.yassin.androidsystemnew.cashe.ColumnConfig.STUDENT_NO_2;
import static com.example.yassin.androidsystemnew.cashe.ColumnConfig.SUBJECTNAMETABLE_1;
import static com.example.yassin.androidsystemnew.cashe.ColumnConfig.WEEK_TABLE_2;


public class WeekListFrag extends Fragment  {
    RecyclerView weekRecycle;
    public static List<Subject>weeklist;
    public static WeekListAdapter weekListAdapter;


    public WeekListFrag() {
        // Required empty public constructor
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_group_list, container, false);
        weekRecycle = view.findViewById(R.id.weeklist);
        weeklist = new ArrayList<>();


       getWeekList(getArguments().getString(SUBJECT_NAME));


        return view;
    }


    public void getWeekList(String subject) {
        weekListAdapter = new WeekListAdapter(getContext(),weeklist,subject);

        weekRecycle.setLayoutManager(new LinearLayoutManager(getContext()));
        weekRecycle.setAdapter(weekListAdapter);

        DBHandler dbHandler =new DBHandler(getContext());

        SQLiteDatabase database = dbHandler.getReadableDatabase();
        String []projections ={KEY_ID,SUBJECTNAMETABLE_1,LEC_NO_2,STUDENT_NO_2};
        String selection = SUBJECTNAMETABLE_1+" = ?";
        String[] selectionArgs={subject};

        @SuppressLint("Recycle") Cursor cursor = database.query(WEEK_TABLE_2,projections,selection,selectionArgs,null,null,null);


        while (cursor.moveToNext()){
            weeklist.add(new Subject(cursor.getString(cursor.getColumnIndex(LEC_NO_2)),cursor.getString(cursor.getColumnIndex(STUDENT_NO_2))));
            weekListAdapter.notifyDataSetChanged();

        }
        cursor.close();
    }


    }



