package com.example.jmg_ascensores;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class TrabajadorActivity extends AppCompatActivity {
    private Button empresa;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vista_trabajador);

        empresa = findViewById(R.id.empresa);


        empresa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Al hacer clic en el botón ADMINISTRADOR, iniciar MainActivity
                Intent intent = new Intent(TrabajadorActivity.this, Tareas_empresa.class);
                startActivity(intent);
                finish(); // Cerrar OpcionesActivity
            }
        });

}}
