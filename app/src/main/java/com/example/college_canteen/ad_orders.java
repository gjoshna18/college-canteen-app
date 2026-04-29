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

public class ad_orders extends AppCompatActivity {

    SQLiteDatabase db;
    ListView l;
    ArrayList<String> dOrders = new ArrayList<>();
    ArrayAdapter adapter;
    String sid;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ad_orders);
        //final GlobalClass globalvariabel = (GlobalClass)getApplicationContext();
        db=openOrCreateDatabase("canteen", Context.MODE_PRIVATE, null);
        l = (ListView) findViewById(R.id.listView1);
        //sid=globalvariabel.GetUsername().toString();
        Cursor res=db.rawQuery("SELECT * FROM orders where ostatus='Delivered'", null);
        if(res.getCount()!=0)
        {
            while (res.moveToNext())
            {
                dOrders.add("Order id: "+res.getInt(1)+"\nOrder info: "+res.getString(2)+"\nTotal: "+res.getInt(3)+"\nPayment method: "+res.getString(4));
            }
        }
        adapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,dOrders);
        l.setAdapter(adapter);
    }
}