package com.example.jmg_ascensores;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
public class ClienteNuevo extends AppCompatActivity {
    private Button ascensor;
    private Button mantenimiento;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cliente_nuevo); // Asegúrate de que esto coincida con tu archivo de diseño

        ascensor = findViewById(R.id.ascensor); // Reemplaza con la ID real de tu botón
        mantenimiento = findViewById(R.id.mantenimiento);

        if (ascensor != null) { // Opcional: Verifica si el botón no es null
            ascensor.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("VistaAdmin", "Botón clicado");
                    Intent intent = new Intent(ClienteNuevo.this, Ascensor.class);
                    startActivity(intent);
                }
            });
            mantenimiento.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("VistaAdmin", "Botón clicado");
                    Intent intent = new Intent(ClienteNuevo.this, Mantenimiento.class);
                    startActivity(intent);
                }
            });
        } else {
            Log.e("RegistroCliente", "¡El botón es null! Verifica el archivo de diseño.");
        }
    }
}
