package com.example.IncidenciasDelictivas;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;

import com.google.android.material.textfield.TextInputEditText;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import java.util.Calendar;

public class Reporte extends AppCompatActivity  {

    String[] SPINNERLIST = {"Robo a persona", "Robo a vehículo", "Robo a casa", "Robo a comercio", "Actividad sospechosa"
            , "Homicidio", "Vandalismo", "Venta de drogas", "Otros"};
    private Toolbar toolbar;
    private TextInputEditText txtFecha;
    private int dia, mes, anio;
    private Button btnEnviar;

    //Camara
    Button btn;
    ImageView imagen;
    Intent i;
    final static int cons =0;
    Bitmap bmp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reporte);

        this.setTitle("Reporte de Incidencias Delictivas");
        toolbar = (Toolbar) findViewById(R.id.back_toolbar);
        toolbar.setTitleTextColor(Color.WHITE);
        txtFecha=(TextInputEditText)findViewById(R.id.txtFecha);
        //txtFecha.setOnClickListener(this);
        btnEnviar=(Button)findViewById(R.id.btnEnviar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.back_btn);

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, SPINNERLIST);
        MaterialBetterSpinner materialDesignSpinner = (MaterialBetterSpinner)
                findViewById(R.id.android_material_design_spinner);
        materialDesignSpinner.setAdapter(arrayAdapter);


        checkCameraPermission();
        init();

    }

    public void init(){
        btn = (Button)findViewById(R.id.btnCamara);
        imagen = (ImageView)findViewById(R.id.imgCamara);
    }

    private void checkCameraPermission() {
        int permissionCheck = ContextCompat.checkSelfPermission(
                this, Manifest.permission.CAMERA);
        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            Log.i("Mensaje", "No se tiene permiso para la camara!.");
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 225);
        } else {
            Log.i("Mensaje", "Tienes permiso para usar la camara.");
        }
    }

    public void cam(View s){
        int id;
        id=s.getId();
        switch (id) {
            case R.id.btnCamara:
                i = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(i, cons);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        if (resultCode== Activity.RESULT_OK){
            Bundle ext = data.getExtras();
            bmp = (Bitmap)ext.get("data");
            imagen.setImageBitmap(bmp);
        }
    }








    public void onClick(View v) {
        final Calendar c = Calendar.getInstance();
        dia=c.get(Calendar.DAY_OF_MONTH);
        mes=c.get(Calendar.MONTH);
        anio=c.get(Calendar.YEAR);

        DatePickerDialog datePickerDialog=new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                txtFecha.setText(dayOfMonth+"/"+(month+1)+"/"+year);
            }
        },dia,mes,anio);
        datePickerDialog.show();
    }

    public void registrar(View n){
        dialogo();
    }

    public void dialogo(){
        new AlertDialog.Builder(this)
                .setTitle("¡Enviado!")
                .setIcon(R.drawable.check)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
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
}
