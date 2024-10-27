package com.example.jmg_ascensores;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

public class DB_Tarea extends AppCompatActivity {

    private EditText nombreTareaInput, descripcionTareaInput;
    private Button guardarTareaButton, registrarTareas;
    private RecyclerView recyclerView;
    private Adapter_Tarea adapter;
    private ArrayList<Ent_TareaItem> tareasList;
    private Connection connection;
    private int codigoMantenimiento;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actividad_tarea);

        nombreTareaInput = findViewById(R.id.nombre_tarea_input);
        descripcionTareaInput = findViewById(R.id.descripcion_tarea_input);
        guardarTareaButton = findViewById(R.id.guardar_tarea_button);
        registrarTareas = findViewById(R.id.registrar_tareas);
        recyclerView = findViewById(R.id.tareas_list);

        // Obtener el código de mantenimiento del intent
        codigoMantenimiento = getIntent().getIntExtra("codigo_mantenimiento", -1);

        tareasList = new ArrayList<>();
        adapter = new Adapter_Tarea(tareasList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        // Listener para guardar tarea localmente
        guardarTareaButton.setOnClickListener(v -> {
            String nombre = nombreTareaInput.getText().toString().trim();
            String descripcion = descripcionTareaInput.getText().toString().trim();

            if (nombre.isEmpty() || descripcion.isEmpty()) {
                Toast.makeText(DB_Tarea.this, "Por favor, completa todos los campos.", Toast.LENGTH_SHORT).show();
                return;
            }

            // Añadir tarea a la lista localmente
            tareasList.add(new Ent_TareaItem(nombre, descripcion));
            adapter.notifyDataSetChanged();

            nombreTareaInput.setText("");
            descripcionTareaInput.setText("");

            Toast.makeText(DB_Tarea.this, "Tarea registrada localmente.", Toast.LENGTH_SHORT).show();
        });

        // Listener para registrar tareas en la base de datos
        registrarTareas.setOnClickListener(v -> {
            Log.d("Debug", "Connection: " + (connection != null ? "Connected" : "Not Connected"));
            Log.d("Debug", "Tareas Count: " + tareasList.size());
            Log.d("Debug", "Codigo Mantenimiento: " + codigoMantenimiento);

            if (connection != null && !tareasList.isEmpty() && codigoMantenimiento != -1) {
                new RegisterTareasTask().execute();
            } else {
                Toast.makeText(DB_Tarea.this, "No hay tareas para registrar o no hay conexión a la base de datos.", Toast.LENGTH_SHORT).show();
            }
        });

        // Conectar a la base de datos
        new ConnectToDatabaseTask() {
            @Override
            protected void onPostExecute(Connection conn) {
                connection = conn;
                if (connection == null) {
                    Toast.makeText(DB_Tarea.this, "Error al conectar con la base de datos.", Toast.LENGTH_LONG).show();
                }
            }
        }.execute();
    }

    private class RegisterTareasTask extends AsyncTask<Void, Void, Boolean> {
        @Override
        protected Boolean doInBackground(Void... voids) {
            if (connection == null) {
                return false; // Return false if connection is null
            }

            try {
                connection.setAutoCommit(false);
                String query = "INSERT INTO tareas (codigo_mantenimiento, nombre, descripcion, estado) VALUES (?, ?, ?, ?)";
                PreparedStatement statement = connection.prepareStatement(query);

                // Añadir todas las tareas al batch
                for (Ent_TareaItem tarea : tareasList) {
                    statement.setInt(1, codigoMantenimiento);
                    statement.setString(2, tarea.getNombre());
                    statement.setString(3, tarea.getDescripcion());
                    statement.setString(4, "pendiente"); // Estado por defecto
                    statement.addBatch();
                }

                statement.executeBatch();
                connection.commit();
                return true;
            } catch (SQLException e) {
                e.printStackTrace();
                try {
                    connection.rollback();
                } catch (SQLException rollbackEx) {
                    rollbackEx.printStackTrace();
                }
                return false;
            }
        }

        @Override
        protected void onPostExecute(Boolean result) {
            if (result) {
                tareasList.clear();
                adapter.notifyDataSetChanged();
                Toast.makeText(DB_Tarea.this, "Todas las tareas han sido registradas.", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(DB_Tarea.this, "Error al registrar las tareas.", Toast.LENGTH_LONG).show();
            }
        }
    }

    private class ConnectToDatabaseTask extends AsyncTask<Void, Void, Connection> {
        @Override
        protected Connection doInBackground(Void... voids) {
            try {
                String url = "jdbc:postgresql://dpg-csf6uqpu0jms73fdsilg-a.oregon-postgres.render.com:5432/db_jmg_m4yg";
                String user = "db_jmg_m4yg_user";
                String password = "uPCFNINR5imA0Ilm4hZfobNOQJm2rgBV";
                return DriverManager.getConnection(url, user, password);
            } catch (SQLException e) {
                e.printStackTrace();
                return null;
            }
        }
    }
}
