package com.example.IncidenciasDelictivas;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Paint;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements TextView.OnClickListener {

    private TextView register;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        register = (TextView) findViewById(R.id.txt_register);
        register.setPaintFlags(register.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        register.setText(getResources().getString(R.string.text_signup));
        register.setOnClickListener(this);
        String a = "";

        getPermission(Manifest.permission.ACCESS_FINE_LOCATION);
    }

    public void getPermission(String permission){
        if (Build.VERSION.SDK_INT >= 23) {
            if (!(checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED))
                ActivityCompat.requestPermissions(this, new String[]{permission}, 1);
        }
    }
    @Override
    public void onClick(View v) {
        Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
        startActivity(intent);

    }

    public void principal(View v) {
        Intent intent = new Intent(MainActivity.this, PrincipalActivity.class);
        startActivity(intent);

    }

    public void recuperar(View v) {
        Intent intent = new Intent(MainActivity.this, RecuperarActivity.class);
        startActivity(intent);

    }
}
