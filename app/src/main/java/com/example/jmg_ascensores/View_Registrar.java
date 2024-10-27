package com.example.jmg_ascensores;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class View_Registrar extends AppCompatActivity {

    private Button cliente;
    private Button trabajador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registro_cliente); // Asegúrate de que esto coincida con tu archivo de diseño

        cliente= findViewById(R.id.Cliente); // Reemplaza con la ID real de tu botón
        trabajador= findViewById(R.id.Trabajador);
        if (cliente != null) { // Opcional: Verifica si el botón no es null
            cliente.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("VistaAdmin", "Botón clicado");
                    Intent intent = new Intent(View_Registrar.this, View_ClienteNuevo.class);
                    startActivity(intent);
                }

            });
            trabajador.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("VistaAdmin", "Botón clicado");
                    Intent intent = new Intent(View_Registrar.this, View_RegTrab.class);
                    startActivity(intent);
                }

            });
        } else {
            Log.e("RegistroCliente", "¡El botón es null! Verifica el archivo de diseño.");
        }
    }
}

