package com.example.shavez.schooldiary;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.util.Calendar;

import dmax.dialog.SpotsDialog;

public class Noten_Add extends AppCompatActivity {

    EditText titel;
    TextView datum,note;
    Button save;
    int fach_id;
    AlertDialog dialog;
    private NumberPicker.OnValueChangeListener valueChangeListener;

    DatePickerDialog.OnDateSetListener mDateListner;
    String[] zahlen;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_noten__add);
        Toolbar toolbarVerify = (Toolbar) findViewById(R.id.toolbar_noten_add);
        toolbarVerify.setTitle("Note Hinzufügen");
        toolbarVerify.setTitleTextColor(Color.WHITE);
        Intent intent = getIntent();
        fach_id = Integer.parseInt(intent.getStringExtra("fach_id"));

        titel = (EditText) findViewById(R.id.note_add_titel);
        datum = (TextView) findViewById(R.id.note_add_datum);
        note = (TextView) findViewById(R.id.note_add_note);
        save = (Button) findViewById(R.id.note_add_button);

        datum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        Noten_Add.this,
                        R.style.Theme_AppCompat_Light_Dialog_MinWidth,
                        mDateListner,
                        year,month,day);
                //dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });
        note.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final NumberPicker numberPicker = new NumberPicker(Noten_Add.this);

                numberPicker.setMinValue(0);
                numberPicker.setMaxValue(24);
                numberPicker.setWrapSelectorWheel(false);
                zahlen = new String[25];
                float zahl = 4;
                int value = 0;
                    for(int i = 0; i < 25; i++){
                        zahlen[i] = ""+ zahl;
                        Log.w("NOTE TEST", "  "+zahl+"    "+ note.getText().toString());

                        if (!"Note Auswählen".equals(note.getText().toString()))
                            if (zahl == Float.parseFloat(note.getText().toString()))
                            value = i;
                        zahl += 0.25;
                    }

                numberPicker.setValue(value);
                numberPicker.setDisplayedValues( zahlen );
                AlertDialog.Builder builder = new AlertDialog.Builder(Noten_Add.this);
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
                    json.put("fachID", "" + fach_id);
                    json.put("userID", "" + MainActivity.benutzer.getBenutzer_id());
                    json.put("beschreibung", titel.getText().toString());
                    json.put("datum", datumArr[2]+"-"+datumArr[1]+"-"+datumArr[0]);
                    json.put("note", note.getText().toString());

                    DatenHochladen t = new DatenHochladen("noten","addNote");
                    t.execute(new JSONObject[]{json});
                    proOff();
                } catch (Exception e){ Log.w("DELETE ERROR", "asdf"); e.getMessage();}
                new Bewertung().notenHolenArr("" + fach_id,true);
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
