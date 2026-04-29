package com.example.college_canteen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class add_item extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    EditText itemId,itemName,price;
    Button add;
    String[] category={"Breakfast","Lunch","Chinese","Snacks"};
    String cat,iid,iname,p;
    SQLiteDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);
        Spinner c=(Spinner) findViewById(R.id.spinner);
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,category);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        c.setAdapter(adapter);
        c.setOnItemSelectedListener((AdapterView.OnItemSelectedListener) this);
        itemId=(EditText) findViewById(R.id.editTextNumber);
        itemName=(EditText) findViewById(R.id.editTextTextPersonName4);
        price=(EditText) findViewById(R.id.editTextNumber2);
        add=(Button) findViewById(R.id.button2);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db=openOrCreateDatabase("canteen", Context.MODE_PRIVATE, null);
                db.execSQL("create table if not exists menu(itemId varchar,itemName varchar,price integer,category varchar);");
                iid=itemId.getText().toString();
                iname=itemName.getText().toString();
                p=price.getText().toString();
                db.execSQL("insert into menu values('"+iid+"','"+iname+"','"+p+"','"+cat+"')");
                Toast.makeText(getApplicationContext(),"Item successfully added.", Toast.LENGTH_SHORT).show();
                clearText();
            }
        });
    }

    public void clearText()
    {
        itemId.setText("");
        itemName.setText("");
        price.setText("");
    }
     @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        cat=category[position];
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}