package com.example.jmg_ascensores;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class View_Cli_ConfMant extends AppCompatActivity {
    private String codCli; // Código del cliente
    private Connection connection; // La conexión a la base de datos
    private static final String TAG = "Confirmar Mant";
    private DB_Mantenimiento_Update dbTerminar = new DB_Mantenimiento_Update();
    private Button btnMconf;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cli_conf_mant);

        codCli = getIntent().getStringExtra("codCli");
        Log.d("Database", "Código del cliente recibido: " + codCli);
        btnMconf = findViewById(R.id.btnConfMantx);
        RecyclerView recyclerView = findViewById(R.id.lstMantConf);
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
    private void handleTerminarClick(Adapter_Mant adapter) {
        new Thread(() -> {
            List<Integer> listId = adapter.getListk();
            for (Integer x : listId) {
                dbTerminar.actualizarManten(String.valueOf(x),"Terminado");
            }
            restartActivity();
        }).start();
    }
    private void restartActivity() {
        runOnUiThread(() -> {
            Intent intent = new Intent(this, View_Trab_ListaTareas.class);
            intent.putExtra("codCli", codCli);
            startActivity(intent);
        });
    }


    private void cargarAscensores(Connection connection, String codCli, RecyclerView recyclerView) {
        new DB_InfoMantConf(connection, View_Cli_ConfMant.this) {
            @Override
            protected void onPostExecute(ArrayList<Ent_Mantenimiento> items) {
                if (items != null) {
                    Adapter_Mant adapter = new Adapter_Mant(items);
                    recyclerView.setAdapter(adapter);
                    btnMconf.setOnClickListener(v -> handleTerminarClick(adapter));
                    Log.d("Database", "LISTA: " + items);
                    Log.i(TAG, "Ascensores cargados: " + items.size());
                } else {
                    Log.e(TAG, "No se encontraron ascensores para el cliente.");
                }
            }
        }.execute(codCli);
    }
}
