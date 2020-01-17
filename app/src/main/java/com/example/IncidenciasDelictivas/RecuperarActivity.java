package com.example.IncidenciasDelictivas;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

public class RecuperarActivity extends AppCompatActivity {

    private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recuperar);

        this.setTitle("Confirmación Código de Seguridad");
        toolbar = (Toolbar) findViewById(R.id.back_toolbar);
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.back_btn);
    }


    public void dialogo(){
        new AlertDialog.Builder(this)
                .setTitle("¡Enviado!")
                .setIcon(R.drawable.check)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //finish();
                        Intent intent = new Intent(RecuperarActivity.this, RestablecerClave.class);
                        startActivity(intent);
                    }
                })
                /*.setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.d("Mensaje","Se canceló la acción");
                    }
                })*/
                .show();
    }

    public void recuperar(View n){


        dialogo();
    }
}
