package com.example.shavez.schooldiary;

import android.app.AlertDialog;
import android.content.DialogInterface;
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
import com.baoyz.widget.PullRefreshLayout;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import dmax.dialog.SpotsDialog;

public class Faecher extends AppCompatActivity {

    public ListView listView;
    public  Button addFach;
    EditText fachname;
    public FachAdapter fachAdapter;
    public static  ArrayList<Fach> fachArr = new ArrayList<>();
    public static Faecher faecher;
    public int itemPos;
    Toolbar toolbar;
    MaterialSearchView searchView;
    PullRefreshLayout refreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faecher);
        listView = (ListView) findViewById(R.id.lvFach);
        addFach = (Button) findViewById(R.id.addFach);
        faecher = this;
        fachAdapter = new FachAdapter(this, fachArr, this);
        listView.setAdapter(fachAdapter);

        Fach fach= new Fach();
        new Fach().faecherHolenArr(true);
        //fachArr = Fach.faecherLaden();
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

        refreshLayout = (PullRefreshLayout) findViewById(R.id.swipeRefreshLayout);
        refreshLayout.setOnRefreshListener(new PullRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Toast.makeText(getApplicationContext(),"REFRESH GO",Toast.LENGTH_SHORT).show();
                new Fach().faecherHolenArr(false);


            }
        });


        addFach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Faecher.this, Fach_Add.class);
                startActivity(i);
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
    AlertDialog dialog;
    public void proOn(){
        dialog = new SpotsDialog(this, "Loading");
        dialog.show();
    }
    public void proOff(){
        dialog.dismiss();
    }


    public void datenGespeichert(){
        fachAdapter.remove(faecher.fachAdapter.getItem(itemPos));
        proOff();
    }
    public void showError(){
        proOff();
        showMessageError("ERROR","Internet verbindungs fehler");
    }
    public  void showMessage(String title, String message){
        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton("Neuladen", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                new Fach().faecherHolenArr(true);
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
