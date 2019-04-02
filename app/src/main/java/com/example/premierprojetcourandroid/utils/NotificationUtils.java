package com.example.premierprojetcourandroid.utils;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.SystemClock;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;

import com.example.premierprojetcourandroid.MainActivity;
import com.example.premierprojetcourandroid.NotificationPublisherBR;
import com.example.premierprojetcourandroid.R;

public class NotificationUtils {

    private static final String CHANNEL_ID = "MonSuperChannel";
    private static final CharSequence CHANNEL_NAME = "Commandes";

    private static void initChannel(Context c) {
        // Uniquement à partir d’Oreo
        if (Build.VERSION.SDK_INT < 26) {
            return;
        }
        NotificationManager nm = (NotificationManager) c.getSystemService(Context.NOTIFICATION_SERVICE);
        // Réglage du channel
        NotificationChannel channel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH);
        channel.setDescription("Commandes"); // Description
        channel.enableLights(true); // Lumière
        channel.enableVibration(true); // Vibration
        channel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
        // Création du channel
        nm.createNotificationChannel(channel);
    }

    public static void createInstantNotification(Context c, String message) {
        //Initialisation du chanel
        initChannel(c);
        //Ce qui se passera quand on cliquera sur la notif
        Intent intent = new Intent(c, MainActivity.class);
        PendingIntent pi = PendingIntent.getActivity(c, 0, intent, PendingIntent.FLAG_ONE_SHOT);
        //La notification
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(c, CHANNEL_ID);
        notificationBuilder.setSmallIcon(R.mipmap.ic_valider)
                .setContentTitle("Ma notification immediate")
                .setContentText(message)
                .setContentIntent(pi)//le clic dessus
                .setPriority(NotificationManager.IMPORTANCE_HIGH)
                .setDefaults(Notification.DEFAULT_ALL);//Son + afficher la notification
        //Envoyer la notification
        NotificationManagerCompat ncm = NotificationManagerCompat.from(c);
        //ENVOIE
        ncm.notify(29, notificationBuilder.build());
    }

    public static void scheduleNotification(Context c, String message, long delay) {
        //Initialisation du chanel
        initChannel(c);
        //La notification
        NotificationCompat.Builder builder = new NotificationCompat.Builder(c, CHANNEL_ID);
        builder.setContentTitle("Scheduled Notification");
        builder.setContentText(message);
        builder.setSmallIcon(R.mipmap.ic_valider);
        //Redirection vers le broadcast
        Intent intent = new Intent(c, NotificationPublisherBR.class);
        intent.putExtra("MaCle", builder.build());
        PendingIntent pendingIntent = PendingIntent.getBroadcast(c, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        //La dans le futur
        long futureInMillis = SystemClock.elapsedRealtime() + delay;
        AlarmManager alarmManager = (AlarmManager)
                c.getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, futureInMillis, pendingIntent);
    }
}
