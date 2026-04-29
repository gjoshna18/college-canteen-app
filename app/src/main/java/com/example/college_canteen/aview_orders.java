package com.example.college_canteen;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

public class aview_orders extends AppCompatActivity {

    String[] pm={"All orders","Cash","E-wallet"};
    String paymentMethod;
    SQLiteDatabase db;
    ListView l;
    Button all;
    ArrayAdapter adapter;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aview_orders);
        db=openOrCreateDatabase("canteen", Context.MODE_PRIVATE, null);
        l = (ListView) findViewById(R.id.listView1);
        Spinner spin = findViewById(R.id.spinner);
        all=(Button)findViewById(R.id.button5);
        ArrayAdapter ad = new ArrayAdapter(this, android.R.layout.simple_spinner_item, pm);
        ad.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(ad);
        spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(pm[position].equals("All orders")){
                    Cursor res=db.rawQuery("SELECT * FROM orders where ostatus='Ready'", null);
                    getList(res);
                }
                else if(pm[position].equals("Cash")){
                    Cursor res=db.rawQuery("SELECT * FROM orders where ostatus='Ready' and payment_method='Cash'", null);
                    getList(res);
                }
                else if(pm[position].equals("E-wallet")){
                    Cursor res=db.rawQuery("SELECT * FROM orders where ostatus='Ready' and payment_method='E-wallet'", null);
                    getList(res);
                }
                else{

                }
            }
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(aview_orders.this,ad_orders.class);
                startActivity(i);
            }
        });
    }
    void getList(Cursor res){
        ArrayList<String> allOrders = new ArrayList<>();
        ArrayList<Integer> oid = new ArrayList<>();
        if(res.getCount()!=0)
        {
            while (res.moveToNext())
            {
                oid.add(res.getInt(1));
                //os.add(res.getString(5));
                //oinfo.add(res.getString(2));
                allOrders.add("Order id: "+res.getInt(1)+"\nOrder info: "+res.getString(2)+"\nTotal: "+res.getInt(3)+"\nPayment method: "+res.getString(4)+"\nStatus: "+res.getString(5));
            }
        }
        adapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,allOrders);
        l.setAdapter(adapter);
        l.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int p=position;
                int orderId=oid.get(position);
                //String orderInfo=oinfo.get(p);
                showAlertDialog(orderId,allOrders,p);
            }
        });
    }
    private void showAlertDialog(int oid,ArrayList allOrders,int p) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(aview_orders.this);
        alertDialog.setTitle("Order delivered?");
        String[] stat = {"Yes","No"};
        //int checkedItem = 0;
        int pp=p;
        alertDialog.setSingleChoiceItems(stat, 1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case 0:
                        db.execSQL("update orders set ostatus='Delivered' where oid='"+oid+"'");
                        break;
                    case 1:
                        break;
                }
            }
        });
        alertDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                allOrders.remove(pp);
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