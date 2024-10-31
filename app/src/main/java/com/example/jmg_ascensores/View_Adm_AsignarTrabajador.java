package com.example.jmg_ascensores;

import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.sql.Connection;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class View_Adm_AsignarTrabajador extends AppCompatActivity {

    private ListView listTrab;
    private Connection connection;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.asignar_trabajador); // Asegúrate de que esto coincida con tu archivo de diseño
        listTrab = findViewById(R.id.lstTrabs);

        new DB_Connect() {
            @Override
            protected void onPostExecute(Connection conn) {
                connection = conn; // Guardar la conexión para su uso posterior
                if (connection != null) {
                    try {
                        // Ejecutar la consulta para obtener los ascensores del cliente
                        List<Ent_Trab> trabs = new DB_InfoTrab(connection, View_Adm_AsignarTrabajador.this).execute().get();// Agregar todos los ascensores a la lista
                        // Configurar el adaptador
                        Adapter_trab adapter = new Adapter_trab(View_Adm_AsignarTrabajador.this, trabs);
                        listTrab.setAdapter(adapter);
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
