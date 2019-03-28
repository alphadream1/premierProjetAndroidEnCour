package com.example.premierprojetcourandroid;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ServiceExACtivity extends AppCompatActivity implements View.OnClickListener {

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
            startService(new Intent(this, MyService.class));
            Log.w("Tag_", "Service activer");
        } else if (v == btStop) {
            // arrete le service
            stopService(new Intent(this, MyService.class));
            Log.w("Tag_", "Service désactiver");
        }
    }
}
