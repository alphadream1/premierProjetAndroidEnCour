package com.example.premierprojetcourandroid;

import android.content.Intent;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.premierprojetcourandroid.service.MyLocationService;
import com.squareup.otto.Subscribe;

public class ServiceExActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btStop;
    private TextView tvEcranService;
    private Button btStart;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_ex);

        //
        btStop = findViewById(R.id.btStop);
        tvEcranService = findViewById(R.id.tvEcranService);
        btStart = findViewById(R.id.btStart);

        // ecoute des bouton
        btStart.setOnClickListener(this);
        btStop.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        if (v == btStart) {
            // lance le service
            startService(new Intent(this, MyLocationService.class));
        } else if (v == btStop) {
            // arrete le service
            stopService(new Intent(this, MyLocationService.class));
        }
    }

    //--------------------
    //location
    //--------------------

    @Override
    protected void onStart() {
        super.onStart();
        MyApplication.getBus().register(this); //OnStart
    }

    @Override
    protected void onStop() {
        super.onStop();
        MyApplication.getBus().unregister(this); //OnStop
    }

    @Subscribe
    public void afficherLocation(Location location) {
        tvEcranService.setText(location.toString());
    }

    public class MonAT extends AsyncTask {

        private EleveBean resultat;
        private Exception exception;

        @Override
        protected Object doInBackground(Object[] objects) {

            try {
                resultat = WSUtils.loadEleveFromWeb();
                // TRAITEMNT LONG MAIS INTERDIT DE TOUCHER AUX COMPOSANTS GRAPHIQUES
            } catch (Exception e) {// En cas d’erreur on la stoque
                e.printStackTrace(); //A ne surtout pas retirer
                exception = e;
            }
            return null;
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            if (exception != null) { //On affiche l’erreur
                // Toast.makeText(context, "Erreur :" + exception.getMessage(), Toast.LENGTH_SHORT).show();
            } else { //On peut modifier les composants graphiques
                tvEcranService.setText("Nom : " + resultat.getText());
            }
        }
    }

}
