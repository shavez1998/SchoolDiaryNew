package com.example.shavez.schooldiary;

import java.util.Date;

/**
 * Created by ateeq on 02/03/2018.
 */

public class Bewertung {
    int bewertung_id;
    float note;
    String bewertung_beschreibung;
    Date bewertung_date;

    public Bewertung(){
        //leer brauche?
    }

    public Bewertung(int bewertung_id, float note, String bewertung_beschreibung, Date bewertung_date){
        this.bewertung_id = bewertung_id;
        this.note = note;
        this.bewertung_beschreibung = bewertung_beschreibung;
        this.bewertung_date = bewertung_date;
    }

    public int getBewertung_id() {
        return bewertung_id;
    }

    public void setBewertung_id(int bewertung_id) {
        this.bewertung_id = bewertung_id;
    }

    public float getNote() {
        return note;
    }

    public void setNote(float note) {
        this.note = note;
    }

    public String getBewertung_beschreibung() {
        return bewertung_beschreibung;
    }

    public void setBewertung_beschreibung(String bewertung_beschreibung) {
        this.bewertung_beschreibung = bewertung_beschreibung;
    }

    public Date getBewertung_date() {
        return bewertung_date;
    }

    public void setBewertung_date(Date bewertung_date) {
        this.bewertung_date = bewertung_date;
    }
}
