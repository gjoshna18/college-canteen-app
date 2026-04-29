package com.example.college_canteen;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class update_item extends AppCompatActivity {

    EditText itemId, price;
    String iid, p;
    SQLiteDatabase db;
    Button delete;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_item);
        itemId = (EditText) findViewById(R.id.editTextNumber);
        price = (EditText) findViewById(R.id.editTextNumber2);
        delete = (Button) findViewById(R.id.button2);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db=openOrCreateDatabase("canteen", Context.MODE_PRIVATE, null);
                //db.execSQL("create table if not exists menu(itemId varchar,itemName varchar,price integer,category varchar);");
                iid=itemId.getText().toString();
                p=price.getText().toString();
                Cursor cc = db.rawQuery("select * from menu where itemId= '" + iid + "' ", null);
                if (cc.moveToFirst())
                {
                    if (cc != null)
                    {
                        if (cc.getCount() > 0)
                        {
                            db.execSQL("update menu set price='"+p+"' where itemId='"+iid+"'");
                            Toast.makeText(getApplicationContext(), "Item updated successfully.", Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "Item id not found.", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        });

    }
}