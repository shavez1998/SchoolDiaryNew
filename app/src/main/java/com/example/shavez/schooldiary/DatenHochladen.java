package com.example.shavez.schooldiary;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.json.JSONObject;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

public class DatenHochladen extends AsyncTask<JSONObject, JSONObject, JSONObject> {

    String url ="";
    String obj;
    String r = "";
    public DatenHochladen(String file_name, String obj_name){
       this.url = "https://gamifygames.000webhostapp.com/SchoolDiary/"+file_name+".php";
       obj = obj_name;
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
    public String getRespone(){
        return r;
    }
}