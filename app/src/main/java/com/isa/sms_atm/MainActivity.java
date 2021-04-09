package com.isa.sms_atm;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener{
    Button btnGuardar;
    TextView txtTel;
    TextView txtMsj;
    Switch swActivo;
    Intent servicioCall;
    ServiceReceiverCall receiverCall;

    String msj;
    String tel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnGuardar = findViewById(R.id.btnGuardar);
        txtTel = findViewById(R.id.txtTel);
        txtMsj = findViewById(R.id.txtMensaje);
        swActivo = findViewById(R.id.swActivar);

        //final SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
        final SharedPreferences sharedPref = getSharedPreferences(getPackageName()+"_preferences", Context.MODE_PRIVATE);
        msj = sharedPref.getString("msj", "En breve me comunico con ud.");
        tel = sharedPref.getString("tel", "NULL");

        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(txtTel.getText().toString().equals("") || txtMsj.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(),"Favor de llenar los campos.", Toast.LENGTH_LONG).show();
                } else {

                    SharedPreferences.Editor editor = sharedPref.edit();
                    editor.putString("tel", txtTel.getText().toString());
                    editor.putString("msj", txtMsj.getText().toString());
                    editor.commit();
                    Toast.makeText(getBaseContext(), "Información registrada", Toast.LENGTH_SHORT).show();
                    txtTel.setText("");
                    txtMsj.setText("");
                }
            }
        });

        swActivo.setOnCheckedChangeListener(this);

    }


    public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
        receiverCall = new ServiceReceiverCall();
        if(isChecked) {
            Toast.makeText(getBaseContext(), "Activar servicio", Toast.LENGTH_SHORT).show();
            servicioCall = new Intent(getBaseContext(), receiverCall.getClass());
            startService(servicioCall);
            Log.d("app_info", "Ya lo inicio");
        }
        else {
            Toast.makeText(getBaseContext(), "Apagar :c", Toast.LENGTH_SHORT).show();
            stopService(servicioCall);
        }
    }
}