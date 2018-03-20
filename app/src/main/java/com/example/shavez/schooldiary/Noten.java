package com.example.shavez.schooldiary;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class Noten extends AppCompatActivity {

    public ListView listView;
    public NotenAdapter notenAdapter;
    ArrayList<Bewertung> bewertungen;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faecher);
        listView = (ListView) findViewById(R.id.lvFach);
        ArrayList<Bewertung> bw = new ArrayList<>();
        notenAdapter = new NotenAdapter(this, bw, this);
        listView.setAdapter(notenAdapter);
        bewertungen = Fach.faecherLaden();
        listViewLaden(fach);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Toast.makeText(Faecher.this,"I am in Listview", Toast.LENGTH_LONG).show();
            }
        });
    }
    public void listViewLaden(ArrayList<Fach> faecherLaden){
        fachAdapter.clear();
        for(Fach f : faecherLaden){
            fachAdapter.add(f);
        }
    }
}
