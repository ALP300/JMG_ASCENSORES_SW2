package com.example.jmg_ascensores;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class VistaAdmin extends AppCompatActivity {
    private Button agregarButton;
    private Button agregarButton2;
    private Button agregarButton3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vista); // Asegúrate de que este es el diseño correcto
        ;
        agregarButton = findViewById(R.id.botonTrabajador); // Cambiado a botonTrabajador
        agregarButton2= findViewById(R.id.Trabajador);
        agregarButton3= findViewById(R.id.Mantenimiento);

        agregarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("VistaAdmin", "Botón clicado");
                Intent intent = new Intent(VistaAdmin.this, RegistroClienteTrabajador.class);
                startActivity(intent);
            }
        });
        agregarButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("VistaAdmin", "Botón clicado");
                Intent intent = new Intent(VistaAdmin.this, Trabajadorcliente.class);
                startActivity(intent);
            }
        });
        agregarButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("VistaAdmin", "Botón clicado");
                Intent intent = new Intent(VistaAdmin.this, HistorialMantenimiento.class);
                startActivity(intent);
            }
        });

    }
}
