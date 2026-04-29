package com.example.college_canteen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class add_balance extends AppCompatActivity {

    EditText sid,balance;
    Button update;
    String s,b,cb;
    int ob,ab,nb;
    SQLiteDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_balance);
        sid=(EditText) findViewById(R.id.editTextTextPersonName7);
        balance=(EditText) findViewById(R.id.editTextNumber3);
        update=(Button) findViewById(R.id.button7);
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db=openOrCreateDatabase("canteen", Context.MODE_PRIVATE, null);
                s=sid.getText().toString();
                b=balance.getText().toString();
                ab=Integer.parseInt(b);
                Cursor cc=db.rawQuery("select * from wallet where sid='"+s+"'",null);
                if(cc.moveToFirst())
                {
                    if (cc != null) {
                        if(cc.getCount() > 0)
                        {
                            ob=cc.getInt(1);
                        }
                    }
                }
                nb=ab+ob;
                db.execSQL("update wallet set balance='"+nb+"' where sid='"+s+"'");
                Toast.makeText(add_balance.this, "Balance updated\nBalance after adding: "  + nb , Toast.LENGTH_SHORT).show();
            }
        });
    }
}