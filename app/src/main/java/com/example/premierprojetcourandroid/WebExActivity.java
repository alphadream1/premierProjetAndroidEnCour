package com.example.premierprojetcourandroid;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.premierprojetcourandroid.utils.OkHttpUtils;

public class WebExActivity extends AppCompatActivity {

    private WebView wvWebEx;
    private EditText etWebEx;
    private TextView tvWebEx;
    MonAT monAT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_ex);
        wvWebEx = findViewById(R.id.wvWebEx);
        etWebEx = findViewById(R.id.etWebEx);
        tvWebEx = findViewById(R.id.tvWebEx);

        //reglages webview
        wvWebEx.setWebViewClient(new WebViewClient());
        //Activr le Javascript (Attention aux performances)
        WebSettings webviewSettings = wvWebEx.getSettings();
        webviewSettings.setJavaScriptEnabled(true);
    }


    public void onClick(View view) {
        wvWebEx.loadUrl(etWebEx.getText().toString());
        if (monAT == null || monAT.getStatus() == AsyncTask.Status.FINISHED) {
            monAT = new MonAT();
            monAT.execute();
        } else {
            Toast.makeText(this, "Tache déjà lancée", Toast.LENGTH_SHORT).show();
        }
    }


    /* ---------------------
    // AsyncTask
    --------------------- */

    public class MonAT extends AsyncTask {

        String resultat;
        Exception exception;


        @Override
        protected Object doInBackground(Object[] objects) {

            try {
                resultat = OkHttpUtils.sendGetOkHttpRequest(etWebEx.getText().toString());
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
                tvWebEx.setText("Une erreur est survenue : " + exception.getMessage());
            } else {
                tvWebEx.setText(resultat);
            }
        }
    }
}
