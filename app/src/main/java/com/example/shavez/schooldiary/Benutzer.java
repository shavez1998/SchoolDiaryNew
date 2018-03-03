package com.example.shavez.schooldiary;

/**
 * Created by ateeq on 02/03/2018.
 */

public class Benutzer {

    int benutzer_id;
    String email;
    String password;
    String vorname;
    String nachname;

    //Konstruktoren
    public Benutzer(){
        //leer, vielleicht könntest du es brauchen irgendwie
    }

    public Benutzer(int benutzer_id, String email, String password, String vorname, String nachname){
        this.benutzer_id = benutzer_id;
        this.email = email;
        this.password = password;
        this.vorname = vorname;
        this.nachname = nachname;
    }

    public int getBenutzer_id() {
        return benutzer_id;
    }

    public void setBenutzer_id(int benutzer_id) {
        this.benutzer_id = benutzer_id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getVorname() {
        return vorname;
    }

    public void setVorname(String vorname) {
        this.vorname = vorname;
    }

    public String getNachname() {
        return nachname;
    }

    public void setNachname(String nachname) {
        this.nachname = nachname;
    }


}
