package com.example.jmg_ascensores;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class View_TrabajadorTarea extends AppCompatActivity {
    private Button empresa1;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vistas_tareas_trabajador);

        empresa1 = findViewById(R.id.button7);

        empresa1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Redirigir a la actividad que contiene el layout vistas_tareas_trabajador
                Intent intent = new Intent(View_TrabajadorTarea.this, View_ConfirmacionTrab.class);
                startActivity(intent);
            }
        });



}}
