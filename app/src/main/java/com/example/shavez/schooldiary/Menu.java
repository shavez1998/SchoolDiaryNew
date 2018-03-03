package com.example.shavez.schooldiary;

import android.database.Cursor;
import android.icu.text.UnicodeSetSpanner;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Menu extends AppCompatActivity {
    DataSource dataSource;
    Button login;
    EditText emailText;
    EditText passwortText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        dataSource.open();
        login = (Button) findViewById(R.id.login);
        emailText = (EditText) findViewById(R.id.email);
        passwortText = (EditText) findViewById(R.id.passwort);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Menu.this,"BUTTON CLICKED", Toast.LENGTH_LONG).show();
                //getDataBenutzer(dataSource.getDataBenutzer());
                //insertDataBenutzer();
            }
        });
    }
    public  void showMessage(String title, String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }

    public void getDataBenutzer(Cursor cursor){
        int idIndex = cursor.getColumnIndex(DatabaseHelper.COL_ID_BENUTZER);
        int vornameIndex = cursor.getColumnIndex(DatabaseHelper.COL_VORNAME);
        int nachnameIndex = cursor.getColumnIndex(DatabaseHelper.COL_ID_BENUTZER);
        int emailIndex = cursor.getColumnIndex(DatabaseHelper.COL_EMAIL);
        int passwortIndex = cursor.getColumnIndex(DatabaseHelper.COL_PASSWORT);
        if (cursor.getCount() == 0) {
            showMessage("ERROR", "No Data found");
            return;
        }

        StringBuffer buffer = new StringBuffer();
        while (cursor.moveToNext()) {
            buffer.append("Id : "+ cursor.getInt(idIndex)+"\n");
            buffer.append("Name : "+ cursor.getString(vornameIndex)+"\n");
            buffer.append("Surname : "+ cursor.getString(nachnameIndex)+"\n");
            buffer.append("Email : "+ cursor.getString(emailIndex)+"\n\n");
            buffer.append("Passwort : "+ cursor.getString(passwortIndex)+"\n\n");
        }
        showMessage("Data", buffer.toString());
    }

    public void insertDataBenutzer(){
        dataSource.insertDataBenutzer("Shavez","Shakeel", emailText.getText().toString(),passwortText.getText().toString());
        Toast.makeText(this,"DATA ISERTED", Toast.LENGTH_LONG).show();
    }
}
