package com.example.premierprojetcourandroid;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    // déclaration composant
    private Button btValider, btAnnuler;
    private RadioGroup monRadioGroup;
    private RadioButton rbJaime, rbJaimePas;
    private EditText etSaisirVotreNom;
    private ImageView iv_android;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 2 je recupère l'instance
        btValider = findViewById(R.id.btValider);
        btAnnuler = findViewById(R.id.btAnnuler);
        monRadioGroup = findViewById(R.id.monRadioGroup);
        etSaisirVotreNom = findViewById(R.id.etSaisirVotreNom);
        iv_android = findViewById(R.id.iv_android);
        rbJaime = findViewById(R.id.rbJaime);
        rbJaimePas = findViewById(R.id.rbJaimePas);

        // 3 je m'abonne au click
        btValider.setOnClickListener(this);
        btAnnuler.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        // on joue sur les valeurs
        if (v == btValider) {
            iv_android.setImageResource(R.mipmap.ic_valider);
            if (rbJaime.isChecked()) {
                etSaisirVotreNom.setText(rbJaime.getText());
                iv_android.setColorFilter(Color.GREEN);
            } else if (rbJaimePas.isChecked()) {
                etSaisirVotreNom.setText(rbJaimePas.getText());
                iv_android.setColorFilter(Color.RED);
            }
        } else if (v == btAnnuler) {
            iv_android.setImageResource(R.mipmap.ic_annuler);
            iv_android.setColorFilter(getResources().getColor(R.color.marron));
            etSaisirVotreNom.setText("");
            monRadioGroup.clearCheck();
        }
    }

}

