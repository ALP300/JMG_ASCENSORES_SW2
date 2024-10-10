package com.example.jmg_ascensores;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class AscensoresMantenimiento extends AppCompatActivity {
    private Button registrarAscensores;
    private Button registrarManteminiento;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vista_ascensores_matenimiento);

        registrarAscensores = findViewById(R.id.ascensor);
        registrarManteminiento= findViewById(R.id.mantenimiento);

        registrarAscensores.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Al hacer clic en el botón ADMINISTRADOR, iniciar MainActivity
                Intent intent = new Intent(AscensoresMantenimiento.this, Ascensor.class);
                startActivity(intent);
                finish(); // Cerrar OpcionesActivity
            }
        });
        registrarManteminiento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Al hacer clic en el botón TRABAJADOR, iniciar MainActivity
                Intent intent = new Intent(AscensoresMantenimiento.this, Mantenimiento.class);
                startActivity(intent);
                finish(); // Cerrar OpcionesActivity
            }
        });

        // Agregar onClickListener para CLIENTE y TRABAJADOR si es necesario
    }
}
