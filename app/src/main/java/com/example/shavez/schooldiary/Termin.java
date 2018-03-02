package com.example.shavez.schooldiary;

import java.util.Date;

/**
 * Created by ateeq on 02/03/2018.
 */

public class Termin {
    int termin_id;
    String termin_titel;
    String termin_beschreibung;
    Date termin_date;

    public Termin(){
        //leer brauchen?
    }
    public Termin(int termin_id, String termin_titel, String termin_beschreibung, Date termin_date){
        this.termin_id = termin_id;
        this.termin_titel = termin_titel;
        this.termin_beschreibung = termin_beschreibung;
        this.termin_date = termin_date;
    }

    public int getTermin_id() {
        return termin_id;
    }

    public void setTermin_id(int termin_id) {
        this.termin_id = termin_id;
    }

    public String getTermin_titel() {
        return termin_titel;
    }

    public void setTermin_titel(String termin_titel) {
        this.termin_titel = termin_titel;
    }

    public String getTermin_beschreibung() {
        return termin_beschreibung;
    }

    public void setTermin_beschreibung(String termin_beschreibung) {
        this.termin_beschreibung = termin_beschreibung;
    }

    public Date getTermin_date() {
        return termin_date;
    }

    public void setTermin_date(Date termin_date) {
        this.termin_date = termin_date;
    }
}
