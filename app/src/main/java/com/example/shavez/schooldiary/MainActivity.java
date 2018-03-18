package com.example.shavez.schooldiary;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    DataSource dataSource;
    Button login;
    Button register;
    EditText emailText;
    EditText passwortText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dataSource = new DataSource(this);

        login = (Button) findViewById(R.id.login);
        register = (Button) findViewById(R.id.register);
        emailText = (EditText) findViewById(R.id.email);
        passwortText = (EditText) findViewById(R.id.passwort);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dataSource.open();
                Intent i = new Intent(MainActivity.this, Faecher.class);
                startActivity(i);
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDataBenutzer(dataSource.getDataBenutzer());
            }
        });

        try {
            Intent intent = getIntent();
            Bundle bundle = intent.getExtras();
            dataSource.open();
            insertDataBenutzer(bundle.get("vorname").toString(),bundle.get("nachname").toString(),
                    bundle.get("email").toString(),bundle.get("passwort").toString());
            dataSource.close();
        } catch (Exception e){

        }
    }
    public  void showMessage(String title, String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
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

    public void insertDataBenutzer(String vorname,String nachname, String email, String passwort){
        dataSource.insertDataBenutzer(vorname,nachname, email,passwort);
        Toast.makeText(this,"Registered", Toast.LENGTH_LONG).show();
    }
}
