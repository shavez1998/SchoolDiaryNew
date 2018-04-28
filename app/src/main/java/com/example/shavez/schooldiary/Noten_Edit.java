package com.example.shavez.schooldiary;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.json.JSONObject;

import dmax.dialog.SpotsDialog;

import static android.support.v4.content.ContextCompat.startActivity;

public class Noten_Edit extends AppCompatActivity {

    EditText titel,datum,note;
    int note_id;
    int fach_id;
    Button save;
    AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_noten__edit);

        titel = (EditText) findViewById(R.id.note_edit_titel);
        datum = (EditText) findViewById(R.id.note_edit_datum);
        note = (EditText) findViewById(R.id.note_edit_note);
        save = (Button) findViewById(R.id.note_edit_button);

        Intent intent = getIntent();
        titel.setText(intent.getStringExtra("titel"));
        datum.setText(intent.getStringExtra("datum"));
        note.setText(intent.getStringExtra("note"));
        note_id = Integer.parseInt(intent.getStringExtra("note_id"));
        fach_id = Integer.parseInt(intent.getStringExtra("fach_id"));

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    proOn();
                    JSONObject json = new JSONObject();
                    json.put("id", "" + note_id);
                    json.put("beschreibung", titel.getText().toString());
                    json.put("datum", datum.getText().toString());
                    json.put("note", note.getText().toString());

                    DatenHochladen t = new DatenHochladen("noten","editNote");
                    t.execute(new JSONObject[]{json});
                    new Bewertung().notenHolenArr("" + fach_id);
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
