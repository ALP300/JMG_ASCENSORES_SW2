package com.example.jmg_ascensores;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class View_TareasEmpresa extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tareas_designadas);

        // Obtener referencia al botón
        Button empresaButton = findViewById(R.id.button7);

        // Establecer el OnClickListener
        empresaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Redirigir a la actividad que contiene el layout vistas_tareas_trabajador
                Intent intent = new Intent(View_TareasEmpresa.this, View_TrabajadorTarea.class);
                startActivity(intent);
            }
        });
    }
}

