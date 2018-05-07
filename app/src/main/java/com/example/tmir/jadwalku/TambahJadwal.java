package com.example.tmir.jadwalku;

import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;
import java.net.*;

import com.google.android.gms.common.api.Response;


public class TambahJadwal extends AppCompatActivity {
    DatabaseHelper myDb;
    EditText editKodeMK,editMatkul,editRuangan,editJam,editDosen;
    Button btnAddData;
    TimePickerDialog timePickerDialog;

    private String kodemk, matkul, ruangan,  jam, dosen, harispin;


    /*private  String hari;
    Button btnviewUpdate;
    Button btnviewAll;
    Button btnDelete;*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_jadwal);
        myDb = new DatabaseHelper(this);

        editKodeMK = (EditText)findViewById(R.id.editText_KodeMK);
        editMatkul = (EditText)findViewById(R.id.editText_Matkul);
        editRuangan = (EditText)findViewById(R.id.editText_Ruangan);

        editJam = (EditText) findViewById(R.id.editText_Jam);
        editDosen = (EditText)findViewById(R.id.editText_Dosen) ;

        btnAddData = (Button)findViewById(R.id.button_add);

        AddData();
      //  viewAll();
      //  UpdateData();
     //  DeleteData();




    }
    public void jampick() {

        editJam.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                int hour = 0;
                int minute =0;

                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(TambahJadwal.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                       editJam.setText( selectedHour + ":" + selectedMinute);
                    }
                }, hour, minute, true);
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();

            }
        });
    }

    public void spinnerHari(){
        Spinner pilihari = (Spinner)findViewById(R.id.pickhari);
        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(TambahJadwal.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.hariii));
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        pilihari.setAdapter(myAdapter);

        pilihari.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                harispin =String.valueOf(parent.getItemAtPosition(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }


    public boolean validasi(){
        konvert();
        boolean valid = true;
        if(kodemk.equals("")){
            editKodeMK.setError("Kode MK tidak boleh kosong");
            valid = false;
        }
        if(matkul.equals("")){
            editMatkul.setError("Isi nama Mata Kuliah");
            valid = false;
        }
        if(ruangan.equals("")){
            editRuangan.setError("Isi ruangan kuliah");
            valid = false;
        }

        if(jam.equals("")){
            editJam.setError("Isi jam kuliah");
            valid = false;
        }
        if (dosen.equals("")){
            editDosen.setError("Isi dosen pengampu mata kuliah");
            valid = false;
        }
        return valid;
    }


    public void konvert(){
        kodemk = editKodeMK.getText().toString().trim();
        matkul = editMatkul.getText().toString().trim();
        ruangan = editRuangan.getText().toString().trim();
        //hari   = editHari.getText().toString().trim();
        jam = editJam.getText().toString().trim();
        dosen = editDosen.getText().toString().trim();

    }



    @Override
    public void onBackPressed()
    {
        MainActivity.ma.RefreshList();
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }

    public  void AddData() {

        jampick();
        spinnerHari();
        btnAddData.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if(validasi()){
                            boolean isInserted = myDb.insertData(editKodeMK.getText().toString(), editMatkul.getText().toString(),
                                    editRuangan.getText().toString(),
                                    harispin
                                    , editJam.getText().toString()
                                    , editDosen.getText().toString()
                            );
                            if (isInserted == true)
                                Toast.makeText(TambahJadwal.this, "Data Inserted", Toast.LENGTH_LONG).show();
                            else
                                Toast.makeText(TambahJadwal.this, "Data not Inserted", Toast.LENGTH_LONG).show();

                        }

                    }

                }
        );

        MainActivity.ma.RefreshList();
    }

    /*
        public void UpdateData() {
        btnviewUpdate.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean isUpdate = myDb.updateData(editKodeMK.getText().toString(),
                                editMatkul.getText().toString(),
                                editRuangan.getText().toString(),
                                editHari.getText().toString(),
                                editJam.getText().toString()
                                , editDosen.getText().toString()
                        );

                        if(isUpdate == true)
                            Toast.makeText(TambahJadwal.this,"Data Update",Toast.LENGTH_LONG).show();
                        else
                            Toast.makeText(TambahJadwal.this,"Data not Updated",Toast.LENGTH_LONG).show();
                    }
                }
        );
    }
    public void DeleteData() {
        btnDelete.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Integer deletedRows = myDb.deleteData(editKodeMK.getText().toString());
                        if(deletedRows > 0)
                            Toast.makeText(TambahJadwal.this,"Data Deleted",Toast.LENGTH_LONG).show();
                        else
                            Toast.makeText(TambahJadwal.this,"Data not Deleted",Toast.LENGTH_LONG).show();
                    }
                }
        );
        MainActivity.ma.RefreshList();
    }


    public void viewAll() {
        btnviewAll.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Cursor res = myDb.getAllData();
                        if(res.getCount() == 0) {
                            // show message
                            showMessage("Error","Nothing found");
                            return;
                        }

                        StringBuffer buffer = new StringBuffer();
                        while (res.moveToNext()) {
                            buffer.append("Kode MK :"+ res.getString(0)+"\n");
                            buffer.append("Matkul :"+ res.getString(1)+"\n");
                            buffer.append("Ruangan :"+ res.getString(2)+"\n");
                            buffer.append("Hari :"+ res.getString(3)+"\n");
                            buffer.append("Jam :"+ res.getString(4)+"\n");
                            buffer.append("Dosen"+ res.getString(5)+"\n\n");

                        }

                        // Show all data
                        showMessage("Data",buffer.toString());
                    }
                }
        );
    }

    public void showMessage(String title,String Message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
    }


*/
}
