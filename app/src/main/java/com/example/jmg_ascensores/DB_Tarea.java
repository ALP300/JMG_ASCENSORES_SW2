package com.example.jmg_ascensores;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class DB_Tarea extends AppCompatActivity {

    private EditText nombreTareaInput, descripcionTareaInput;
    private Button guardarTareaButton, registrarTareas;
    private Spinner spnAsc;
    private RecyclerView recyclerView;
    private Adapter_Tarea adapter;
    private ArrayList<Ent_TareaItem> tareasList;
    private ArrayList<Ent_Ascensor> listImp;
    private List<String> listAsc;
    private Connection connection;
    private int codigoMantenimiento;
    private Integer codAsc;
    private String codCli;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.adm_registrar_tarea);

        nombreTareaInput = findViewById(R.id.nombre_tarea_input);
        descripcionTareaInput = findViewById(R.id.descripcion_tarea_input);
        guardarTareaButton = findViewById(R.id.guardar_tarea_button);
        registrarTareas = findViewById(R.id.registrar_tareas);
        recyclerView = findViewById(R.id.tareas_list);
        spnAsc = findViewById(R.id.spnAsc);
        // Obtener el código de mantenimiento del intent
        codigoMantenimiento = getIntent().getIntExtra("codigo_mantenimiento", -1);
        tareasList = new ArrayList<>();
        adapter = new Adapter_Tarea(tareasList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        codCli = getIntent().getStringExtra("codCli");
        Log.d("Database", "Connection: " + codCli);

        // Listener para guardar tarea localmente


        // Listener para registrar tareas en la base de datos


        // Conectar a la base de datos
        new ConnectToDatabaseTask() {
            @Override
            protected void onPostExecute(Connection conn) {
                connection = conn;
                if (connection == null) {
                    Toast.makeText(DB_Tarea.this, "Error al conectar con la base de datos.", Toast.LENGTH_LONG).show();
                }
                try {
                    listImp= new DB_AscCli(connection, DB_Tarea.this).execute(codCli).get();
                    HashMap<String, Integer> ascensorMap = new HashMap<>();
                    listAsc = new ArrayList<>();
                    for (Ent_Ascensor ascens : listImp) {
                        String Text="Ascensor "+ascens.getMarca() + ", "+ ascens.getModel();
                        listAsc.add(Text);
                        ascensorMap.put(Text, ascens.getCodAsc());
                    }
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(DB_Tarea.this, android.R.layout.simple_list_item_1, listAsc);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spnAsc.setAdapter(adapter);
                    spnAsc.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            // Acción al seleccionar un elemento
                            String selectedItem = parent.getItemAtPosition(position).toString();
                            Log.d("Database", "axaxaxaa " + selectedItem);
                            codAsc = ascensorMap.get(selectedItem);
                            Log.d("Database", "numero o.o " + codAsc);
                        }


                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {
                            // No hacer nada
                        }
                    });
                    guardarTareaButton.setOnClickListener(v -> {
                        String nombre = nombreTareaInput.getText().toString().trim();
                        String descripcion = descripcionTareaInput.getText().toString().trim();

                        if (nombre.isEmpty() || descripcion.isEmpty()) {
                            Toast.makeText(DB_Tarea.this, "Por favor, completa todos los campos.", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        // Añadir tarea a la lista localmente
                        tareasList.add(new Ent_TareaItem(nombre, descripcion, codAsc));
                        adapter.notifyDataSetChanged();

                        nombreTareaInput.setText("");
                        descripcionTareaInput.setText("");

                        Toast.makeText(DB_Tarea.this, "Tarea registrada localmente.", Toast.LENGTH_SHORT).show();
                    });



                } catch (ExecutionException e) {
                    throw new RuntimeException(e);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }.execute();

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
    }


    private class RegisterTareasTask extends AsyncTask<Void, Void, Boolean> {
        @Override
        protected Boolean doInBackground(Void... voids) {
            if (connection == null) {
                return false; // Return false if connection is null
            }

            try {
                connection.setAutoCommit(false);
                String query = "INSERT INTO tareas (codigo_mantenimiento, nombre, descripcion, estado, cod_ascensor) VALUES (?, ?, ?, ?, ?)";
                PreparedStatement statement = connection.prepareStatement(query);

                // Añadir todas las tareas al batch
                for (Ent_TareaItem tarea : tareasList) {
                    statement.setInt(1, codigoMantenimiento);
                    statement.setString(2, tarea.getNombre());
                    statement.setString(3, tarea.getDescripcion());
                    statement.setString(4, "pendiente"); // Estado por defecto
                    statement.setInt(5, tarea.getCod_Asc());
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

                // Iniciar la actividad Home_Admin
                Intent intent = new Intent(DB_Tarea.this, Home_Admin.class);
                startActivity(intent);
                finish(); // Finaliza la actividad actual si es necesario
            } else {
                Toast.makeText(DB_Tarea.this, "Error al registrar las tareas.", Toast.LENGTH_LONG).show();
            }
        }
    }

    private class ConnectToDatabaseTask extends AsyncTask<Void, Void, Connection> {
        @Override
        protected Connection doInBackground(Void... voids) {
            try {
                String url = "jdbc:postgresql://dpg-csfb3ue8ii6s739e581g-a.oregon-postgres.render.com:5432/db_jmg_tcnw";
                String user = "db_jmg_user";
                String password = "zALyb2rS9hQ49tB5ijVpYgiMvJajoiL1";
                return DriverManager.getConnection(url, user, password);
            } catch (SQLException e) {
                e.printStackTrace();
                return null;
            }
        }
    }
}
