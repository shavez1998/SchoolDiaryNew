package com.example.shavez.schooldiary;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by ateeq on 02/03/2018.
 */

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1; //datenkbank version
    public static final String DATABASE_NAME = "student_diary.db"; //datenbank name

    //benutzer tabelle
    public static final String TABLE_BENUTZER = "benutzer";
    public static final String COL_ID_BENUTZER = "ID";
    public static final String COL_VORNAME = "Vorname";
    public static final String COL_NACHNAME = "Nachname";
    public static final String COL_FK_FRAGE = "ID_Frage";
    public static final String COL_Antwort = "Antwort";
    public static final String COL_FK_LOGIN = "ID_Login";

    //login tabelle
    public static final String TABLE_LOGIN = "Login";
    public static final String COL_ID_LOGIN = "ID";
    public static final String COL_EMAIL_LOGIN = "Email";
    public static final String COL_PASSWORT_LOGIN = "Passwort";

    //Fach tabelle
    public static final String TABLE_FACH = "fach";
    public static final String COL_ID_FACH = "ID";
    public static final String COL_NAME_FACH ="Name";

    //Fragen
    public static final String TABLE_FRAGEN = "frage";
    public static final String COL_ID_FRAGEN = "ID";
    public static final String COL_FRAGE = "Frage";

    //Termin tabelle
    public static final String TABLE_TERMIN = "termin";
    public static final String COL_ID_TERMIN = "ID";
    public static final String COL_TERMIN_TITEL = "Titel";
    public static final String COL_TERMIN_BESCHREIBUNG = "Beschreibung";
    public static final String COL_TERMIN_DATE = "Datum";
    public static final String COL_FK_BENUTZER_TERMIN = "ID_Benutzer";

    //Bewertung tabelle
    public static final String TABLE_BEWERTUNG = "bewertung";
    public static final String COL_BEWERTUNG_ID = "ID";
    public static final String COL_BEWERTUNG_BESCHREIBUNG = "Beschreibung";
    public static final String COL_BEWERTUNG_NOTE = "Note";
    public static final String COL_BEWERTUNG_DATE = "Datum";
    public static final String COL_FK_BENUTZER = "ID_Benutzer";

    //Foreign Key Tabelle
    public static final String TABLE_FK = "BenutzerFach";
    public static final String COL_BENUTZER_ID = "Benutzer_ID";
    public static final String COL_FACH_ID = "Fach_ID";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null,DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        try {
            String table1= " create table " + TABLE_BENUTZER + "("+COL_ID_BENUTZER +  " Integer Primary Key Autoincrement, "+
                    COL_VORNAME + " Text, "+ COL_NACHNAME + " Text, "+ COL_Antwort + " Text, "+ COL_FK_FRAGE +
                    " Integer, FOREIGN KEY (" + COL_FK_FRAGE + ") REFERENCES " + TABLE_FRAGEN + "(" + COL_ID_FRAGEN + "), "+
                    COL_FK_LOGIN+"Integer FOREIGN KEY ("+COL_FK_LOGIN+") REFERENCES " + TABLE_LOGIN +"("+COL_ID_LOGIN+"))";
            sqLiteDatabase.execSQL(table1);

            String table2= " create table " + TABLE_LOGIN + "("+COL_ID_LOGIN +  " Integer Primary Key Autoincrement, "+
                    COL_VORNAME + " Text, "+ COL_EMAIL_LOGIN + " Text, "+ COL_PASSWORT_LOGIN + " Text)";
            sqLiteDatabase.execSQL(table2);

            String table3= " create table " + TABLE_FACH + "("+COL_ID_FACH +  " Integer Primary Key Autoincrement, "+
                    COL_NAME_FACH + " Text"+")";
            sqLiteDatabase.execSQL(table3);

            String table4 = " create table " + TABLE_FRAGEN + "("+COL_ID_FRAGEN +  " Integer Primary Key Autoincrement, "+
                    COL_FRAGE + " Text"+")";
            sqLiteDatabase.execSQL(table4);

            String table5 = " create table " + TABLE_TERMIN + "("+COL_ID_TERMIN +  "Integer Primary Key Autoincrement, "+
                    COL_TERMIN_TITEL + "Text, "+ COL_TERMIN_BESCHREIBUNG + "Integer, "+ COL_TERMIN_DATE + "Date, "+
                    COL_FK_BENUTZER_TERMIN + "Integer, FOREIGN KEY (" + COL_FK_BENUTZER_TERMIN + ") REFERENCES " +
                    TABLE_TERMIN + "("+ COL_ID_TERMIN+"))";
            sqLiteDatabase.execSQL(table5);



            String table6 = " create table " + TABLE_BEWERTUNG + "("+COL_BEWERTUNG_ID+  "Integer Primary Key Autoincrement, "+
                    COL_BEWERTUNG_NOTE + "Text, "+ COL_BEWERTUNG_BESCHREIBUNG + "Text, " + COL_BEWERTUNG_DATE + "Date, " +
                    COL_FK_BENUTZER + "Integer, FOREIGN KEY (" + COL_FK_BENUTZER + ") REFERENCES " +
                    TABLE_BENUTZER + "(" + COL_ID_BENUTZER + "))";
            sqLiteDatabase.execSQL(table6);

            String table7 = " create table " + TABLE_FK + "("+COL_BENUTZER_ID +  "Integer Primary Key, "+
                    COL_FACH_ID + "Integer Primary Key, "+ COL_BENUTZER_ID + "Integer, FOREIGN KEY (" +
                    COL_BENUTZER_ID + ") REFERENCES " + TABLE_BENUTZER + "("+ COL_ID_BENUTZER +"), " +
                    COL_FACH_ID + "Integer FOREIGN KEY ("+ COL_FACH_ID+ ") REFERENCES " + TABLE_FACH + "("+COL_ID_FACH+"))";
            sqLiteDatabase.execSQL(table7);

        } catch(Exception e){

        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("drop table if exists "+TABLE_BENUTZER);
        sqLiteDatabase.execSQL("drop table if exists "+TABLE_LOGIN);
        sqLiteDatabase.execSQL("drop table if exists "+TABLE_BEWERTUNG);
        sqLiteDatabase.execSQL("drop table if exists "+TABLE_FACH);
        sqLiteDatabase.execSQL("drop table if exists "+TABLE_TERMIN);
        sqLiteDatabase.execSQL("drop table if exists "+ TABLE_FK);
        onCreate(sqLiteDatabase);
    }

}
