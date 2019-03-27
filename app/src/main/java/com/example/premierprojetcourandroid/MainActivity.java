package com.example.premierprojetcourandroid;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
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
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, TimePickerDialog.OnTimeSetListener, DatePickerDialog.OnDateSetListener {

    private static final int ITEM_ID_AD = 1;
    private static final int ITEM_ID_TP = 2;
    private static final int ITEM_ID_DP = 3;

    // exo supplementaire
    private static final SimpleDateFormat FORMAT = new SimpleDateFormat("dd/MM/yyyy HH:mm");

    // 1) déclaration composant
    private Button btValider, btAnnuler;
    private RadioGroup monRadioGroup;
    private RadioButton rbJaime, rbJaimePas;
    private EditText etSaisirVotreNom;
    private ImageView iv_android;
    private Button btNextScreen;


    // exo supplementaire
    //Données
    private Calendar dateChoisi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 2) je recupère l'instance
        btValider = findViewById(R.id.btValider);
        btAnnuler = findViewById(R.id.btAnnuler);
        monRadioGroup = findViewById(R.id.monRadioGroup);
        etSaisirVotreNom = findViewById(R.id.etSaisirVotreNom);
        iv_android = findViewById(R.id.iv_android);
        rbJaime = findViewById(R.id.rbJaime);
        rbJaimePas = findViewById(R.id.rbJaimePas);
        btNextScreen = findViewById(R.id.btNextScreen);

        // 3) je m'abonne au click
        btValider.setOnClickListener(this);
        btAnnuler.setOnClickListener(this);
        btNextScreen.setOnClickListener(this);

        // exo supplementaire
        // recup les dates et heure du datepicker et timepicker
        dateChoisi = Calendar.getInstance();

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
        menu.add(0, ITEM_ID_AD, 0, "AlertDialog");
        menu.add(0, ITEM_ID_TP, 0, "TimePicker");
        menu.add(0, ITEM_ID_DP, 0, "DatePicker");
        return super.onCreateOptionsMenu(menu);
    }

   /* @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case 1:
                AlertDialog.Builder alertDialogBuider = new AlertDialog.Builder(this);
                alertDialogBuider.setMessage("voici mon alert");
                alertDialogBuider.setTitle("mon alert");
                alertDialogBuider.setPositiveButton("ok",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(MainActivity.this, "click sur ok", Toast.LENGTH_SHORT).show();
                            }
                        });
                alertDialogBuider.show();
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
    }*/

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == ITEM_ID_TP) {
            //Ici je lance le TimePicker
            TimePickerDialog timePickerDialog = new TimePickerDialog(this, this, 14, 33, true);
            timePickerDialog.show();

        } else if (item.getItemId() == ITEM_ID_DP) {
            //Ici je lance le DatePicker
            //Gestion de la date
            Calendar calendar = Calendar.getInstance();
//Création de la fenêtre
//Pour le callback -> Alt+entree -> implémente méthode -> Génère la méthode onTimeSet
            DatePickerDialog datePickerDialog = new DatePickerDialog(this, this,
                    calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH));
//Afficher la fenêtre
            datePickerDialog.show();

        } else if (item.getItemId() == ITEM_ID_AD) {
            //Ici je lance l'AlertDialog
            //Préparation de la fenêtre
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
//Message
            alertDialogBuilder.setMessage("Mon message");
//titre
            alertDialogBuilder.setTitle("Mon titre");
//bouton ok
            alertDialogBuilder.setPositiveButton("ok",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //Affiche un toast apres le click sur le bouton ok
                            Toast.makeText(MainActivity.this, "Click sur ok",
                                    Toast.LENGTH_SHORT).show();
                        }
                    });
//Icone
            alertDialogBuilder.setIcon(R.mipmap.ic_launcher);
//Afficher la fenêtre
            alertDialogBuilder.show();

        }


        return super.onOptionsItemSelected(item);
    }

    //-------------------
    //Callback timePicker
    //-------------------
    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        Toast.makeText(MainActivity.this, "Heure choisi " + hourOfDay + " : " + minute, Toast.LENGTH_SHORT).show();

        // exo supplementaire
        dateChoisi.set(Calendar.HOUR_OF_DAY, hourOfDay);
        dateChoisi.set(Calendar.MINUTE, minute);
        String maDate = FORMAT.format(dateChoisi.getTime());
        Toast.makeText(this, maDate, Toast.LENGTH_SHORT).show();
    }

    //--------------------
    // Callback Datepicker
    //--------------------
    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        // premiere option
        Toast.makeText(MainActivity.this, "Date choisi " + dayOfMonth + "/" + (month + 1) + "/" + year, Toast.LENGTH_SHORT).show();


        // tp sup car fini plus tot => utilisé le SimpleDateFormat !!!
        dateChoisi.set(Calendar.YEAR, year);
        dateChoisi.set(Calendar.MONTH, month);
        dateChoisi.set(Calendar.DAY_OF_MONTH, dayOfMonth);

        String maDate = FORMAT.format(dateChoisi.getTime());
        Toast.makeText(this, maDate, Toast.LENGTH_LONG).show();
        // je ne sais pas ce qui est le mieux mais la 2eme genere plus de code!!!
    }

    //--------------------
    //Private
    //--------------------

}

