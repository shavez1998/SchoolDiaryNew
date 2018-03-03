package com.example.shavez.schooldiary;

import android.content.Intent;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        register = (Button) findViewById(R.id.r_register);
        cancel = (Button) findViewById(R.id.r_cancel);
        vorname = (EditText) findViewById(R.id.r_vorname);
        nachname = (EditText) findViewById(R.id.r_nachname);
        email = (EditText) findViewById(R.id.r_email);
        passwort = (EditText) findViewById(R.id.r_passwort);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Register.this, MainActivity.class);
                i.putExtra("vorname", vorname.getText().toString());
                i.putExtra("nachname", nachname.getText().toString());
                i.putExtra("email", email.getText().toString());
                i.putExtra("passwort", passwort.getText().toString());
                startActivity(i);
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Register.this,"Canceled", Toast.LENGTH_LONG).show();
                Intent i = new Intent(Register.this, MainActivity.class);
                startActivity(i);
            }
        });

    }
}
