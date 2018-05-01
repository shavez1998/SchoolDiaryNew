package com.example.shavez.schooldiary;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import dmax.dialog.SpotsDialog;

public class Fach_Add extends AppCompatActivity {

    CheckBox fachCheck;
    String fachAdd;
    TextView fachView;
    TextView checkView;
    Spinner fachSpinner;
    EditText fachEdit;
    Button fachButton;
    Toolbar fachToolbar;
    String[] faecher;
    AlertDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fach__add);

        fachCheck = (CheckBox) findViewById(R.id.fachCheck);
        fachView = (TextView) findViewById(R.id.fachView);
        checkView = (TextView) findViewById(R.id.checkView);
        fachSpinner = (Spinner) findViewById(R.id.fachSpinner);
        fachEdit = (EditText) findViewById(R.id.fachEdit);
        fachButton = (Button) findViewById(R.id.fachButton);
        fachToolbar = (Toolbar) findViewById(R.id.fachToolbar);

        fachToolbar.setTitle("Fach Hinzufügen");
        fachToolbar.setTitleTextColor(Color.WHITE);

        fachEdit.setVisibility(View.INVISIBLE);

        String url = "getFachEx";
        Client client = new Client();
        client.getDaten("faecher", url, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int stausCode, cz.msebera.android.httpclient.Header[] headers, JSONObject response) {
                try {
                    JSONArray arr;
                    if (response != null) {

                        arr = response.getJSONArray("daten");
                        int count = Integer.parseInt(response.getJSONObject("countArr").getString("count"));

                        Log.e("intcount", count+"");

                        faecher = new String[count];

                        for(int i = 0; i<count; i++){
                            JSONObject data = arr.getJSONObject(i);
                            faecher[i] = data.getString("fach");

                        }
                    }
                } catch (Exception ex) {
                    Log.e("FachError", ex.toString());
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(),
                        android.R.layout.simple_spinner_item, faecher);
                fachSpinner.setAdapter(adapter);
            }
        });

        fachCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(fachCheck.isChecked()){
                    fachSpinner.setVisibility(View.INVISIBLE);
                    fachEdit.setVisibility(View.VISIBLE);


                }else{
                    fachSpinner.setVisibility(View.VISIBLE);
                    fachEdit.setVisibility(View.INVISIBLE);

                }

            }
        });

        fachButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(fachCheck.isChecked()){
                    fachAdd = fachEdit.getText().toString();

                    try {
                        // proOn();
                        JSONObject json = new JSONObject();
                        json.put("fachname", fachAdd);
                        json.put("uid", MainActivity.USERID);
                        DatenHochladen t = new DatenHochladen("faecher","addFach","fachadd",Fach_Add.this);
                        t.execute(new JSONObject[]{json});
                        //proOff();
                    } catch (Exception e){ }
                }else{
                    try {
                         proOn();
                        JSONObject json = new JSONObject();
                        json.put("fachname", fachAdd);
                        json.put("uid", MainActivity.USERID);
                        DatenHochladen t = new DatenHochladen("faecher", "addFachEx","fachadd",Fach_Add.this);
                        t.execute(new JSONObject[]{json});
                        Log.e("Fach", fachAdd);
                    }catch (Exception ex){}
                }
            }
        });
        fachSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                TextView text = (TextView) view;
                fachAdd = text.getText().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //Another interface callback
            }
        });

    }
    public void proOn(){
        dialog = new SpotsDialog(this, "Loading");
        dialog.show();
    }
    public void proOff(){
        dialog.dismiss();
    }

    public void datenGespeichert(){

        new Fach().faecherHolenArr(false);
        proOff();
        finish();
    }
    public void showError(){
        proOff();
        showMessageError("ERROR","Internet verbindungs fehler");
    }
    public  void showMessageError(String title, String message){
        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(this);
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
