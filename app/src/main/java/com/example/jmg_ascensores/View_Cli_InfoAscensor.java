package com.example.jmg_ascensores;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.sql.Connection;
import java.util.ArrayList;

public class View_Cli_InfoAscensor extends AppCompatActivity {
    private String codCli; // Código del cliente
    private Connection connection; // La conexión a la base de datos
    private static final String TAG = "Ascensor_Cliente";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.info_asc);

        codCli = getIntent().getStringExtra("codCli");
        Log.d("Database", "Código del cliente recibido: " + codCli);

        RecyclerView recyclerView = findViewById(R.id.lstInfAsc);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Establecer conexión y cargar datos de ascensores
        new DB_Connect() {
            @Override
            protected void onPostExecute(Connection conn) {
                connection = conn;
                if (connection != null) {
                    cargarAscensores(connection, codCli, recyclerView);
                } else {
                    Log.e(TAG, "Conexión nula");
                }
            }
        }.execute();
    }

    private void cargarAscensores(Connection connection, String codCli, RecyclerView recyclerView) {
        new DB_AscCli(connection, View_Cli_InfoAscensor.this) {
            @Override
            protected void onPostExecute(ArrayList<Ent_Ascensor> items) {
                if (items != null) {
                    Adapter_Ascensor adapter = new Adapter_Ascensor(items);
                    recyclerView.setAdapter(adapter);
                    Log.d("Database", "LISTA: " + items);
                    Log.i(TAG, "Ascensores cargados: " + items.size());
                } else {
                    Log.e(TAG, "No se encontraron ascensores para el cliente.");
                }
            }
        }.execute(codCli);
    }
}
