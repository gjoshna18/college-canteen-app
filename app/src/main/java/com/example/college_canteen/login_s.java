package com.example.college_canteen;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

public class login_s extends AppCompatActivity {
    EditText u,p;
    Button login;
    TextView aklogin,reg;
    SQLiteDatabase db;
    String uid,pass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final GlobalClass globalvariabel = (GlobalClass)getApplicationContext();
        setContentView(R.layout.activity_login_s);
        aklogin=(TextView) findViewById(R.id.textView5);
        reg=(TextView) findViewById(R.id.textView6);
        u=(EditText) findViewById(R.id.editTextTextPersonName);
        p=(EditText) findViewById(R.id.editTextTextPassword);
        login=(Button) findViewById(R.id.button);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uid=u.getText().toString();
                pass=p.getText().toString();
                if(uid.equals("")||pass.equals("")) {
                    Toast.makeText(login_s.this, "Please enter all the fields..!", Toast.LENGTH_LONG).show();
                }
                else {
                    db = openOrCreateDatabase("canteen", Context.MODE_PRIVATE, null);
                    Cursor cc = db.rawQuery("select * from std where sid= '" + uid + "' and p= '" + pass + "' ", null);
                    if (cc.moveToFirst()) {
                        String temp = "";
                        if (cc != null) {
                            if (cc.getCount() > 0) {
                                scan g = new scan();
                                g.execute();
                                globalvariabel.Setusername(u.getText().toString().trim());
                                Intent b = new Intent(login_s.this, view_menu.class);
                                startActivity(b);
                                clear();
                                return;
                            }
                        }
                    } else {
                        Toast.makeText(login_s.this, "Login Failed...!", Toast.LENGTH_SHORT).show();
                        clear();
                    }
                }
            }
        });
        aklogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(login_s.this, login_ak.class);
                startActivity(i);
            }
        });
        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(login_s.this, sregister.class);
                startActivity(i);
            }
        });
    }
    public class scan extends AsyncTask<String, String, String> {

        private ProgressDialog pd;

        protected void onPreExecute() {
            super.onPreExecute();
            pd = new ProgressDialog(login_s.this);
            pd.setTitle("Please Wait!!");
            pd.setMessage("Logging you In....");
            pd.setMax(10);
            pd.show();
        }

        @Override
        protected String doInBackground(String... params) {
            // TODO Auto-generated method stub
            return null;
        }
    }
    public void clear()
    {
        u.setText("");
        p.setText("");
    }
}