package com.example.jmg_ascensores;

import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.sql.Connection;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import java.util.concurrent.ExecutionException;
import androidx.cardview.widget.CardView;
import android.graphics.Color;

public class View_Cli_ProxManten extends AppCompatActivity {

    private ListView listProxMant;
    private Connection connection;
    private String codCli;
    private Ent_Cliente clientex;
    private TextView txtEmp;
    private TextView txtCod;
    private TextView txtUbi;
    private TextView txtFec;
    private CardView cardFecha; // Agrega la referencia al CardView

    private static final String TAG = "View_ProxManten"; // Etiqueta para los logs

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.prox_mant);

        // Inicializa los TextViews y el CardView
        txtEmp = findViewById(R.id.txtEmp);
        txtCod = findViewById(R.id.txtCod);
        txtUbi = findViewById(R.id.txtUbic);
        txtFec = findViewById(R.id.txtFec);
        cardFecha = findViewById(R.id.cardFecha); // Inicializa el CardView

        // Verificación y solicitud de permiso de ubicación
        if (checkSelfPermission(android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 1);  // 1 es el requestCode
        } else {
            // Permiso concedido
            setupData();
        }
    }

    private void setupData() {
        codCli = getIntent().getStringExtra("codCli");

        new DB_Connect() {
            @Override
            protected void onPostExecute(Connection conn) {
                connection = conn;
                if (connection != null) {
                    try {
                        clientex = new DB_ClienteID(connection, View_Cli_ProxManten.this).execute(codCli).get();
                        Log.d("View_ProxManten", "Clientex: " + clientex);
                        if (clientex != null) {
                            txtEmp.setText("Nombre de la empresa: " + clientex.getNombre_empresa());
                            txtCod.setText("Código de la empresa: " + clientex.getCodigo());

                            double latitud = clientex.getLatitud();
                            double longitud = clientex.getLongitud();
                            Log.d(TAG, "Latitud: " + latitud + ", Longitud: " + longitud); // Log para las coordenadas

                            // Comprobar si las coordenadas son válidas
                            if (latitud != 0 && longitud != 0) {
                                obtenerDireccion(latitud, longitud);
                                Log.d("View_ProxManten", "Latitud: " + clientex.getLatitud() + ", Longitud: " + clientex.getLongitud());
                            } else {
                                txtUbi.setText("Ubicación: Coordenadas no válidas");
                                Log.w(TAG, "Coordenadas no válidas: " + latitud + ", " + longitud); // Log de advertencia
                            }

                            Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
                            Date fechaActual = calendar.getTime();
                            long diferenciaTiempo = clientex.getFec().getTime() - fechaActual.getTime();
                            double diasDifDecimal = (double) diferenciaTiempo / (1000 * 60 * 60 * 24);
                            long diasDif = (long) Math.ceil(diasDifDecimal);
                            txtFec.setText("Fecha: Faltan " + diasDif + " días hasta el próximo mantenimiento");

                            // Cambiar el color del CardView según los días restantes
                            if (diasDif >= 10) {
                                cardFecha.setCardBackgroundColor(Color.parseColor("#70FF00")); // Verde
                            } else if (diasDif >= 5) {
                                cardFecha.setCardBackgroundColor(Color.parseColor("#FFE600")); // Amarillo
                            } else if (diasDif >= 1) {
                                cardFecha.setCardBackgroundColor(Color.parseColor("#FF0000")); // Rojo
                            } else {
                                cardFecha.setCardBackgroundColor(Color.parseColor("#FFFFFF")); // Color por defecto o algún otro color
                            }
                        }
                    } catch (ExecutionException | InterruptedException e) {
                        Log.e(TAG, "Error al obtener cliente: ", e); // Log para errores
                    }
                }
            }
        }.execute();
    }

    private void obtenerDireccion(double latitud, double longitud) {
        Log.d(TAG, "Obteniendo dirección para Latitud: " + latitud + ", Longitud: " + longitud); // Log para obtener dirección
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(latitud, longitud, 1);
            if (addresses != null && !addresses.isEmpty()) {
                Address address = addresses.get(0);
                String direccion = address.getAddressLine(0); // Dirección completa
                txtUbi.setText("Ubicación: " + direccion);
                Log.d(TAG, "Dirección obtenida: " + direccion); // Log para dirección obtenida
            } else {
                txtUbi.setText("Ubicación: Dirección no disponible");
                Log.w(TAG, "No se encontró dirección para las coordenadas"); // Log de advertencia
            }
        } catch (IOException e) {
            txtUbi.setText("Ubicación: Error al obtener la dirección");
            Log.e(TAG, "Error al obtener la dirección: ", e); // Log de error
        }
    }
    public void mostrarCliente(Ent_Cliente cliente) {
        if (cliente != null) {
            txtEmp.setText("Nombre de la empresa: " + cliente.getNombre_empresa());
            txtCod.setText("Código de la empresa: " + cliente.getCodigo());

            double latitud = cliente.getLatitud();
            double longitud = cliente.getLongitud();

            // Verifica si las coordenadas son válidas
            if (latitud != 0 && longitud != 0) {
                obtenerDireccion(latitud, longitud);
            } else {
                txtUbi.setText("Ubicación: Coordenadas no válidas");
            }

            Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
            Date fechaActual = calendar.getTime();
            long diferenciaTiempo = cliente.getFec().getTime() - fechaActual.getTime();
            double diasDifDecimal = (double) diferenciaTiempo / (1000 * 60 * 60 * 24);
            long diasDif = (long) Math.ceil(diasDifDecimal);
            txtFec.setText("Fecha: " + diasDif + " días hasta el próximo mantenimiento");
        } else {
            txtEmp.setText("Datos no encontrados");
            txtCod.setText("");
            txtUbi.setText("");
            txtFec.setText("");
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permiso concedido, ahora puedes llamar a setupData()
                setupData();
            } else {
                // Permiso denegado, muestra un mensaje o maneja el caso en que el permiso no se conceda
                txtUbi.setText("Ubicación: Permiso denegado");
            }
        }
    }
}
