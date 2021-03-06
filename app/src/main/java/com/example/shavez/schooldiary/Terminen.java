package com.example.shavez.schooldiary;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.content.Intent;
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

import com.baoyz.widget.PullRefreshLayout;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import java.util.ArrayList;

import dmax.dialog.SpotsDialog;

import static android.support.v4.content.ContextCompat.startActivity;

public class Terminen extends AppCompatActivity {

    public ListView listView;
    public TerminAdapter terminAdapter;
    ArrayList<Termin> terminArr;
    public static Terminen terminen;
    AlertDialog dialog;
    Toolbar toolbar;
    MaterialSearchView searchView;
    PullRefreshLayout refreshLayout;
    Button save;
    public  int itemPos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terminen);
        setTitle("Terminen");
        terminen = this;
        listView = (ListView) findViewById(R.id.lvTermin);
        save = (Button) findViewById(R.id.addTermin);
        ArrayList<Termin> t = new ArrayList<>();
        terminAdapter = new TerminAdapter(this, t, this);
        listView.setAdapter(terminAdapter);
        Termin t1 = new Termin();
        t1.terminHolenArr(true);

        toolbar = (Toolbar) findViewById(R.id.toolbar_termin);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Termine");
        toolbar.setTitleTextColor(Color.parseColor("#FFFFFF"));

        searchView = (MaterialSearchView) findViewById(R.id.search_view);
        searchView.setOnSearchViewListener(new MaterialSearchView.SearchViewListener() {
            @Override
            public void onSearchViewShown() {

            }

            @Override
            public void onSearchViewClosed() {

                //If closed Search View , lvFach will return default
                listViewLaden(terminArr);

            }
        });
        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if(newText != null && !newText.isEmpty()){
                    ArrayList<Termin> terminFound = new ArrayList<>();
                    for (Termin item: terminArr){
                        newText = newText.toLowerCase();
                        String fach_name = item.getTermin_titel().toLowerCase();
                        if(fach_name.contains(newText)){
                            terminFound.add(item);
                        }
                    }
                    listViewLaden(terminFound);
                }
                else {
                    // default Array
                    listViewLaden(terminArr);
                }
                return  true;
            }
        });

        refreshLayout = (PullRefreshLayout) findViewById(R.id.swipeRefreshLayout);
        refreshLayout.setOnRefreshListener(new PullRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Toast.makeText(getApplicationContext(),"REFRESH GO",Toast.LENGTH_SHORT).show();
                new Termin().terminHolenArr(false);
                refreshLayout.setRefreshing(false);

            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(Terminen.this, Termine_Add.class);
                startActivity(i);
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Intent i = new Intent(Terminen.this,Termine_Info.class);
                i.putExtra("titel", terminAdapter.getItem(position).getTermin_titel());
                i.putExtra("datum", terminAdapter.getItem(position).getTermin_datum());
                i.putExtra("beschreibung", terminAdapter.getItem(position).getTermin_beschreibung());
                startActivity(i);

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
        listViewLaden(terminArr);
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

    public void datenGespeichert(){
        terminAdapter.remove(terminen.terminAdapter.getItem(itemPos));
        proOff();
    }
    public void showError(){
        proOff();
        showMessageError("ERROR","Internet verbindungs fehler");
    }
    public void showMessage(String title, String message){
        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton("Neuladen", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                new Termin().terminHolenArr(true);
            }
        });
        builder.show();
    }
    public  void showMessageError(String title, String message){
        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.show();
    }
}