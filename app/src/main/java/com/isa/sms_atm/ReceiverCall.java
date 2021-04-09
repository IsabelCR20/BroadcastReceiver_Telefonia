package com.isa.sms_atm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.TelephonyManager;
import android.util.Log;

public class ReceiverCall extends BroadcastReceiver {
    static boolean ring = false;
    String numTel;
    @Override
    public void onReceive(Context context, Intent intent) {
        if(intent.getAction().equals("android.intent.action.PHONE_STATE")){
            //Log.d("app_info", "Estado: " +intent.getExtras().getString(TelephonyManager.EXTRA_STATE));
            if(intent.getExtras().getString(TelephonyManager.EXTRA_STATE).equals("RINGING") && !ring){
                ring = true;
                numTel = intent.getExtras().getString(TelephonyManager.EXTRA_INCOMING_NUMBER);
                //Log.d("app_info", "Numero de telefono:" + intent.getExtras().getString(TelephonyManager.EXTRA_INCOMING_NUMBER));

            }
            if(intent.getExtras().getString(TelephonyManager.EXTRA_STATE).equals("IDLE")){
                ring = false;
            }
        }

    }
}
