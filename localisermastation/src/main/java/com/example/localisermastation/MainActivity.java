package com.example.localisermastation;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioButton;

public class MainActivity extends AppCompatActivity {


    private RadioButton rbPieton;
    private RadioButton rbCycliste;

    private static final int ITEM_ID_MCP = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rbPieton = findViewById(R.id.rbPieton);
        rbCycliste = findViewById(R.id.rbCycliste);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(0, ITEM_ID_MCP, 0, "Explication des modes d'affichages");
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == ITEM_ID_MCP) {
            startActivity(new Intent(this, ExplicationActivity.class));
        }
        return super.onOptionsItemSelected(item);
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
