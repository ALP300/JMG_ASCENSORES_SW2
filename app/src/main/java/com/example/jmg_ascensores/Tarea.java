// Tarea.java
package com.example.jmg_ascensores;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.ArrayList;

public class Tarea extends AppCompatActivity {

    private EditText nombreTareaInput, descripcionTareaInput;
    private Button guardarTareaButton;
    private Button registrarTareas;
    private RecyclerView recyclerView;
    private TareaAdapter adapter;
    private ArrayList<TareaItem> tareasList;
    private Connection connection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actividad_tarea); // Usa el layout creado

        // Enlazamos los campos del layout
        nombreTareaInput = findViewById(R.id.nombre_tarea_input);
        descripcionTareaInput = findViewById(R.id.descripcion_tarea_input);
        guardarTareaButton = findViewById(R.id.guardar_tarea_button);
        registrarTareas = findViewById(R.id.registrar_tareas);
        recyclerView = findViewById(R.id.tareas_list);

        // Inicializamos la lista de tareas
        tareasList = new ArrayList<>();
        adapter = new TareaAdapter(tareasList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        guardarTareaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nombre = nombreTareaInput.getText().toString().trim();
                String descripcion = descripcionTareaInput.getText().toString().trim();

                if (nombre.isEmpty() || descripcion.isEmpty()) {
                    Toast.makeText(Tarea.this, "Por favor, completa todos los campos.", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Registra la tarea en la lista local
                tareasList.add(new TareaItem(nombre, descripcion));

                // Actualiza el RecyclerView
                adapter.notifyDataSetChanged();

                // Limpiar los campos
                nombreTareaInput.setText("");
                descripcionTareaInput.setText("");

                Toast.makeText(Tarea.this, "Tarea registrada con éxito.", Toast.LENGTH_SHORT).show();
            }
        });

        registrarTareas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (connection != null && !tareasList.isEmpty()) {
                    new RegisterTareasTask().execute();  // Ejecuta el AsyncTask para la inserción
                } else {
                    Toast.makeText(Tarea.this, "No hay tareas para registrar o no hay conexión a la base de datos.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Conexión a la base de datos
        new ConnectToDatabaseTask() {
            @Override
            protected void onPostExecute(Connection conn) {
                connection = conn;
                if (connection == null) {
                    Toast.makeText(Tarea.this, "Error al conectar con la base de datos.", Toast.LENGTH_LONG).show();
                }
            }
        }.execute();
    }

    private class RegisterTareasTask extends AsyncTask<Void, Void, Boolean> {
        @Override
        protected Boolean doInBackground(Void... voids) {
            try {
                connection.setAutoCommit(false);
                String query = "INSERT INTO tareas (nombre, descripcion) VALUES (?, ?)";
                PreparedStatement statement = connection.prepareStatement(query);

                for (TareaItem tarea : tareasList) {
                    statement.setString(1, tarea.getNombre());
                    statement.setString(2, tarea.getDescripcion());
                    statement.addBatch();
                }

                statement.executeBatch();
                connection.commit();
                return true;
            } catch (Exception e) {
                try {
                    connection.rollback();
                } catch (Exception rollbackEx) {
                    rollbackEx.printStackTrace();
                }
                e.printStackTrace();
                return false;
            }
        }

        @Override
        protected void onPostExecute(Boolean result) {
            if (result) {
                tareasList.clear();
                adapter.notifyDataSetChanged();
                Toast.makeText(Tarea.this, "Todas las tareas han sido registradas.", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(Tarea.this, "Error al registrar las tareas.", Toast.LENGTH_LONG).show();
            }
        }
    }

    private class ConnectToDatabaseTask extends AsyncTask<Void, Void, Connection> {
        @Override
        protected Connection doInBackground(Void... voids) {
            Connection conn = null;
            try {
                String url = "jdbc:postgresql://jmg_bd_user:Z73pxTACjrn4uzswVY0I4msxc7yMzha8@dpg-cs391pjv2p9s738vcbi0-a.oregon-postgres.render.com:5432/jmg_bd";
                String user = "jmg_bd_user";
                String password = "Z73pxTACjrn4uzswVY0I4msxc7yMzha8";
                conn = DriverManager.getConnection(url, user, password);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return conn;
        }
    }
}
