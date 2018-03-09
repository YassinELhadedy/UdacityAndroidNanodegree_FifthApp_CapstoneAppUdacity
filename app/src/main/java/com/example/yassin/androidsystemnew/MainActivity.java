package com.example.yassin.androidsystemnew;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yassin.androidsystemnew.adapter.GroupListAdapter;
import com.example.yassin.androidsystemnew.cashe.DBHandler;
import com.example.yassin.androidsystemnew.model.Subject;

import java.util.ArrayList;

import static com.example.yassin.androidsystemnew.cashe.ColumnConfig.KEY_ID;
import static com.example.yassin.androidsystemnew.cashe.ColumnConfig.SUBJECTLECNO_1;
import static com.example.yassin.androidsystemnew.cashe.ColumnConfig.SUBJECTNAMETABLE_1;
import static com.example.yassin.androidsystemnew.cashe.ColumnConfig.SUBJECT_TABLE_1;

public class MainActivity extends AppCompatActivity  {
    public static ArrayList<Subject> names;
    GroupListAdapter groupListAdapter;
    TextView emptyGroup;
    EditText et_table_name;
    AlertDialog ad_tables_add;
    DBHandler dbHandler;
    RecyclerView groupsNameList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.main_actionbar);


        dbHandler =new DBHandler(this);
        names = new ArrayList<>();

        getSubjectsList();
        setEmptyText();


        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Add New Subject", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                addSubjectName();
            }
        });

    }

    void getSubjectsList(){
         groupsNameList = findViewById(R.id.groupnamelist);
        groupListAdapter = new GroupListAdapter(this,names);

        groupsNameList.setLayoutManager(new LinearLayoutManager(this));
        groupsNameList.setAdapter(groupListAdapter);



        SQLiteDatabase database = dbHandler.getReadableDatabase();
        String []projections ={KEY_ID,SUBJECTNAMETABLE_1,SUBJECTLECNO_1};

        @SuppressLint("Recycle") Cursor cursor = database.query(SUBJECT_TABLE_1,projections,null,null,null,null,null);


        while (cursor.moveToNext()){
         names.add(new Subject(cursor.getString(cursor.getColumnIndex(SUBJECTNAMETABLE_1)),cursor.getString(cursor.getColumnIndex(SUBJECTLECNO_1))));
         groupListAdapter.notifyDataSetChanged();
        }
        cursor.close();



    }
    void addSubjectName(){

        et_table_name = new EditText(this);
        AlertDialog.Builder builder_tables = new AlertDialog.Builder(this);
        builder_tables.setTitle("Create New Group");
        builder_tables.setMessage("Enter Group Name : ");
        builder_tables.setView(et_table_name);
        builder_tables.setPositiveButton("Submit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String table_name = et_table_name.getText().toString();
                if (table_name.matches(""))
                    Toast.makeText(MainActivity.this, "Please First Enter The Group Name", Toast.LENGTH_LONG).show();
                else {
                    if (groupIsExisted(table_name)) {
                        Toast.makeText(MainActivity.this, "Duplicate Name", Toast.LENGTH_LONG).show();
                    } else {
                      saveSubjectTable(table_name);
                        et_table_name.setText("");
                    }
                }
            }
        });
        builder_tables.setNegativeButton("Cancle", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                et_table_name.setText("");
            }
        });
        builder_tables.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                et_table_name.setText("");
            }
        });
        builder_tables.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                et_table_name.setText("");
            }
        });
        ad_tables_add = builder_tables.create();
        ad_tables_add.show();




    }
    void setEmptyText(){
        emptyGroup = findViewById(R.id.emptygroup);
        if (names.isEmpty()){
            emptyGroup.setText(getString(R.string.tvnogroup));

        }else {
            emptyGroup.setText("");
        }
    }

    private void saveSubjectTable(String table_name) {
        SQLiteDatabase database = dbHandler.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(SUBJECTNAMETABLE_1,table_name);
        database.insert(SUBJECT_TABLE_1,null,values);
        names.add(new Subject(table_name,"0"));
        groupListAdapter.notifyDataSetChanged();
        setEmptyText();

    }


    private boolean groupIsExisted(String tableName) {
        for (int i =0 ;i<names.size() ; i++){
            if (names.get(i).getName().equals( tableName)) return true;

        }
        return false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        names.clear();
        getSubjectsList();
        setEmptyText();
        super.onResume();
    }



}
