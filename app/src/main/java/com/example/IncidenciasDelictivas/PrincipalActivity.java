package com.example.IncidenciasDelictivas;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.material.navigation.NavigationView;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import WebService.Asynchtask;
import WebService.WebService;
import de.hdodenhof.circleimageview.CircleImageView;


public class PrincipalActivity extends AppCompatActivity implements Asynchtask, OnMapReadyCallback {

    private Toolbar toolbar;
    private DrawerLayout drawerlayout;
    private NavigationView navigationView;
    private Menu menu;

    private LocationManager locManager;
    private Location loc;
    private String longitud, latitud;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        //MAPA
        try {
            SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
            mapFragment.getMapAsync(this);
        }
        catch (Exception ex){
        }



        //NAVIGATIONVIEW
        toolbar = (Toolbar)findViewById(R.id.appbar);
        navigationView = (NavigationView)findViewById(R.id.navview);
        drawerlayout = (DrawerLayout)findViewById(R.id.drawer_layout);

        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.menuic);

        Map<String, String> datos = new HashMap<String, String>();
        WebService ws = new WebService("http://www.json-generator.com/api/json/get/ceaMewaiBe/",datos,PrincipalActivity.this, PrincipalActivity.this);
        ws.execute("");

//        getPermission(Manifest.permission.ACCESS_FINE_LOCATION);
    }

    @Override
    public void processFinish(String result) throws JSONException {
        JSONArray jsonArray = new JSONArray(result);
        Persona persona = new Persona();
        for (int i = 0; i < jsonArray.length(); i++){
            JSONObject user = jsonArray.getJSONObject(i);
            String usuario = user.getString("usuario");
            String contrasena = user.getString("contrasena");
            String url_imagen = user.getString("url_imagen");

            JSONArray menu = user.getJSONArray("menu");
            ArrayList<String> listado = new ArrayList<String>();
            for (int j = 0; j < menu.length(); j++){
                JSONObject aux = menu.getJSONObject(j);
                Iterator<?> iterator = aux.keys();
                while(iterator.hasNext()){
                    String key =(String)iterator.next();
                    String lista_menu = aux.getString(key);
                    listado.add(lista_menu);
                }
            }

            persona.setUsuario(usuario);
            persona.setContrasena(contrasena);
            persona.setUrl_imagen(url_imagen);
            persona.setOpciones(listado);
        }

        menu = navigationView.getMenu();

        for(int i = 0; i <= persona.getOpciones().size()-1;i++){
            menu.add(persona.getOpciones().get(i)).setIcon(R.drawable.itemic);
        }

        View headView = navigationView.getHeaderView(0);
        ((TextView) headView.findViewById(R.id.textView)).setText(persona.getUsuario());

        CircleImageView perfil = (CircleImageView)findViewById(R.id.profile_image);
        //((CircleImageView) headView.findViewById(R.id.profile_image));

        Glide.with(this.getApplicationContext())
                .load(persona.getUrl_imagen())
                .error(R.drawable.nofound)
                .into(perfil);
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                drawerlayout = (DrawerLayout)findViewById(R.id.drawer_layout);
                drawerlayout.openDrawer(GravityCompat.START);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        googleMap.getUiSettings().setZoomControlsEnabled(true);

        CameraUpdate camUpd1 = CameraUpdateFactory.newLatLngZoom(new LatLng(-1.0125, -79.4698), 15);


        googleMap.moveCamera(camUpd1);

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            return;

        }
        googleMap.setMyLocationEnabled(true);


        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {
            Toast.makeText(this, "No se han definido los permisos necesarios.", Toast.LENGTH_LONG).show();
            return;
        }else
        {
            locManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            loc = locManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            longitud=String.valueOf(loc.getLongitude());
            latitud=String.valueOf(loc.getLatitude());
            Toast.makeText(this, longitud+" "+latitud, Toast.LENGTH_LONG).show();
        }
    }



    public void reporte(View v) {
        Intent intent = new Intent(PrincipalActivity.this, Reporte.class);
        startActivity(intent);
    }

    public void notificacion(View v) {
        Intent intent = new Intent(PrincipalActivity.this, NotificacionesActivity.class);
        startActivity(intent);
    }

    public void estadistica(View v) {
        Intent intent = new Intent(PrincipalActivity.this, NotificacionesActivity.class);
        startActivity(intent);
    }
}
