package com.example.localisermastation;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RadioButton;

public class MainActivity extends AppCompatActivity {


    private RadioButton rbPieton;
    private RadioButton rbCycliste;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rbPieton = findViewById(R.id.rbPieton);
        rbCycliste = findViewById(R.id.rbCycliste);
    }

    public void onClickGo(View view) {
        Intent intent = new Intent(this, MapsActivity.class);
        if (rbCycliste.isChecked()) {
            intent.putExtra("Mode_Cycliste", true);
        } else if (rbPieton.isChecked()) {
            intent.putExtra("Mode_Pieton", true);
        }
        startActivity(intent);
        finish();
    }
}
