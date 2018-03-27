package com.example.shavez.schooldiary;

/**
 * Created by Dangerous on 27/03/2018.
 */

public class Fragen {


    int frage_id;
    String frage;
    public Fragen(int id, String frage){
        this.frage = frage;
        this.frage_id = id;
    }

    public int getFrge_id() {
        return frage_id;
    }

    public void setFrage_id(int frage_id) {
        this.frage_id = frage_id;
    }

    public String getFrage() {
        return frage;
    }

    public void setFrage(String frage) {
        this.frage = frage;
    }
}
