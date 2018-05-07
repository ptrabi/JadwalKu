package com.example.tmir.jadwalku;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class DeleteJadwal extends AppCompatActivity {
    DatabaseHelper myDb;
    EditText editKodeMK;
//    Button btnAddData;
//    Button btnviewAll;
    Button btnDelete;

   // Button btnviewUpdate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_jadwal);
        myDb = new DatabaseHelper(this);
        btnDelete = (Button)findViewById(R.id.button_delete1);
       editKodeMK = (EditText)findViewById(R.id.editText_KodeMK);
        DeleteData();
    }

    public void DeleteData() {
        btnDelete.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Integer deletedRows = myDb.deleteData(editKodeMK.getText().toString());
                        if(deletedRows > 0)
                            Toast.makeText(DeleteJadwal.this,"Data Deleted",Toast.LENGTH_LONG).show();
                        else
                            Toast.makeText(DeleteJadwal.this,"Data not Deleted",Toast.LENGTH_LONG).show();
                    }
                }
        );
        MainActivity.ma.RefreshList();
    }

    @Override
    public void onBackPressed()
    {
        MainActivity.ma.RefreshList();
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }


}
