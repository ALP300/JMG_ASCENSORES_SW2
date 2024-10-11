package com.example.jmg_ascensores;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class AscensoresMantenimiento extends AppCompatActivity {
    private Button registrarAscensores;
    private Button registrarMantenimiento;
    private String clienteCodigo; // Almacena el código del cliente

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vista_ascensores_matenimiento);

        // Recibir el código del cliente de la actividad anterior
        clienteCodigo = getIntent().getStringExtra("codigo_cliente");

        registrarAscensores = findViewById(R.id.ascensor);
        registrarMantenimiento = findViewById(R.id.mantenimiento);

        registrarAscensores.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Al hacer clic en el botón de registrar ascensor
                Intent intent = new Intent(AscensoresMantenimiento.this, Ascensor.class);
                intent.putExtra("codigo_cliente", clienteCodigo); // Pasar el código del cliente
                startActivity(intent);
                finish(); // Cerrar AscensoresMantenimiento
            }
        });

        registrarMantenimiento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Al hacer clic en el botón de registrar mantenimiento
                Intent intent = new Intent(AscensoresMantenimiento.this, Mantenimiento.class);
                intent.putExtra("codigo_cliente", clienteCodigo); // Pasar el código del cliente
                startActivity(intent);
                finish(); // Cerrar AscensoresMantenimiento
            }
        });
    }
}
