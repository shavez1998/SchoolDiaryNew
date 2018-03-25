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
import android.widget.TextView;
import android.widget.Toast;

public class Register extends AppCompatActivity {

    EditText vorname,nachname,email,passwort;
    Button register,cancel;
    DataSource dataSource;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        register = (Button) findViewById(R.id.r_register_next);
        cancel = (Button) findViewById(R.id.r_cancel);
        vorname = (EditText) findViewById(R.id.r_vorname);
        nachname = (EditText) findViewById(R.id.r_nachname);
        email = (EditText) findViewById(R.id.r_email);
        passwort = (EditText) findViewById(R.id.r_passwort);
        dataSource = new DataSource(this);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean nichtAusgefuhlt = true;
                if(!vorname.getText().toString().isEmpty())
                    if(!nachname.getText().toString().isEmpty())
                        if(!email.getText().toString().isEmpty())
                            if(!passwort.getText().toString().isEmpty())
                                nichtAusgefuhlt = false;

                if(nichtAusgefuhlt){
                    showMessage("ERROR","Bitte alle Feldern Ausfühlen");
                } else {
                    Intent i = new Intent(Register.this, Register2.class);
                    i.putExtra("vorname",vorname.getText().toString());
                    i.putExtra("nachname",nachname.getText().toString());
                    i.putExtra("email",email.getText().toString());
                    i.putExtra("passwort",passwort.getText().toString());
                    startActivity(i);
                }

                // ATEEQ QUERY : -ÜBERPRÜFEN OB DIE GEGEBENE EMAIL VORHANDEN IST, WENN JA FEHLERMELDUNG
                //               -WENN ALLES PASST; DATEN IN DATENBANK SPEICHERN


                /*
                dataSource.open();
                boolean emailUsed = checkIfEmailUsed(dataSource.getDataBenutzer());
                dataSource.close();
                if(!emailUsed){
                    Toast.makeText(Register.this,"Registered", Toast.LENGTH_LONG).show();
                } else{
                    Toast.makeText(Register.this,"Email Used", Toast.LENGTH_LONG).show();
                }
                */
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Register.this,"Canceled", Toast.LENGTH_LONG).show();
                Intent i = new Intent(Register.this, MainActivity.class);
                startActivity(i);
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
            }
        });

    }
    public boolean checkIfEmailUsed(Cursor cursor){
        boolean emailUsed = false;
        int emailIndex = cursor.getColumnIndex(DatabaseHelper.COL_EMAIL_LOGIN);
        if (cursor.getCount() == 0) {
            showMessage("ERROR", "No Data found");
        }
        else {
            while (cursor.moveToNext()) {
                final String emailDB = cursor.getString(emailIndex).toLowerCase().toString();
                if(email.getText().toString() == emailDB){
                    showMessage("ERROR", "EMAIL USED");
                    emailUsed = true;
                }
            }
        }
        return emailUsed;
    }
    public void insertDataBenutzer(String vorname,String nachname, String email, String passwort){
        dataSource.insertDataBenutzer(vorname,nachname, email,passwort);
        Toast.makeText(this,"Registered", Toast.LENGTH_LONG).show();
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
}
