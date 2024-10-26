package com.example.jmg_ascensores;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private Button adminButton;
    private Button clienteButton;
    private Button trabajadorButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.opciones);

        adminButton = findViewById(R.id.button);
        clienteButton = findViewById(R.id.button2);
        trabajadorButton = findViewById(R.id.button3);

        adminButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Al hacer clic en el botón ADMINISTRADOR, iniciar MainActivity
                Intent intent = new Intent(MainActivity.this, LoginAdmin.class);
                startActivity(intent);
                finish(); // Cerrar OpcionesActivity
            }
        });

        clienteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Al hacer clic en el botón ADMINISTRADOR, iniciar MainActivity
                Intent intent = new Intent(MainActivity.this, LoginCliente.class);
                startActivity(intent);
                finish(); // Cerrar OpcionesActivity
            }
        });

        trabajadorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Al hacer clic en el botón TRABAJADOR, iniciar MainActivity
                Intent intent = new Intent(MainActivity.this, LoginTrab.class);
                startActivity(intent);
                finish(); // Cerrar OpcionesActivity
            }
        });

        // Agregar onClickListener para CLIENTE y TRABAJADOR si es necesario
    }
}
