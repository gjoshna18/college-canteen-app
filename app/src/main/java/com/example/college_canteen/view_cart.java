package com.example.college_canteen;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

public class view_cart extends AppCompatActivity {

    TextView t;
    ListView l;
    Button placeOrder;
    RadioGroup po;
    RadioButton cash,ew,sb;
    ArrayAdapter adapter;
    String iname,price,q,c,pm,sid;
    int cost,tc=0,oid,nb,balance;
    ArrayList<String> list;
    ArrayList<String> nlist = new ArrayList<>();
    ArrayList<String> ilist = new ArrayList<>();
    ArrayList<String> oinfo =new ArrayList<>();
    ArrayList<Integer> iprice =new ArrayList<>();
    SQLiteDatabase db;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final GlobalClass globalvariabel = (GlobalClass)getApplicationContext();
        setContentView(R.layout.activity_view_cart);
        l = (ListView) findViewById(R.id.listView1);
        t=(TextView)findViewById(R.id.textView30);
        placeOrder=(Button)findViewById(R.id.button);
        po=(RadioGroup)findViewById(R.id.radioGroup);
        cash=(RadioButton)findViewById(R.id.radioButton);
        ew=(RadioButton)findViewById(R.id.radioButton2);
        sid=globalvariabel.GetUsername().toString();
        ArrayList<String> itemList = (ArrayList<String>) getIntent().getSerializableExtra("key");
        Intent intent = getIntent();
        balance=getIntent().getIntExtra("balance",0);
        for (int i = 0; i < itemList.size(); i++)
        {
            ilist.add(itemList.get(i));
        }
        for (int i = 0; i < ilist.size(); i++)
        {
            String n=ilist.get(i);
            n=n.substring(1, n.length() - 1);
            list = new ArrayList<>(Arrays.asList(n.split(",")));
            iname=list.get(0);
            price=list.get(1).trim();
            q=list.get(2).trim();
            cost=Integer.parseInt(price)*Integer.parseInt(q);
            tc+=cost;
            c=String.valueOf(cost);
            nlist.add("Item name: "+iname+"\nPrice: "+price+"\nQuantity: "+q+"\nCost: "+c);
            oinfo.add(iname+"-"+q);
            iprice.add(cost);
        }
        adapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,nlist);
        l.setAdapter(adapter);
        changeTotal(tc);
        l.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(view_cart.this);
                alertDialog.setTitle("Do you want to remove the item?");
                alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int which) {
                        itemList.remove(position);
                        nlist.remove(position);
                        tc=tc-(iprice.get(position));
                        changeTotal(tc);
                        adapter.notifyDataSetChanged();
                        return;
                    }
                });
                alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                alertDialog.show();
            }
        });
        placeOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db=openOrCreateDatabase("canteen", Context.MODE_PRIVATE, null);
                db.execSQL("create table if not exists orders(sid varchar,oid integer,oinfo varchar,total_cost integer, payment_method varchar,ostatus varchar);");
                if(cash.isChecked()){
                    oid=generate_oid();
                    int selectedId=po.getCheckedRadioButtonId();
                    sb=(RadioButton)findViewById(selectedId);
                    pm= (String) sb.getText();
                    db.execSQL("insert into orders values('"+sid+"','"+oid+"','"+oinfo+"','"+tc+"','"+pm+"','Order placed')");
                    Intent i=new Intent(view_cart.this,order_placed.class);
                    i.putExtra("key",oid);
                    startActivity(i);
                }
                if(ew.isChecked()){
                    if(tc<=balance){
                        oid=generate_oid();
                        int selectedId=po.getCheckedRadioButtonId();
                        sb=(RadioButton)findViewById(selectedId);
                        pm= (String) sb.getText();
                        db.execSQL("insert into orders values('"+sid+"','"+oid+"','"+oinfo+"','"+tc+"','"+pm+"','Order placed')");
                        nb=balance-tc;
                        db.execSQL("update wallet set balance='"+nb+"' where sid='"+sid+"'");
                        Intent i=new Intent(view_cart.this,order_placed.class);
                        i.putExtra("key",oid);
                        startActivity(i);
                    }
                    else
                    {
                        AlertDialog.Builder alertDialog = new AlertDialog.Builder(view_cart.this);
                        alertDialog.setTitle("Error");
                        alertDialog.setMessage("Your e-wallet balance is less than the total amount.\nPlease select Cash option and place your order.");
                        alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int which) {
                                dialog.cancel();
                            }
                        });
                        alertDialog.show();
                    }
                }
            }
        });
    }
    public int generate_oid()
    {
        int oi = (int)(Math.random()*9000)+1000;
        //Store integer in a string
        return oi;
    }
    void changeTotal(int tc){
        t.setText(String.valueOf(tc));
    }

}