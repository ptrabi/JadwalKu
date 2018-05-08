package com.example.tmir.jadwalku;


import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.v7.app.ActionBar;
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





public class TambahJadwal extends AppCompatActivity {
    DatabaseHelper myDb;
    EditText editKodeMK,editMatkul,editRuangan,editJam,editDosen;
    Button btnAddData;


    private String kodemk, matkul, ruangan,  dosen, harispin;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_jadwal);
        myDb = new DatabaseHelper(this);
        logo();

        editKodeMK = (EditText)findViewById(R.id.editText_KodeMK);
        editMatkul = (EditText)findViewById(R.id.editText_Matkul);
        editRuangan = (EditText)findViewById(R.id.editText_Ruangan);
        editJam = (EditText) findViewById(R.id.editText_Jam);
        editDosen = (EditText)findViewById(R.id.editText_Dosen) ;

        btnAddData = (Button)findViewById(R.id.button_add);

        AddData();




    }
    public void logo(){
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setIcon(R.mipmap.ic_launcher);
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
        dosen = editDosen.getText().toString().trim();

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
                            if (isInserted)
                                Toast.makeText(TambahJadwal.this, "Data Inserted", Toast.LENGTH_LONG).show();
                            else
                                Toast.makeText(TambahJadwal.this, "Data not Inserted", Toast.LENGTH_LONG).show();

                        }

                    }

                }
        );


    }

    @Override
    public void onBackPressed()
    {
        MainActivity.ma.RefreshList();
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }
}
