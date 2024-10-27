package com.example.jmg_ascensores;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class VistaCliente extends AppCompatActivity {

    private Button btnManProx,btnInfo;
    private String codCli;
    private Button btnCerrarSesion;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.vista_cliente); // Asegúrate de que este es el diseño correcto
        codCli = getIntent().getStringExtra("codCli");
        btnManProx= findViewById(R.id.btnManProx);
        btnInfo= findViewById(R.id.btnInfAsc);
        btnCerrarSesion = findViewById(R.id.btnCerrarSesion);
        btnManProx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("VistaAdmin", "Botón clicado");
                Intent intent = new Intent(VistaCliente.this, VistaProxMan.class);
                intent.putExtra("codCli",codCli);
                startActivity(intent);
            }
        });
        btnInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(VistaCliente.this, Ascensor_Cliente.class);
                intent.putExtra("codCli",codCli);
                startActivity(intent);
            }
        });

        btnCerrarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Redirigir a la actividad de inicio de sesión
                Intent intent = new Intent(VistaCliente.this,IniciarSesionCliente.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK); // Limpiar pila de actividades
                startActivity(intent);
                finish();  // Cerrar la actividad actual
            }
        });
    }



}
