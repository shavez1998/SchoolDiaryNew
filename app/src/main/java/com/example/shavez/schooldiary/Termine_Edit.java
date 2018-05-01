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

public class Termine_Edit extends AppCompatActivity {

    EditText titel,beschreibung;
    TextView datum;
    int termin_id;
    Button save;
    AlertDialog dialog;
    DatePickerDialog.OnDateSetListener mDateListner;
    String[] zahlen;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_termine__edit);
        Toolbar toolbarVerify = (Toolbar) findViewById(R.id.toolbar_termin_edit);
        toolbarVerify.setTitle("Termin Ändern");
        toolbarVerify.setTitleTextColor(Color.WHITE);

        titel = (EditText) findViewById(R.id.termin_edit_titel);
        datum = (TextView) findViewById(R.id.termin_edit_datum);
        beschreibung = (EditText) findViewById(R.id.termin_edit_beschreibung);
        save = (Button) findViewById(R.id.termin_edit_button);

        Intent intent = getIntent();
        titel.setText(intent.getStringExtra("titel"));
        datum.setText(intent.getStringExtra("datum"));
        beschreibung.setText(intent.getStringExtra("beschreibung"));
        termin_id = Integer.parseInt(intent.getStringExtra("termin_id"));

        String help = datum.getText().toString();

        final String[] datumArr = help.split("\\.");
        Log.w("DATUM ", ""+datumArr.length + " "+datum.getText().toString()+"  HELP : "+ help);
        //Log.w("DATUM ", ""+Integer.parseInt(datumArr[0])+"."+Integer.parseInt(datumArr[1])+"."+Integer.parseInt(datumArr[2]));

        datum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = Integer.parseInt(datumArr[2]);
                int month = Integer.parseInt(datumArr[1])-1;
                int day = Integer.parseInt(datumArr[0]);
                DatePickerDialog dialog = new DatePickerDialog(
                        Termine_Edit.this,
                        R.style.Theme_AppCompat_Light_Dialog_MinWidth,
                        mDateListner,
                        year,month,day);
                //dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
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
                    json.put("id", "" + termin_id);
                    json.put("titel", titel.getText().toString());
                    json.put("datum", datumArr[2]+"-"+datumArr[1]+"-"+datumArr[0]);
                    json.put("beschreibung", beschreibung.getText().toString());

                    DatenHochladen t = new DatenHochladen("terminen","editTermin");
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
