package com.ferraris.premierprojetcourandroid.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.ferraris.premierprojetcourandroid.utils.NotificationUtils;

public class OutOfApplicationService extends Service {

    @Override
    public void onCreate() {
        super.onCreate();
        Log.w("TAG_", "OutOfApplicationService.onCreate");
        startForeground(15, NotificationUtils.getNotif(this, "Un super message"));
    }


    @Override
    public void onDestroy() {
        super.onDestroy();

        Log.w("TAG_", "OutOfApplicationService.onDestroy");
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
