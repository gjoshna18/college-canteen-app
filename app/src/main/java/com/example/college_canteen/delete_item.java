package com.example.college_canteen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class delete_item extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    EditText itemId;
    Button delete;
    String[] category={"Breakfast","Lunch","Chinese","Snacks"};
    String cat,iid;
    SQLiteDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_item);
        Spinner c=(Spinner) findViewById(R.id.spinner);
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,category);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        c.setAdapter(adapter);
        c.setOnItemSelectedListener((AdapterView.OnItemSelectedListener) this);
        itemId=(EditText) findViewById(R.id.editTextNumber);
        delete=(Button) findViewById(R.id.button2);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db=openOrCreateDatabase("canteen", Context.MODE_PRIVATE, null);
                //db.execSQL("create table if not exists menu(itemId varchar,itemName varchar,price integer,category varchar);");
                iid=itemId.getText().toString();
                Cursor cc = db.rawQuery("select * from menu where itemId= '" + iid + "' ", null);
                if (cc.moveToFirst())
                {
                    if (cc != null)
                    {
                        if (cc.getCount() > 0)
                        {
                            db.execSQL("DELETE FROM menu WHERE itemId='"+iid+"';");
                            Toast.makeText(getApplicationContext(),"Item deleted successfully.", Toast.LENGTH_SHORT).show();
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

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        cat=category[position];
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}