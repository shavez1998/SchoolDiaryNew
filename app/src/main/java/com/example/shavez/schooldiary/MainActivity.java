package com.example.shavez.schooldiary;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import dmax.dialog.SpotsDialog;


public class MainActivity extends AppCompatActivity {

    public static Benutzer benutzer;
    android.app.AlertDialog dialog;
    Button login;
    Button register;
    EditText emailText;
    EditText passwortText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        benutzer = new Benutzer();

        login = (Button) findViewById(R.id.login);
        register = (Button) findViewById(R.id.register);
        emailText = (EditText) findViewById(R.id.email);
        passwortText = (EditText) findViewById(R.id.passwort);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, Register.class);
                startActivity(i);
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean nichtAusgefuhlt = true;
                if(!emailText.getText().toString().isEmpty())
                    if(!passwortText.getText().toString().isEmpty())
                                nichtAusgefuhlt = false;

                if(nichtAusgefuhlt){
                    Intent i = new Intent(MainActivity.this, Menu.class);
                    startActivity(i);
                    showMessage("ERROR","Bitte alle Feldern Ausfühlen");
                } else {
                    proOn();
                    Client client = new Client();
                    String url = "f=checkLogin"+"&e="+emailText.getText().toString()+"&p="+passwortText.getText().toString();
                    client.getDaten("benutzer",url, new JsonHttpResponseHandler(){
                        @Override
                        public void onSuccess(int stausCode, cz.msebera.android.httpclient.Header[] headers, JSONObject response){
                            try{
                                boolean login = true;
                                JSONArray arr;
                                if(response != null){
                                    arr = response.getJSONArray("daten");
                                    JSONObject data = arr.getJSONObject(0);
                                    if(data.has("id")){
                                        int id = Integer.parseInt(data.getString("id"));
                                        benutzer.setBenutzer_id(id);
                                    }
                                    if(data.has("vorname")){
                                        benutzer.setVorname(data.getString("vorname"));
                                    }
                                    if(data.has("nachname")){
                                        benutzer.setNachname(data.getString("nachname"));
                                    }
                                    if(data.has("email")){
                                        benutzer.setEmail(data.getString("email"));
                                    }

                                    if(data.has("error")){
                                        showMessage("ERROR","Email oder Passwort falsch!!");
                                        passwortText.setText("");
                                        login = false;
                                    }
                                }
                                if(login) {

                                    proOff();
                                    //new Fach().faecherHolenArr();
                                    Log.e("LIST LEN", "TEST");
                                    Intent i = new Intent(MainActivity.this, Menu.class);
                                    startActivity(i);
                                }
                            } catch (Exception e){
                                Toast.makeText(getApplicationContext(), "ERROR", Toast.LENGTH_LONG).show();
                                //new Fach().faecherHolenArr();
                               // Log.e("LIST LEN", ""+ Fach.list.size());
                            }
                        }
                    });

                }
            }
        });

    }
    public  void showMessage(String title, String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.show();
    }
    public void proOn(){
        dialog = new SpotsDialog(this, "Loading");
        dialog.show();
    }
    public void proOff(){  dialog.dismiss(); }

    public void FachArrFuellen(){

    }


}
