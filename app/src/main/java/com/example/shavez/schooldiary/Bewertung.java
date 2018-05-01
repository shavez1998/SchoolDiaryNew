package com.example.shavez.schooldiary;

import android.util.Log;

import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

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

    public Bewertung () {

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

    public Bewertung noteHolen(JSONObject object){

        Bewertung note = new Bewertung();
        try {
            if(object.has("beschreibung")){
                note.setBewertung_titel(object.getString("beschreibung"));
            }
            if(object.has("id")){
                note.setBewertung_id(Integer.parseInt(object.getString("id")));
            }
            if(object.has("datum")){
                String[] datumArr = object.getString("datum").split("-");

                note.setBewertung_datum(datumArr[2]+"."+datumArr[1]+"."+datumArr[0]);
            }
            if(object.has("note")){
                float n = Float.parseFloat(object.getString("note"));
                note.setNote(n);
            }
        }catch (Exception e){

        }
        return note;
    }

    public void notenHolenArr(String fach_id, final Boolean loadDialog){
        if(loadDialog)
            Noten.noten.proOn();
        final ArrayList<Bewertung> list = new ArrayList<Bewertung>();
        Client client = new Client();
        String url = "f=getNoten&uid="+MainActivity.benutzer.getBenutzer_id()+"&fid="+fach_id;
        client.getDaten("noten",url, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int stausCode, cz.msebera.android.httpclient.Header[] headers, JSONObject response){
                try{
                    JSONArray arr;
                    if(response != null){
                        arr = response.getJSONArray("daten");
                        for(int i = 0; i < arr.length();i++){
                            Bewertung note = noteHolen(arr.getJSONObject(i));
                            list.add(note);
                        }
                    }
                    Noten.noten.notenArr = list;
                    Noten.noten.serArrList();
                    if(loadDialog)
                        Noten.noten.proOff();
                } catch (Exception e){ }
            }
        });
        //return list;
    }

}
