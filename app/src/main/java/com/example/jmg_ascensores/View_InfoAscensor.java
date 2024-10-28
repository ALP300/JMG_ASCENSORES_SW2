package com.example.jmg_ascensores;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class View_InfoAscensor extends AppCompatActivity {
    private String codCli; // Código del cliente
    private Connection connection; // La conexión a la base de datos
    private static final String TAG = "Ascensor_Cliente";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.info_asc); // Asegúrate de que este es el layout correcto

        // Probar con un valor fijo para codCli
        codCli = getIntent().getStringExtra("codCli");
        Log.d("Database", "Código del cliente recibido: " + codCli); // Log para verificar el valor

        RecyclerView listView = findViewById(R.id.lstInfAsc);
        listView.setLayoutManager(new LinearLayoutManager(this));

        new DB_Connect() {
            @Override
            protected void onPostExecute(Connection conn) {
                connection = conn; // Guardar la conexión para su uso posterior
                if (connection != null) {
                    try {
                        // Ejecutar la consulta para obtener los ascensores del cliente
                        ArrayList<Ent_Ascensor> items = new ArrayList<>();
                        items = new DB_AscCli(connection, View_InfoAscensor.this).execute(codCli).get();
                        // Configurar el adaptador
                        Adapter_Ascensor adapter = new Adapter_Ascensor(items);
                        listView.setAdapter(adapter);
                        Log.d("Database", "LISTA: " + items);
                        Log.i(TAG, "Ascensores cargados: " + items.size());
                    } catch (ExecutionException e) {
                        Log.e(TAG, "Error en la ejecución: " + e.getMessage());
                    } catch (InterruptedException e) {
                        Log.e(TAG, "Error interrumpido: " + e.getMessage());
                    }
                } else {
                    Log.e(TAG, "Conexión nula");
                }
            }
        }.execute();




    }
}
