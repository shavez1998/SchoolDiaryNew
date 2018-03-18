package com.example.shavez.schooldiary;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

/**
 * Created by ateeq on 02/03/2018.
 */

public class Fach {
    String fach_name;
    String fach_beschreibung;
    float dnote;
    int fach_id;

    public Fach(){
        //leer, brauchen?
    }
    public Fach(int id, String fach_name, String fach_beschreibung, float durchschnittsnote){
        this.fach_name = fach_name;
        this.fach_beschreibung = fach_beschreibung;
        this.dnote = durchschnittsnote;
        this.fach_id = id;
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

    public float getDurchschnittsnote() {
        return dnote;
    }

    public void setDurchschnittsnote(float durchschnittsnote) {
        this.dnote = durchschnittsnote;
    }

    public static ArrayList<Fach> faecherLaden(){
        ArrayList<Fach> list = new ArrayList<>();
        for(int i=0; i < 30;i++){
            try {
                Fach f = new Fach((i+1),"Java "+i,"Android Studio", 8);
                list.add(f);
            }catch (Exception e){

            }
        }
        return list;
    }


}
