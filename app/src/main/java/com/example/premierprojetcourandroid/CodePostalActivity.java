package com.example.premierprojetcourandroid;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.premierprojetcourandroid.model.beans.CityBean;
import com.example.premierprojetcourandroid.model.ws.WSUtils;

import java.util.ArrayList;

public class CodePostalActivity extends AppCompatActivity implements View.OnClickListener {

    // mes composants graphique
    private EditText etCodePostal;
    private TextView tvCodePostal;
    private Button btCodePostal;

    // mes données
    MonAT monAT;
    private ArrayList<CityBean> cityBeanArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_code_postal);

        etCodePostal = findViewById(R.id.etCodePostal);
        tvCodePostal = findViewById(R.id.textView);
        btCodePostal = findViewById(R.id.btCodePostal);

        btCodePostal.setOnClickListener(this);

        cityBeanArrayList = new ArrayList<>();
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
                tvCodePostal.setText("Une erreur est survenue : " + exception.getMessage());
            } else {
                tvCodePostal.setText("");
            }
        }
    }
}
