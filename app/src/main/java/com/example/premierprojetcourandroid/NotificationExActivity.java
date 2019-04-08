package com.example.premierprojetcourandroid;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.premierprojetcourandroid.utils.NotificationUtils;

public class NotificationExActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btDelay;
    private Button btNow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_ex);


        btDelay = findViewById(R.id.btDelay);
        btNow = findViewById(R.id.btNow);

        btDelay.setOnClickListener(this);
        btNow.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        if (v == btNow) {
            // remplir ici pour notif directe
            NotificationUtils.createInstantNotification(this, "NOW");

        } else if (v == btDelay) {
            //remplir ici pour notif decalé
            NotificationUtils.scheduleNotification(this, "DELAY", 15000);
        }
    }

}
