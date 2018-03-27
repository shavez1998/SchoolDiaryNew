package com.example.shavez.schooldiary;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class Register2 extends AppCompatActivity {

    public Spinner frage1, frage2;
    public EditText antwort1, antwort2;
    Button back, register;
    String vorname, nachname, email, passwort;
    String auswahl1, auswahl2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register2);
        frage1 = (Spinner) findViewById(R.id.frage1);
        frage2 = (Spinner) findViewById(R.id.frage2);
        antwort1 = (EditText) findViewById(R.id.antwort1);
        antwort2 = (EditText) findViewById(R.id.antwort2);
        back = (Button) findViewById(R.id.r_back);
        register = (Button) findViewById(R.id.r_register);
        frage1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                TextView text = (TextView) view;
                auswahl1 = text.getText().toString();
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
                auswahl2 = text.getText().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //Another interface callback
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*try {
                    JSONObject json = new JSONObject();
                    json.put("vorname", "Ali");
                    json.put("nachname", "Shan");
                    json.put("frage", "Wem lieben Sie?");
                    json.put("antwort", "Waheed");
                    json.put("email", "alishan@yahoo.com");
                    json.put("passwort", "AliShan");
                    json.put("frage2", "Wo ist ihr Vater geboren?");
                    json.put("antwort2", "Val di Funes");
                    DatenHochladen t = new DatenHochladen("registerReceive");
                    t.execute(new JSONObject[]{json});
                }catch (Exception e){

                }
                */
                finish();

            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    JSONObject json = new JSONObject();
                    json.put("vorname", vorname);
                    json.put("nachname", nachname);
                    json.put("frage", auswahl1);
                    json.put("antwort", antwort1.getText().toString());
                    json.put("email", email);
                    json.put("passwort", passwort);
                    json.put("frage2", auswahl2);
                    json.put("antwort2", antwort2.getText().toString());
                    DatenHochladen t = new DatenHochladen("registerReceive");
                    t.execute(new JSONObject[]{json});
                }catch (Exception e){

                }

                Intent i = new Intent(Register2.this, MainActivity.class);
                startActivity(i);
            }
        });
        Intent i = getIntent();
        vorname = i.getStringExtra("vorname");
        nachname = i.getStringExtra("nachname");
        email = i.getStringExtra("email");
        passwort = i.getStringExtra("passwort");
        getFragen();
    }

    public void getFragen(){
        final ArrayList<String> frage_arr = new ArrayList<String>();
        final ArrayList<String> frage_arr1 = new ArrayList<String>();
        Client client = new Client();
        String url = "f=getFragen";
        client.getDaten("fragen",url, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int stausCode, cz.msebera.android.httpclient.Header[] headers, JSONObject response){
                try{
                    JSONArray arr;
                    if(response != null){
                        arr = response.getJSONArray("daten");
                        for(int i = 0; i < arr.length();i++){
                            JSONObject data = arr.getJSONObject(i);
                            if(data.has("frage")){
                                if(i%2==0)
                                    frage_arr.add(data.getString("frage"));
                                else
                                    frage_arr1.add(data.getString("frage"));
                            }
                        }
                    }
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(),
                            android.R.layout.simple_spinner_item, frage_arr);
                    ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(getApplicationContext(),
                            android.R.layout.simple_spinner_item, frage_arr1);
                    frage1.setAdapter(adapter);
                    frage2.setAdapter(adapter1);

                } catch (Exception e){
                    Toast.makeText(getApplicationContext(), "ERROR", Toast.LENGTH_LONG).show();
                }
            }
        });
    }


}
