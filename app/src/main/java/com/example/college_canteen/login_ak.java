package com.example.college_canteen;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

public class login_ak extends AppCompatActivity {
    String u,p;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_ak);
        EditText user=(EditText) findViewById(R.id.user);
        EditText pass=(EditText) findViewById(R.id.pass);
        Button login=(Button) findViewById(R.id.button3);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               //u=user.getText().toString();
               //p=pass.getText().toString();
               if(user.getText().toString().equals("admin")&&pass.getText().toString().equals("admin")){
                   Intent i = new Intent(login_ak.this, admin_home.class);
                   startActivity(i);
               }
               else if(user.getText().toString().equals("kitchen")&&pass.getText().toString().equals("kitchen")){
                    Intent i = new Intent(login_ak.this, kitchen_home.class);
                    startActivity(i);
               }
               else{
                   Toast.makeText(login_ak.this, "Incorrect login credentials " , Toast.LENGTH_LONG).show();
               }
            }
        });
    }
}