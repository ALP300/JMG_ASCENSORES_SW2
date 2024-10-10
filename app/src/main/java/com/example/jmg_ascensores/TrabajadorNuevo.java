package com.example.jmg_ascensores;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class TrabajadorNuevo extends AppCompatActivity {
    private Button myButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.trabajador_nuevo); // Asegúrate de que coincida con el nombre del layout

        myButton = findViewById(R.id.prueba2); // ID del botón

        if (myButton != null) {
            myButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Redirige a la actividad de registro de trabajador
                    Intent intent = new Intent(TrabajadorNuevo.this, RegistroTrabajadorActivity.class);
                    startActivity(intent);
                }
            });
        }
    }
}
