package com.example.shavez.schooldiary;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentProviderOperation;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.ContactsContract;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager.LayoutParams;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

public class TestJSON extends Activity {
    /** Called when the activity is first created. */
    public int iLanguage = 0;
    TextView lbl;
    Typeface arabicFont = null;
    int TIMEOUT_MILLISEC = 10000; // = 10 seconds

    @Override
    public void onCreate(Bundle savedInstanceState) {
        try {

            super.onCreate(savedInstanceState);
            //    setContentView(R.layout.main);
            getWindow().setLayout(LayoutParams.FILL_PARENT,
                    LayoutParams.FILL_PARENT);
            // ////
            arabicFont = Typeface.createFromAsset(getAssets(),
                    "fonts/DroidSansArabic.ttf");
            //lbl = (TextView) findViewById(R.id.lbl);
            //

        } catch (Throwable t) {
            Toast.makeText(this, "Request failed: " + t.toString(),
                    Toast.LENGTH_LONG).show();
        }
    }

    public  void clickbuttonRecieve(View v) {
        try {
            JSONObject json = new JSONObject();
            json.put("vorname", "Ali");
            json.put("nachname", "Shan");
            json.put("frage", "Wem lieben Sie?");
            json.put("antwort", "Waheed");
            json.put("email", "alishan@yahoo.com");
            json.put("passwort", "AliShan");
            json.put("frage2", "Wo ist ihr Vater geboren?");
            json.put("antwort2", "Val di Funes");
            HttpParams httpParams = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(httpParams,
                    TIMEOUT_MILLISEC);
            HttpConnectionParams.setSoTimeout(httpParams, TIMEOUT_MILLISEC);
            HttpClient client = new DefaultHttpClient(httpParams);
            //
            //String url = "http://10.0.2.2:8080/sample1/webservice2.php?json={\"UserName\":1,\"FullName\":2}";
            String url = "https://gamifygames.000webhostapp.com/SchoolDiary/registerReceive.php";

            HttpPost request = new HttpPost(url);
            request.setEntity(new ByteArrayEntity(json.toString().getBytes(
                    "UTF8")));
            request.setHeader("daten", json.toString());
            HttpResponse response = client.execute(request);
            HttpEntity entity = response.getEntity();

        } catch (Throwable t) {

        }
    }

    public void clickbutton(View v) {
        try {
            // http://androidarabia.net/quran4android/phpserver/connecttoserver.php

            // Log.i(getClass().getSimpleName(), "send  task - start");
            HttpParams httpParams = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(httpParams,
                    TIMEOUT_MILLISEC);
            HttpConnectionParams.setSoTimeout(httpParams, TIMEOUT_MILLISEC);
            //
            HttpParams p = new BasicHttpParams();
            // p.setParameter("name", pvo.getName());
            p.setParameter("userEmail", "shavez123@gmail");

            // Instantiate an HttpClient
            HttpClient httpclient = new DefaultHttpClient(p);
            String url = "http://10.0.2.2:8080/sample1/webservice1.php?user=1&format=json";
            String url1 = "https://gamifygames.000webhostapp.com/SchoolDiary/benutzer.php?userEmail=shavez123@gmail.com";
            HttpPost httppost = new HttpPost(url1);

            // Instantiate a GET HTTP method
            try {
                Log.i(getClass().getSimpleName(), "send  task - start");
                //
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(
                        2);
                nameValuePairs.add(new BasicNameValuePair("userEmail", "shavez123@gmail.com"));
                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                Log.i(getClass().getSimpleName(), "send  task - start    ALL OK1");
                ResponseHandler<String> responseHandler = new BasicResponseHandler();
                String responseBody = httpclient.execute(httppost,
                        responseHandler);
                Log.i(getClass().getSimpleName(), "send  task - start    ALL OK2");
                // Parse
                JSONObject json = new JSONObject(responseBody);
                JSONArray jArray = json.getJSONArray("daten");
                ArrayList<HashMap<String, String>> mylist = new ArrayList<HashMap<String, String>>();

                for (int i = 0; i < jArray.length(); i++) {
                    HashMap<String, String> map = new HashMap<String, String>();
                    JSONObject e = jArray.getJSONObject(i);
                    String s = e.getString("vorname");
                    JSONObject jObject = new JSONObject(s);
                    /*
                    map.put("idusers", jObject.getString("idusers"));
                    map.put("UserName", jObject.getString("UserName"));
                    map.put("FullName", jObject.getString("FullName"));
                    */
                    mylist.add(map);
                }
                Toast.makeText(this, responseBody, Toast.LENGTH_LONG).show();

            } catch (ClientProtocolException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            // Log.i(getClass().getSimpleName(), "send  task - end");

        } catch (Throwable t) {
            Toast.makeText(this, "Request failed: " + t.toString(),
                    Toast.LENGTH_LONG).show();
        }

    }

    public class Data {
        // private List<User> users;
        public List<User> users;

        // +getters/setters
    }

    static class User {
        String idusers;
        String UserName;
        String FullName;

        public String getUserName() {
            return UserName;
        }

        public String getidusers() {
            return idusers;
        }

        public String getFullName() {
            return FullName;
        }

        public void setUserName(String value) {
            UserName = value;
        }

        public void setidusers(String value) {
            idusers = value;
        }

        public void setFullName(String value) {
            FullName = value;
        }
    }
}
