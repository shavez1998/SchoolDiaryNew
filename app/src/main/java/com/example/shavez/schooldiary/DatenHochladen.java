package com.example.shavez.schooldiary;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

public class DatenHochladen extends AsyncTask<JSONObject, JSONObject, JSONObject> {

    String url ="";
    String obj;
    Activity activity;
    String klasse = "";

    public String r = "nothing";
    public DatenHochladen(String file_name, String obj_name,String kl, Activity ac){
       this.url = "https://gamifygames.000webhostapp.com/SchoolDiary/"+file_name+".php";
       obj = obj_name;
       klasse = kl;
       activity = ac;
    }
    @Override
    protected JSONObject doInBackground(JSONObject... data) {
        JSONObject json = data[0];
        HttpClient client = new DefaultHttpClient();
        HttpConnectionParams.setConnectionTimeout(client.getParams(), 100000);

        JSONObject jsonResponse = null;
        HttpPost post = new HttpPost(url);
        try {
            Log.w("JSON", json.toString());
            StringEntity se = new StringEntity(obj+"="+json.toString());
            post.addHeader("content-type", "application/x-www-form-urlencoded");
            post.setEntity(se);

            HttpResponse response;
            response = client.execute(post);
            String resFromServer = org.apache.http.util.EntityUtils.toString(response.getEntity());
            r = resFromServer;
            Log.w("JSONRES", r);
        } catch (Exception e) {
            Log.w("JSON1", r);
            e.printStackTrace();
        }

        return jsonResponse;
    }
    @Override
    protected void onPostExecute(JSONObject result){
        if(r.equals("nothing"))
            error();
        else
            saved();
    }

    private void error(){
        switch (klasse){
            case "reg2":
                ((Register2) activity).showError();
                break;
            case "faecher":
                ((Faecher) activity).showError();
                break;
            case "noten":
                ((Noten) activity).showError();
                break;
            case "notenadd":
                ((Noten_Add) activity).showError();
                break;
            case "notenedit":
                ((Noten_Edit) activity).showError();
                break;
            case "terminen":
                ((Terminen) activity).showError();
                break;
            case "terminenadd":
                ((Termine_Add) activity).showError();
                break;
            case "terminenedit":
                ((Termine_Edit) activity).showError();
                break;
            case "fachadd":
                ((Fach_Add) activity).showError();
                break;
            case "passwordreset":
                ((PasswordReset) activity).showError();
                break;
        }
    }
    private void saved(){
        switch (klasse){
            case "reg2":
                ((Register2) activity).datenGespeichert();
                break;
            case "faecher":
                ((Faecher) activity).datenGespeichert();
                break;
            case "noten":
                ((Noten) activity).datenGespeichert();
                break;
            case "notenadd":
                ((Noten_Add) activity).datenGespeichert();
                break;
            case "notenedit":
                ((Noten_Edit) activity).datenGespeichert();
                break;
            case "termien":
                ((Terminen) activity).datenGespeichert();
                break;
            case "termienadd":
                ((Termine_Add) activity).datenGespeichert();
                break;
            case "termienedit":
                ((Termine_Edit) activity).datenGespeichert();
                break;
            case "fachadd":
                ((Fach_Add) activity).datenGespeichert();
                break;
            case "passwordreset":
                ((PasswordReset) activity).datenGespeichert();
                break;
        }
    }
}