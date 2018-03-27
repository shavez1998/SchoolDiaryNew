package com.example.shavez.schooldiary;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import static android.support.v4.content.ContextCompat.startActivity;

public class Noten_Edit extends AppCompatActivity {

    EditText titel,datum,note;
    Button save;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_noten__edit);

        titel = (EditText) findViewById(R.id.note_edit_titel);
        datum = (EditText) findViewById(R.id.note_edit_datum);
        note = (EditText) findViewById(R.id.note_edit_note);
        save = (Button) findViewById(R.id.note_edit_button);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Intent intent = getIntent();
        titel.setText(intent.getStringExtra("titel"));
        datum.setText(intent.getStringExtra("datum"));
        note.setText(intent.getStringExtra("note"));

    }
}
