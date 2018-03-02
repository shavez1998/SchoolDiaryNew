package com.example.shavez.schooldiary;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

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
    public static final String COL_EMAIL = "Email";
    public static final String COL_PASSWORT = "Passwort";

    //Fach tabelle
    public static final String TABLE_FACH = "fach";
    public static final String COL_ID_FACH = "ID";
    public static final String COL_FACH_BESCHREIBUNG = "Beschreibung";
    public static final String COL_DURCHSCHNITTSNOTE = "Durchschnittsnote";

    //Termin tabelle
    public static final String TABLE_TERMIN = "termin";
    public static final String COL_ID_TERMIN = "ID";
    public static final String COL_TITEL = "Titel";
    public static final String COL_TERMIN_BESCHREIBUNG = "Beschreibung";
    public static final String COL_TERMIN_DATE = "Datum";

    //Bewertung tabelle
    public static final String TABLE_BEWERTUNG = "bewertung";
    public static final String COL_BEWERTUNG_ID = "ID";
    public static final String COL_BEWERTUNG_BESCHREIBUNG = "Beschreibung";
    public static final String COL_NOTE = "Note";
    public static final String COL_BESCHREIBUNG_DATE = "Datum";

    public DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(" create table "+ TABLE_BENUTZER + "(ID Integer Primary Key Autoincrement, Vorname Text, Nachname Text, Email Text, Passwort Text)");
        sqLiteDatabase.execSQL(" create table "+ TABLE_BEWERTUNG + "(ID Integer Primary Key Autoincrement, Note Integer, Beschreibung Text, Datum Date)");
        sqLiteDatabase.execSQL(" create table "+ TABLE_FACH + "(ID Integer Primary Key Autoincrement, Name Text, Beschreibung Text, Durchschnittsnote Integer)");
        sqLiteDatabase.execSQL(" create table "+ TABLE_TERMIN + "(ID Integer Primary Key Autoincrement, Titel Text, Beschreibung Text, Datum Date)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("drop table if exists "+TABLE_BENUTZER);
        sqLiteDatabase.execSQL("drop table if exists "+TABLE_BEWERTUNG);
        sqLiteDatabase.execSQL("drop table if exists "+TABLE_FACH);
        sqLiteDatabase.execSQL("drop table if exists "+TABLE_TERMIN);
        onCreate(sqLiteDatabase); // s
    }
}
