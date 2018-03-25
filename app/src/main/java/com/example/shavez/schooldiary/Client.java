package com.example.shavez.schooldiary;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import java.net.URLEncoder;

/**
 * Created by Dangerous on 25/03/2018.
 */

public class Client {

    private AsyncHttpClient client;
    public  Client(){
        client = new AsyncHttpClient();
    }

    public void getDaten(String query, JsonHttpResponseHandler handler){
        try{
            String url = "https://gamifygames.000webhostapp.com/SchoolDiary/benutzer.php?userEmail="+ query;
            client.get(url , handler);
        }catch (Exception e){

        }
    }
}
