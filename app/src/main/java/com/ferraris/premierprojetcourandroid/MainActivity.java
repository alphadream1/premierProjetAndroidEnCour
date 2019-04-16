package com.ferraris.premierprojetcourandroid;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
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
    private static final int ITEM_ID_DAL = 4;
    private static final int ITEM_ID_WEA = 5;
    private static final int ITEM_ID_CP = 6;
    private static final int ITEM_ID_NOTIF = 7;
    private static final int ITEM_ID_GM = 8;

    // pour avoir des samples de code, file/new/import sample

    // exo supplementaire
    @SuppressLint("SimpleDateFormat")
    private static final SimpleDateFormat FORMAT;

    static {
        FORMAT = new SimpleDateFormat("dd/MM/yyyy HH:mm");
    }

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
        menu.add(0, ITEM_ID_DAL, 0, "Service Exemple");
        menu.add(0, ITEM_ID_WEA, 0, "Web Activity");
        menu.add(0, ITEM_ID_CP, 0, "Code Postal Activity");
        menu.add(0, ITEM_ID_NOTIF, 0, "Notification Activity");
        menu.add(0, ITEM_ID_GM, 0, "Google Maps");
        return super.onCreateOptionsMenu(menu);
    }

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
                    (dialog, which) -> {
                        //Affiche un toast apres le click sur le bouton ok
                        Toast.makeText(MainActivity.this, "Click sur ok",
                                Toast.LENGTH_SHORT).show();
                    });
            //Icone
            alertDialogBuilder.setIcon(R.mipmap.ic_launcher);
            //Afficher la fenêtre
            alertDialogBuilder.show();

        } else if (item.getItemId() == ITEM_ID_DAL) {
            //Etape 1 : Est ce qu'on a déjà la permission ?
            //Attention prendre le Manifest d’android.util
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                //On a la permission
                startActivity(new Intent(this, ServiceExActivity.class));
            } else {
                //Etape 2 : On affiche la fenêtre de demande de permission
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 0);
            }

        } else if (item.getItemId() == ITEM_ID_WEA) {
            startActivity(new Intent(this, WebExActivity.class));
        } else if (item.getItemId() == ITEM_ID_CP) {
            startActivity(new Intent(this, CodePostalActivity.class));
        } else if (item.getItemId() == ITEM_ID_NOTIF) {
            startActivity(new Intent(this, NotificationExActivity.class));
        } else if (item.getItemId() == ITEM_ID_GM) {
            startActivity(new Intent(this, MapsActivity.class));
        }
        boolean b;
        b = super.onOptionsItemSelected(item);
        return b;
    }

    //--------------------
    //Callback demande permission
    //--------------------
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] gr) {
        super.onRequestPermissionsResult(requestCode, permissions, gr);

        //Est ce que c'est la permission qu'on a demandé ?
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            //ON a la permission
            startActivity(new Intent(this, ServiceExActivity.class));
        } else {
            //On n'a pas la permission
            Toast.makeText(this, "il faut la permission pour aller sur l'écran", Toast.LENGTH_SHORT).show();
        }
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

    }

    //--------------------
    //Private
    //--------------------

}

