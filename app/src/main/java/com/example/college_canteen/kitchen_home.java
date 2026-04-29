package com.example.college_canteen;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class kitchen_home extends AppCompatActivity {

    SQLiteDatabase db;
    ListView l;
    ArrayList<String> pendingItems = new ArrayList<>();
    ArrayList<Integer> oid = new ArrayList<>();
    ArrayList<String> os = new ArrayList<>();
    ArrayList<String> oinfo = new ArrayList<>();
    ArrayAdapter adapter;
    String updatedStatus;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kitchen_home);
        db=openOrCreateDatabase("canteen", Context.MODE_PRIVATE, null);
        l = (ListView) findViewById(R.id.listView1);
        Cursor res=db.rawQuery("SELECT * FROM orders where ostatus='Order placed' or ostatus='Preparing'", null);
        if(res.getCount()!=0)
        {
            while (res.moveToNext())
            {
                oid.add(res.getInt(1));
                os.add(res.getString(5));
                oinfo.add(res.getString(2));
                pendingItems.add("Order id: "+res.getInt(1)+"\nOrder info: "+res.getString(2)+"\nStatus: "+res.getString(5));

            }
        }
        adapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,pendingItems);
        l.setAdapter(adapter);
        l.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int p=position;
                int orderId=oid.get(position);
                String orderInfo=oinfo.get(p);
                showAlertDialog(orderId,p,orderInfo);
            }
        });
    }
    private void showAlertDialog(int oid,int p,String oinfo) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(kitchen_home.this);
        alertDialog.setTitle("Update status");
        String[] stat = {"Preparing","Ready"};
        int checkedItem = 0;
        int pp=p;
        alertDialog.setSingleChoiceItems(stat, checkedItem, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case 0:
                        db.execSQL("update orders set ostatus='Preparing' where oid='"+oid+"'");
                        updatedStatus=stat[0];
                        break;
                    case 1:
                        db.execSQL("update orders set ostatus='Ready' where oid='"+oid+"'");
                        updatedStatus=stat[1];
                        break;
                }
            }
        });
        alertDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                pendingItems.set(p,"Order id: "+oid+"\nOrder info: "+oinfo+"\nStatus: "+updatedStatus);
                adapter.notifyDataSetChanged();
                dialog.dismiss();
            }
        });
        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        alertDialog.show();
    }
}