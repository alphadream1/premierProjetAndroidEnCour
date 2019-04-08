package com.example.premierprojetcourandroid.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import java.util.Locale;

public class MyBroadcast extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.w("TAG_", Locale.getDefault().getDisplayLanguage());
        Toast.makeText(context, Locale.getDefault().getDisplayLanguage(), Toast.LENGTH_SHORT).show();
    }
}
