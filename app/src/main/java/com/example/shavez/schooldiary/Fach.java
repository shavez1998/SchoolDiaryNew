package com.example.shavez.schooldiary;

import android.app.AlertDialog;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import dmax.dialog.SpotsDialog;

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
        Fach a = new Fach(1,"Deutsch ", 6);
        list.add(a);

        Fach b = new Fach(234,"Italienisch ",7);
        list.add(b);

        Fach c = new Fach(3,"Englisch ",9);
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
    public Fach fachHolen(JSONObject object){

        Fach fach = new Fach();
        try {
            if(object.has("fach_name")){
                fach.setFach_name(object.getString("fach_name"));
            }
            if(object.has("id")){
                fach.setFach_id(Integer.parseInt(object.getString("id")));
            }
            if(object.has("dnote")){
                float dnote = Float.parseFloat(object.getString("dnote"));

                Log.w("Dnote", ""+dnote);
                //float dnote1 = Math.round(4.5 * 100)/ 100);;
                fach.setDurchschnittsnote(Float.parseFloat(""+Math.round(dnote * 10.0)/ 10.0));
            }
        }catch (Exception e){

        }
        return fach;
    }

    public void faecherHolenArr(){

        //Faecher.faecher.proOn();
        final ArrayList<Fach> list = new ArrayList<Fach>();
        Client client = new Client();
        String url = "f=getFach&uid="+MainActivity.benutzer.getBenutzer_id();
        //start
        client.getDaten("faecher",url, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int stausCode, cz.msebera.android.httpclient.Header[] headers, JSONObject response){
                try{
                    JSONArray arr;
                    if(response != null){
                        arr = response.getJSONArray("daten");
                        for(int i = 0; i < arr.length();i++){
                            Fach fach = fachHolen(arr.getJSONObject(i));
                            list.add(fach);
                        }
                    }
                    Faecher.faecher.fachArr = list;
                    Faecher.faecher.serArrList();
                    Faecher.faecher.proOff();
                    //end
                   // dialog.dismiss();
                } catch (Exception e){ }
            }
        });
        //return list;
    }


}
