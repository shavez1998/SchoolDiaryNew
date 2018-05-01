package com.example.shavez.schooldiary;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
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

import static android.support.v4.content.ContextCompat.startActivity;

public class Noten_Edit extends AppCompatActivity {

    EditText titel;
    TextView datum,note;
    int note_id;
    int fach_id;
    Button save;
    AlertDialog dialog;
    DatePickerDialog.OnDateSetListener mDateListner;
    String[] zahlen;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_noten__edit);
        Toolbar toolbarVerify = (Toolbar) findViewById(R.id.toolbar_noten_edit);
        toolbarVerify.setTitle("Note Ändern");
        toolbarVerify.setTitleTextColor(Color.WHITE);
        titel = (EditText) findViewById(R.id.note_edit_titel);
        datum = (TextView) findViewById(R.id.note_edit_datum);
        note = (TextView) findViewById(R.id.note_edit_note);
        save = (Button) findViewById(R.id.note_edit_button);

        Intent intent = getIntent();
        titel.setText(intent.getStringExtra("titel"));
        datum.setText(intent.getStringExtra("datum"));
        note.setText(intent.getStringExtra("note"));
        note_id = Integer.parseInt(intent.getStringExtra("note_id"));
        fach_id = Integer.parseInt(intent.getStringExtra("fach_id"));

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
                        Noten_Edit.this,
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
        note.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final NumberPicker numberPicker = new NumberPicker(Noten_Edit.this);

                numberPicker.setMinValue(0);
                numberPicker.setMaxValue(24);
                numberPicker.setWrapSelectorWheel(false);
                zahlen = new String[25];
                float zahl = 4;
                int value = 0;
                for(int i = 0; i < 25; i++){
                    zahlen[i] = ""+ zahl;
                    if (zahl == Float.parseFloat(note.getText().toString()))
                        value = i;
                    zahl += 0.25;
                }
                numberPicker.setValue(value);
                numberPicker.setDisplayedValues( zahlen );
                AlertDialog.Builder builder = new AlertDialog.Builder(Noten_Edit.this);
                builder.setTitle("Note Auswählen");

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        note.setText(zahlen[numberPicker.getValue()]);
                    }
                });

                builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                builder.setView(numberPicker);
                builder.create();
                builder.show();
            }

        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    proOn();
                    String[] datumArr = datum.getText().toString().split("\\.");
                    JSONObject json = new JSONObject();
                    json.put("id", "" + note_id);
                    json.put("beschreibung", titel.getText().toString());
                    json.put("datum", datumArr[2]+"-"+datumArr[1]+"-"+datumArr[0]);
                    json.put("note", note.getText().toString());

                    DatenHochladen t = new DatenHochladen("noten","editNote","notenedit",Noten_Edit.this);
                    t.execute(new JSONObject[]{json});
                } catch (Exception e){ Log.w("DELETE ERROR", "asdf"); e.getMessage();}
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
        new Bewertung().notenHolenArr("" + fach_id,false);
        proOff();
        finish();
    }
    public void showError(){
        proOff();
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
