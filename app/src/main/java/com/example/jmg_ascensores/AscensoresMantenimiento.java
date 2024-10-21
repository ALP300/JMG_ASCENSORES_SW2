package com.example.jmg_ascensores;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class AscensoresMantenimiento extends AppCompatActivity {
    private Button registrarAscensores;
    private String clienteCodigo; // Almacena el código del cliente

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vista_ascensores_matenimiento);

        // Recibir el código del cliente de la actividad anterior
        clienteCodigo = getIntent().getStringExtra("codigo_cliente");

        registrarAscensores = findViewById(R.id.ascensor);
        // Aquí ya no declaramos el botón para mantenimiento, ya que no lo necesitamos
        // registrarMantenimiento = findViewById(R.id.mantenimiento);

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

        // Si ya no necesitas el botón de mantenimiento, elimina el código correspondiente
        // registrarMantenimiento.setOnClickListener(new View.OnClickListener() {
        //     @Override
        //     public void onClick(View v) {
        //         // Código relacionado con el registro de mantenimiento
        //     }
        // });
    }
}
