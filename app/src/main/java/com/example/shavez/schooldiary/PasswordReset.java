package com.example.shavez.schooldiary;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.Random;

import dmax.dialog.SpotsDialog;

public class PasswordReset extends AppCompatActivity {

    String frage_1 = "";
    String frage_2 = "";
    int frageHelp = 0;
    String antwort = "";
    String antwort2= "";
    String pass = "";
    String passConf = "";

    private TextView emailLabel;
    private EditText emailInput;
    private String email = "";
    private Button weiterEmail;

    private TextView frageLabel;
    private EditText frageInput;
    private Button weiterFrage;

    private TextView passLabel;
    private TextView passLabelConf;
    private EditText passwort;
    private EditText passwortConf;
    private Button resetButton;
    android.app.AlertDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        setContentView(R.layout.activity_password_reset);
        super.onCreate(savedInstanceState);

        Toolbar toolbarVerify = (Toolbar) findViewById(R.id.toolbarVerify);
        toolbarVerify.setTitle("Password Reset");
        toolbarVerify.setTitleTextColor(Color.WHITE);

        emailLabel = (TextView) findViewById(R.id.emailLabel);
        emailInput = (EditText) findViewById(R.id.emailInput);
        email = "";
        weiterEmail = (Button) findViewById(R.id.weiterEmail);

        frageLabel = (TextView) findViewById(R.id.frageLabel);
        frageInput = (EditText) findViewById(R.id.frageInput);
        weiterFrage = (Button) findViewById(R.id.weiterFrage);

        passLabel = (TextView) findViewById(R.id.passwortLabel);
        passLabelConf = (TextView) findViewById(R.id.passwortConfLabel);
        passwort = (EditText) findViewById(R.id.passwort);
        passwortConf = (EditText) findViewById(R.id.passwortConf);
        resetButton = (Button) findViewById(R.id.reset);

        frageLabel.setVisibility(View.INVISIBLE);
        frageInput.setVisibility(View.INVISIBLE);
        weiterFrage.setVisibility(View.INVISIBLE);

        passLabel.setVisibility(View.INVISIBLE);
        passLabelConf.setVisibility(View.INVISIBLE);
        passwort.setVisibility(View.INVISIBLE);
        passwortConf.setVisibility(View.INVISIBLE);
        resetButton.setVisibility(View.INVISIBLE);

        emailInput.setText("ateeq.raja@ymail.com");


        weiterEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                email = emailInput.getText().toString();

                String url = "f=checkFrage" + "&e=" + email;
                Client client = new Client();

                client.getDaten("benutzer", url, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int stausCode, cz.msebera.android.httpclient.Header[] headers, JSONObject response) {

                        try {
                            JSONArray arr;
                            if (response != null) {
                                arr = response.getJSONArray("daten");
                                JSONObject data = arr.getJSONObject(0);
                                if(!data.has("error")) {
                                    frage_1 = data.getString("frage");
                                    frage_2 = data.getString("frage2");
                                    antwort = data.getString("antwort");
                                    antwort2 = data.getString("antwort2");

                                    emailLabel.setVisibility(View.INVISIBLE);
                                    emailInput.setVisibility(View.INVISIBLE);
                                    emailInput.setVisibility(View.INVISIBLE);
                                    weiterEmail.setVisibility(View.INVISIBLE);


                                    //next page
                                    frageLabel.setVisibility(View.VISIBLE);
                                    frageInput.setVisibility(View.VISIBLE);
                                    weiterFrage.setVisibility(View.VISIBLE);

                                    Random r = new Random();
                                    int rand = r.nextInt(80 - 65) + 65;
                                    if (rand >= 74) {
                                        frageLabel.setText(frage_1);
                                        frageHelp = 1;
                                    } else {
                                        frageLabel.setText(frage_2);
                                        frageHelp = 2;
                                    }
                                }else {
                                    showMessageError("ERROR","Email nicht gefunden!");
                                }

                            }
                        } catch (Exception e) {
                            Toast.makeText(getApplicationContext(), "ERROR", Toast.LENGTH_LONG).show();
                            //new Fach().faecherHolenArr();
                            // Log.e("LIST LEN", ""+ Fach.list.size());
                        }
                    }
                });


            }
        });

        weiterFrage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if((frageInput.getText().toString().equals(antwort) && frageHelp == 1) ||
                        (frageInput.getText().toString().equals(antwort2) && frageHelp == 2)  ){
                    frageLabel.setVisibility(View.INVISIBLE);
                    frageInput.setVisibility(View.INVISIBLE);
                    weiterFrage.setVisibility(View.INVISIBLE);

                    //next page
                    passLabel.setVisibility(View.VISIBLE);
                    passLabelConf.setVisibility(View.VISIBLE);
                    passwort.setVisibility(View.VISIBLE);
                    passwortConf.setVisibility(View.VISIBLE);
                    resetButton.setVisibility(View.VISIBLE);
                } else {
                    showMessageError("ERROR","Falsche Antwort!");
                }


            }

        });

        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pass = passwort.getText().toString();
                passConf = passwortConf.getText().toString();

                if(pass.equals(passConf)){

                    try {

                        proOn();
                        JSONObject json = new JSONObject();
                        json.put("email", "" + email);
                        json.put("pass", "" + passConf);

                        DatenHochladen t = new DatenHochladen("benutzer", "passChange","passwordreset",PasswordReset.this);
                        t.execute(new JSONObject[]{json});


                    }catch (Exception ex){
                        Log.e("error", ex.toString());
                    }
                }else{
                    showMessageError("Error", "Passwort stimmt nich überein");
                }

            }
        });

    }


    public void proOn(){
        dialog = new SpotsDialog(this, "Loading");
        dialog.show();
    }
    public void proOff(){
        dialog.dismiss();
    }
    public void datenGespeichert(){
        proOff();
        finish();
    }
    public void showError(){
        showMessageError("ERROR","Internet verbindungs fehler");
    }
    public  void showMessageError(String title, String message){
        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(this);
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
}
