package com.example.shavez.schooldiary;

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

public class Noten extends AppCompatActivity {

    public ListView listView;
    public NotenAdapter notenAdapter;
    ArrayList<Bewertung> bewertungen;
    EditText searchText;
    ImageButton searchBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_noten);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        String fach_name = bundle.get("fach_name").toString();
        setTitle(fach_name);

        listView = (ListView) findViewById(R.id.lvNote);
        searchText = (EditText) findViewById(R.id.note_search_text);
        searchBtn = (ImageButton) findViewById(R.id.note_search_btn);
        ArrayList<Bewertung> bw = new ArrayList<>();
        notenAdapter = new NotenAdapter(this, bw, this);
        listView.setAdapter(notenAdapter);
        bewertungen = Bewertung.notenLaden(fach_name);
        listViewLaden(bewertungen);

        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String suchText = searchText.getText().toString();
            }
        });
    }
    public void listViewLaden(ArrayList<Bewertung> noten){
        notenAdapter.clear();
        for(Bewertung note : noten){
            notenAdapter.add(note);
        }
    }

}
