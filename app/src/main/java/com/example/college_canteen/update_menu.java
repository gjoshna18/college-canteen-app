package com.example.college_canteen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class update_menu extends AppCompatActivity {

    Button add,update,delete;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_menu);
        add=(Button) findViewById(R.id.button7);
        update=(Button) findViewById(R.id.button8);
        delete=(Button) findViewById(R.id.button9);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent b = new Intent(update_menu.this,add_item.class);
                startActivity(b);
            }
        });
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent b = new Intent(update_menu.this,update_item.class);
                startActivity(b);
            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent b = new Intent(update_menu.this,delete_item.class);
                startActivity(b);
            }
        });

    }
}