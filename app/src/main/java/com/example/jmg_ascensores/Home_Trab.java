package com.example.jmg_ascensores;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

public class Home_Trab extends AppCompatActivity {
    private LinearLayout btnEmpresasAsignadas;
    private LinearLayout btnCerrarSesion;
    private String codTrab;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.trab_menu);

        btnEmpresasAsignadas = findViewById(R.id.menu_empresa);
        btnCerrarSesion = findViewById(R.id.menu_cerrar_sesion); // Corregir ID

        codTrab = getIntent().getStringExtra("codTrab");

        btnEmpresasAsignadas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Iniciar View_TareasEmpresa al hacer clic en "Empresas Asignadas"
                Intent intent = new Intent(Home_Trab.this, View_Trab_TareasEmpresa.class);
                intent.putExtra("codTrab", codTrab);
                startActivity(intent);
                finish(); // Cerrar Home_Trab
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
    }
}
