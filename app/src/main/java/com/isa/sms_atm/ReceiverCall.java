package com.isa.sms_atm;

import android.app.Application;
import android.app.Notification;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class ReceiverCall extends BroadcastReceiver {
    private static int CANAL = 5;
    static boolean ring = false;
    String numTel="";
    String msj="";
    String telefono ="";
    String mensaje = "";

    public ReceiverCall(String telefono, String mensaje) {
        super();
        this.telefono = telefono;
        this.mensaje = mensaje;
    }
    public ReceiverCall() {
        super();
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        SharedPreferences sp = context.getSharedPreferences(context.getPackageName()+"_preferences", Context.MODE_PRIVATE);
        this.telefono = sp.getString("tel", "NULL");
        this.mensaje = sp.getString("msj", "NULL");
        Log.d("app_info", "Numero de telefono: " + telefono + "     Mensaje: "+ mensaje);

        if(intent.getAction().equals("android.intent.action.PHONE_STATE")){
            //Log.d("app_info", "Estado: " +intent.getExtras().getString(TelephonyManager.EXTRA_STATE));
            if(intent.getExtras().getString(TelephonyManager.EXTRA_STATE).equals("RINGING") && !ring){
                ring = true;
                numTel = intent.getExtras().getString(TelephonyManager.EXTRA_INCOMING_NUMBER);
                //Log.d("app_info", "Numero de telefono:" + intent.getExtras().getString(TelephonyManager.EXTRA_INCOMING_NUMBER));
                if(numTel.equals(telefono)){
                    Log.d("app_info", "Hay que responder!!!  " + mensaje);
                    // Respondiendo con mensaje
                    EnviarMensaje();
                    //
                    NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "CANAL")
                            .setSmallIcon(R.drawable.fondo)
                            .setContentTitle("SMS Automático")
                            .setContentText("Hola! Se ha enviado un mensaje a " + telefono)
                            .setPriority(NotificationCompat.PRIORITY_DEFAULT);
                    NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);

                    // notificationId is a unique int for each notification that you must define
                    notificationManager.notify(1, builder.build());
                }
            }
            if(intent.getExtras().getString(TelephonyManager.EXTRA_STATE).equals("IDLE")){
                ring = false;
            }
        }

    }

    private void EnviarMensaje(){
        SmsManager smsM = SmsManager.getDefault();
        smsM.sendTextMessage(telefono,null, mensaje, null, null);
    }
}
