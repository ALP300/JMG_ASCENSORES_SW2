package com.example.jmg_ascensores;

import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.sql.Connection;
import java.util.List;

public class View_Trab_TareasEmpresa extends AppCompatActivity {

    private Connection connection; // La conexión a la base de datos
    private ListView lstEmps;
    private String codTrab;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.trab_tareas_designadas);

        // Obtener referencia al botón
        codTrab = getIntent().getStringExtra("codTrab");
        // Establecer el OnClickListener
         lstEmps = findViewById(R.id.lstBtnEmp);

        new DB_Connect() {
            @Override
            protected void onPostExecute(Connection conn) {
                connection = conn; // Guardar la conexión para su uso posterior
                if (connection != null) {
                    try {
                        // Ejecutar la consulta para obtener los ascensores del cliente
                        List<Ent_Cliente> cliens;
                        cliens = new DB_InfoClientsWhere(connection, View_Trab_TareasEmpresa.this).execute(codTrab).get();
                        // Configurar el adaptador
                        Adapter_BtnEmpresas adapter = new Adapter_BtnEmpresas(View_Trab_TareasEmpresa.this,cliens);
                        lstEmps.setAdapter(adapter);
                        Log.d("Database", "LISTA: ");
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                } else {
                    Log.d("Database", "Conexión nula");
                }
            }
        }.execute();
    }
}

