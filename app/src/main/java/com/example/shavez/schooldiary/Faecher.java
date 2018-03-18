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

public class Faecher extends AppCompatActivity {

    public ListView listView;
    public FachAdapter fachAdapter;
    private Button edit_btn, del_btn;
    ArrayList<Fach> fach;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faecher);
        listView = (ListView) findViewById(R.id.lvFach);
        edit_btn = (Button) findViewById(R.id.fach_edit);
        del_btn = (Button) findViewById(R.id.fach_edit);
        ArrayList<Fach> fach = new ArrayList<>();
        fachAdapter = new FachAdapter(this, fach, this);
        listView.setAdapter(fachAdapter);
        fach = Fach.faecherLaden();
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
