package com.example.jmg_ascensores;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class Home_Trab extends AppCompatActivity {
    private Button btnEmpresasAsignadas;
    private Button btnCerrarSesion;
    private String codTrab;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vista_trabajador);

        btnEmpresasAsignadas = findViewById(R.id.idEmpresa);
        btnCerrarSesion = findViewById(R.id.btnCerrarSesion);
        codTrab = getIntent().getStringExtra("codTrab");

        btnEmpresasAsignadas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Al hacer clic en el botón ADMINISTRADOR, iniciar MainActivity

                Intent intent = new Intent(Home_Trab.this, View_TareasEmpresa.class);
                intent.putExtra("codTrab", codTrab);
                startActivity(intent);
                finish(); // Cerrar OpcionesActivity
            }
        });

        btnCerrarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Redirigir a la actividad de inicio de sesión
                Intent intent = new Intent(Home_Trab.this, Home_Main.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK); // Limpiar pila de actividades
                startActivity(intent);
                finish();  // Cerrar la actividad actual
            }
        });


}}
