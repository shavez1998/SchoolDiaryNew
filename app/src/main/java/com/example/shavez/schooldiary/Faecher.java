package com.example.shavez.schooldiary;

import android.content.Intent;
import android.graphics.Color;
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
import android.support.v7.widget.Toolbar;
import android.view.Menu;

import com.miguelcatalan.materialsearchview.MaterialSearchView;

import java.util.ArrayList;
import java.util.List;

public class Faecher extends AppCompatActivity {

    public ListView listView;
    public FachAdapter fachAdapter;
    ArrayList<Fach> fachArr = new ArrayList<>();
    public static Faecher faecher;

    Toolbar toolbar;
    MaterialSearchView searchView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faecher);
        listView = (ListView) findViewById(R.id.lvFach);
        faecher = this;
        fachAdapter = new FachAdapter(this, fachArr, this);
        listView.setAdapter(fachAdapter);
        Fach fach= new Fach();
        fach.faecherHolenArr();
        Toast.makeText(this,"ARR SIZE " + fachArr.size(), Toast.LENGTH_SHORT).show();
        listViewLaden(fachArr);

        toolbar = (Toolbar) findViewById(R.id.toolbar_fach);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Fächer");
        toolbar.setTitleTextColor(Color.parseColor("#FFFFFF"));

        searchView = (MaterialSearchView) findViewById(R.id.search_view);
        searchView.setOnSearchViewListener(new MaterialSearchView.SearchViewListener() {
            @Override
            public void onSearchViewShown() {

            }

            @Override
            public void onSearchViewClosed() {

                //If closed Search View , lvFach will return default
                 listViewLaden(fachArr);

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
                    ArrayList<Fach> fachFound = new ArrayList<>();
                    for (Fach item: fachArr){
                        newText = newText.toLowerCase();
                        String fach_name = item.getFach_name().toLowerCase();
                        if(fach_name.contains(newText)){
                            fachFound.add(item);
                        }
                    }
                    listViewLaden(fachFound);
                }
                else {
                    // default Array
                    listViewLaden(fachArr);
                }
                return  true;
            }
        });


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Intent i = new Intent(Faecher.this, Noten.class);
                i.putExtra("fach_id", fachAdapter.getItem(position).getFach_id());
                i.putExtra("fach_name", fachAdapter.getItem(position).getFach_name());
                startActivity(i);
            }
        });
    }
    public void listViewLaden(ArrayList<Fach> faecherLaden){
        fachAdapter.clear();
        for(Fach f : faecherLaden){
            fachAdapter.add(f);
        }
    }
    public void serArrList(){
        listViewLaden(fachArr);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
       getMenuInflater().inflate(R.menu.menu_search,menu);
       MenuItem item = menu.findItem(R.id.action_search);
       searchView.setMenuItem(item);
       return  true;
    }
}
