package com.example.jmg_ascensores;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import android.content.SharedPreferences;
import android.content.Context;

public class VistaAdmin extends AppCompatActivity {
    private Button agregarButton;
    private Button agregarButton2;
    private Button agregarButton3;
    private Button btnCerrarSesion;  // Botón de cerrar sesión
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vista); // Asegúrate de que este es el diseño correcto
        ;
        agregarButton = findViewById(R.id.botonTrabajador); // Cambiado a botonTrabajador
        agregarButton2= findViewById(R.id.Trabajador);
        agregarButton3= findViewById(R.id.Mantenimiento);
        btnCerrarSesion = findViewById(R.id.btnCerrarSesion);


        agregarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("VistaAdmin", "Botón clicado");
                Intent intent = new Intent(VistaAdmin.this, RegistroClienteTrabajador.class);
                startActivity(intent);
            }
        });
        agregarButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("VistaAdmin", "Botón clicado");
                Intent intent = new Intent(VistaAdmin.this, Trabajadorcliente.class);
                startActivity(intent);
            }
        });
        agregarButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("VistaAdmin", "Botón clicado");
                Intent intent = new Intent(VistaAdmin.this, HistorialMantenimiento.class);
                startActivity(intent);
            }
        });

        btnCerrarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Redirigir a la actividad de inicio de sesión
                Intent intent = new Intent(VistaAdmin.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK); // Limpiar pila de actividades
                startActivity(intent);
                finish();  // Cerrar la actividad actual
            }
        });
    }

}
