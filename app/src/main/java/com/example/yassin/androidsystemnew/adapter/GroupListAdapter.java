package com.example.yassin.androidsystemnew.adapter;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yassin.androidsystemnew.WeekListActivity;
import com.example.yassin.androidsystemnew.R;
import com.example.yassin.androidsystemnew.cashe.DBHandler;
import com.example.yassin.androidsystemnew.model.Subject;

import java.util.List;


import static com.example.yassin.androidsystemnew.MainActivity.names;
import static com.example.yassin.androidsystemnew.WeekListActivity.SUBJECT_NAME;
import static com.example.yassin.androidsystemnew.cashe.ColumnConfig.SUBJECTNAMETABLE_1;
import static com.example.yassin.androidsystemnew.cashe.ColumnConfig.SUBJECT_TABLE_1;
import static com.example.yassin.androidsystemnew.cashe.ColumnConfig.WEEK_TABLE_2;


public class GroupListAdapter extends RecyclerView.Adapter<GroupListAdapter.MyViewHolder> {

    private Context mContext;
    private List<Subject> groupname;

    public GroupListAdapter(Context mContext, List<Subject> groupname) {
        this.mContext = mContext;
        this.groupname = groupname;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_group, parent, false);
        return new MyViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.name.setText(groupname.get(position).getName());
        if (groupname.get(position).getLecNo()==null){
            holder.noOfstudent.setText("0");

        }else {
            holder.noOfstudent.setText(groupname.get(position).getLecNo());
        }

    }

    @Override
    public int getItemCount() {
        return groupname.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder  {
        TextView name , noOfstudent;
        AlertDialog ad_rename_tab,ad_del_conf;
        EditText et_rename_table;

        MyViewHolder(View view) {
            super(view);
            name =  view.findViewById(R.id.groupname);
            noOfstudent = view.findViewById(R.id.numberofStudent);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = getAdapterPosition();
                    mContext.startActivity(new Intent(mContext, WeekListActivity.class)
                    .putExtra(SUBJECT_NAME,groupname.get(pos).getName()));
                }
            });
            view.setOnCreateContextMenuListener(new View.OnCreateContextMenuListener() {
                @Override
                public void onCreateContextMenu(ContextMenu contextMenu, final View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
                    contextMenu.setHeaderTitle("Select The Action");
                    contextMenu.add(0, view.getId(), 0, "Rename").setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem menuItem) {

                            et_rename_table = new EditText(mContext);
                            AlertDialog.Builder builder_rename_tab = new AlertDialog.Builder(mContext);
                            et_rename_table.setText(groupname.get(getAdapterPosition()).getName());
                            builder_rename_tab.setView(et_rename_table);
                            builder_rename_tab.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    String new_table_name = et_rename_table.getText().toString();
                                    if(new_table_name.matches(""));
                                    else if(groupIsExisted(new_table_name)) {
                                        Toast.makeText(mContext, "Duplicate Name", Toast.LENGTH_LONG).show();
                                    } else {
                                       renameGroup(groupname.get(getAdapterPosition()).getName(),new_table_name);
//                                       names.remove(getAdapterPosition());
                                       names.set(getAdapterPosition(),new Subject(new_table_name,names.get(getAdapterPosition()).getLecNo()));
                                       notifyDataSetChanged();
                                        Toast.makeText(view.getContext(), "Rename done", Toast.LENGTH_LONG).show();


                                    }
                                }
                            });
                            ad_rename_tab = builder_rename_tab.create();
                            ad_rename_tab.show();


                            return true;
                        }
                    });//groupId, itemId, order, title
                    contextMenu.add(0, view.getId(), 0, "Delete").setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem menuItem) {

                            AlertDialog.Builder builder_del_group = new AlertDialog.Builder(mContext);
                            builder_del_group.setTitle("Confirmation");
                            builder_del_group.setMessage("Do you want to delete " +'"'+" "+groupname.get(getAdapterPosition()).getName()+" "+'"'+" Group ?");
                            builder_del_group.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Toast.makeText(view.getContext(), "Delete done", Toast.LENGTH_LONG).show();
                                    deleteGroup(groupname.get(getAdapterPosition()).getName());
                                    names.remove(getAdapterPosition());
                                    notifyDataSetChanged();
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
                    contextMenu.add(0, view.getId(), 0, "Export CSV Sheet").setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem menuItem) {
                            Toast.makeText(view.getContext(), "Export done", Toast.LENGTH_LONG).show();



                            return true;
                        }
                    });

                }
            });
        }

        void renameGroup(String oldName,String newName){
            DBHandler dbHandler = new DBHandler(mContext);
            SQLiteDatabase database = dbHandler.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put(SUBJECTNAMETABLE_1,newName);
            String selection =SUBJECTNAMETABLE_1+" = ?";
            String []selectionArgs={oldName};
            database.update(SUBJECT_TABLE_1,values,selection,selectionArgs);
//            update.update();

//            mContext.startActivity(new Intent(mContext, MainActivity.class));
        }
        void deleteGroup(String oldName){
            DBHandler dbHandler =new DBHandler(mContext);
            SQLiteDatabase database =dbHandler.getWritableDatabase();

            String selection = SUBJECTNAMETABLE_1 +" = ?";
            String[] selectionArgs = {oldName};

            database.delete(SUBJECT_TABLE_1,selection,selectionArgs);
            database.delete(WEEK_TABLE_2,selection,selectionArgs);
//            update.update();
//            mContext.startActivity(new Intent(mContext,MainActivity.class));
        }
        private boolean groupIsExisted(String tableName) {
            for (int i =0 ;i<groupname.size() ; i++){
                if (groupname.get(i).getName().equals( tableName)) return true;

            }
            return false;
        }


    }


}
