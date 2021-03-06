package com.example.shavez.schooldiary;

import android.util.Log;

import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;

import cz.msebera.android.httpclient.Header;

/**
 * Created by ateeq on 02/03/2018.
 */

public class Termin {
    int termin_id;
    String termin_titel;
    String termin_beschreibung;
    String termin_date;

    public Termin(){
        //leer brauchen?
    }
    public Termin(int termin_id, String termin_titel, String termin_beschreibung, String termin_date){
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

    public String getTermin_datum() {
        return termin_date;
    }

    public void setTermin_datum(String termin_date) {
        this.termin_date = termin_date;
    }

    public Termin terminHolen(JSONObject object){

        Termin termin= new Termin();
        try {
            if(object.has("beschreibung")){
                termin.setTermin_beschreibung(object.getString("beschreibung"));
            }
            if(object.has("id")){
                termin.setTermin_id(Integer.parseInt(object.getString("id")));
            }
            if(object.has("datum")){
                String[] datumArr = object.getString("datum").split("-");
                termin.setTermin_datum(datumArr[2]+"."+datumArr[1]+"."+datumArr[0]);
            }
            if(object.has("titel")){
                termin.setTermin_titel(object.getString("titel"));
            }
        }catch (Exception e){

        }
        return termin;
    }

    public void terminHolenArr(final Boolean loadLabel){
        if(loadLabel)
            Terminen.terminen.proOn();
        final ArrayList<Termin> list = new ArrayList<Termin>();
        Client client = new Client();
        String url = "f=getTerminen&uid="+MainActivity.USERID;
        client.getDaten("terminen",url, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int stausCode, cz.msebera.android.httpclient.Header[] headers, JSONObject response){
                try{
                    JSONArray arr;
                    if(response != null){
                        arr = response.getJSONArray("daten");
                        for(int i = 0; i < arr.length();i++){
                            Termin termin = terminHolen(arr.getJSONObject(i));
                            list.add(termin);
                        }
                    }
                    Terminen.terminen.terminArr = list;
                    Terminen.terminen.serArrList();
                    if(loadLabel)
                        Terminen.terminen.proOff();
                } catch (Exception e){ }
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Terminen.terminen.proOff();
                Terminen.terminen.showMessage("ERROR", "Internet verbindungs fehler");

            }
        });
    }
}
