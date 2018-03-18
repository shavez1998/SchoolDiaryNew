package com.example.shavez.schooldiary;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

/**
 * Created by ahmed on 03.03.2018.
 */

public class DataSource {
    private SQLiteDatabase database;
    private DatabaseHelper dbHelper;
    Context context;

    public DataSource(Context c){
        context = c;
        dbHelper = new DatabaseHelper(c);
    }

    public void open(){
        database = dbHelper.getWritableDatabase();
    }
    public void close(){
        dbHelper.close();
    }
    public boolean insertDataBenutzer(String vorname, String nachname, String email, String passwort){
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.COL_VORNAME, vorname);
        contentValues.put(DatabaseHelper.COL_NACHNAME, nachname);
        //contentValues.put(DatabaseHelper.COL_EMAIL, email);
        //contentValues.put(DatabaseHelper.COL_PASSWORT, passwort);
        long result = database.insert(DatabaseHelper.TABLE_BENUTZER,null,contentValues);
        if(result == -1) return  false;
        return true;
    }
    public Cursor getDataBenutzer(){
        return database.rawQuery("SELECT * FROM "+ DatabaseHelper.TABLE_BENUTZER, null);

    }

}
