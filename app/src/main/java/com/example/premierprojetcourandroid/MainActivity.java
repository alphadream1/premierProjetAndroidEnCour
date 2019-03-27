package com.example.premierprojetcourandroid;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TimePicker;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, TimePickerDialog.OnTimeSetListener, DatePickerDialog.OnDateSetListener {

    // déclaration composant
    private Button btValider, btAnnuler;
    private RadioGroup monRadioGroup;
    private RadioButton rbJaime, rbJaimePas;
    private EditText etSaisirVotreNom;
    private ImageView iv_android;
    private Button btNextScreen;

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
        btNextScreen = findViewById(R.id.btNextScreen);

        // 3 je m'abonne au click
        btValider.setOnClickListener(this);
        btAnnuler.setOnClickListener(this);
        btNextScreen.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        // on joue sur les valeurs
        if (v == btValider) {
            iv_android.setImageResource(R.mipmap.ic_valider);
            btNextScreen.setVisibility(View.VISIBLE);
            if (rbJaime.isChecked()) {
                etSaisirVotreNom.setText(rbJaime.getText());
                iv_android.setColorFilter(Color.GREEN);
            } else if (rbJaimePas.isChecked()) {
                etSaisirVotreNom.setText(rbJaimePas.getText());
                iv_android.setColorFilter(Color.RED);
            }
        } else if (v == btAnnuler) {
            iv_android.setImageResource(R.mipmap.ic_annuler);
            iv_android.setColorFilter(getResources().getColor(R.color.marron, getTheme()));
            etSaisirVotreNom.setText("");
            monRadioGroup.clearCheck();
            btNextScreen.setVisibility(View.INVISIBLE);
        } else if (v == btNextScreen) {
            Intent intent = new Intent(this, SecondActivity.class);
            intent.putExtra("maCle", etSaisirVotreNom.getText().toString());
            startActivity(intent);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(0, 1, 0, "AlertDialog");
        menu.add(0, 2, 0, "TimePicker");
        menu.add(0, 3, 0, "DatePicker");
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case 1:
                //remplir ici
                break;
            case 2:
                TimePickerDialog timePickerDialog = new TimePickerDialog(this, this, 12, 30, true);
                timePickerDialog.show();
                break;
            case 3:
                Calendar calendar = Calendar.getInstance();
                DatePickerDialog datePickerDialog = new DatePickerDialog(this, this, calendar.get(calendar.YEAR), calendar.get(calendar.MONTH), calendar.get(calendar.DAY_OF_MONTH));
                datePickerDialog.show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

    }
}

