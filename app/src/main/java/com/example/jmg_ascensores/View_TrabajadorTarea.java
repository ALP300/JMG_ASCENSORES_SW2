package com.example.jmg_ascensores;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class View_TrabajadorTarea extends AppCompatActivity{
    private Button btnConf;
    private String empresa;
    private String data;
    private String ubicacion[] = new String[2];
    private TextView txtTit;
    private GoogleMap mMap;
    private double latitud;
    private double longitud;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vistas_tareas_trabajador);

        btnConf = findViewById(R.id.btnConf);
        empresa = getIntent().getStringExtra("empresa");
        data = getIntent().getStringExtra("ubicacion");
        txtTit = findViewById(R.id.txtNomEm);
        txtTit.setText(empresa);

        btnConf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Al hacer clic en el botón ADMINISTRADOR, iniciar MainActivity

            }
        });

}

}
