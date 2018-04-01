package com.example.shavez.schooldiary;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

public class Faecher extends AppCompatActivity {

    public ListView listView;
    public FachAdapter fachAdapter;
    EditText searchText;
    ImageButton searchBtn;
    ArrayList<Fach> fachArr = new ArrayList<>();
    public static Faecher faecher;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faecher);
        listView = (ListView) findViewById(R.id.lvFach);
        searchText = (EditText) findViewById(R.id.fach_search_text);
        searchBtn = (ImageButton) findViewById(R.id.fach_search_btn);
        faecher = this;
        fachAdapter = new FachAdapter(this, fachArr, this);
        listView.setAdapter(fachAdapter);
        Fach fach= new Fach();
        fach.faecherHolenArr();
        //fachArr = Fach.faecherLaden();
        Toast.makeText(this,"ARR SIZE " + fachArr.size(), Toast.LENGTH_SHORT).show();
        listViewLaden(fachArr);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Intent i = new Intent(Faecher.this, Noten.class);
                i.putExtra("fach_id", fachAdapter.getItem(position).getFach_id());
                i.putExtra("fach_name", fachAdapter.getItem(position).getFach_name());
                startActivity(i);
            }
        });
        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String suchText = searchText.getText().toString();
                //ATEEQ QUERY : FÄCHERN SUCHEN
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
}
