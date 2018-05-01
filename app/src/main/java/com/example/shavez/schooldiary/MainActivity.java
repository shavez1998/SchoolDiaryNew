package com.example.shavez.schooldiary;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;
import dmax.dialog.SpotsDialog;


public class MainActivity extends AppCompatActivity {

    public static int USERID;
    public android.app.AlertDialog dialog;
    CheckBox checkBox ;
    Button login;
    Button register;
    EditText emailText;
    EditText passwortText;
    TextView passForgot;
    SharedPreferences prefs;
    SharedPreferences.Editor editor;
    private boolean activeUserLog = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        login = (Button) findViewById(R.id.login);
        register = (Button) findViewById(R.id.register);
        emailText = (EditText) findViewById(R.id.email);
        passwortText = (EditText) findViewById(R.id.passwort);
        checkBox = (CheckBox) findViewById(R.id.checkBox);
        passForgot = (TextView) findViewById(R.id.passForgot);

        passForgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, PasswordReset.class);
                startActivity(i);
            }
        });
        prefs = this.getApplicationContext().getSharedPreferences("userdetails", Context.MODE_PRIVATE);
        editor = prefs.edit();
        try {
            Intent i = getIntent();
            String logout = i.getStringExtra("logout");
            if(logout.equals("true")){
                editor.clear();
                editor.commit();
                Log.e("HELP ME ","SHAVEZ");
            }
        }catch (Exception e){

        }


        String benutzerID = prefs.getString("UserID", "");
        if(!benutzerID.equalsIgnoreCase("")) {
            USERID = Integer.parseInt(prefs.getString("UserID", ""));
            Intent i = new Intent(MainActivity.this, Menu.class);
            startActivity(i);
        }






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
                emailText.getText().toString().trim();
                emailText.getText().toString().toLowerCase();
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
                                        USERID = Integer.parseInt(data.getString("id"));
                                        if(checkBox.isChecked()) {
                                            editor.putString("UserID", "" + USERID);
                                            editor.apply();
                                        }
                                    }


                                    if(data.has("error")){
                                        proOff();
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
                        @Override
                        public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                            showMessage("ERROR", "Internet verbindungs fehler");
                            proOff();
                        }
                    });

                }
            }
        });

    }
    public void showMessage(String title, String message){
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
    public  void proOff(){  dialog.dismiss(); }

    @Override
    public void onBackPressed() {
        return;
    }


}
