package com.example.premierprojetcourandroid;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;

public class ServiceExACtivity extends AppCompatActivity {

    private Button btStop;
    private TextView tvEcranService;
    private Button btStart;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_ex);
        btStop = findViewById(R.id.btStop);
        tvEcranService = findViewById(R.id.tvEcranService);
        btStart = findViewById(R.id.btStart);
    }
}
