package com.example.shavez.schooldiary;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

/**
 * Created by ateeq on 02/03/2018.
 */

public class Fach {
    String fach_name;
    float dnote;
    int fach_id;

    public Fach(){
        //leer, brauchen?
    }
    public Fach(int id, String fach_name, float durchschnittsnote){
        this.fach_name = fach_name;
        this.dnote = durchschnittsnote;
        this.fach_id = id;
    }


    public void setFach_id(int id){ this.fach_id = id; }

    public int getFach_id() { return  fach_id; }

    public String getFach_name() {
        return fach_name;
    }

    public void setFach_name(String fach_name) {
        this.fach_name = fach_name;
    }

    public float getDurchschnittsnote() {
        return dnote;
    }

    public void setDurchschnittsnote(float durchschnittsnote) {
        this.dnote = durchschnittsnote;
    }

    public static ArrayList<Fach> faecherLaden(){
        ArrayList<Fach> list = new ArrayList<>();
        float dnote = 4;
        for(int i=0; i < 30;i++){
            try {
                dnote += 0.2;
                dnote =  Math.round(dnote * 100)/ 100f;
                Fach f = new Fach((i+1),"Java "+i, dnote);
                list.add(f);
            }catch (Exception e){

            }
        }
        return list;
    }


}
