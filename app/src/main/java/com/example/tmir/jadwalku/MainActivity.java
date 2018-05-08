package com.example.tmir.jadwalku;

import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    ListView ListView01;
    protected Cursor cursor;
    DatabaseHelper dbhelpme;
    boolean doubleTap = false;
    public static MainActivity ma;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        logo();
        ma= this;
        dbhelpme = new DatabaseHelper(this);
        ListView01 = (ListView)findViewById(R.id.listView1);

    RefreshList();
    }

    public void logo(){
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setIcon(R.mipmap.ic_launcher);
    }
   public void RefreshList() {
        cursor = dbhelpme.getAllData();
        ArrayList<String> listData = new ArrayList<>();
        while(cursor.moveToNext()){
           listData.add(cursor.getString(3));
        }

        Object[] st = listData.toArray();
        for(Object s: st){
            if(listData.indexOf(s) !=listData.lastIndexOf(s)){
                listData.remove(listData.lastIndexOf(s));
            }
        }

        ListAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,listData);
            ListView01.setAdapter(adapter);
            ListView01.setOnItemClickListener(new AdapterView.OnItemClickListener() {
           @Override
           public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               String harinya = (ListView01.getItemAtPosition(position).toString());
               Cursor res = dbhelpme.getSatuHari(harinya);
               if(res.getCount() == 0) {
                   // show message
                   showMessage("Error","Nothing found");
                   return;
               }

               StringBuffer buffer = new StringBuffer();
               while (res.moveToNext()) {
                   buffer.append("Kode MK : "+ res.getString(0)+"\n");
                   buffer.append("Matkul : "+ res.getString(1)+"\n");
                   buffer.append("Ruangan : "+ res.getString(2)+"\n");
                   buffer.append("Hari : "+ res.getString(3)+"\n");
                   buffer.append("Jam : "+ res.getString(4)+"\n");
                   buffer.append("Dosen : "+ res.getString(5)+"\n\n");

               }

               // Show all data
               showMessage("Data",buffer.toString());
           }
       });

    }





    public void pindahTambah(View view) {
        Intent intent = new Intent(MainActivity.this, TambahJadwal.class);
        startActivity(intent);
    }

    public void pindahRubah(View view) {
        Intent intent = new Intent(MainActivity.this, EditJadwal.class);
        startActivity(intent);
    }
    public void pindahDelete(View view) {
        Intent intent = new Intent(MainActivity.this, DeleteJadwal.class);
        startActivity(intent);
    }
    public void MariLihatPeta(View view) {
        Intent intent = new Intent(MainActivity.this, AkuPeta.class);
        startActivity(intent);
    }

    public void showMessage(String title,String Message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
    }

    @Override
    public void onBackPressed()
    {
        if(doubleTap){
            super.onBackPressed();
        }else {
            this.doubleTap=true;
            Toast.makeText(this, "Tekan sekali lagi untuk keluar",Toast.LENGTH_SHORT).show();

            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    doubleTap = false;
                }
            },500);
        }

    }
}