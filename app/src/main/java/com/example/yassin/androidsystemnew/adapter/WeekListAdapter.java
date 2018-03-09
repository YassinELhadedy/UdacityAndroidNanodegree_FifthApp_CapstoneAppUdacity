package com.example.yassin.androidsystemnew.adapter;


import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yassin.androidsystemnew.R;
import com.example.yassin.androidsystemnew.ScanListActivity;
import com.example.yassin.androidsystemnew.WeekListActivity;
import com.example.yassin.androidsystemnew.cashe.DBHandler;
import com.example.yassin.androidsystemnew.frag.PreviewTableFra;
import com.example.yassin.androidsystemnew.model.Subject;


import java.util.List;

import static com.example.yassin.androidsystemnew.WeekListActivity.LEC_NO;
import static com.example.yassin.androidsystemnew.WeekListActivity.SUBJECT_NAME;
import static com.example.yassin.androidsystemnew.cashe.ColumnConfig.LEC_NO_2;
import static com.example.yassin.androidsystemnew.cashe.ColumnConfig.SUBJECTLECNO_1;
import static com.example.yassin.androidsystemnew.cashe.ColumnConfig.SUBJECTNAMETABLE_1;
import static com.example.yassin.androidsystemnew.cashe.ColumnConfig.SUBJECT_TABLE_1;
import static com.example.yassin.androidsystemnew.cashe.ColumnConfig.WEEK_TABLE_2;
import static com.example.yassin.androidsystemnew.frag.PreviewTableFra.Activity;
import static com.example.yassin.androidsystemnew.frag.WeekListFrag.weeklist;


public class  WeekListAdapter extends RecyclerView.Adapter<WeekListAdapter.MyViewHolder> {

    private Context mContext;
    private List<Subject> lecname;
    private String subject;

    public WeekListAdapter(Context mContext, List<Subject> lecname,String subject) {
        this.mContext = mContext;
        this.lecname = lecname;
        this.subject=subject;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_week, parent, false);
        return new MyViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.name.setText("Lec"+lecname.get(position).getName());
        if (lecname.get(position).getLecNo()==null){
            holder.noOfstudent.setText("0");
        }else {
            holder.noOfstudent.setText(lecname.get(position).getLecNo());
        }


    }

    @Override
    public int getItemCount() {
        return lecname.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView name, noOfstudent;
        AlertDialog ad_del_conf;


        MyViewHolder(View view) {
            super(view);
            name = view.findViewById(R.id.groupname);
            noOfstudent = view.findViewById(R.id.numberofStudent);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    WeekListActivity myActivity = (WeekListActivity)mContext;
                    PreviewTableFra previewTableFra =new PreviewTableFra();
                    Bundle bundle= new Bundle();
                    bundle.putString(SUBJECT_NAME,subject);
                    bundle.putString(LEC_NO,lecname.get(getAdapterPosition()).getName());
                    bundle.putString(Activity,"WeekList");
                    previewTableFra.setArguments(bundle);

                    myActivity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment, previewTableFra, "").commit();


                }
            });


            view.setOnCreateContextMenuListener(new View.OnCreateContextMenuListener() {
                @Override
                public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
                    contextMenu.setHeaderTitle("Select The Action");
                    contextMenu.add(0, view.getId(), 0, "Delete").setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem menuItem) {

                            AlertDialog.Builder builder_del_group = new AlertDialog.Builder(mContext);
                            builder_del_group.setTitle("Confirmation");
                            builder_del_group.setMessage("Do you want to delete " +'"'+"Lec "+lecname.get(getAdapterPosition()).getName()+" "+'"'+" Group ?");
                            builder_del_group.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Toast.makeText(mContext, "Delete done", Toast.LENGTH_LONG).show();

                                    deleteWeek(lecname.get(getAdapterPosition()).getName());
                                    weeklist.remove(getAdapterPosition());
                                    notifyDataSetChanged();
                                    updateSubjectNameList(subject,""+(weeklist.size()-1));


                                }
                            });
                            builder_del_group.setNegativeButton("No", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {}});
                            ad_del_conf = builder_del_group.create();
                            ad_del_conf.show();



                            return true;
                        }
                    });
                    contextMenu.add(0, view.getId(), 0, "Scan and Preview").setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem menuItem) {


                            mContext.startActivity(new Intent(mContext, ScanListActivity.class)
                            .putExtra(SUBJECT_NAME,subject)
                            .putExtra(LEC_NO,lecname.get(getAdapterPosition()).getName()));



                            return true;
                        }
                    });
                }
            });
        }
    }

    private void deleteWeek(String name) {
        DBHandler dbHandler =new DBHandler(mContext);
        SQLiteDatabase database = dbHandler.getWritableDatabase();
        String selection = LEC_NO_2 +" = ?";
        String []selectionArgs ={name};
        database.delete(WEEK_TABLE_2,selection,selectionArgs);

    }
    private void updateSubjectNameList(String subjectname,String size){
        String newSize ;
        if(size.equals("-1")){
            newSize = "0";
        }else{
            newSize =size;
        }
        DBHandler dbHandler =new DBHandler(mContext);
        SQLiteDatabase database = dbHandler.getWritableDatabase();
        ContentValues values =new ContentValues();
        values.put(SUBJECTLECNO_1,newSize);

        String selection =SUBJECTNAMETABLE_1+" = ?";
        String []selectionArgs={subjectname};
        database.update(SUBJECT_TABLE_1,values,selection,selectionArgs);

    }



}