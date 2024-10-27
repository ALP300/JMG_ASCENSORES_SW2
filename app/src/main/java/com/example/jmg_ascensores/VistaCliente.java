package com.example.jmg_ascensores;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class VistaCliente extends AppCompatActivity {

    private Button btnManProx, btnInfo, btnCerrarSesion;
    private TextView textViewBienvenida; // Agrega el TextView para mostrar la bienvenida
    private String codCli;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vista_cliente); // Asegúrate de que este es el diseño correcto

        codCli = getIntent().getStringExtra("codCli");
        Log.i("VistaCliente", "Código del cliente recibido: " + codCli);
        String nombreEmpresa = getIntent().getStringExtra("nombre_empresa"); // Recibe el nombre de la empresa

        // Configura el TextView para mostrar la bienvenida
        textViewBienvenida = findViewById(R.id.textView9); // Asegúrate de que este es el ID correcto
        if (nombreEmpresa != null) {
            textViewBienvenida.setText("BIENVENIDO CLIENTE: " + nombreEmpresa);
        }

        btnManProx = findViewById(R.id.btnManProx);
        btnInfo = findViewById(R.id.btnInfAsc);
        btnCerrarSesion = findViewById(R.id.btnCerrarSesion);
        btnManProx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("VistaAdmin", "Botón clicado");
                Intent intent = new Intent(VistaCliente.this, VistaProxMan.class);
                intent.putExtra("codCli", codCli);
                startActivity(intent);
            }
        });

        btnInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(VistaCliente.this, Ascensor_Cliente.class);
                intent.putExtra("codCli", codCli);
                Log.i("VistaCliente", "Código del cliente que se enviará: " + codCli);
                startActivity(intent);
            }
        });

        btnCerrarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Redirigir a la actividad de inicio de sesión
                Intent intent = new Intent(VistaCliente.this,MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK); // Limpiar pila de actividades
                startActivity(intent);
                finish();  // Cerrar la actividad actual
            }
        });
    }
}

