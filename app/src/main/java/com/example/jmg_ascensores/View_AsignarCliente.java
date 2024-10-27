package com.example.jmg_ascensores;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class View_AsignarCliente extends AppCompatActivity {

    private ListView listTrab;
    private Connection connection;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.asignar_cliente); // Asegúrate de que esto coincida con tu archivo de diseño
        listTrab = findViewById(R.id.lstTrabs);

        new DB_Connect() {
            @Override
            protected void onPostExecute(Connection conn) {
                connection = conn; // Guardar la conexión para su uso posterior
                if (connection != null) {
                    try {
                        // Ejecutar la consulta para obtener los ascensores del cliente
                        List<Ent_Trab> trabs = new DB_InfTrab(connection, View_AsignarCliente.this).execute().get();// Agregar todos los ascensores a la lista
                        // Configurar el adaptador
                        Adapter_trab adapter = new Adapter_trab(View_AsignarCliente.this, trabs);
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
