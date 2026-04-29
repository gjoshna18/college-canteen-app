package com.example.college_canteen;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class change_password extends AppCompatActivity {

    EditText np,ncp;
    Button update;
    SQLiteDatabase db;
    String sid;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        final GlobalClass globalvariabel = (GlobalClass)getApplicationContext();
        np=(EditText)findViewById(R.id.editTextTextPassword2);
        ncp=(EditText)findViewById(R.id.editTextTextPassword3);
        update=(Button) findViewById(R.id.button4);
        sid=globalvariabel.GetUsername().toString();
        db=openOrCreateDatabase("canteen", Context.MODE_PRIVATE, null);
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(np.getText().toString().equals(ncp.getText().toString())) {
                    db.execSQL("update std set p='" + np.getText().toString() + "' where sid='" + sid + "'");
                    Toast.makeText(getApplicationContext(), "Password updated", Toast.LENGTH_SHORT).show();
                    clearText();
                }
                else{
                    Toast.makeText(change_password.this, "Password didn't match" , Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    public void clearText()
    {
        np.setText("");
        ncp.setText("");
    }
}