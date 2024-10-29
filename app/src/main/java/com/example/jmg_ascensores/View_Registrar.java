package com.example.jmg_ascensores;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout; // Importa LinearLayout
import androidx.appcompat.app.AppCompatActivity;

public class View_Registrar extends AppCompatActivity {

    private LinearLayout clienteLayout; // Cambia a LinearLayout
    private LinearLayout trabajadorLayout; // Cambia a LinearLayout

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registro_cliente); // Asegúrate de que esto coincida con tu archivo de diseño

        clienteLayout = findViewById(R.id.ClienteLayout); // ID del LinearLayout para CLIENTE NUEVO
        trabajadorLayout = findViewById(R.id.TrabajadorLayout); // ID del LinearLayout para REGISTRAR TRABAJADOR NUEVO

        // Configura el clic para el LinearLayout del cliente
        clienteLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("VistaAdmin", "Botón CLIENTE clicado");
                Intent intent = new Intent(View_Registrar.this, View_ClienteNuevo.class);
                startActivity(intent);
            }
        });

        // Configura el clic para el LinearLayout del trabajador
        trabajadorLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("VistaAdmin", "Botón TRABAJADOR clicado");
                Intent intent = new Intent(View_Registrar.this, View_RegTrab.class);
                startActivity(intent);
            }
        });
    }
}
