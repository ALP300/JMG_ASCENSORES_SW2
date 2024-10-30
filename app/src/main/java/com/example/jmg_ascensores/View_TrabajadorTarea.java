package com.example.jmg_ascensores;

import android.annotation.SuppressLint;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class View_TrabajadorTarea extends AppCompatActivity implements OnMapReadyCallback{
    private Button btnConf;
    private String empresa;
    private String data;
    private String codCli;
    private String ubicacion[] = new String[2];
    private TextView txtTit;
    private TextView txtDir;
    private double latitud;
    private double longitud;
    private MapView mapView;
    private GoogleMap googleMap;
    private static final String MAPVIEW_BUNDLE_KEY = "MapViewBundleKey";
    private Bundle mapViewBundle;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vistas_tareas_trabajador);

        btnConf = findViewById(R.id.btnConf);
        txtDir = findViewById(R.id.txtDir);
        txtTit = findViewById(R.id.txtNomEm);
        empresa = getIntent().getStringExtra("empresa");
        data = getIntent().getStringExtra("ubicacion");
        codCli = getIntent().getStringExtra("codCli");

        if (data != null) {
            txtTit.setText(empresa);
            mapView = findViewById(R.id.mapView);
            ubicacion = data.split(",");

            if (ubicacion.length == 2) {
                String latitudStr = ubicacion[0]; // Primer dato (latitud)
                String longitudStr = ubicacion[1]; // Segundo dato (longitud)

                // Convertir a double si es necesario
                latitud = Double.parseDouble(latitudStr);
                longitud = Double.parseDouble(longitudStr);
                //Log.d("Database", "→"+latitud+longitud);
                // Ahora puedes usar latitud y longitud
                obtenerDireccion(latitud,longitud);

                if (savedInstanceState != null) {
                    mapViewBundle = savedInstanceState.getBundle(MAPVIEW_BUNDLE_KEY);
                }

                mapView.onCreate(mapViewBundle);
                mapView.getMapAsync(this);
                btnConf.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Al hacer clic en el botón ADMINISTRADOR, iniciar MainActivity

                    }
                });
            } else {
                // Manejar el caso donde no hay 2 partes
                Log.e("Database", "La ubicación no contiene dos datos separados por una coma.");
            }
        } else {
            // Manejar el caso donde 'ubicacion' es null
            Toast.makeText(View_TrabajadorTarea.this, "No hay ubicacion", Toast.LENGTH_LONG).show();

        }


}
    private void obtenerDireccion(double latitud, double longitud) {

        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(latitud, longitud, 1);
            if (addresses != null && !addresses.isEmpty()) {
                Address address = addresses.get(0);
                String direccion = address.getAddressLine(0); // Dirección completa
                txtDir.setText("Ubicación: " + direccion);
            } else {
                txtDir.setText("Ubicación: Dirección no disponible");
            }
        } catch (IOException e) {
            txtDir.setText("Ubicación: Error al obtener la dirección");
        }
    }


    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        googleMap = googleMap;

        // Establece una ubicación inicial
        LatLng initialLocation = new LatLng(latitud, longitud); // Cambia a la ubicación deseada
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(initialLocation, 16));
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        Bundle mapViewBundle = outState.getBundle(MAPVIEW_BUNDLE_KEY);
        if (mapViewBundle == null) {
            mapViewBundle = new Bundle();
            outState.putBundle(MAPVIEW_BUNDLE_KEY, mapViewBundle);
        }
        mapView.onSaveInstanceState(mapViewBundle);
    }
}
