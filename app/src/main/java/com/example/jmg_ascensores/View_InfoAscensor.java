package com.example.jmg_ascensores;

import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

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
        codCli = "cliente91"; // Aquí puedes usar un valor de prueba fijo
        Log.i(TAG, "Código del cliente recibido: " + codCli); // Log para verificar el valor

        ListView listView = findViewById(R.id.lstInfAsc);
        List<Ent_Ascensor> items = new ArrayList<>();

        // Iniciar la conexión a la base de datos
        new DB_Connect() {
            @Override
            protected void onPostExecute(Connection conn) {
                connection = conn; // Guardar la conexión para su uso posterior
                if (connection != null) {
                    try {
                        // Ejecutar la consulta para obtener los ascensores del cliente
                        List<Ent_Ascensor> ascens = new DB_AscCli(connection, View_InfoAscensor.this).execute(codCli).get();
                        items.addAll(ascens); // Agregar todos los ascensores a la lista

                        // Configurar el adaptador
                        Adapter_ascensor adapter = new Adapter_ascensor(View_InfoAscensor.this, items);
                        listView.setAdapter(adapter);
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
