package com.example.shavez.schooldiary;

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
    EditText emailText;
    EditText passwortText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dataSource = new DataSource(this);
        dataSource.open();
        login = (Button) findViewById(R.id.login);
        emailText = (EditText) findViewById(R.id.email);
        passwortText = (EditText) findViewById(R.id.passwort);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getDataBenutzer(dataSource.getDataBenutzer());
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
        int nachnameIndex = cursor.getColumnIndex(DatabaseHelper.COL_NACHNAME);
        int emailIndex = cursor.getColumnIndex(DatabaseHelper.COL_EMAIL);
        int passwortIndex = cursor.getColumnIndex(DatabaseHelper.COL_PASSWORT);

        if (cursor.getCount() == 0) {
            showMessage("ERROR", "No Data found");
            Toast.makeText(MainActivity.this,"NO DATA found", Toast.LENGTH_LONG).show();
           // return;
        }
        else {
            Toast.makeText(this, "YYYYY   " + cursor.getColumnName(nachnameIndex), Toast.LENGTH_LONG).show();
            //showMessage("ERROR", "No Data found");
            StringBuffer buffer = new StringBuffer();

            while (cursor.moveToNext()) {

                buffer.append("Id : " + cursor.getString(idIndex) + "\n");
                buffer.append("Name : " + cursor.getString(vornameIndex) + "\n");
                buffer.append("Surname : " + cursor.getString(nachnameIndex) + "\n");
                buffer.append("Email : " + cursor.getString(emailIndex) + "\n");
                buffer.append("Passwort : " + cursor.getString(passwortIndex) + "\n\n");

            }
            showMessage("Data", buffer.toString());

        }
    }

    public void insertDataBenutzer(){
        dataSource.insertDataBenutzer("Shavez","Shakeel", emailText.getText().toString(),passwortText.getText().toString());
        Toast.makeText(this,"DATA ISERTED", Toast.LENGTH_LONG).show();
    }
}
