package com.example.shavez.schooldiary;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by ateeq on 02/03/2018.
 */

public class Bewertung {
    int bewertung_id;
    float note;
    String bewertung_titel;
    String bewertung_date;

    public Bewertung(){
        //leer brauche?
    }

    public Bewertung(int bewertung_id, float note, String bewertung_titel, String bewertung_date){
        this.bewertung_id = bewertung_id;
        this.note = note;
        this.bewertung_titel = bewertung_titel;
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

    public String getBewertung_titel() {
        return bewertung_titel;
    }

    public void setBewertung_titel(String bewertung_titel) {
        this.bewertung_titel = bewertung_titel;
    }

    public String getBewertung_datum() {
        return bewertung_date;
    }

    public void setBewertung_datum(String bewertung_date) {
        this.bewertung_date = bewertung_date;
    }

    public static ArrayList<Bewertung> notenLaden(String fach_name){
        ArrayList<Bewertung> list = new ArrayList<>();
        float note = 4;
        for(int i=0; i < 30;i++){
            try {
                note += 0.2;
                note =  Math.round(note * 100)/ 100f;
                Bewertung f = new Bewertung((i+1), note,"Beispiel Note ", "20.03.2018");
                list.add(f);
            }catch (Exception e){

            }
        }
        return list;
    }
}
