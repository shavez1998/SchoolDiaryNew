package com.example.shavez.schooldiary;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.*;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.miguelcatalan.materialsearchview.MaterialSearchView;

import java.util.ArrayList;

import dmax.dialog.SpotsDialog;

public class Noten extends AppCompatActivity {

    public ListView listView;
    public Button addNote;
    public NotenAdapter notenAdapter;
    ArrayList<Bewertung> notenArr;
    public static Noten noten;
    String fach_id ;
    AlertDialog dialog;
    Toolbar toolbar;
    MaterialSearchView searchView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_noten);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        String fach_name = bundle.get("fach_name").toString();
        fach_id = bundle.get("fach_id").toString();
        setTitle(fach_name);
        noten = this;
        listView = (ListView) findViewById(R.id.lvNote);
        addNote = (Button) findViewById(R.id.addNote);
        ArrayList<Bewertung> bw = new ArrayList<>();
        notenAdapter = new NotenAdapter(this, bw, this);
        listView.setAdapter(notenAdapter);
        Bewertung b = new Bewertung();
        b.notenHolenArr(fach_id);

        addNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Noten.this, Noten_Add.class);
                i.putExtra("fach_id", fach_id);
                startActivity(i);
            }
        });
        toolbar = (Toolbar) findViewById(R.id.toolbar_noten);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(fach_name);
        toolbar.setTitleTextColor(Color.parseColor("#FFFFFF"));

        searchView = (MaterialSearchView) findViewById(R.id.search_view);
        searchView.setOnSearchViewListener(new MaterialSearchView.SearchViewListener() {
            @Override
            public void onSearchViewShown() {

            }

            @Override
            public void onSearchViewClosed() {

                //If closed Search View , lvFach will return default
                listViewLaden(notenArr);

            }
        });
        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if(newText != null && !newText.isEmpty() ){
                    ArrayList<Bewertung> notenFound = new ArrayList<>();
                    for (Bewertung item: notenArr){
                        newText = newText.toLowerCase();
                        String fach_name = item.getBewertung_titel().toLowerCase();
                        if(fach_name.contains(newText)){
                            notenFound.add(item);
                        }
                    }
                    listViewLaden(notenFound);
                }
                else {
                    // default Array
                    listViewLaden(notenArr);
                }
                return  true;
            }
        });

    }
    public void listViewLaden(ArrayList<Bewertung> noten){
        notenAdapter.clear();
        for(Bewertung note : noten){
            notenAdapter.add(note);
        }
    }
    public void serArrList(){
        listViewLaden(notenArr);
    }

    public boolean onCreateOptionsMenu(android.view.Menu menu){
        getMenuInflater().inflate(R.menu.menu_search,menu);
        MenuItem item = menu.findItem(R.id.action_search);
        searchView.setMenuItem(item);
        return  true;
    }

    public void proOn(){
        dialog = new SpotsDialog(this, "Loading");
        dialog.show();
    }
    public void proOff(){
        dialog.dismiss();
    }

}
