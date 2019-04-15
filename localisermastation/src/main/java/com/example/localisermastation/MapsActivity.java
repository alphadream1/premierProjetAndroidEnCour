package com.example.localisermastation;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.localisermastation.bean.StationBean;
import com.example.localisermastation.utils.WsUtils;
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
import java.util.Collections;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.InfoWindowAdapter {

    private GoogleMap mMap;
    private ArrayList<StationBean> data;
    private Boolean modeAffichagePieton;
    private Boolean modeAffichageCycliste;
    private LocationManager lm;
    private Location location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        // recuperation de l'info si mode pieton ou cycliste
        Intent intent = getIntent();
        if (intent != null) {
            if (intent.hasExtra("Mode_Pieton")) {
                modeAffichagePieton = true;
                modeAffichageCycliste = false;
            } else if (intent.hasExtra("Mode_Cycliste")) {
                modeAffichagePieton = false;
                modeAffichageCycliste = true;
            }
        }

        // instanciation de la liste
        data = new ArrayList<>();

        // lancement de l'asynctask
        assert mapFragment != null;
        mapFragment.getMapAsync(this);
        MonAT monAT = new MonAT();
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
        //--------------

        lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        location = lm.getLastKnownLocation(lm.getBestProvider(new Criteria(), false));

        // affichage des markers
        for (StationBean temp : data) {
            if (temp.getPosition() != null) {
                LatLng pos = new LatLng(temp.getPosition().getLat(), temp.getPosition().getLng());
                // remplissage des données distance de chaque StationBean
                if (location != null) {
                    temp.setDistance((long) distance(location, pos));
                }
                // debut du choix des markers
                if (temp.getStatus().equals("OPEN")) {
                    if (modeAffichageCycliste) {
                        if (temp.getAvailable_bike_stands() >= temp.getBike_stands() / 2) {
                            mMap.addMarker(new MarkerOptions().position(pos).title(temp.getName()).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN))).setTag(temp);
                        } else if (temp.getAvailable_bike_stands() < temp.getBike_stands() / 2) {
                            mMap.addMarker(new MarkerOptions().position(pos).title(temp.getName()).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE))).setTag(temp);
                        } else if (temp.getAvailable_bike_stands() == 0) {
                            mMap.addMarker(new MarkerOptions().position(pos).title(temp.getName()).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED))).setTag(temp);
                        }

                    } else if (modeAffichagePieton) {
                        if (temp.getAvailable_bikes() >= temp.getBike_stands() / 2) {
                            mMap.addMarker(new MarkerOptions().position(pos).title(temp.getName()).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN))).setTag(temp);
                        } else if (temp.getAvailable_bikes() < temp.getBike_stands() / 2) {
                            mMap.addMarker(new MarkerOptions().position(pos).title(temp.getName()).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE))).setTag(temp);
                        } else if (temp.getAvailable_bikes() == 0) {
                            mMap.addMarker(new MarkerOptions().position(pos).title(temp.getName()).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED))).setTag(temp);
                        }
                    }
                } else {
                    mMap.addMarker(new MarkerOptions().position(pos).title(temp.getName()).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE))).setTag(temp);
                }
                //Remplir le build
                if (temp.getNumber() == 1033) {
                    Log.w("TAG_", "marker bergerac non compter");
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

    public void onClickRetour(View view) {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    public void onClickZoom(View view) {
        Collections.sort(data, StationBean.ComparatorDistance);
        LatLngBounds.Builder latLngProche = new LatLngBounds.Builder();

        for (int i = 0; i < 5; i++) {
            if (data.get(i).getPosition() != null) {
                latLngProche.include(new LatLng(data.get(i).getPosition().getLat(), data.get(i).getPosition().getLng()));
            }
        }
        latLngProche.include(new LatLng(location.getLatitude(), location.getLongitude()));
        mMap.animateCamera(CameraUpdateFactory.newLatLngBounds(latLngProche.build(), 100));
    }

    public void OnClickRetourTls(View view) {
        refreshMap();
    }

    public static double distance(Location location, LatLng latLng) {
        double theta = location.getLongitude() - latLng.longitude;
        double dist = Math.sin(Math.toRadians(location.getLatitude())) * Math.sin(Math.toRadians(latLng.latitude)) + Math.cos(Math.toRadians(location.getLatitude())) * Math.cos(Math.toRadians(latLng.latitude)) * Math.cos(Math.toRadians(theta));
        dist = Math.acos(dist);
        dist = Math.toDegrees(dist);
        dist = dist * 60 * 1.1515;
        Log.w("TAG_", "" + dist);
        return dist;
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
        View view = LayoutInflater.from(this).inflate(R.layout.marker_infowindow, null);

        TextView tvNumero = view.findViewById(R.id.tvNumero);
        TextView tvNom = view.findViewById(R.id.tvNom);
        TextView tvStatus = view.findViewById(R.id.tvStatus);
        TextView tvAdresse = view.findViewById(R.id.tvAdresse);
        TextView tvNbrePlace = view.findViewById(R.id.tvNbrePlace);
        TextView tvNbrePlaceDispo = view.findViewById(R.id.tvNbrePlaceDispo);
        TextView tvNbreVeloDispo = view.findViewById(R.id.tvNbreVeloDispo);

        StationBean maStation = (StationBean) marker.getTag();
        tvNumero.setText(maStation.getNumber() + "");
        tvNom.setText(maStation.getName());
        tvStatus.setText(maStation.getStatus());
        tvAdresse.setText(maStation.getAddress());
        tvNbrePlace.setText(maStation.getBike_stands() + "");
        tvNbrePlaceDispo.setText(maStation.getAvailable_bike_stands() + "");
        tvNbreVeloDispo.setText(maStation.getAvailable_bikes() + "");

        return view;
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

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
