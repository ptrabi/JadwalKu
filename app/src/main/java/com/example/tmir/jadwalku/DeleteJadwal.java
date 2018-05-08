package com.example.tmir.jadwalku;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.List;

public class DeleteJadwal extends AppCompatActivity{
    DatabaseHelper myDb;
    EditText editKodeMK;
    Button btnDelete, btnDelAll;
    Spinner ChooseKodeMK;
    private String mkdel;
    private static final String[] kosong = {"Belum ada data yang diinputkan"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_jadwal);
        logo();

        myDb = new DatabaseHelper(this);


        btnDelete = (Button)findViewById(R.id.button_delete1);
        editKodeMK = (EditText)findViewById(R.id.editText_KodeMK);
        btnDelAll = (Button)findViewById(R.id.delAll);


        loadSpinner();
        DeleteData();

        DeleteSemua();



    }
    public void logo(){
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setIcon(R.mipmap.ic_launcher);
    }


    private void loadSpinner() {
        // database handler
        DatabaseHelper db = new DatabaseHelper (getApplicationContext());
        ChooseKodeMK = (Spinner)findViewById(R.id.spinner);
        // Spinner Drop down elements

        List<String> mk = db.getdata();

        if (mk.isEmpty()){

            ArrayAdapter<String> dataKosong = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,kosong);
            dataKosong.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            ChooseKodeMK.setAdapter(dataKosong);
        }else{
        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item,mk);

        // Drop down layout style - list view with radio button
        dataAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ChooseKodeMK.setAdapter(dataAdapter);
        ChooseKodeMK.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
               mkdel =String.valueOf(parent.getItemAtPosition(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    }



    private void DeleteSemua() {

        btnDelAll.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        DatabaseHelper db = new DatabaseHelper(getApplicationContext());
                        List<String > mk = db.getdata();
                        if(mk.isEmpty()){
                            Toast.makeText(DeleteJadwal.this, "Data kosong", Toast.LENGTH_SHORT).show();
                        }else {
                            AlertDialog diaBox = DeleteAll();
                            diaBox.show();

                        }
                        }
                }
        );
    }



    private AlertDialog DeleteAll() {
        AlertDialog myQuittingDialogBox =new AlertDialog.Builder(this)

                .setTitle("Delete")
                .setMessage("Apakah anda yakin ingin menghapus semua data?")
                .setPositiveButton("Delete", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {
                        myDb.deleteAllData();
                        Toast.makeText(DeleteJadwal.this, "Data Deleted", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }

                })
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .create();
        return myQuittingDialogBox;
    }


    public void DeleteData() {

        btnDelete.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        DatabaseHelper db = new DatabaseHelper(getApplicationContext());
                        List<String > mk = db.getdata();
                        if(mk.isEmpty()){
                            Toast.makeText(DeleteJadwal.this, "Data kosong", Toast.LENGTH_SHORT).show();
                        }
                        else {dialogdel();
                        }
                    }
                }
        );

    }
   public void dialogdel()
    {



        Cursor res = myDb.getSatuMK(mkdel);
       if(res.getCount()==0){
           showMessage("Error","Kode MK tidak ditemukan");
       }else {       StringBuffer buffer = new StringBuffer();
           while(res.moveToNext()){
               buffer.append("Kode MK : "+ res.getString(0)+"\n");
               buffer.append("Matkul : "+ res.getString(1)+"\n");
               buffer.append("Ruangan : "+ res.getString(2)+"\n");
               buffer.append("Hari : "+ res.getString(3)+"\n");
               buffer.append("Jam : "+ res.getString(4)+"\n");
               buffer.append("Dosen : "+ res.getString(5)+"\n\n");

           }
           showMessageDel("Data", buffer.toString());}


    }

    @Override
    public void onBackPressed()
    {
        MainActivity.ma.RefreshList();
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }
    public void showMessageDel(String title,String Message){
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message+"\n Apakah ini data yang ingin anda hapus?\n");
        builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Integer deletedRow = myDb.deleteData(mkdel);
                if(deletedRow>0) Toast.makeText(DeleteJadwal.this, "Data Deleted", Toast.LENGTH_SHORT).show();
                else Toast.makeText(DeleteJadwal.this, "Data not deleted", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();
    }

    public void showMessage(String title,String Message){
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
    }

}
