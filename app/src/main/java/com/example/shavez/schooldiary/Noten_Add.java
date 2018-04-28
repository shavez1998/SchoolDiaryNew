package com.example.shavez.schooldiary;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.json.JSONObject;

import dmax.dialog.SpotsDialog;

public class Noten_Add extends AppCompatActivity {

    EditText titel,datum,note;
    Button save;
    int fach_id;
    AlertDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_noten__add);

        Intent intent = getIntent();
        fach_id = Integer.parseInt(intent.getStringExtra("fach_id"));

        titel = (EditText) findViewById(R.id.note_add_titel);
        datum = (EditText) findViewById(R.id.note_add_datum);
        note = (EditText) findViewById(R.id.note_add_note);
        save = (Button) findViewById(R.id.note_add_button);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    proOn();
                    JSONObject json = new JSONObject();
                    json.put("fachID", "" + fach_id);
                    json.put("userID", "" + MainActivity.benutzer.getBenutzer_id());
                    json.put("beschreibung", titel.getText().toString());
                    json.put("datum", datum.getText().toString());
                    json.put("note", note.getText().toString());

                    DatenHochladen t = new DatenHochladen("noten","addNote");
                    t.execute(new JSONObject[]{json});
                    proOff();
                } catch (Exception e){ Log.w("DELETE ERROR", "asdf"); e.getMessage();}
                new Bewertung().notenHolenArr("" + fach_id);
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
