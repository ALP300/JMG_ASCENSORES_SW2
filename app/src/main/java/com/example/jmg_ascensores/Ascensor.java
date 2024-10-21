package com.example.jmg_ascensores;

import android.content.Intent;
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

public class Ascensor extends AppCompatActivity {

    private EditText marcaInput, modeloInput;
    private Button guardarButton;
    private Button registrarAscensores;
    private RecyclerView recyclerView;
    private AscensorAdapter adapter;
    private ArrayList<AscensorItem> ascensoresList;
    private Connection connection; // La conexión a la base de datos
    private String clienteCodigo; // Almacenará el código del cliente

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ascensor); // Asegúrate de que este sea el layout correcto

        // Enlazamos los campos del layout
        marcaInput = findViewById(R.id.marca_input);
        modeloInput = findViewById(R.id.modelo_input);
        guardarButton = findViewById(R.id.guardar_button);
        registrarAscensores = findViewById(R.id.registrar_ascensores);
        recyclerView = findViewById(R.id.ascensores_list);

        // Recibir el código del cliente de la actividad anterior
        clienteCodigo = getIntent().getStringExtra("codigo_cliente");

        // Inicializamos la lista de ascensores
        ascensoresList = new ArrayList<>();
        adapter = new AscensorAdapter(ascensoresList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        guardarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String marca = marcaInput.getText().toString().trim();
                String modelo = modeloInput.getText().toString().trim();

                if (marca.isEmpty() || modelo.isEmpty()) {
                    Toast.makeText(Ascensor.this, "Por favor, completa todos los campos.", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Aquí deberías realizar la conexión con la base de datos y registrar el ascensor
                if (connection != null) {
                    // Registra el ascensor en la lista local
                    ascensoresList.add(new AscensorItem(marca, modelo));

                    // Actualiza el RecyclerView
                    adapter.notifyDataSetChanged();

                    // Limpiar los campos
                    marcaInput.setText("");
                    modeloInput.setText("");

                    Toast.makeText(Ascensor.this, "Ascensor registrado con éxito.", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(Ascensor.this, "Error: No se puede conectar a la base de datos.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        registrarAscensores.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (connection != null && !ascensoresList.isEmpty()) {
                    // Start the Tarea activity
                    Intent intent = new Intent(Ascensor.this, Mantenimiento.class);
                    startActivity(intent);
                    new RegisterAscensoresTask().execute();  // Executes the AsyncTask for insertion
                } else {
                    Toast.makeText(Ascensor.this, "No hay ascensores para registrar o no hay conexión a la base de datos.", Toast.LENGTH_SHORT).show();
                }
            }
        });


        // Conexión a la base de datos
        new ConnectToDatabaseTask() {
            @Override
            protected void onPostExecute(Connection conn) {
                connection = conn;
                if (connection == null) {
                    Toast.makeText(Ascensor.this, "Error al conectar con la base de datos.", Toast.LENGTH_LONG).show();
                }
            }
        }.execute();
    }

    private class RegisterAscensoresTask extends AsyncTask<Void, Void, Boolean> {
        @Override
        protected Boolean doInBackground(Void... voids) {
            try {
                // Inicia una transacción
                connection.setAutoCommit(false);

                // Prepara la consulta de inserción
                String query = "INSERT INTO ascensores (marca, modelo, codigo_cliente) VALUES (?, ?, ?)";
                PreparedStatement statement = connection.prepareStatement(query);

                // Inserta cada ascensor de la lista
                for (AscensorItem ascensor : ascensoresList) {
                    statement.setString(1, ascensor.getMarca());
                    statement.setString(2, ascensor.getModelo());
                    statement.setString(3, clienteCodigo); // Usa el código del cliente recibido
                    statement.addBatch(); // Añade la operación al batch
                }

                // Ejecuta el batch de inserciones
                statement.executeBatch();

                // Confirma la transacción
                connection.commit();

                return true; // Indica éxito
            } catch (Exception e) {
                try {
                    // En caso de error, revertir los cambios
                    connection.rollback();
                } catch (Exception rollbackEx) {
                    rollbackEx.printStackTrace();
                }
                e.printStackTrace();
                return false; // Indica fallo
            }
        }

        @Override
        protected void onPostExecute(Boolean result) {
            if (result) {
                // Vaciar la lista local de ascensores después de registrarlos
                ascensoresList.clear();
                adapter.notifyDataSetChanged();
                Toast.makeText(Ascensor.this, "Todos los ascensores han sido registrados.", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(Ascensor.this, "Error al registrar los ascensores.", Toast.LENGTH_LONG).show();
            }
        }
    }

    // Clase para conectar a la base de datos
    private class ConnectToDatabaseTask extends AsyncTask<Void, Void, Connection> {
        @Override
        protected Connection doInBackground(Void... voids) {
            Connection conn = null;
            try {
                // URL de conexión a PostgreSQL
                String url = "jdbc:postgresql://dpg-cs391pjv2p9s738vcbi0-a.oregon-postgres.render.com:5432/jmg_bd";
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
