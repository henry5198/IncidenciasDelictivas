package com.example.IncidenciasDelictivas;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import WebService.Asynchtask;
import WebService.WebService;

public class NotificacionesActivity extends AppCompatActivity implements Asynchtask {

    public Noticias[] noticias ;
    private ListView lstOpciones;
    private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notificaciones);

        this.setTitle("Notificaciones");
        toolbar = (Toolbar) findViewById(R.id.back_toolbar);
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.back_btn);

        Map<String, String> datos = new HashMap<>();
        WebService ws = new WebService("http://revistas.uteq.edu.ec/webservice/noticia", datos, NotificacionesActivity.this, NotificacionesActivity.this);
        ws.execute("");
        lstOpciones = (ListView)findViewById(R.id.lst_opciones);
        View header =getLayoutInflater().inflate(R.layout.ly_cabecera,null);
        lstOpciones.addHeaderView(header);
    }

    @Override
    public void processFinish(String result) throws JSONException {
        Log.i("processFinish",result);
        //Leer el JSON
        JSONArray noticias= new JSONArray(result);
        ArrayList<Noticias> noticiasArrayList = Noticias.jsonObjectsBuild(noticias);
        AdaptadorNoticias adaptadornoticias = new AdaptadorNoticias(this, noticiasArrayList);
        lstOpciones.setAdapter(adaptadornoticias);
    }
}
