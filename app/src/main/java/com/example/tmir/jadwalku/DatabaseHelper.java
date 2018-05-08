package com.example.tmir.jadwalku;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ProgrammingKnowledge on 4/3/2015.
 */
public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "student.db";
    public static final String TABLE_NAME = "jadwalku";
    public static final String COL_1 = "KODEMK";
    public static final String COL_2 = "MATKUL";
    public static final String COL_3 = "RUANGAN";
    public static final String COL_4 = "HARI";
    public static final String COL_5 = "JAM";
    public static final String COL_6 = "DOSEN";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME +" (KODEMK TEXT PRIMARY KEY not null,matkul TEXT not null,ruangan TEXT not null,Hari Text not null, jam TEXT not null,dosen TEXT not null)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);
    }

    public boolean insertData(String kodemk, String matkul,String ruangan,String hari, String jam,String dosen) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1,kodemk);
        contentValues.put(COL_2,matkul);
        contentValues.put(COL_3,ruangan);
        contentValues.put(COL_4,hari);
        contentValues.put(COL_5,jam);
        contentValues.put(COL_6,dosen);
        long result = db.insert(TABLE_NAME,null ,contentValues);
        db.rawQuery("Delete From jadwalku where kodemk = '' or kodemk is null",null);
        if(result == -1)
            return false;
        else
            return true;
    }

    public Cursor getAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "Select kodemk, matkul, ruangan, upper(hari),jam, dosen  FROM "+TABLE_NAME;
        Cursor data = db.rawQuery(query, null);
        return data;
    }
    public Cursor getSatuHari(String harinya) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "Select * FROM "+TABLE_NAME+" WHERE hari is not null  and hari <> '' and upper(hari) = upper('"+harinya+"')";
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    public Cursor getSatuMK(String mknya) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "Select * FROM "+TABLE_NAME+" WHERE kodemk = ('"+mknya+"')";
        Cursor data = db.rawQuery(query, null);
        return data;
    }
    public List<String > getdata(){
        List<String> projects = new ArrayList<String>();

        String selectQuery = "SELECT KODEMK FROM jadwalku" ;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery,null);
        if (cursor.moveToFirst()) {
            do {
                projects.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }

        // closing connection
        cursor.close();
        db.close();

        // returning lables
        return projects;
        }



    public boolean updateData(String kodemk,String matkul,String ruangan,String hari,String jam, String dosen) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1,kodemk);
        contentValues.put(COL_2,matkul);
        contentValues.put(COL_3,ruangan);
        contentValues.put(COL_4,hari);
        contentValues.put(COL_5,jam);
        contentValues.put(COL_6,dosen);
        db.update(TABLE_NAME, contentValues, "KODEMK = ?",new String[] { kodemk });

        return true;
    }

    public Integer deleteData (String kodemk) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME, "KODEMK = ?",new String[] {kodemk});
    }
    public void deleteAllData () {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from "+ TABLE_NAME);

    }


}