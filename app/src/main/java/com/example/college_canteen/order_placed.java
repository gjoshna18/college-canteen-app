package com.example.college_canteen;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

public class order_placed extends AppCompatActivity {

    TextView oid;
    Button back;
    int orderid;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_placed);
        oid=(TextView)findViewById(R.id.textView3);
        back=(Button) findViewById(R.id.button2);
        Intent intent = getIntent();
        int orderid=getIntent().getIntExtra("key",0);
        oid.append(String.valueOf(orderid));
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(order_placed.this,view_menu.class);
                startActivity(i);
            }
        });

    }
}