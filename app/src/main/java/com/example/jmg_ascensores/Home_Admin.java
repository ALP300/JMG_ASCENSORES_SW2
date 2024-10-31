package com.example.jmg_ascensores;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import androidx.appcompat.app.AppCompatActivity;

public class Home_Admin extends AppCompatActivity {
    private LinearLayout botonTrabajador;
    private LinearLayout trabajador;
    private LinearLayout mantenimiento;
    private LinearLayout btnCerrarSesion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.adm_menu); // Asegúrate de que este es el diseño correcto

        // Vinculamos las vistas con sus respectivos id
        botonTrabajador = findViewById(R.id.botonTrabajador);
        trabajador = findViewById(R.id.Trabajador);
        mantenimiento = findViewById(R.id.Mantenimiento);
        btnCerrarSesion = findViewById(R.id.btnCerrarSesion);

        // Configuramos los listeners para cada botón
        botonTrabajador.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("VistaAdmin", "Botón Añadir Trabajador/Cliente clicado");
                Intent intent = new Intent(Home_Admin.this, View_Adm_Registrar.class);
                startActivity(intent);
            }
        });

        trabajador.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("VistaAdmin", "Botón Asignar Trabajador clicado");
                Intent intent = new Intent(Home_Admin.this, View_Adm_AsignarTrabajador.class);
                startActivity(intent);
            }
        });

        mantenimiento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("VistaAdmin", "Botón Historial de Mantenimientos clicado");
                Intent intent = new Intent(Home_Admin.this, View_Adm_HistorialMantenimiento.class);
                startActivity(intent);
            }
        });

        btnCerrarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("VistaAdmin", "Botón Cerrar Sesión clicado");
                // Redirigir a la actividad de inicio de sesión
                Intent intent = new Intent(Home_Admin.this, Home_Main.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK); // Limpiar pila de actividades
                startActivity(intent);
                finish();  // Cerrar la actividad actual
            }
        });
    }
}
