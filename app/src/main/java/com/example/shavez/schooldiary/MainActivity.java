package com.example.shavez.schooldiary;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        Toast.makeText(this,"HALLO SCHOOL PROJECT",Toast.LENGTH_LONG).show();
    }
}
