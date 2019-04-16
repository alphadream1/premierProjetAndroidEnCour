package com.ferraris.premierprojetcourandroid.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import com.ferraris.premierprojetcourandroid.service.OutOfApplicationService;

public class StartDeviceReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.w("TAG_", "Démarrage");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            context.startForegroundService(new Intent(context, OutOfApplicationService.class));
        } else {
            context.startService(new Intent(context, OutOfApplicationService.class));
        }
    }
}
