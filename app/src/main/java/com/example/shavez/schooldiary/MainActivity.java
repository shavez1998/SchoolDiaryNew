package com.example.shavez.schooldiary;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONArray;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    public static Benutzer benutzer;
    DataSource dataSource;
    Button login;
    Button register;
    EditText emailText;
    EditText passwortText;
    public static int USER_ID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dataSource = new DataSource(this);
        benutzer = new Benutzer();

        login = (Button) findViewById(R.id.login);
        register = (Button) findViewById(R.id.register);
        emailText = (EditText) findViewById(R.id.email);
        passwortText = (EditText) findViewById(R.id.passwort);

        register.setOnClickListener(new View.OnClickListener() {
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


                Intent i = new Intent(MainActivity.this, Register.class);
                startActivity(i);
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean nichtAusgefuhlt = true;
                if(!emailText.getText().toString().isEmpty())
                    if(!passwortText.getText().toString().isEmpty())
                                nichtAusgefuhlt = false;

                if(nichtAusgefuhlt){
                    showMessage("ERROR","Bitte alle Feldern Ausfühlen");
                } else {

                    Client client = new Client();
                    String url = "f=checkLogin"+"&e="+emailText.getText().toString()+"&p="+passwortText.getText().toString();
                    client.getDaten("benutzer",url, new JsonHttpResponseHandler(){
                        @Override
                        public void onSuccess(int stausCode, cz.msebera.android.httpclient.Header[] headers, JSONObject response){
                            try{
                                boolean login = true;
                                JSONArray arr;
                                if(response != null){
                                    arr = response.getJSONArray("daten");
                                    JSONObject data = arr.getJSONObject(0);
                                    if(data.has("id")){
                                        int id = Integer.parseInt(data.getString("id"));
                                        benutzer.setBenutzer_id(id);
                                    }
                                    if(data.has("vorname")){
                                        benutzer.setVorname(data.getString("vorname"));
                                    }
                                    if(data.has("nachname")){
                                        benutzer.setNachname(data.getString("nachname"));
                                    }
                                    if(data.has("email")){
                                        benutzer.setEmail(data.getString("email"));
                                    }
                                    if(data.has("passwort")){
                                        benutzer.setPassword(data.getString("passwort"));
                                    }
                                    if(data.has("frage")){
                                        benutzer.setFrage(data.getString("frage"));
                                    }
                                    if(data.has("frage2")){
                                        benutzer.setFrage2(data.getString("frage2"));
                                    }
                                    if(data.has("antwort")){
                                        benutzer.setAntwort(data.getString("antwort"));
                                    }
                                    if(data.has("antwort2")){
                                        benutzer.setAntwort2(data.getString("antwort2"));
                                    }
                                    if(data.has("error")){
                                        showMessage("ERROR","Email oder Passwort falsch!!");
                                        passwortText.setText("");
                                        login = false;
                                    }
                                }
                                if(login) {
                                    Toast.makeText(getApplicationContext(), response.toString(), Toast.LENGTH_LONG).show();
                                    Intent i = new Intent(MainActivity.this, Menu.class);
                                    startActivity(i);
                                }
                            } catch (Exception e){
                                Toast.makeText(getApplicationContext(), "ERROR", Toast.LENGTH_LONG).show();
                            }
                        }
                    });

                }


                //ATEEQ QUERY : EMAIL UND PASSWORD ÜBERPRÜFEN, USER_ID SPEICHERN
            }
        });

    }
    public  void showMessage(String title, String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
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

    public void getDataBenutzer(Cursor cursor){
        boolean login = false;
        String[][] data = new String[cursor.getCount()][5];
        int idIndex = cursor.getColumnIndex(DatabaseHelper.COL_ID_BENUTZER);
        int vornameIndex = cursor.getColumnIndex(DatabaseHelper.COL_VORNAME);
        int nachnameIndex = cursor.getColumnIndex(DatabaseHelper.COL_NACHNAME);
        //int emailIndex = cursor.getColumnIndex(DatabaseHelper.COL_EMAIL);
        //int passwortIndex = cursor.getColumnIndex(DatabaseHelper.COL_PASSWORT);

        if (cursor.getCount() == 0) {
            showMessage("ERROR", "No Data found");
            Toast.makeText(MainActivity.this,"NO DATA found", Toast.LENGTH_LONG).show();
           // return;
        }
        else {
            StringBuffer buffer = new StringBuffer();

            while (cursor.moveToNext()) {
                try {
                    //if (emailText.getText().toString().equals(cursor.getString(emailIndex)) && passwortText.getText().toString().equals(cursor.getString(passwortIndex)))
                        login = true;
                } catch (Exception e){}
                buffer.append("Id : " + cursor.getString(idIndex) + "\n");
                buffer.append("Name : " + cursor.getString(vornameIndex) + "\n");
                buffer.append("Surname : " + cursor.getString(nachnameIndex) + "\n");
                //buffer.append("Email : " + cursor.getString(emailIndex) + "\n");
                //buffer.append("Passwort : " + cursor.getString(passwortIndex) + "\n\n");
            }
            if(login)
                showMessage("Data", buffer.toString());
            else
                showMessage("Data", "FAILED");
            dataSource.close();
        }
    }




}
