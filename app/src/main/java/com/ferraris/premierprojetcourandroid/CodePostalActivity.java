package com.ferraris.premierprojetcourandroid;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.ferraris.premierprojetcourandroid.model.beans.CityBean;
import com.ferraris.premierprojetcourandroid.model.ws.WSUtils;

import java.util.ArrayList;

public class CodePostalActivity extends AppCompatActivity implements View.OnClickListener {

    // mes composants graphique
    private EditText etCodePostal;
    private Button btCodePostal;
    //private TextView tvCodePostal;
    private ProgressBar pbCodePostal;
    private RecyclerView rv;

    // mes données
    private ArrayList<CityBean> cities;

    //mes outils
    MonAT monAT;
    private CityAdapter cityAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_code_postal);

        etCodePostal = findViewById(R.id.etCodePostal);
        btCodePostal = findViewById(R.id.btCodePostal);
        pbCodePostal = findViewById(R.id.pbCodePostal);

        btCodePostal.setOnClickListener(this);
        pbCodePostal.setVisibility(View.GONE);

        // creation de la liste
        cities = new ArrayList<>();
        //instanciation d'un CityAdapter
        cityAdapter = new CityAdapter(cities);
        rv = findViewById(R.id.rvCodePostal);
        //adapter a affiche
        rv.setAdapter(cityAdapter);
        // gestion de l'affichage
        rv.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void onClick(View v) {
        Toast.makeText(this, "Click sur le bouton", Toast.LENGTH_SHORT).show();
        if (monAT == null || monAT.getStatus() == AsyncTask.Status.FINISHED) {
            monAT = new MonAT();
            monAT.execute();
        } else {
            Toast.makeText(this, "Tache déjà lancée", Toast.LENGTH_SHORT).show();
        }
    }

    public class MonAT extends AsyncTask {

        ArrayList<CityBean> resultat;
        Exception exception;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pbCodePostal.setVisibility(View.VISIBLE);
        }


        @Override
        protected Object doInBackground(Object[] objects) {

            try {
                // le resultat ici
                resultat = WSUtils.getCities(etCodePostal.getText().toString());
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
                Toast.makeText(CodePostalActivity.this, "Une erreur est survenue : " + exception.getMessage(), Toast.LENGTH_SHORT).show();
                //tvCodePostal.setText("Une erreur est survenue : " + exception.getMessage());
            } else {
                // copie les adresses des données d'une liste a l'autre.
                cities.clear();
                cities.addAll(resultat);

                cityAdapter.notifyDataSetChanged();

                pbCodePostal.setVisibility(View.GONE);

//                //Afficher le resultat
//                String aAfficher = "";
//                for (CityBean cityBean : resultat) {
//                    aAfficher += cityBean.getCp() + " : " + cityBean.getVille() + " \n";
//                }
//                tvCodePostal.setText(aAfficher);
//                pbCodePostal.setVisibility(View.GONE);

            }
        }
    }
}
