package com.example.jmg_ascensores;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class OpcionesActivity extends AppCompatActivity {

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
                Intent intent = new Intent(OpcionesActivity.this, MainActivity.class);
                startActivity(intent);
                finish(); // Cerrar OpcionesActivity
            }
        });

        // Agregar onClickListener para CLIENTE y TRABAJADOR si es necesario
    }
}
