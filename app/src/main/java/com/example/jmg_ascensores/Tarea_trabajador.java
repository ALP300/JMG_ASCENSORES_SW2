package com.example.jmg_ascensores;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class Tarea_trabajador extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vistas_tareas_trabajador);

        // Obtener referencia al botón
        Button empresaButton = findViewById(R.id.button7);

        // Establecer el OnClickListener
        empresaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Redirigir a la actividad que contiene el layout vistas_tareas_trabajador
                Intent intent = new Intent(Tarea_trabajador.this, TrabajadorTareaActivity.class);
                startActivity(intent);
            }
        });
    }
}

