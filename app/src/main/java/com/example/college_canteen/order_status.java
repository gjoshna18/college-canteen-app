package com.example.college_canteen;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class order_status extends AppCompatActivity {

    SQLiteDatabase db;
    ListView l;
    ArrayList<String> orderStatus = new ArrayList<>();
    ArrayAdapter adapter;
    String sid;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_status);
        final GlobalClass globalvariabel = (GlobalClass)getApplicationContext();
        db=openOrCreateDatabase("canteen", Context.MODE_PRIVATE, null);
        l = (ListView) findViewById(R.id.listView1);
        sid=globalvariabel.GetUsername().toString();
        Cursor res=db.rawQuery("SELECT * FROM orders where sid='"+sid+"' and (ostatus='Preparing' or ostatus='Ready' or ostatus='Order placed')", null);
        if(res.getCount()!=0)
        {
            while (res.moveToNext())
            {
                orderStatus.add("Order id: "+res.getInt(1)+"\nStatus: "+res.getString(5));
            }
        }
        adapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,orderStatus);
        l.setAdapter(adapter);
    }
}