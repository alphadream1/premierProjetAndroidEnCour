package com.example.velostation;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.velostation.bean.StationBean;
import com.example.velostation.utils.WsUtils;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.InfoWindowAdapter {

    private GoogleMap mMap;
    private MonAT monAT;
    private ArrayList<StationBean> data;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);

        // instanciation de la liste
        data = new ArrayList<>();

        // lancement de l'asynctask
        mapFragment.getMapAsync(this);


        monAT = new MonAT();
        monAT.execute();

        // on verifie que l'on a bien l'autorisation de localisation, si on ne l'a pas on l'a demande
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 0);
        }


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        refreshMap();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        refreshMap();
    }

    public void refreshMap() {
        // verification que la carte est crée
        if (mMap == null) {
            return;
        }
        // on verifie que l'on a bien l'autorisation de localisation, si on ne l'a pas on l'a demande
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);
        }

        //nettoyage de la carte
        mMap.clear();
        //Créer le build pour l'animate camera
        LatLngBounds.Builder latLngBounds = new LatLngBounds.Builder();
        int nbStationAffiche = 0;
        // affichage des markers
        for (StationBean temp : data) {
            //Log.w("Tag_", temp.getPosition().toString());
            if (temp.getPosition() != null) {
                LatLng pos = new LatLng(temp.getPosition().getLat(), temp.getPosition().getLng());
                // voir pour s'amuser ici???
                mMap.addMarker(new MarkerOptions().position(pos).title(temp.getName()).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED))).setTag(temp);
                //Remplir le build
                if (temp.getNumber() == 1033) {
                } else {
                    latLngBounds.include(pos);
                    nbStationAffiche++;
                }
            }
        }

        if (nbStationAffiche >= 2) {
            mMap.animateCamera(CameraUpdateFactory.newLatLngBounds(latLngBounds.build(), 100));
        }

        mMap.setInfoWindowAdapter(this);

    }

    //-----------------------
    // gestion des info bulle
    //-----------------------
    @Override
    public View getInfoWindow(Marker marker) {
        return null;
    }

    @Override
    public View getInfoContents(Marker marker) {
        View view = LayoutInflater.from(this).inflate(R.layout.marker_cellule, null);


        TextView tvNumero = view.findViewById(R.id.tvNumero);
        TextView tvNom = view.findViewById(R.id.tvNom);
        TextView tvStatus = view.findViewById(R.id.tvStatus);

        StationBean maStation = (StationBean) marker.getTag();
        tvNumero.setText(maStation.getNumber() + "");
        tvNom.setText(maStation.getName());
        tvStatus.setText(maStation.getStatus());
        return view;
    }

    //----------------------
    // AsyncTask
    //----------------------
    public class MonAT extends AsyncTask {
        private ArrayList<StationBean> result;
        private Exception exception;

        @Override
        protected Object doInBackground(Object[] objects) {

            try {
                result = WsUtils.getStations();
            } catch (Exception e) {
                e.printStackTrace();
                exception = e;
            }
            return null;
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);

            if (exception != null) {
                Toast.makeText(MapsActivity.this, "Une erreur est survenue : " + exception, Toast.LENGTH_SHORT).show();
            } else {
                data.clear();
                data.addAll(result);
                refreshMap();
            }
        }
    }
}
