package com.example.shavez.schooldiary;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TextView;

import org.json.JSONObject;

import java.util.Calendar;

import dmax.dialog.SpotsDialog;

public class Termine_Add extends AppCompatActivity {
    EditText titel,beschreibung;
    TextView datum;
    Button save;
    AlertDialog dialog;
    DatePickerDialog.OnDateSetListener mDateListner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_termine__add);
        Toolbar toolbarVerify = (Toolbar) findViewById(R.id.toolbar_termin_add);
        toolbarVerify.setTitle("Termin Hinzufügen");
        toolbarVerify.setTitleTextColor(Color.WHITE);

        titel = (EditText) findViewById(R.id.termin_add_titel);
        datum = (TextView) findViewById(R.id.termin_add_datum);
        beschreibung = (EditText) findViewById(R.id.termin_add_beschreibung);
        save = (Button) findViewById(R.id.termin_add_button);

        datum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        Termine_Add.this,
                        R.style.Theme_AppCompat_Light_Dialog_MinWidth,
                        mDateListner,
                        year,month,day);
                dialog.show();
            }
        });

        mDateListner = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month++;
                datum.setText(day+"."+month+"."+year);
            }
        };
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {

                    proOn();
                    String[] datumArr = datum.getText().toString().split("\\.");
                    JSONObject json = new JSONObject();
                    json.put("uid", "" + MainActivity.benutzer.getBenutzer_id());
                    json.put("titel", titel.getText().toString());
                    json.put("datum", datumArr[2]+"-"+datumArr[1]+"-"+datumArr[0]);
                    json.put("beschreibung", beschreibung.getText().toString());

                    DatenHochladen t = new DatenHochladen("terminen","addTermin");
                    t.execute(new JSONObject[]{json});
                    new Termin().terminHolenArr(false);
                    proOff();
                } catch (Exception e){ Log.w("DELETE ERROR", "asdf"); e.getMessage();}

                finish();
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
}
