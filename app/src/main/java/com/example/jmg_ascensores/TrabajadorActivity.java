package com.example.jmg_ascensores;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class TrabajadorActivity extends AppCompatActivity {
    private Button empresa1;
    private Button btnCerrarSesion;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vista_trabajador);

        empresa1 = findViewById(R.id.idempresa);
        btnCerrarSesion = findViewById(R.id.btnCerrarSesion);


        empresa1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Al hacer clic en el botón ADMINISTRADOR, iniciar MainActivity
                Intent intent = new Intent(TrabajadorActivity.this, Tareas_empresa.class);
                startActivity(intent);
                finish(); // Cerrar OpcionesActivity
            }
        });

        btnCerrarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Redirigir a la actividad de inicio de sesión
                Intent intent = new Intent(TrabajadorActivity.this,MainActivityTrabajador.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK); // Limpiar pila de actividades
                startActivity(intent);
                finish();  // Cerrar la actividad actual
            }
        });


}}
