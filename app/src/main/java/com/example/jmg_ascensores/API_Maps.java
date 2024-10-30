package com.example.jmg_ascensores;

import android.content.Intent;
import android.location.Geocoder;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class API_Maps extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private Marker marker;
    private Button guardarUbicacionButton, buscarUbicacionButton;
    private EditText direccionInput;

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

        // Inicializa los componentes del layout
        guardarUbicacionButton = findViewById(R.id.guardar_ubicacion_button);
        buscarUbicacionButton = findViewById(R.id.buscar_ubicacion_button);
        direccionInput = findViewById(R.id.direccion_input);

        // Acción para el botón "Buscar ubicación"
        buscarUbicacionButton.setOnClickListener(v -> {
            String direccion = direccionInput.getText().toString();
            if (!direccion.isEmpty()) {
                buscarUbicacion(direccion);
            } else {
                Toast.makeText(API_Maps.this, "Por favor, ingresa una dirección.", Toast.LENGTH_SHORT).show();
            }
        });

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
                Toast.makeText(API_Maps.this, "Por favor, selecciona una ubicación en el mapa.", Toast.LENGTH_SHORT).show();
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

    private void buscarUbicacion(String direccion) {
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        try {
            List<android.location.Address> direcciones = geocoder.getFromLocationName(direccion, 1);
            if (direcciones.size() > 0) {
                android.location.Address address = direcciones.get(0);
                LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());
                if (marker != null) marker.remove();
                marker = mMap.addMarker(new MarkerOptions().position(latLng).title("Ubicación encontrada"));
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
            } else {
                Toast.makeText(this, "Dirección no encontrada", Toast.LENGTH_SHORT).show();
            }
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Error al buscar la dirección", Toast.LENGTH_SHORT).show();
        }
    }
}
