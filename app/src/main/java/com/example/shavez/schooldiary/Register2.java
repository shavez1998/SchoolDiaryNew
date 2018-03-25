package com.example.shavez.schooldiary;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class Register2 extends AppCompatActivity {

    public Spinner frage1, frage2;
    public EditText antwort1, antwort2;
    String vorname, nachname, email, passwort;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register2);
        frage1 = (Spinner) findViewById(R.id.frage1);
        frage2 = (Spinner) findViewById(R.id.frage2);
        antwort1 = (EditText) findViewById(R.id.antwort1);
        antwort2 = (EditText) findViewById(R.id.antwort2);
        frage1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                TextView text = (TextView) view;
                Toast.makeText(getApplicationContext(),"S "+text.toString(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //Another interface callback
            }
        });
        frage2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                TextView text = (TextView) view;
                Toast.makeText(getApplicationContext(),"S "+text.toString(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //Another interface callback
            }
        });
        Intent i = getIntent();
        vorname = i.getStringExtra("vorname");
        nachname = i.getStringExtra("nachname");
        email = i.getStringExtra("email");
        passwort = i.getStringExtra("passwort");
        String[] fragen = new String[5];
        fragen[0] = "Your Name?";
        fragen[1] = "Your DAD Name?";
        fragen[2] = "Your Hobby?";
        fragen[3] = "Your Phone number?";
        fragen[4] = "Your MOM name?";

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, fragen );
        frage1.setAdapter(adapter);
        frage2.setAdapter(adapter);
    }

}
