package com.example.premierprojetcourandroid;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.premierprojetcourandroid.model.beans.MaVille;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnInfoWindowClickListener, GoogleMap.InfoWindowAdapter {

    private GoogleMap mMap;

    // data


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setInfoWindowAdapter(this);
        mMap.setOnInfoWindowClickListener(this);
        // Add a marker in Sydney and move the camera
//        LatLng sydney = new LatLng(-34, 151);
//        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

        MaVille toulouse = new MaVille("Toulouse", new LatLng(43.59999, 1.43333));
        MarkerOptions markerToulouse = new MarkerOptions();
        markerToulouse.position(toulouse.getPosition());
        markerToulouse.title(toulouse.getNom());

        MaVille noe = new MaVille("Noé", new LatLng(43.35, 1.2667));
        MarkerOptions markerNoe = new MarkerOptions();
        markerNoe.position(noe.getPosition());
        markerNoe.title(noe.getNom());

        markerToulouse.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
        markerNoe.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
        mMap.addMarker(markerToulouse)
                .setTag(toulouse);
        mMap.addMarker(markerNoe).setTag(noe);
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(toulouse.getPosition(), 10));

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);
        }
        LatLngBounds.Builder latLngBounds = new LatLngBounds.Builder();
        latLngBounds.include(new LatLng(43.35, 1.2667));
        latLngBounds.include(new LatLng(43.59999, 1.43333));
        mMap.animateCamera(CameraUpdateFactory.newLatLngBounds(latLngBounds.build(), 100));// le 100 est en pixel du coup attention
    }

    @Override
    public void onInfoWindowClick(Marker marker) {
        MaVille ville = (MaVille) marker.getTag();
        Toast.makeText(this, "Click sur la pop up " + ville.getNom(), Toast.LENGTH_SHORT).show();
        //Ferme la fenêtre
        marker.hideInfoWindow();
    }

    @Override
    public View getInfoWindow(Marker marker) {
        return null;
    }

    @Override
    public View getInfoContents(Marker marker) {
        //Layoutde la fenêtre. Créer un fichier XML représantantla popup
        View view = LayoutInflater.from(this).inflate(R.layout.marker_cellule, null);
        TextView tv = (TextView) view.findViewById(R.id.tv);
        ImageView iv = (ImageView) view.findViewById(R.id.iv);
        MaVille maVille = (MaVille) marker.getTag();
        tv.setText(maVille.getNom());
        iv.setImageResource(R.mipmap.ic_launcher_round);
        iv.setColorFilter(Color.CYAN);
        return view;
    }
}

