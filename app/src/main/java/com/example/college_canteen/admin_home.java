package com.example.college_canteen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class admin_home extends AppCompatActivity {
    ImageButton menu,balance,orders;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);
        menu=(ImageButton) findViewById(R.id.imageButton2);
        balance=(ImageButton) findViewById(R.id.imageButton3);
        orders=(ImageButton)findViewById(R.id.imageButton);
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(admin_home.this,update_menu.class);
                startActivity(i);
            }
        });
        balance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(admin_home.this,add_balance.class);
                startActivity(i);
            }
        });
        orders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(admin_home.this,aview_orders.class);
                startActivity(i);
            }
        });
    }
}