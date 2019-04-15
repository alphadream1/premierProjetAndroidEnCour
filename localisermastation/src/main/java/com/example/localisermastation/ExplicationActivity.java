package com.example.localisermastation;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class ExplicationActivity extends AppCompatActivity {

    private TextView tvModeCycliste;
    private TextView tvModePieton;

    private static final String modeCycliste = "En Vert, les stations où plus de la moitié des places sont disponibles \n" +
            " En Orange, les stations où moins de la moitié des places sont disponibles \n" +
            "En Rouge, les stations où il n'y a pas de places disponibles \n" +
            "En Bleu, les stations en maintenance \n";

    private static final String modePieton = "En Vert, les stations où plus de la moitié des vélos sont disponibles \n" +
            " En Orange, les stations où moins de la moitié des vélos sont disponibles \n" +
            "En Rouge, les stations où il n'y a pas de vélos disponibles \n" +
            "En Bleu, les stations en maintenance";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_explication);
        tvModeCycliste = findViewById(R.id.tvModeCycliste);
        tvModePieton = findViewById(R.id.tvModePieton);

        tvModeCycliste.setText(modeCycliste);
        tvModePieton.setText(modePieton);
    }

    public void onClickRetourAcueil(View view) {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }
}
