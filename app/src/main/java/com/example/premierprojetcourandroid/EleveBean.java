package com.example.premierprojetcourandroid;

public class EleveBean {

    String nom;
    String prenom;

    public EleveBean(String nom, String prenom) {
        this.nom = nom;
        this.prenom = prenom;
    }

    public String getText() {
        String res = getNom() + " " + getPrenom();
        return res;
    }
    //---------------------
    //GETTER AND SETTER
    //---------------------

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }
}
