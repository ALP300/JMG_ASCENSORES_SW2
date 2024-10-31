package com.example.jmg_ascensores;

import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.sql.Connection;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class View_Adm_HistorialMantenimiento extends AppCompatActivity {
    private ListView lista;
    private Connection connection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.adm_historial_mantenimiento); // Asegúrate de que esto coincida con tu archivo de diseño
        lista = findViewById(R.id.lstHist);
         // Reemplaza con la ID real de tu botón

        new DB_Connect() {
            @Override
            protected void onPostExecute(Connection conn) {
                connection = conn; // Guardar la conexión para su uso posterior
                if (connection != null) {
                    try {
                        // Ejecutar la consulta para obtener los ascensores del cliente
                        List<Ent_Mantenimiento> listMant = new DB_InfoHistorial(connection, View_Adm_HistorialMantenimiento.this).execute().get();// Agregar todos los ascensores a la lista
                        // Configurar el adaptador
                        Adapter_Historial adapter = new Adapter_Historial(View_Adm_HistorialMantenimiento.this, listMant);
                        lista.setAdapter(adapter);

                    } catch (ExecutionException e) {
                        throw new RuntimeException(e);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }.execute();
    }
}
