package com.example.college_canteen;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class view_menu extends AppCompatActivity {

    SQLiteDatabase db;
    ListView l;
    ArrayList<String> allItems = new ArrayList<>();
    ArrayList<String> miname = new ArrayList<>();
    ArrayList<Integer> miprice = new ArrayList<>();
    ArrayAdapter adapter;
    String q,n,gu;
    int ba;
    Button cart;
    TextView uname,balance;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_menu);
        final GlobalClass globalvariabel = (GlobalClass)getApplicationContext();
        db=openOrCreateDatabase("canteen", Context.MODE_PRIVATE, null);
        l = (ListView) findViewById(R.id.listView1);
        cart=(Button) findViewById(R.id.button);
        uname=(TextView) findViewById(R.id.textView31);
        balance=(TextView) findViewById(R.id.textView33);
        gu=globalvariabel.GetUsername().toString();
        Cursor cc1=db.rawQuery("SELECT * from std where sid='"+gu+"'", null);
        if(cc1.getCount()!=0)
        {
            while (cc1.moveToNext())
            {
                n=cc1.getString(1);
            }
        }
        uname.append(" "+String.valueOf(n));
        Cursor cc2=db.rawQuery("SELECT * from wallet where sid='"+gu+"'", null);
        if(cc2.getCount()!=0)
        {
            while (cc2.moveToNext())
            {
                ba=cc2.getInt(1);
            }
        }
        balance.append(" "+String.valueOf(ba));
        final ArrayList<String> list = new ArrayList<String>();
        Cursor res=db.rawQuery("SELECT * FROM menu", null);
        if(res.getCount()!=0)
        {
            while (res.moveToNext())
            {
                list.add(res.getString(1)+"\nCost: "+res.getInt(2)+"/-");
                miname.add(res.getString(1));
                miprice.add(res.getInt(2));
            }
        }
        adapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,list);
        l.setAdapter(adapter);
        l.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(view_menu.this);
                String name=miname.get(position);
                alertDialog.setTitle(name);
                alertDialog.setMessage("Enter quantity");
                final EditText input = new EditText(view_menu.this);
                input.setInputType(InputType.TYPE_CLASS_NUMBER);
                alertDialog.setView(input);
                alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int which) {
                        q=input.getText().toString();
                        Toast.makeText(getApplicationContext(), "Added to cart", Toast.LENGTH_SHORT).show();
                        dialog.cancel();
                        String name=miname.get(position);
                        int price=miprice.get(position);
                        ArrayList<String> item = new ArrayList<>();
                        item.add(name);
                        item.add(String.valueOf(price));
                        item.add(q);
                        allItems.add(String.valueOf(item));
                        adapter.notifyDataSetChanged();
                        return;
                    }
                });
                alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                alertDialog.show();
            }


        });
        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(view_menu.this,view_cart.class);
                i.putExtra("key",allItems);
                i.putExtra("balance",ba);
                startActivity(i);
            }
        });
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId()) {
            case R.id.orderStatus:
                startActivity(new Intent(this, order_status.class));
                return true;
            case R.id.orderHistroy:
                startActivity(new Intent(this, order_history.class));
                return true;
            case R.id.cp:
                startActivity(new Intent(this, change_password.class));
                return true;
            case R.id.l:
                startActivity(new Intent(this, login_s.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }

    }
}