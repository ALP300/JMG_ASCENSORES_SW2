package com.example.jmg_ascensores;

import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.sql.Connection;
import java.util.List;

public class View_Trab_ListaEmpresas extends AppCompatActivity {

    private Connection connection; // La conexión a la base de datos
    private ListView lstEmps;
    private String codTrab;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.trab_empresas_designadas);

        // Obtener referencia al botón
        codTrab = getIntent().getStringExtra("codTrab");
        // Establecer el OnClickListener
         lstEmps = findViewById(R.id.lstBtnEmp);
                    try {
                        // Ejecutar la consulta para obtener los ascensores del cliente
                        List<Ent_Cliente> cliens;
                        cliens = new DB_InfoDetalleClientWhere( View_Trab_ListaEmpresas.this).execute(codTrab).get();
                        // Configurar el adaptador
                        Adapter_BtnEmpresas adapter = new Adapter_BtnEmpresas(View_Trab_ListaEmpresas.this,cliens,codTrab);
                        lstEmps.setAdapter(adapter);
                        Log.d("Database", "LISTA: ");
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
    }
}

