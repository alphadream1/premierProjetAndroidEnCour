package com.ferraris.localisermastation;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
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

import com.ferraris.localisermastation.bean.StationBean;
import com.ferraris.localisermastation.utils.WsUtils;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.InfoWindowAdapter {

    public static final String MODE_REQ_CODE = "Mode";
    public static final String STATION_STATE_OPEN = "OPEN";
    public static final int NB_STATION_AFFICHEE = 5;

    private GoogleMap mMap;
    private ArrayList<StationBean> data;
    private boolean modeAffichageCycliste; //False pour le pieton
    private LocationManager lm;
    private Location myLocation;
    private boolean zoom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        // recuperation de l'info si mode pieton ou cycliste
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra(MODE_REQ_CODE)) {
            modeAffichageCycliste = intent.getBooleanExtra(MODE_REQ_CODE, true);
        }

        // instanciation de la liste
        data = new ArrayList<>();

        // lancement de l'asynctask
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
        mMap.setInfoWindowAdapter(this);

        refreshMap();
    }

    public void refreshMap() {
        // verification que la carte est crée
        if (mMap == null) {
            return;
        }
        //nettoyage de la carte
        mMap.clear();

        // on verifie que l'on a bien l'autorisation de localisation, si on ne l'a pas on l'a demande
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);
            myLocation = getLastKnownLocation();
        }

        //Créer le build pour l'animate camera
        LatLngBounds.Builder latLngBounds = new LatLngBounds.Builder();
        int nbStationAffiche = 0;
        //--------------


        // affichage des markers
        for (StationBean temp : data) {
            if (temp.getPosition() != null) {
                LatLng pos = new LatLng(temp.getPosition().getLat(), temp.getPosition().getLng());
                // remplissage des données distance de chaque StationBean
                if (myLocation != null) {
                    temp.setDistance(distance(myLocation, pos));
                }
                // debut du choix des markers
                BitmapDescriptor markerIcone = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED);
                if (STATION_STATE_OPEN.equals(temp.getStatus())) {
                    if (modeAffichageCycliste) {
                        if (temp.getAvailable_bike_stands() >= temp.getBike_stands() / 2) {
                            markerIcone = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN);
                        } else if (temp.getAvailable_bike_stands() < temp.getBike_stands() / 2 && temp.getAvailable_bike_stands() > 0) {
                            markerIcone = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE);
                        }

                    } else {
                        if (temp.getAvailable_bikes() >= temp.getBike_stands() / 2) {
                            markerIcone = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN);
                        } else if (temp.getAvailable_bikes() < temp.getBike_stands() / 2 && temp.getAvailable_bike_stands() > 0) {
                            markerIcone = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE);
                        }
                    }
                } else {
                    markerIcone = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE);
                }

                mMap.addMarker(new MarkerOptions().position(pos).title(temp.getName()).icon(markerIcone)).setTag(temp);

                latLngBounds.include(pos);
                nbStationAffiche++;

            }
        }

        //En cas de zoom et de position connu je zoom sur les stations alentours
        if (myLocation != null && zoom) {
            Collections.sort(data, StationBean.ComparatorDistance);
            LatLngBounds.Builder latLngProche = new LatLngBounds.Builder();
            nbStationAffiche = 0;
            for (int i = 0; i < NB_STATION_AFFICHEE && i < data.size(); i++) {
                if (data.get(i).getPosition() != null) {
                    latLngProche.include(data.get(i).getPosition().getLatLng());
                    nbStationAffiche++;
                }
            }
            latLngProche.include(new LatLng(myLocation.getLatitude(), myLocation.getLongitude()));

            if (nbStationAffiche >= 1) {
                mMap.animateCamera(CameraUpdateFactory.newLatLngBounds(latLngProche.build(), 100));
            } else {
                Toast.makeText(this, "Aucune station", Toast.LENGTH_SHORT).show();
            }
        } else {
            if (nbStationAffiche >= 2) {
                mMap.animateCamera(CameraUpdateFactory.newLatLngBounds(latLngBounds.build(), 100));
            }
        }


    }

    private Location getLastKnownLocation() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return null;
        }
        LocationManager mLocationManager = (LocationManager) getApplicationContext().getSystemService(LOCATION_SERVICE);
        List<String> providers = mLocationManager.getProviders(true);
        Location bestLocation = null;
        for (String provider : providers) {
            Location l = mLocationManager.getLastKnownLocation(provider);
            if (l == null) {
                continue;
            }
            if (bestLocation == null || l.getAccuracy() < bestLocation.getAccuracy()) {
                // Found best last known location: %s", l);
                bestLocation = l;
            }
        }
        return bestLocation;
    }

    public void onClickZoom(View view) {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            zoom = true;
            refreshMap();
        } else {
            //on redemande la permission
            Toast.makeText(this, "Il faut la permission de localisation", Toast.LENGTH_SHORT).show();
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 0);
        }
    }

    public void OnClickRetourTls(View view) {
        zoom = false;
        refreshMap();
    }

    public static double distance(Location location, LatLng latLng) {
        float[] results = new float[1];
        Location.distanceBetween(location.getLatitude(), latLng.latitude, location.getLongitude(), latLng.longitude, results);
       /* double theta = myLocation.getLongitude() - latLng.longitude;
        double dist = Math.sin(Math.toRadians(myLocation.getLatitude())) * Math.sin(Math.toRadians(latLng.latitude)) + Math.cos(Math.toRadians(myLocation.getLatitude())) * Math.cos(Math.toRadians(latLng.latitude)) * Math.cos(Math.toRadians(theta));
        dist = Math.acos(dist);
        dist = Math.toDegrees(dist);
        dist = dist * 60 * 1.1515;
        Log.w("TAG_", "" + dist);
        return dist;*/
        return results[0];
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
