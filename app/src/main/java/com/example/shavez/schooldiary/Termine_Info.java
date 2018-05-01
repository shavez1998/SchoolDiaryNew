package com.example.shavez.schooldiary;


import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Termine_Info extends AppCompatActivity {

    TextView datum,titel,beschreibung;
    Button ok;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_termine__info);
        Toolbar toolbarVerify = (Toolbar) findViewById(R.id.toolbar_termin_info);
        toolbarVerify.setTitle("Termin Info");
        toolbarVerify.setTitleTextColor(Color.WHITE);


        titel = (TextView) findViewById(R.id.termin_info_titel);
        datum = (TextView) findViewById(R.id.termin_info_datum);
        beschreibung = (TextView) findViewById(R.id.termin_info_beschreibung);
        ok = (Button) findViewById(R.id.termin_info_button);

        Intent intent = getIntent();
        titel.setText(intent.getStringExtra("titel"));
        datum.setText(intent.getStringExtra("datum"));
        beschreibung.setText(intent.getStringExtra("beschreibung"));



        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

}
