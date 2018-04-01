package com.example.shavez.schooldiary;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class Terminen extends AppCompatActivity {

    public ListView listView;
    public TerminAdapter terminAdapter;
    ArrayList<Termin> termin;
    EditText searchText;
    ImageButton searchBtn;
    public static Terminen terminen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terminen);
        setTitle("Terminen");
        terminen = this;
        listView = (ListView) findViewById(R.id.lvNote);
        searchText = (EditText) findViewById(R.id.termin_search_text);
        searchBtn = (ImageButton) findViewById(R.id.termin_search_btn);
        ArrayList<Termin> t = new ArrayList<>();
        terminAdapter = new TerminAdapter(this, t, this);
        listView.setAdapter(terminAdapter);
        Termin t1 = new Termin();
        t1.terminHolenArr();

        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String suchText = searchText.getText().toString();
            }
        });
    }

    public void listViewLaden(ArrayList<Termin> t) {
        terminAdapter.clear();
        for (Termin termin : t) {
            terminAdapter.add(termin);
        }
    }

    public void serArrList() {
        listViewLaden(termin);
    }
}