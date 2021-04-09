package com.isa.sms_atm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    Button btnGuardar;
    TextView txtTel;
    TextView txtMsj;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnGuardar = findViewById(R.id.btnGuardar);
        txtTel = findViewById(R.id.txtTel);
        txtMsj = findViewById(R.id.txtMensaje);

        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(txtTel.getText().equals("") || txtMsj.getText().equals("")){
                    Toast.makeText(getApplicationContext(),"Favor de llenar los campos.", Toast.LENGTH_LONG);
                } else {
                    SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPref.edit();
                    editor.putString("tel", txtTel.getText().toString());
                    editor.putString("msj", txtMsj.getText().toString());
                    editor.commit();
                }
            }
        });

        BroadcastReceiver miReceptor = new ReceiverCall();
        IntentFilter intento = new IntentFilter(TelephonyManager.ACTION_PHONE_STATE_CHANGED);
        this.registerReceiver(miReceptor,intento);

    }
}