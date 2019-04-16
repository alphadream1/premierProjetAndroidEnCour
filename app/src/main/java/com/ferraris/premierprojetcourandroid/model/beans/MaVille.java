package com.ferraris.premierprojetcourandroid.model.beans;

import android.media.tv.TvContract;

import com.google.android.gms.maps.model.LatLng;

public class MaVille {
    private String nom;
    private LatLng position;
    private TvContract.Channels.Logo logo;

    public TvContract.Channels.Logo getLogo() {
        return logo;
    }

    public void setLogo(TvContract.Channels.Logo logo) {
        this.logo = logo;
    }

    public MaVille(String nom, LatLng position) {
        this.nom = nom;
        this.position = position;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public LatLng getPosition() {
        return position;
    }

    public void setPosition(LatLng position) {
        this.position = position;
    }
}
