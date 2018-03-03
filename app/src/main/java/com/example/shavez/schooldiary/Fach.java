package com.example.shavez.schooldiary;

/**
 * Created by ateeq on 02/03/2018.
 */

public class Fach {
    int fach_id;
    String fach_name;
    String fach_beschreibung;
    int durchschnittsnote;

    public Fach(){
        //leer, brauchen?
    }
    public Fach(int fach_id, String fach_name, String fach_beschreibung, int durchschnittsnote){
        this.fach_id = fach_id;
        this.fach_name = fach_name;
        this.fach_beschreibung = fach_beschreibung;
        this.durchschnittsnote = durchschnittsnote;
    }

    public int getFach_id() {
        return fach_id;
    }

    public void setFach_id(int fach_id) {
        this.fach_id = fach_id;
    }

    public String getFach_name() {
        return fach_name;
    }

    public void setFach_name(String fach_name) {
        this.fach_name = fach_name;
    }

    public String getFach_beschreibung() {
        return fach_beschreibung;
    }

    public void setFach_beschreibung(String fach_beschreibung) {
        this.fach_beschreibung = fach_beschreibung;
    }

    public int getDurchschnittsnote() {
        return durchschnittsnote;
    }

    public void setDurchschnittsnote(int durchschnittsnote) {
        this.durchschnittsnote = durchschnittsnote;
    }

}
