package com.example.jmg_ascensores;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class VistaTrab extends AppCompatActivity {
    private Button empresa1;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vista_trabajador);

        empresa1 = findViewById(R.id.idempresa);


        empresa1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Al hacer clic en el botón ADMINISTRADOR, iniciar MainActivity
                Intent intent = new Intent(VistaTrab.this, Tareas_empresa.class);
                startActivity(intent);
                finish(); // Cerrar OpcionesActivity
            }
        });

}}
