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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.vista_cliente); // Asegúrate de que este es el diseño correcto
        codCli = getIntent().getStringExtra("codCli");
        btnManProx= findViewById(R.id.btnManProx);
        btnInfo= findViewById(R.id.btnInfAsc);
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
    }



}
