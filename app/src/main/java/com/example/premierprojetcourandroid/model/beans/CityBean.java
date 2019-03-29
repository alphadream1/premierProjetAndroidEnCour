package com.example.premierprojetcourandroid.model.beans;

public class CityBean {

    private String ville;
    private String cp;

    public CityBean() {
    }

    public CityBean(String ville, String cp) {
        this.ville = ville;
        this.cp = cp;
    }

    @Override
    public String toString() {
        return "ville = " + ville + ", cp = " + cp;
    }

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public String getCp() {
        return cp;
    }

    public void setCp(String cp) {
        this.cp = cp;
    }
}
