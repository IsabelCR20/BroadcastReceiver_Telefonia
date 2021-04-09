package com.isa.sms_atm;

import android.app.Notification;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.telephony.TelephonyManager;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class ServiceReceiverCall extends Service {
     String telefono;
     String mensaje;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("app_info", "Llevando ");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("app_info", "Llevando a cabo servicio");

        BroadcastReceiver miReceptor = new ReceiverCall();
        IntentFilter intento = new IntentFilter(TelephonyManager.ACTION_PHONE_STATE_CHANGED);
        this.registerReceiver(miReceptor,intento);

        // Notificación y segundo plano
        Notification notificacion = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.fondo)
                .setContentTitle("SMS ATM")
                .setContentText("El servicio de mensajería automático está activo.")
                .build();
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        startForeground(2, notificacion);

        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("app_info", "Serviico detenido");
    }
}
