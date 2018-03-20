package com.example.shavez.schooldiary;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class Register2 extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    public Spinner frage1, frage2;
    public EditText antwort1, antwort2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register2);
        frage1 = (Spinner) findViewById(R.id.frage1);
        frage2 = (Spinner) findViewById(R.id.frage2);
        antwort1 = (EditText) findViewById(R.id.antwort1);
        antwort2 = (EditText) findViewById(R.id.antwort2);
        frage1.setOnItemSelectedListener(this);
        frage2.setOnItemSelectedListener(this);
        String[] fragen = new String[5];
        fragen[0] = "Your Name?";
        fragen[1] = "Your DAD Name?";
        fragen[2] = "Your Hobby?";
        fragen[3] = "Your Phone number?";
        fragen[0] = "Your MOM name?";

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, fragen );
        frage1.setAdapter(adapter);
        frage2.setAdapter(adapter);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        TextView text = (TextView) view;
        Toast.makeText(this,"S "+text.toString(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
