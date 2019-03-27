package com.example.premierprojetcourandroid;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class SecondActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btRetour;
    private TextView tvDeuxiemeEcran;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        btRetour = findViewById(R.id.btRetour);
        tvDeuxiemeEcran = findViewById(R.id.tvDeuxiemeEcran);

        btRetour.setOnClickListener(this);
        if (getIntent().getExtras() != null) {
            String valeur = getIntent().getExtras().getString("maCle");
            //  String valeur = getIntent().getStringExtra("maCle");
            tvDeuxiemeEcran.setText(valeur);
        }

    }


    @Override
    public void onClick(View v) {
        if (v == btRetour) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }
}
