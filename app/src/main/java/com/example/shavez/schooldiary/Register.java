package com.example.shavez.schooldiary;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;
import dmax.dialog.SpotsDialog;



public class Register extends AppCompatActivity {

    EditText vorname,nachname,email,passwort;
    Button next,cancel;
    DataSource dataSource;
    android.app.AlertDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        next = (Button) findViewById(R.id.r_register_next);
        cancel = (Button) findViewById(R.id.r_cancel);
        vorname = (EditText) findViewById(R.id.r_vorname);
        nachname = (EditText) findViewById(R.id.r_nachname);
        email = (EditText) findViewById(R.id.r_email);
        passwort = (EditText) findViewById(R.id.r_passwort);
        dataSource = new DataSource(this);

        next.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
            boolean nichtAusgefuhlt = true;
            vorname.getText().toString().trim();
            nachname.getText().toString().trim();
            email.getText().toString().trim();
            email.getText().toString().toLowerCase();
            if (!vorname.getText().toString().isEmpty())
                if (!nachname.getText().toString().isEmpty())
                    if (!email.getText().toString().isEmpty())
                        if (!passwort.getText().toString().isEmpty())
                            nichtAusgefuhlt = false;

            if (nichtAusgefuhlt) {
                showMessage("ERROR", "Bitte alle Feldern Ausfühlen");
            } else {
                proOn();
                Client client = new Client();
                String url = "f=checkRegister" + "&e=" + email.getText().toString();
                client.getDaten("registerReceive", url, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int stausCode, cz.msebera.android.httpclient.Header[] headers, JSONObject response) {
                        try {
                            boolean login = true;
                            JSONArray arr;
                            if (response != null) {
                                arr = response.getJSONArray("daten");
                                JSONObject data = arr.getJSONObject(0);
                                if (data.has("create")) {
                                    Intent i = new Intent(Register.this, Register2.class);
                                    i.putExtra("vorname", vorname.getText().toString());
                                    i.putExtra("nachname", nachname.getText().toString());
                                    i.putExtra("email", email.getText().toString());
                                    i.putExtra("passwort", passwort.getText().toString());
                                    startActivity(i);
                                }
                                if (data.has("error")) {
                                    showMessage("ERROR", "Email existiert bereits!");
                                }

                            }
                            proOff();
                        } catch (Exception e) {
                            Toast.makeText(getApplicationContext(), "ERROR", Toast.LENGTH_LONG).show();
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
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(Register.this,"Canceled", Toast.LENGTH_LONG).show();
                Intent i = new Intent(Register.this, MainActivity.class);
                startActivity(i);
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
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
    public void proOff(){
        dialog.dismiss();
    }



}
