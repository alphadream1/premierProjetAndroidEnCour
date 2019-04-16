package com.ferraris.premierprojetcourandroid;

import android.content.Intent;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.ferraris.premierprojetcourandroid.model.beans.EleveBean;
import com.ferraris.premierprojetcourandroid.model.ws.WSUtils;
import com.ferraris.premierprojetcourandroid.service.MyLocationService;
import com.squareup.otto.Subscribe;

public class ServiceExActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btStop;
    private TextView tvEcranService;
    private Button btStart;
    private ProgressBar progressBar;
    private Button btLoad;

    private MonAT monAT;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_ex);

        //
        btStop = findViewById(R.id.btStop);
        tvEcranService = findViewById(R.id.tvEcranService);
        btStart = findViewById(R.id.btStart);
        btLoad = findViewById(R.id.btLoad);

        // ecoute des bouton
        btStart.setOnClickListener(this);
        btStop.setOnClickListener(this);
        btLoad.setOnClickListener(this);

        //progressBar
        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);
    }

    //--------------------
    //
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //        if(progressDialog != null) {
        //            progressDialog.dismiss();
        //            progressDialog = null;
        //        }
    }


    //----------------------
    //CallBack otto
    //----------------------

    @Subscribe
    public void afficherLocation(Location location) {
        tvEcranService.setText(location.toString());
    }

    //----------------------
    // CallBack ClickBoutton
    //----------------------

    @Override
    public void onClick(View v) {
        if (v == btStart) {
            // lance le service
            startService(new Intent(this, MyLocationService.class));
        } else if (v == btStop) {
            // arrete le service
            stopService(new Intent(this, MyLocationService.class));
        } else if (v == btLoad) {
            //je lance l'AsyncTask

            if (monAT == null || monAT.getStatus() == AsyncTask.Status.FINISHED) {
                monAT = new MonAT();
                monAT.execute();
            } else {
                Toast.makeText(this, "Tache déjà  en cours", Toast.LENGTH_SHORT).show();
            }
        }
    }


    //-------------------
    //AsyncTask
    //-------------------
    public class MonAT extends AsyncTask {

        EleveBean resultat;
        Exception exception;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
            //progressDialog = ProgressDialog.show(ServiceActivity.this, "", "CHargement en cours");
        }

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
            progressBar.setVisibility(View.GONE);

            if (exception != null) {
                tvEcranService.setText("Une erreur est survenue : " + exception.getMessage());
            } else {
                Toast.makeText(ServiceExActivity.this, resultat.getPrenom() + " " + resultat.getNom(), Toast.LENGTH_SHORT).show();
                tvEcranService.setText(resultat.getPrenom() + " " + resultat.getNom());
            }
        }
    }

}
