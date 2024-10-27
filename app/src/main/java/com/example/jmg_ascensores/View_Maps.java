package com.example.jmg_ascensores;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.fragment.app.FragmentActivity;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class View_Maps extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private Marker marker;
    private Button guardarUbicacionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        // Inicializa el fragmento del mapa
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }

        // Inicializa el botón de guardar ubicación
        guardarUbicacionButton = findViewById(R.id.guardar_ubicacion_button);

        // Acción para el botón "Guardar ubicación"
        guardarUbicacionButton.setOnClickListener(v -> {
            if (marker != null) {
                // Obtener las coordenadas del marcador
                LatLng latLng = marker.getPosition();

                // Retorna la ubicación seleccionada
                Intent resultIntent = new Intent();
                resultIntent.putExtra("ubicacion", latLng.latitude + "," + latLng.longitude);
                setResult(RESULT_OK, resultIntent); // Enviar las coordenadas de vuelta a la actividad que inició el mapa
                finish(); // Cierra la actividad y regresa
            } else {
                Toast.makeText(View_Maps.this, "Por favor, selecciona una ubicación en el mapa.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Configura el mapa con una posición inicial (puedes ajustarla)
        LatLng defaultLocation = new LatLng(-34, 151); // Establecer una ubicación predeterminada
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(defaultLocation, 10));

        // Listener para que el usuario seleccione una ubicación
        mMap.setOnMapClickListener(latLng -> {
            if (marker != null) marker.remove();  // Elimina el marcador anterior si existe
            marker = mMap.addMarker(new MarkerOptions().position(latLng).title("Ubicación seleccionada"));
        });
    }
}

