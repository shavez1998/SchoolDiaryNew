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
        Fach a = new Fach(234,"Deutsch ", 6);
        list.add(a);

        Fach b = new Fach(234,"Italienisch ",7);
        list.add(b);

        Fach c = new Fach(234,"Englisch ",9);
        list.add(c);

        Fach d = new Fach(234,"Geschichte ",Math.round(4.5 * 100)/ 100f);
        list.add(d);

        Fach e = new Fach(234,"Android ",6);
        list.add(e);

        Fach f = new Fach(234,"Elektro ",5);
        list.add(f);

        Fach g = new Fach(234,"RWK ",9);
        list.add(g);

        Fach h = new Fach(234,"IT ",7);
        list.add(h);

        Fach i = new Fach(234,"Mathematik ",4);
        list.add(i);

        Fach j = new Fach(234,"Sport ",8);
        list.add(j);



        return list;
    }


}
